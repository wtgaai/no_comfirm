package com.example.nocommandconfirm.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ClientCommandSourceMixin {

    @Inject(method = "handleRunCommand", at = @At("HEAD"), cancellable = true)
    private static void skipCommandConfirmation(ClientPlayerEntity player, String command, Screen screen, CallbackInfo ci) {
        ci.cancel();
        if (player != null) {
            String cmd = command.startsWith("/") ? command : "/" + command;
            player.networkHandler.sendChatMessage(cmd);
        }
    }
}
