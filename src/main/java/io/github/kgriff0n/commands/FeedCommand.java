package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import io.github.kgriff0n.Config;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;

import static net.minecraft.commands.Commands.literal;

public class FeedCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("feed")
                    .requires(Permissions.require("lambda.admin.feed", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getPlayerOrException()))
                    .then(Commands.argument("target", EntityArgument.player())
                            .executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "target")))
                    )
            );
        });
    }

    private static int execute(CommandSourceStack source, ServerPlayer target) {
        ServerPlayer player = source.getPlayer();

        target.getFoodData().setFoodLevel(20);
        target.getFoodData().setSaturation(20);

        if (player == target) {
            player.sendSystemMessage(Component.nullToEmpty(Config.feedSelf));
        } else {
            player.sendSystemMessage(Component.nullToEmpty(String.format(Config.feedOthers, target.getName())));
            target.sendSystemMessage(Component.nullToEmpty(Config.feedSelf));
        }
        return Command.SINGLE_SUCCESS;
    }
}
