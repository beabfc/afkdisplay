package io.github.beabfc.afkdisplay;

import net.fabricmc.api.DedicatedServerModInitializer;

public class AfkDisplay implements DedicatedServerModInitializer {
    public static String MOD_VERSION;
    public static final String MOD_ID = "afkdisplay";
    public static final String MOD_NAME = "AfkDisplay";
    public static final String MOD_AUTHO_STRING = "beabfc, (fork by sakura-ryoko)";

    @Override
    public void onInitializeServer() {
        AfkDisplayInfo.getVersion();
        AfkDisplayLogger.initLogger(MOD_ID);
        ConfigManager.initConfig();
        ConfigManager.loadConfig();
        AfkDisplayLogger.info("Config successful.");
        AfkDisplayPlaceholders.registerAfk();
        AfkDisplayPlaceholders.registerAfkDisplay();
    }
}
