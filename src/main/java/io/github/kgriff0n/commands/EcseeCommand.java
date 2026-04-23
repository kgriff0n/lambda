package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import eu.pb4.sgui.api.gui.SimpleGui;
import io.github.kgriff0n.Config;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import static net.minecraft.commands.Commands.literal;

public class EcseeCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("ecsee")
                    .requires(Permissions.require("lambda.admin.ecsee", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getPlayerOrException()))
                    .then(Commands.argument("target", EntityArgument.player())
                            .executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "target")))
                    )
            );
        });
    }

    private static int execute(CommandSourceStack source, ServerPlayer target) {
        ServerPlayer player = source.getPlayer();

        SimpleGui targetEnderchest = new SimpleGui(MenuType.GENERIC_9x3, player, false);

        for (int i = 0; i < 27; i++) {
            targetEnderchest.setSlot(i, new Slot(target.getEnderChestInventory(), i, 0, 0));
        }

        targetEnderchest.setTitle(Component.literal(String.format(Config.ecseeTitle, target.getName().getString())));
        player.makeSound(SoundEvents.ENDER_CHEST_OPEN);
        targetEnderchest.open();

        return Command.SINGLE_SUCCESS;
    }
}
