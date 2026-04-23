package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import static net.minecraft.commands.Commands.literal;

public class HatCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("hat")
                    .requires(Permissions.require("lambda.misc.hat", PermissionLevel.GAMEMASTERS))
                    .executes(context -> execute(context.getSource())));
        });
    }

    private static int execute(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();

        ItemStack mainHand = player.getMainHandItem();
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        player.setItemSlot(EquipmentSlot.HEAD, mainHand);
        player.setItemInHand(InteractionHand.MAIN_HAND, helmet);

        return Command.SINGLE_SUCCESS;
    }
}
