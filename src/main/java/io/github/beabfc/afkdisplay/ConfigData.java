package io.github.beabfc.afkdisplay;

public class ConfigData {
    public AfkDisplayOptions afkDisplayOptions = new AfkDisplayOptions();
    public PacketOptions packetOptions = new PacketOptions();
    public PlayerListOptions playerListOptions = new PlayerListOptions();
    public MessageOptions messageOptions = new MessageOptions();

    public static class AfkDisplayOptions {
        public boolean enableAfkCommand;
        public boolean enableAfkInfoCommand;
        public int afkCommandPermissions;
        public int afkInfoCommandPermissions;
        public int afkDisplayCommandPermissions;
        public String afkPlaceholder;
        public String afkDisplayPlaceholderAfk;
        public String afkDisplayPlaceholder;
    }

    public static class PacketOptions {
        public int timeoutSeconds;
        public Boolean resetOnMovement;
        public Boolean resetOnLook;
    }

    public static class PlayerListOptions {
        public boolean enableListDisplay;
        public String afkPlayerName;
    }

    public static class MessageOptions {
        public boolean enableChatMessages;
        public String wentAfk;
        public String returned;
        public String defaultReason;
    }
}
