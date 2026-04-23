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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static net.minecraft.commands.Commands.literal;

public class CoinflipCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("coinflip")
                    .requires(Permissions.require("lambda.misc.coinflip", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), new ArrayList<>()))
                    .then(Commands.argument("targets", EntityArgument.players())
                            .executes(context -> execute(context.getSource(), EntityArgument.getPlayers(context, "targets")))));
        });
    }

    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets) {
        ServerPlayer player = source.getPlayer();
        String side = new Random().nextInt(2) == 0 ? "heads" : "tails";

        player.sendSystemMessage(Component.literal(String.format(Config.coinflipSelf, side)));

        for (ServerPlayer target : targets) {
            if (target != player) {
                target.sendSystemMessage(Component.literal(String.format(Config.coinflipOthers, player.getName(), side)));
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}
