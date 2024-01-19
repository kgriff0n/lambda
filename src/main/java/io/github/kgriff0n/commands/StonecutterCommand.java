package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class StonecutterCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("stonecutter")
                    .requires(Permissions.require("lambda.util.stonecutter", 4))
                    .executes(context -> execute(context.getSource())));
        });
    }

    private static int execute(ServerCommandSource source) {
        ServerPlayerEntity player = source.getPlayer();

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, playerEntity) -> new StonecutterScreenHandler(syncId, inventory, ScreenHandlerContext.create(player.getWorld(), player.getBlockPos())) {
            @Override
            public boolean canUse(PlayerEntity player) {
                return true;
            }
        }, Text.translatable("container.stonecutter")));

        return Command.SINGLE_SUCCESS;
    }
}
