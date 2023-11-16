package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.data.ModData.*;
import io.github.beabfc.afkdisplay.config.ConfigManager;
import io.github.beabfc.afkdisplay.placeholders.AfkDisplayPlaceholders;
import io.github.beabfc.afkdisplay.commands.CommandManager;
import io.github.beabfc.afkdisplay.util.*;

// implementing "DedicatedServerModInitalizer" makes it so that this Mod only works in a server environment type.
import net.fabricmc.api.DedicatedServerModInitializer;

public class AfkDisplay implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        AfkDisplayInfo.initModInfo();
        AfkDisplayLogger.initLogger(AFK_MOD_ID);
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
    }
}
