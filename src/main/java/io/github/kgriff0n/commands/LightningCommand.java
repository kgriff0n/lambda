package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import java.util.Collection;

import static net.minecraft.commands.Commands.literal;

public class LightningCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("lightning")
                    .requires(Permissions.require("lambda.misc.lightning", PermissionLevel.GAMEMASTERS))
                    .then(Commands.argument("targets", EntityArgument.players())
                            .executes(context -> execute(context.getSource(), EntityArgument.getPlayers(context, "targets")))));
        });
    }

    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets) {

        for (ServerPlayer target : targets) {
            ServerLevel world = target.level();
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
            lightning.setPosRaw(target.getX(), target.getY(), target.getZ());
            world.addFreshEntity(lightning);
        }

        return Command.SINGLE_SUCCESS;
    }
}
