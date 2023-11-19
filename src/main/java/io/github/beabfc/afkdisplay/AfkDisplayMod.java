package io.github.beabfc.afkdisplay;

import java.util.Collection;

import io.github.beabfc.afkdisplay.commands.CommandManager;
import io.github.beabfc.afkdisplay.config.ConfigManager;
import io.github.beabfc.afkdisplay.placeholders.PlaceholderManager;
import io.github.beabfc.afkdisplay.util.AfkDisplayConflicts;
import io.github.beabfc.afkdisplay.util.AfkDisplayInfo;
import io.github.beabfc.afkdisplay.util.AfkDisplayLogger;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class AfkDisplayMod {
    static private Collection<String> dpCollection;

    // Dedicated Server
    public static void initServer() {
        AfkDisplayLogger.initLogger();
        AfkDisplayInfo.initModInfo();
        AfkDisplayInfo.displayModInfo();
        AfkDisplayConflicts.checkMods();
        AfkDisplayLogger.debug("Config Initializing.");
        ConfigManager.initConfig();
        ConfigManager.loadConfig();
        AfkDisplayLogger.debug("Config successful, registering commands.");
        CommandManager.register();
        AfkDisplayLogger.debug("Command registrations done, registering placeholders.");
        PlaceholderManager.registerPlaceholders();
        AfkDisplayLogger.debug("All Placeholders registered, checking datapacks.");
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            dpCollection = server.getDataPackManager().getEnabledNames();
            AfkDisplayConflicts.checkDatapacks(dpCollection);
        });
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> {
            dpCollection = server.getDataPackManager().getEnabledNames();
            AfkDisplayConflicts.checkDatapacks(dpCollection);
        });
    }

    // Generic Mod
    public static void initMain() {
        AfkDisplayLogger.initLogger();
        AfkDisplayInfo.initModInfo();
        AfkDisplayInfo.displayModInfo();
        if (AfkDisplayInfo.isServer()) {
            AfkDisplayConflicts.checkMods();
            AfkDisplayLogger.debug("Config Initializing.");
            ConfigManager.initConfig();
            ConfigManager.loadConfig();
            AfkDisplayLogger.debug("Config successful, registering commands.");
            CommandManager.register();
            AfkDisplayLogger.debug("Command registrations done, registering placeholders.");
            PlaceholderManager.registerPlaceholders();
            AfkDisplayLogger.debug("All Placeholders registered, checking datapacks.");
            ServerLifecycleEvents.SERVER_STARTED.register(server -> {
                dpCollection = server.getDataPackManager().getEnabledNames();
                AfkDisplayConflicts.checkDatapacks(dpCollection);
            });
            ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> {
                dpCollection = server.getDataPackManager().getEnabledNames();
                AfkDisplayConflicts.checkDatapacks(dpCollection);
            });
        } else {
            AfkDisplayLogger.warn("MOD running in a CLIENT Environment.  Disabling.");
        }
    }
}
