package io.github.beabfc.afkdisplay;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class Config {
    public boolean enableAfkCommand = true;
    public String afkPlaceholder = "[AFK]";
    public String afkDisplayPlaceholder = "<i><gray>[AFK] %player:displayname%</i></gray>";
    public PacketOptions packetOptions = new PacketOptions();
    public PlayerListOptions playerListOptions = new PlayerListOptions();
    public MessageOptions messageOptions = new MessageOptions();

    public static class PacketOptions {
        public int timeoutSeconds = 180;
        public boolean resetOnMovement = false;
        public boolean resetOnLook = false;

    }

    public static class PlayerListOptions {
        public boolean enableListDisplay = true;
        public String afkColor = "gray";
        public String afkPlayerName = "[AFK] %player:displayname%";
    }

    public static class MessageOptions {
        public boolean enableChatMessages = true;
        public String messageColor = "yellow";
        public String wentAfk = "%player:displayname% is now AFK";
        public String returned = "%player:displayname% is no longer AFK";
    }

    public static Config load(String configName) {
        try {
            File configFile = FabricLoader.getInstance().getConfigDir().resolve(configName).toFile();
            // noinspection ResultOfMethodCallIgnored
            configFile.createNewFile();
            Config config = new Toml().read(configFile).to(Config.class);
            new TomlWriter().write(config, configFile);
            return config;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
