package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import io.github.kgriff0n.Config;
import io.github.kgriff0n.Lambda;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Random;

import static net.minecraft.server.command.CommandManager.literal;

public class LambdaCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("lambda")
                    .requires(Permissions.require("lambda.admin.info", PermissionLevel.GAMEMASTERS))
                    .executes(context -> info(context.getSource()))
                    .then(literal("info")
                            .requires(Permissions.require("lambda.admin.info", PermissionLevel.GAMEMASTERS))
                            .executes(context -> info(context.getSource()))
                    )
                    .then(literal("reload")
                            .requires(Permissions.require("lambda.admin.reload", PermissionLevel.GAMEMASTERS))
                            .executes(context -> reload(context.getSource()))
                    )
            );
        });
    }

    private static int info(ServerCommandSource source) {
        ServerPlayerEntity player = source.getPlayer();

        int colorIndex = new Random().nextInt(16);
        String colorsCode = "0123456789abcdef";

        player.sendMessage(Text.literal(String.format("§%sLambda λ | %s", colorsCode.charAt(colorIndex), Lambda.VERSION)));
        return Command.SINGLE_SUCCESS;
    }

    private static int reload(ServerCommandSource source) {
        ServerPlayerEntity player = source.getPlayer();

        int colorIndex = new Random().nextInt(16);
        String colorsCode = "0123456789abcdef";

        Config.loadFile();
        player.sendMessage(Text.literal(String.format("§%sLambda configurations have been successfully reloaded", colorsCode.charAt(colorIndex))));

        return Command.SINGLE_SUCCESS;
    }

}
