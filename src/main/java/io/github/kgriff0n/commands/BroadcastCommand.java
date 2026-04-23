package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;
import java.util.Collection;

import static net.minecraft.commands.Commands.literal;

public class BroadcastCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("broadcast")
                    .requires(Permissions.require("lambda.admin.broadcast", PermissionLevel.GAMEMASTERS))
                    .then(Commands.argument("targets", EntityArgument.players())
                            .then(Commands.argument("message", StringArgumentType.greedyString())
                                    .executes(context -> execute(context.getSource(), EntityArgument.getPlayers(context, "targets"), StringArgumentType.getString(context, "message"))))));
        });
    }

    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets, String message) {
        String formattedMessage;

        formattedMessage = message.replace("&", "§");

        for (ServerPlayer target : targets) {
            target.sendSystemMessage(Component.literal(formattedMessage));
        }

        return Command.SINGLE_SUCCESS;
    }
}
