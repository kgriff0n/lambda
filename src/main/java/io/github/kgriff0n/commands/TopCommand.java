package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.server.command.CommandManager.literal;

public class TopCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("top")
                    .requires(Permissions.require("lambda.util.top", 4))
                    .executes(context -> execute(context.getSource())));
        });
    }

    private static int execute(ServerCommandSource source) {
        ServerPlayerEntity player = source.getPlayer();
        ServerWorld world = player.getServerWorld();
        BlockPos pos = player.getBlockPos();

        int y;
        for (y = 319;
             y >= pos.getY() && world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).isAir();
             y--);
        //empty body

        player.teleport(pos.getX() + 0.5, y + 1, pos.getZ() + 0.5);

        return Command.SINGLE_SUCCESS;
    }
}
