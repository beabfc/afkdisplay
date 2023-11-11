package io.github.beabfc.afkdisplay;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;

import static io.github.beabfc.afkdisplay.AfkDisplay.MOD_ID;

import java.io.File;

public class ConfigManager {
    public static ConfigData CONFIG = new ConfigData();

    public static void initConfig() {
        CONFIG.afkDisplayOptions.afkDisplayCommandPermissions = "3";
        CONFIG.afkDisplayOptions.afkDisplayPlaceholder = "%player:displayname%";
        CONFIG.afkDisplayOptions.afkDisplayPlaceholderAfk = "<i><gray>[AFK] %player:displayname_unformatted%<r>";
        CONFIG.afkDisplayOptions.afkPlaceholder = "<i><gray>[AFK]<r>";
        CONFIG.afkDisplayOptions.enableAfkCommand = true;
        CONFIG.packetOptions.resetOnLook = false;
        CONFIG.packetOptions.resetOnMovement = false;
        CONFIG.packetOptions.timeoutSeconds = 180;
        CONFIG.playerListOptions.afkPlayerName = "<i><gray>[AFK] %player:displayname%<r>";
        CONFIG.playerListOptions.enableListDisplay = true;
        CONFIG.messageOptions.enableChatMessages = true;
        CONFIG.messageOptions.wentAfk = "%player:displayname% <yellow>is now AFK<r>";
        CONFIG.messageOptions.returned = "%player:displayname% <yellow>is no longer AFK<r>";
    }

    public static void loadConfig() {
        File conf = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".toml").toFile();
        try {
            if (conf.exists()) {
                CONFIG = new Toml().read(conf).to(ConfigData.class);
            } else {
                AfkDisplayLogger.info("Config not found, creating new file.");
                initConfig();
                conf.createNewFile();
            }
            new TomlWriter().write(CONFIG, conf);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void reloadConfig() {
        AfkDisplayLogger.info("Reloading Config.");
        loadConfig();
    }
}
