package com.example.nocommandconfirm.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 1.21.6以降、run_command の確認ダイアログは
 * Screen#handleRunCommand が制御している。
 *
 * このメソッドは本来 ConfirmScreen（「コマンドを実行しますか？」画面）を
 * 開くが、キャンセルしてコマンドを直接ネットワークハンドラへ送ることで
 * 確認ダイアログを完全にスキップする。
 *
 * 旧来の ClientCommandSource#hasPermissionLevel は 1.21.4 以前の対象であり
 * 1.21.11 では確認ダイアログとは無関係のため使用しない。
 */
@Mixin(Screen.class)
public class ClientCommandSourceMixin {

    @Inject(method = "handleRunCommand", at = @At("HEAD"), cancellable = true)
    private void skipCommandConfirmation(String command, CallbackInfo ci) {
        ci.cancel();

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            // "/" で始まっている場合は除去してコマンドとして送信
            String cmd = command.startsWith("/") ? command.substring(1) : command;
            client.player.networkHandler.sendCommand(cmd);
        }
    }
}
