package io.github.beabfc.afkdisplay.config;

import static io.github.beabfc.afkdisplay.data.ModData.*;

import io.github.beabfc.afkdisplay.data.ConfigData;
import io.github.beabfc.afkdisplay.util.AfkDisplayLogger;

import java.io.File;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

import net.fabricmc.loader.api.FabricLoader;

public class ConfigManager {
    public static ConfigData CONFIG = new ConfigData();

    public static void initConfig() {
        CONFIG.afkDisplayOptions.afkDisplayCommandPermissions = 3;
        CONFIG.afkDisplayOptions.afkDisplayPlaceholder = "%player:displayname%";
        CONFIG.afkDisplayOptions.afkDisplayPlaceholderAfk = "<i><gray>[AFK] %player:displayname_unformatted%<r>";
        CONFIG.afkDisplayOptions.afkPlaceholder = "<i><gray>[AFK]<r>";
        CONFIG.afkDisplayOptions.enableAfkCommand = true;
        CONFIG.afkDisplayOptions.enableAfkInfoCommand = true;
        CONFIG.afkDisplayOptions.afkCommandPermissions = 0;
        CONFIG.afkDisplayOptions.afkInfoCommandPermissions = 2;
        CONFIG.packetOptions.resetOnLook = false;
        CONFIG.packetOptions.resetOnMovement = false;
        CONFIG.packetOptions.timeoutSeconds = 180;
        CONFIG.playerListOptions.afkPlayerName = "<i><gray>[AFK] %player:displayname%<r>";
        CONFIG.playerListOptions.enableListDisplay = true;
        CONFIG.playerListOptions.afkUpdateTime = -1;
        CONFIG.messageOptions.enableChatMessages = true;
        CONFIG.messageOptions.wentAfk = "%player:displayname% <yellow>is now AFK<r>";
        CONFIG.messageOptions.returned = "%player:displayname% <yellow>is no longer AFK<r>";
        CONFIG.messageOptions.prettyDuration = true;
        CONFIG.messageOptions.defaultReason = "<gray>poof!<r>";
    }

    public static void loadConfig() {
        File conf = FabricLoader.getInstance().getConfigDir().resolve(AFK_MOD_ID + ".toml").toFile();
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
