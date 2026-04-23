package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import io.github.kgriff0n.Config;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;

import static net.minecraft.commands.Commands.literal;

public class TrashCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("trash")
                    .requires(Permissions.require("lambda.util.trash", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource())));
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("disposal")
                    .requires(Permissions.require("lambda.util.disposal", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource())));
        });
    }

    private static int execute(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();

        player.openMenu(new SimpleMenuProvider((syncId, inventory, playerEntity) -> ChestMenu.oneRow(syncId, inventory), Component.nullToEmpty(Config.trashTitle)));

        return Command.SINGLE_SUCCESS;
    }
}
