package io.github.beabfc.afkdisplay;

import io.github.beabfc.afkdisplay.ConfigData.*;

public final class Config {
    public final ConfigData configData = null;

    public boolean enableAfkCommand() {
        return configData.enableAfkCommand;
    }

    // FIXME:
    // public int afkDisplayCommandPermissions() {
    // return configData.afkDisplayCommandPermissions;
    // }

    public String afkPlaceholder() {
        return configData.afkPlaceholder;
    }

    public String afkDisplayPlaceholder() {
        return configData.afkDisplayPlaceholder;
    }

    public PacketOptions packetOptions() {
        return configData.packetOptions;
    }

    public PlayerListOptions playerListOptions() {
        return configData.playerListOptions;
    }

    public MessageOptions messageOptions() {
        return configData.messageOptions;
    }
}
