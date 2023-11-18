package io.github.beabfc.afkdisplay;

import io.github.beabfc.afkdisplay.commands.CommandManager;
import io.github.beabfc.afkdisplay.config.ConfigManager;
import io.github.beabfc.afkdisplay.placeholders.PlaceholderManager;
import io.github.beabfc.afkdisplay.util.AfkDisplayConflicts;
import io.github.beabfc.afkdisplay.util.AfkDisplayInfo;
import io.github.beabfc.afkdisplay.util.AfkDisplayLogger;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import java.util.Collection;

public class AfkDisplay implements DedicatedServerModInitializer {
    static private Collection<String> dpCollection;

    @Override
    public void onInitializeServer() {
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
