package org.kr1v.unlockedcamera.client;

import com.terraformersmc.modmenu.config.ModMenuConfigManager;
import net.fabricmc.api.ClientModInitializer;

public class UnlockedcameraClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        UnlockedCameraConfigManager.initializeConfig();
    }
}
