package io.github.beabfc.afkdisplay.util;

import java.util.Collection;
import java.util.Iterator;

import net.fabricmc.loader.api.FabricLoader;

public class AfkDisplayConflicts {
    public static boolean checkMods() {
        String modVer;
        boolean modCheck = true;

        // Check for SvrUtil --> /afk command
        if (FabricLoader.getInstance().isModLoaded("svrutil")) {
            modVer = FabricLoader.getInstance().getModContainer("svrutil").get().getMetadata().getVersion()
                    .getFriendlyString();
            AfkDisplayLogger.warn("SrvUtil-" + modVer
                    + " has been found, please verify that the /afk command is disabled under config/svrutil/commands.json.");
            modCheck = false;
        }
        return modCheck;
    }

    public static boolean checkDatapacks(Collection<String> dpCollection) {
        boolean dpCheck = true;
        // Check for any datapacks matching with "afk"
        final Iterator<String> iterator = dpCollection.iterator();
        for (; iterator.hasNext();) {
            String dpString = iterator.next();
            if (dpString.contains("afk") || dpString.contains("Afk") || dpString.contains("AFK")) {
                AfkDisplayLogger.warn(
                        "Possible conflict found with datapack: " + dpString + " -- please remove/disable it.");
                dpCheck = false;
            }
        }
        return dpCheck;
    }
}
