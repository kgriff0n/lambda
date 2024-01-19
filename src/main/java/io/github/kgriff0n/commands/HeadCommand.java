package io.github.kgriff0n.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.ScoreHolderArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class HeadCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("head")
                    .requires(Permissions.require("lambda.misc.head", 4))
                    .executes(context -> execute(context.getSource(), context.getSource().getName()))
                    .then(CommandManager.argument("target", StringArgumentType.word()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                            .executes(context -> execute(context.getSource(), StringArgumentType.getString(context, "target")))
                    )
            );
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("skull")
                    .requires(Permissions.require("lambda.misc.skull", 4))
                    .executes(context -> execute(context.getSource(), context.getSource().getName()))
                    .then(CommandManager.argument("target", StringArgumentType.word()).suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                            .executes(context -> execute(context.getSource(), StringArgumentType.getString(context, "target")))
                    )
            );
        });
    }

    private static int execute(ServerCommandSource source, String target) {
        ServerPlayerEntity player = source.getPlayer();

        ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD);
        playerHead.getOrCreateNbt().putString("SkullOwner", target);
        player.giveItemStack(playerHead);

        return Command.SINGLE_SUCCESS;
    }
}
