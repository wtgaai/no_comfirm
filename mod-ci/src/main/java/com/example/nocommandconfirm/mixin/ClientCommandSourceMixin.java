package com.example.nocommandconfirm.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ClientCommandSourceMixin {

    @Inject(method = "handleRunCommand", at = @At("HEAD"), cancellable = true)
    private void skipCommandConfirmation(String command, CallbackInfo ci) {
        ci.cancel();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            // sendChatMessage は "/" で始まるとコマンドとして送信される
            String cmd = command.startsWith("/") ? command : "/" + command;
            player.sendChatMessage(cmd, null);
        }
    }
}
