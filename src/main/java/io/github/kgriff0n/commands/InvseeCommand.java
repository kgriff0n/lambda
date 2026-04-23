package io.github.kgriff0n.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
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
import net.minecraft.world.item.Items;
import java.util.List;

import static net.minecraft.commands.Commands.literal;

public class InvseeCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("invsee")
                    .requires(Permissions.require("lambda.admin.invsee", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getPlayerOrException()))
                    .then(Commands.argument("target", EntityArgument.player())
                            .executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "target")))
                    )
            );
        });
    }

    private static int execute(CommandSourceStack source, ServerPlayer target) {
        ServerPlayer player = source.getPlayer();

        SimpleGui targetInventory = new SimpleGui(MenuType.GENERIC_9x6, player, false);

        /* First row */
        targetInventory.setSlot(0, new GuiElementBuilder(Items.PLAYER_HEAD)
                .setProfile(target.getUUID())
                .setName(target.getName())
                .glow()
        );
        targetInventory.setSlot(1, Items.GRAY_STAINED_GLASS_PANE.getDefaultInstance());
        // Armor
        for (int i = 0; i < 4; i++) {
            targetInventory.setSlot(i + 2, new Slot(target.getInventory(), 39 - i, 0, 0));
        }

        targetInventory.setSlot(6, Items.GRAY_STAINED_GLASS_PANE.getDefaultInstance());
        // Offhand
        targetInventory.setSlot(7, new Slot(target.getInventory(), 40, 0, 0));
        targetInventory.setSlot(8, Items.GRAY_STAINED_GLASS_PANE.getDefaultInstance());

        /* Second row */
        //Hotbar
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlot(i + 9, new Slot(target.getInventory(), i, 0, 0));
        }

        /* Third row */
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlot(i + 18, Items.GRAY_STAINED_GLASS_PANE.getDefaultInstance());
        }

        /* Others rows */
        //Inventory
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlot(i + 27, new Slot(target.getInventory(), 9 + i, 0, 0));
        }
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlot(i + 36, new Slot(target.getInventory(), 18 + i, 0, 0));
        }
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlot(i + 45, new Slot(target.getInventory(), 27 + i, 0, 0));
        }


        targetInventory.setTitle(Component.literal(String.format(Config.invseeTitle, target.getName().getString())));
        player.makeSound(SoundEvents.ENDER_CHEST_OPEN);
        targetInventory.open();

        return Command.SINGLE_SUCCESS;
    }
}
