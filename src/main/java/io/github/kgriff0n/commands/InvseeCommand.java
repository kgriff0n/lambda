package io.github.kgriff0n.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import io.github.kgriff0n.Config;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.List;

import static net.minecraft.server.command.CommandManager.literal;

public class InvseeCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("invsee")
                    .requires(Permissions.require("lambda.admin.invsee", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getPlayerOrThrow()))
                    .then(CommandManager.argument("target", EntityArgumentType.player())
                            .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayer(context, "target")))
                    )
            );
        });
    }

    private static int execute(ServerCommandSource source, ServerPlayerEntity target) {
        ServerPlayerEntity player = source.getPlayer();

        SimpleGui targetInventory = new SimpleGui(ScreenHandlerType.GENERIC_9X6, player, false);

        /* First row */
        targetInventory.setSlot(0, new GuiElementBuilder(Items.PLAYER_HEAD)
                .setProfile(target.getUuid())
                .setName(target.getName())
                .glow()
        );
        targetInventory.setSlot(1, Items.GRAY_STAINED_GLASS_PANE.getDefaultStack());
        // Armor
        for (int i = 0; i < 4; i++) {
            targetInventory.setSlotRedirect(i + 2, new Slot(target.getInventory(), 39 - i, 0, 0));
        }

        targetInventory.setSlot(6, Items.GRAY_STAINED_GLASS_PANE.getDefaultStack());
        // Offhand
        targetInventory.setSlotRedirect(7, new Slot(target.getInventory(), 40, 0, 0));
        targetInventory.setSlot(8, Items.GRAY_STAINED_GLASS_PANE.getDefaultStack());

        /* Second row */
        //Hotbar
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlotRedirect(i + 9, new Slot(target.getInventory(), i, 0, 0));
        }

        /* Third row */
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlot(i + 18, Items.GRAY_STAINED_GLASS_PANE.getDefaultStack());
        }

        /* Others rows */
        //Inventory
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlotRedirect(i + 27, new Slot(target.getInventory(), 9 + i, 0, 0));
        }
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlotRedirect(i + 36, new Slot(target.getInventory(), 18 + i, 0, 0));
        }
        for (int i = 0; i < 9; i++) {
            targetInventory.setSlotRedirect(i + 45, new Slot(target.getInventory(), 27 + i, 0, 0));
        }


        targetInventory.setTitle(Text.literal(String.format(Config.invseeTitle, target.getName().getString())));
        player.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN);
        targetInventory.open();

        return Command.SINGLE_SUCCESS;
    }
}
