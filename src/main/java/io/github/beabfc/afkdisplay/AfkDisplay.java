package io.github.beabfc.afkdisplay;

import io.github.beabfc.afkdisplay.config.ConfigManager;
import io.github.beabfc.afkdisplay.placeholders.AfkDisplayPlaceholders;
import io.github.beabfc.afkdisplay.commands.CommandManager;
import io.github.beabfc.afkdisplay.util.*;

import net.fabricmc.api.DedicatedServerModInitializer;

public class AfkDisplay implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        AfkDisplayLogger.initLogger();
        AfkDisplayInfo.initModInfo();
        AfkDisplayInfo.displayModInfo();
        if (AfkDisplayInfo.isServer()) {
            AfkDisplayLogger.debug("Config Initalizing.");
            ConfigManager.initConfig();
            ConfigManager.loadConfig();
            CommandManager.register();
            AfkDisplayLogger.debug("Config successful, registering placeholders.");
            AfkDisplayPlaceholders.registerAfk();
            AfkDisplayPlaceholders.registerAfkDisplayName();
            AfkDisplayPlaceholders.registerAfkDuration();
            AfkDisplayPlaceholders.registerAfkTime();
            AfkDisplayPlaceholders.registerAfkReason();
            AfkDisplayLogger.debug("All Placeholders registerd.");
        } else {
            AfkDisplayLogger.error("MOD running in a CLIENT Environment.  Disabling config.");
        }
    }
}
