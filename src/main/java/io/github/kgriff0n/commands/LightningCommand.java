package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.literal;

public class LightningCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("lightning")
                    .requires(Permissions.require("lambda.misc.lightning", 4))
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                            .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets")))));
        });
    }

    private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets) {

        for (ServerPlayerEntity target : targets) {
            ServerWorld world = target.getServerWorld();
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.setPos(target.getX(), target.getY(), target.getZ());
            world.spawnEntity(lightning);
        }

        return Command.SINGLE_SUCCESS;
    }
}
