package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;

import static net.minecraft.commands.Commands.literal;

public class EnchantingTableCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("enchanting")
                    .requires(Permissions.require("lambda.util.enchanting", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource())));
        });
    }

    private static int execute(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();

        player.openMenu(new SimpleMenuProvider((syncId, inventory, playerEntity) -> new EnchantmentMenu(syncId, inventory, ContainerLevelAccess.create(player.level(), player.blockPosition())) {
            @Override
            public boolean stillValid(Player player) {
                return true;
            }
        }, Component.translatable("container.enchant")));

        return Command.SINGLE_SUCCESS;
    }
}
