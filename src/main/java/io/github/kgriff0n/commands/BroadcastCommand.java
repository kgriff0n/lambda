package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.literal;

public class BroadcastCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("broadcast")
                    .requires(Permissions.require("lambda.admin.broadcast", 4))
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                            .then(CommandManager.argument("message", StringArgumentType.greedyString())
                                    .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets"), StringArgumentType.getString(context, "message"))))));
        });
    }

    private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, String message) {
        String formattedMessage;

        formattedMessage = message.replace("&", "§");

        for (ServerPlayerEntity target : targets) {
            target.sendMessage(Text.literal(formattedMessage));
        }

        return Command.SINGLE_SUCCESS;
    }
}
