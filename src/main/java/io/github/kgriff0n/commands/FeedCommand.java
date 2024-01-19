package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import io.github.kgriff0n.Config;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class FeedCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("feed")
                    .requires(Permissions.require("lambda.admin.feed", 4))
                    .executes(context -> execute(context.getSource(), context.getSource().getPlayerOrThrow()))
                    .then(CommandManager.argument("target", EntityArgumentType.player())
                            .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayer(context, "target")))
                    )
            );
        });
    }

    private static int execute(ServerCommandSource source, ServerPlayerEntity target) {
        ServerPlayerEntity player = source.getPlayer();

        target.getHungerManager().setFoodLevel(20);
        target.getHungerManager().setSaturationLevel(20);

        if (player == target) {
            player.sendMessage(Text.of(Config.feedSelf));
        } else {
            player.sendMessage(Text.of(String.format(Config.feedOthers, target.getEntityName())));
            target.sendMessage(Text.of(Config.feedSelf));
        }
        return Command.SINGLE_SUCCESS;
    }
}
