package org.kr1v.unlockedcamera.client;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class UnlockedCameraConfigScreen implements ModMenuApi {

    public static ConfigBuilder getConfigBuilder() {

        new UnlockedCameraConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(MinecraftClient.getInstance().currentScreen)
                .setTitle(Text.of("Unlocked camera - general"));

        builder.setSavingRunnable(UnlockedCameraConfigManager::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));


        general.addEntry(entryBuilder.startBooleanToggle(Text.of("Invert movement"), UnlockedCameraConfigManager.getConfig().shouldInvertMovement)
                .setDefaultValue(false)
                .setTooltip(Text.of("When looking upside down, will invert movement. Not recommended, will also invert strafing"))
                .setSaveConsumer(newValue -> UnlockedCameraConfigManager.getConfig().shouldInvertMovement = newValue)
                .build());


        general.addEntry(entryBuilder.startBooleanToggle(Text.of("Invert movement swimming"), UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming)
                .setDefaultValue(true)
                .setTooltip(Text.of("When looking upside down and swimming, will invert movement. Is recommended, very intuitive"))
                .setSaveConsumer(newValue -> UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.of("Invert mouse"), UnlockedCameraConfigManager.getConfig().shouldInvertMouse)
                .setDefaultValue(true)
                .setTooltip(Text.of("When looking upside down, will invert horizontal mouse movement. Is recommended, very intuitive"))
                .setSaveConsumer(newValue -> UnlockedCameraConfigManager.getConfig().shouldInvertMouse = newValue)
                .build());

        return builder;

    }
}