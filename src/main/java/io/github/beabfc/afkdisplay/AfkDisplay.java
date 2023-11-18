package io.github.beabfc.afkdisplay;

import io.github.beabfc.afkdisplay.commands.CommandManager;
import io.github.beabfc.afkdisplay.config.ConfigManager;
import io.github.beabfc.afkdisplay.placeholders.PlaceholderManager;
import io.github.beabfc.afkdisplay.util.AfkDisplayInfo;
import io.github.beabfc.afkdisplay.util.AfkDisplayLogger;
import net.fabricmc.api.DedicatedServerModInitializer;

public class AfkDisplay implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        AfkDisplayLogger.initLogger();
        AfkDisplayInfo.initModInfo();
        AfkDisplayInfo.displayModInfo();
        if (AfkDisplayInfo.isServer()) {
            AfkDisplayLogger.debug("Config Initializing.");
            ConfigManager.initConfig();
            ConfigManager.loadConfig();
            AfkDisplayLogger.debug("Config successful, registering commands.");
            CommandManager.register();
            AfkDisplayLogger.debug("Command registrations done, registering placeholders.");
            PlaceholderManager.registerPlaceholders();
            AfkDisplayLogger.debug("All Placeholders registered.");
        } else {
            AfkDisplayLogger.warn("MOD running in a CLIENT Environment.  Disabling.");
        }
    }
}
