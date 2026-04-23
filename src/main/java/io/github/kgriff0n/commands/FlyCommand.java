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

public class FlyCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("fly")
                    .requires(Permissions.require("lambda.admin.fly", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getPlayerOrException()))
                    .then(Commands.argument("target", EntityArgument.player())
                            .executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "target")))
                    )
            );
        });
    }

    private static int execute(CommandSourceStack source, ServerPlayer target) {
        ServerPlayer player = source.getPlayer();

        if (target.getAbilities().mayfly) {
            target.getAbilities().mayfly = false;
            target.getAbilities().flying = false;
            target.sendSystemMessage(Component.literal(Config.flyDisabledSelf));
        } else {
            target.getAbilities().mayfly = true;
            target.sendSystemMessage(Component.literal(Config.flyEnabledSelf));
        }
        target.onUpdateAbilities();

        if (target != player) {
            player.sendSystemMessage(target.getAbilities().mayfly
                    ? Component.literal(String.format(Config.flyEnabledOthers, target.getName()))
                    : Component.literal(String.format(Config.flyDisabledOthers, target.getName())));
        }

        return Command.SINGLE_SUCCESS;
    }
}
