package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import eu.pb4.sgui.api.gui.SimpleGui;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

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

    private static int execute(ServerCommandSource source) {
        ServerPlayerEntity player = source.getPlayer();

        SimpleGui enderChest = new SimpleGui(ScreenHandlerType.GENERIC_9X3, player, false);

        for (int i = 0; i < 27; i++) {
            enderChest.setSlotRedirect(i, new Slot(player.getEnderChestInventory(), i, 0, 0));
        }
        enderChest.setTitle(Text.translatable("container.enderchest"));
        player.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN);
        enderChest.open();

        return Command.SINGLE_SUCCESS;
    }
}
