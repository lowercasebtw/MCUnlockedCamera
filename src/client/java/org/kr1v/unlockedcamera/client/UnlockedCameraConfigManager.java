package org.kr1v.unlockedcamera.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class UnlockedCameraConfigManager {
    private static final Logger log = LogManager.getLogger(UnlockedCameraConfigManager.class);
    private static File configFile;
    private static UnlockedCameraConfig config;
    private static final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();

    private static void prepareConfigFile() {
        if (UnlockedCameraConfigManager.configFile == null) {
            UnlockedCameraConfigManager.configFile = new File(FabricLoader.getInstance().getConfigDir().toString(), "unlockedcamera.json");
        }
    }

    public static void initializeConfig() {
        if (UnlockedCameraConfigManager.config == null) {
            UnlockedCameraConfigManager.config = new UnlockedCameraConfig();
            load();
        }
    }

    public static void save() {
        prepareConfigFile();
        final String jsonString = gson.toJson(config);
        try {
            final FileWriter fileWriter = new FileWriter(UnlockedCameraConfigManager.configFile);
            try {
                fileWriter.write(jsonString);
                fileWriter.close();
            } catch (Throwable t) {
                try {
                    fileWriter.close();
                } catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw t;
            }
        } catch (IOException e) {
            log.error("e: ", e);
        }
        load();
    }

    private static void load() {
        prepareConfigFile();
        try {
            if (!UnlockedCameraConfigManager.configFile.exists()) {
                save();
            } else {
                final BufferedReader br = new BufferedReader(new FileReader(UnlockedCameraConfigManager.configFile));
                final UnlockedCameraConfig parsed = gson.fromJson(br, UnlockedCameraConfig.class);
                if (parsed != null) {
                    UnlockedCameraConfigManager.config = parsed;
                    System.out.println(parsed);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("e: ", e);
        }
    }

    public static UnlockedCameraConfig getConfig() {
        if (UnlockedCameraConfigManager.config == null) {
            UnlockedCameraConfigManager.config = new UnlockedCameraConfig();
        }

        return UnlockedCameraConfigManager.config;
    }
}
