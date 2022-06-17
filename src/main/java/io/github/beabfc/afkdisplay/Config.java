package io.github.beabfc.afkdisplay;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;


public class Config {
    public int timeoutSeconds = 180;
    public boolean resetOnMovement = false;
    public boolean resetOnLook = false;
    public String afkColor = "gray";
    public String afkPrefix = "[AFK] ";

    public static Config load(String configName) {
        try {
            File configFile = FabricLoader.getInstance().getConfigDir().resolve(configName).toFile();
            //noinspection ResultOfMethodCallIgnored
            configFile.createNewFile();
            Config config = new Toml().read(configFile).to(Config.class);
            new TomlWriter().write(config, configFile);
            return config;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
