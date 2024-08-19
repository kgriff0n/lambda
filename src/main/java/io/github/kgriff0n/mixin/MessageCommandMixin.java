package io.github.kgriff0n.mixin;

import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

import static io.github.kgriff0n.commands.ReplyCommand.replyList;

@Mixin(MessageCommand.class)
public class MessageCommandMixin {

    @Inject(method = "execute",at = @At("HEAD"))
    private static void getTargets(ServerCommandSource source, Collection<ServerPlayerEntity> targets, SignedMessage message, CallbackInfo ci) {
        if (targets.size() == 1) {

            String sourceName = source.getName();
            String targetName = targets.iterator().next().getName().getString();

            replyList.put(sourceName, targetName);
            replyList.put(targetName, sourceName);
        }
    }
}
