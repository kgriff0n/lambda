package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import eu.pb4.sgui.api.gui.SimpleGui;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import static net.minecraft.commands.Commands.literal;

public class EnderChestCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("enderchest")
                    .requires(Permissions.require("lambda.util.enderchest", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource())));
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("ec")
                    .requires(Permissions.require("lambda.util.ec", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource())));
        });
    }

    private static int execute(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();

        SimpleGui enderChest = new SimpleGui(MenuType.GENERIC_9x3, player, false);

        for (int i = 0; i < 27; i++) {
            enderChest.setSlot(i, new Slot(player.getEnderChestInventory(), i, 0, 0));
        }
        enderChest.setTitle(Component.translatable("container.enderchest"));
        player.makeSound(SoundEvents.ENDER_CHEST_OPEN);
        enderChest.open();

        return Command.SINGLE_SUCCESS;
    }
}
