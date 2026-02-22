package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import io.github.kgriff0n.Config;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class FlyCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("fly")
                    .requires(Permissions.require("lambda.admin.fly", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getPlayerOrThrow()))
                    .then(CommandManager.argument("target", EntityArgumentType.player())
                            .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayer(context, "target")))
                    )
            );
        });
    }

    private static int execute(ServerCommandSource source, ServerPlayerEntity target) {
        ServerPlayerEntity player = source.getPlayer();

        if (target.getAbilities().allowFlying) {
            target.getAbilities().allowFlying = false;
            target.getAbilities().flying = false;
            target.sendMessage(Text.literal(Config.flyDisabledSelf));
        } else {
            target.getAbilities().allowFlying = true;
            target.sendMessage(Text.literal(Config.flyEnabledSelf));
        }
        target.sendAbilitiesUpdate();

        if (target != player) {
            player.sendMessage(target.getAbilities().allowFlying
                    ? Text.literal(String.format(Config.flyEnabledOthers, target.getName()))
                    : Text.literal(String.format(Config.flyDisabledOthers, target.getName())));
        }

        return Command.SINGLE_SUCCESS;
    }
}
