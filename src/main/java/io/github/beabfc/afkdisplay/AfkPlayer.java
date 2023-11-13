package io.github.beabfc.afkdisplay;

public interface AfkPlayer {
    boolean isAfk();

    void enableAfk(String reason);
    // void enableAfk();

    void disableAfk();

    long afkTimeMs();

    String afkTimeString();

    String afkReason();
}
