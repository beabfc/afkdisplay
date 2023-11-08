package io.github.beabfc.afkdisplay;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;

public class ConfigManager {
    private static String configName = "afkdisplay.toml";
    private static Config config = null;
    private static ConfigData configData = null;

    public static ConfigData getConfig() {
        if (config == null) {
            if (configData == null) {
                configData = loadConfig();
            }
            config = new Config();
        }
        return configData;
    }

    public static ConfigData loadConfig() {
        ConfigData configData = null;
        File conf = null;
        try {
            conf = FabricLoader.getInstance().getConfigDir().resolve(configName).toFile();
            if (conf.exists() == false) {
                conf.createNewFile();
            }
            configData = new Toml().read(conf).to(ConfigData.class);
            // ConfigData configData;
            new TomlWriter().write(configData, conf);
            return configData;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
