package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.ConfigManager.*;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class AfkDisplay implements DedicatedServerModInitializer {
    public static String MOD_VERSION;
    public static final String MOD_ID = "afkdisplay";
    public static final String MOD_NAME = "AfkDisplay";
    public static final String MOD_AUTHO_STRING = "beabfc, (forked by) sakura-ryoko";
    public static final String MOD_URL_RESOURCE = "https://github.com/beabfc/afkdisplay";

    @Override
    public void onInitializeServer() {
        AfkDisplayInfo.getVersionInfo();
        AfkDisplayLogger.initLogger(MOD_ID);
        ConfigManager.initConfig();
        ConfigManager.loadConfig();
        AfkDisplayLogger.info("Config successful.");
        if (CONFIG.afkDisplayOptions.enableAfkCommand) {
            CommandRegistrationCallback.EVENT
                    .register((dispatcher, registryAccess, environment) -> AfkCommand.register(dispatcher));
        }
        if (CONFIG.afkDisplayOptions.enableAfkInfoCommand) {
            CommandRegistrationCallback.EVENT
                    .register((dispatcher, registryAccess, environment) -> AfkInfoCommand.register(dispatcher));
        }
        CommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess, environment) -> AfkDisplayCommand.register(dispatcher));
        AfkDisplayPlaceholders.registerAfk();
        AfkDisplayPlaceholders.registerAfkDisplayName();
        AfkDisplayPlaceholders.registerAfkDuration();
        AfkDisplayPlaceholders.registerAfkTime();
        AfkDisplayPlaceholders.registerAfkReason();
    }
}
