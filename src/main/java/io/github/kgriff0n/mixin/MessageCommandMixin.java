package io.github.kgriff0n.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.commands.MsgCommand;
import net.minecraft.server.level.ServerPlayer;

import static io.github.kgriff0n.commands.ReplyCommand.replyList;

@Mixin(MsgCommand.class)
public class MessageCommandMixin {

    @Inject(method = "sendMessage",at = @At("HEAD"))
    private static void getTargets(CommandSourceStack source, Collection<ServerPlayer> targets, PlayerChatMessage message, CallbackInfo ci) {
        if (targets.size() == 1) {

            String sourceName = source.getTextName();
            String targetName = targets.iterator().next().getName().getString();

            replyList.put(sourceName, targetName);
            replyList.put(targetName, sourceName);
        }
    }
}
