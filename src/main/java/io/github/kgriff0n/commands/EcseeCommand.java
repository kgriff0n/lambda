package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import eu.pb4.sgui.api.gui.SimpleGui;
import io.github.kgriff0n.Config;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class EcseeCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("ecsee")
                    .requires(Permissions.require("lambda.admin.ecsee", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource(), context.getSource().getPlayerOrThrow()))
                    .then(CommandManager.argument("target", EntityArgumentType.player())
                            .executes(context -> execute(context.getSource(), EntityArgumentType.getPlayer(context, "target")))
                    )
            );
        });
    }

    private static int execute(ServerCommandSource source, ServerPlayerEntity target) {
        ServerPlayerEntity player = source.getPlayer();

        SimpleGui targetEnderchest = new SimpleGui(ScreenHandlerType.GENERIC_9X3, player, false);

        for (int i = 0; i < 27; i++) {
            targetEnderchest.setSlotRedirect(i, new Slot(target.getEnderChestInventory(), i, 0, 0));
        }

        targetEnderchest.setTitle(Text.literal(String.format(Config.ecseeTitle, target.getName().getString())));
        player.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN);
        targetEnderchest.open();

        return Command.SINGLE_SUCCESS;
    }
}
