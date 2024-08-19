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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static net.minecraft.server.command.CommandManager.literal;

public class CoinflipCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("coinflip")
                    .requires(Permissions.require("lambda.misc.coinflip", 4))
                    .executes(context -> execute(context.getSource(), new ArrayList<>()))
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                            .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayers(context, "targets")))));
        });
    }

    private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets) {
        ServerPlayerEntity player = source.getPlayer();
        String side = new Random().nextInt(2) == 0 ? "heads" : "tails";

        player.sendMessage(Text.literal(String.format(Config.coinflipSelf, side)));

        for (ServerPlayerEntity target : targets) {
            if (target != player) {
                target.sendMessage(Text.literal(String.format(Config.coinflipOthers, player.getName(), side)));
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}
