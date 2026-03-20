package com.example.nocommandconfirm;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoCommandConfirmClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("no_command_confirm");

    @Override
    public void onInitializeClient() {
        LOGGER.info("No Command Confirm loaded — command confirmation dialogs are disabled.");
    }
}
