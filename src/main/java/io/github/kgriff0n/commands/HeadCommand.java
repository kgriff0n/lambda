package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;

import static net.minecraft.commands.Commands.literal;

public class HeadCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("head")
                    .requires(Permissions.require("lambda.misc.head", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getTextName()))
                    .then(Commands.argument("target", StringArgumentType.word()).suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                            .executes(context -> execute(context.getSource(), StringArgumentType.getString(context, "target")))
                    )
            );
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("skull")
                    .requires(Permissions.require("lambda.misc.skull", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getTextName()))
                    .then(Commands.argument("target", StringArgumentType.word()).suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
                            .executes(context -> execute(context.getSource(), StringArgumentType.getString(context, "target")))
                    )
            );
        });
    }

    private static int execute(CommandSourceStack source, String target) {
        ServerPlayer player = source.getPlayer();
        MinecraftServer server = source.getServer();
        source.getServer().getCommands().performPrefixedCommand(server.createCommandSourceStack(), "give " + player.getName().getString() + " player_head[profile={name:\"" + target + "\"}] 1");

        return Command.SINGLE_SUCCESS;
    }
}
