package io.github.beabfc.afkdisplay.data;

public interface AfkPlayerData {
    boolean isAfk();

    void enableAfk(String reason);
    // void enableAfk();

    void disableAfk();

    long afkTimeMs();

    String afkTimeString();

    String afkReason();

    void updatePlayerList();
}
