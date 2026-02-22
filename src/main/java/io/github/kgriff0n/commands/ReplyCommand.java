package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.HashMap;

import static net.minecraft.server.command.CommandManager.literal;

public class ReplyCommand {

    public static HashMap<String, String> replyList = new HashMap<>();

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("r")
                    .requires(Permissions.require("lambda.util.reply", PermissionLevel.GAMEMASTERS))
                    .then(CommandManager.argument("message", StringArgumentType.greedyString())
                            .executes(context -> execute(context.getSource(), StringArgumentType.getString(context, "message")))));
        });
    }

    private static int execute(ServerCommandSource source, String message) {

        String targetName = replyList.get(source.getName());
        source.getServer().getCommandManager().parseAndExecute(source, String.format("msg %s %s", targetName, message));

        return Command.SINGLE_SUCCESS;
    }
}
