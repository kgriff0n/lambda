package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;

import static net.minecraft.commands.Commands.literal;

public class TopCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("top")
                    .requires(Permissions.require("lambda.util.top", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource())));
        });
    }

    private static int execute(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        ServerLevel world = player.level().getLevel();
        BlockPos pos = player.blockPosition();

        int y;
        for (y = 319;
             y >= pos.getY() && world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).isAir();
             y--);
        //empty body

        player.randomTeleport(pos.getX() + 0.5, y + 1, pos.getZ() + 0.5, false);

        return Command.SINGLE_SUCCESS;
    }
}
