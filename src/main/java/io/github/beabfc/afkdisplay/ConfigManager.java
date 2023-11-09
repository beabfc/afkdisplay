package io.github.beabfc.afkdisplay;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;

public class ConfigManager {
    public static ConfigData CONFIG = new ConfigData();

    public static void initConfig() {
        CONFIG.afkDisplayOptions.afkDisplayCommandPermissions = "3";
        CONFIG.afkDisplayOptions.afkDisplayPlaceholder = "<i><gray>[AFK] %player:displayname%</i></gray>";
        CONFIG.afkDisplayOptions.afkPlaceholder = "[AFK]";
        CONFIG.afkDisplayOptions.enableAfkCommand = true;
        CONFIG.packetOptions.resetOnLook = false;
        CONFIG.packetOptions.resetOnMovement = false;
        CONFIG.packetOptions.timeoutSeconds = 180;
        CONFIG.playerListOptions.afkColor = "gray";
        CONFIG.playerListOptions.afkPlayerName = "[AFK] %player:displayname%";
        CONFIG.playerListOptions.enableListDisplay = true;
        CONFIG.messageOptions.enableChatMessages = true;
        CONFIG.messageOptions.messageColor = "yellow";
        CONFIG.messageOptions.wentAfk = "%player:displayname% is now AFK";
        CONFIG.messageOptions.returned = "%player:displayname% is no longer AFK";
    }

    public static void loadConfig() {
        File conf = FabricLoader.getInstance().getConfigDir().resolve("afkdisplay.toml").toFile();
        try {
            if (conf.exists()) {
                CONFIG = new Toml().read(conf).to(ConfigData.class);
            } else {
                initConfig();
                conf.createNewFile();
            }
            new TomlWriter().write(CONFIG, conf);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void reloadConfig() {
        loadConfig();
    }
}
