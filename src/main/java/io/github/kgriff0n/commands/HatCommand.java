package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

import static net.minecraft.server.command.CommandManager.literal;

public class HatCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("hat")
                    .requires(Permissions.require("lambda.misc.hat", 4))
                    .executes(context -> execute(context.getSource())));
        });
    }

    private static int execute(ServerCommandSource source) {
        ServerPlayerEntity player = source.getPlayer();

        ItemStack mainHand = player.getMainHandStack();
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);

        player.getInventory().armor.set(EquipmentSlot.HEAD.getEntitySlotId(), mainHand);
        player.setStackInHand(Hand.MAIN_HAND, helmet);

        return Command.SINGLE_SUCCESS;
    }
}
