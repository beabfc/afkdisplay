package io.github.beabfc.afkdisplay.data;

public class ConfigData {
    public AfkDisplayOptions afkDisplayOptions = new AfkDisplayOptions();
    public PacketOptions packetOptions = new PacketOptions();
    public PlaceholderOptions PlaceholderOptions = new PlaceholderOptions();
    public PlayerListOptions playerListOptions = new PlayerListOptions();
    public MessageOptions messageOptions = new MessageOptions();

    public static class AfkDisplayOptions {
        public boolean enableAfkCommand;
        public boolean enableAfkInfoCommand;
        public int afkCommandPermissions;
        public int afkInfoCommandPermissions;
        public int afkDisplayCommandPermissions;
        public String afkTimeoutString;
    }

    public static class PacketOptions {
        public int timeoutSeconds;
        public Boolean resetOnMovement;
        public Boolean resetOnLook;
    }

    public static class PlaceholderOptions {
        public String afkPlaceholder;
        public String afkDisplayNamePlaceholderAfk;
        public String afkDisplayNamePlaceholder;
        public String afkDurationPlaceholderFormatting;
        public String afkTimePlaceholderFormatting;
        public String afkReasonPlaceholderFormatting;
    }

    public static class PlayerListOptions {
        public boolean enableListDisplay;
        public String afkPlayerName;
    }

    public static class MessageOptions {
        public boolean enableChatMessages;
        public String wentAfk;
        public String returned;
        public boolean prettyDuration;
        public String defaultReason;
    }
}
