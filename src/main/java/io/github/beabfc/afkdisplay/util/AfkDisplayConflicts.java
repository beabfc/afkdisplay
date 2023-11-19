package io.github.beabfc.afkdisplay.util;

import java.util.Collection;
import java.util.Iterator;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;

public class AfkDisplayConflicts {
    public static boolean checkMods() {
        String modTarget;
        String modVer;
        String modName;
        ModMetadata modData;
        boolean modCheck = true;

        // Check for svrutil --> /afk command primarily, the rest is ok
        modTarget = "svrutil";
        if (FabricLoader.getInstance().isModLoaded(modTarget)) {
            modData = FabricLoader.getInstance().getModContainer(modTarget).get().getMetadata();
            modVer = modData.getVersion().getFriendlyString();
            modName = modData.getName();
            AfkDisplayLogger.warn(modName + "-" + modVer
                    + " has been found, please verify that the /afk command is disabled under config/svrutil/commands.json.");
            modCheck = false;
        }

        // Check for antilogout --> /afk command, and changes timeout behavior's
        // (Remove)
        modTarget = "antilogout";
        if (FabricLoader.getInstance().isModLoaded(modTarget)) {
            modData = FabricLoader.getInstance().getModContainer(modTarget).get().getMetadata();
            modVer = modData.getVersion().getFriendlyString();
            modName = modData.getName();
            AfkDisplayLogger.warn(modName + "-" + modVer
                    + " has been found, please remove this mod to avoid AFK timeout confusion.");
            modCheck = false;
        }

        // Check for sessility --> changes timeout behavior's (Remove)
        modTarget = "sessility";
        if (FabricLoader.getInstance().isModLoaded(modTarget)) {
            modData = FabricLoader.getInstance().getModContainer(modTarget).get().getMetadata();
            modVer = modData.getVersion().getFriendlyString();
            modName = modData.getName();
            AfkDisplayLogger.warn(modName + "-" + modVer
                    + " has been found, please remove this mod to avoid AFK timeout confusion.");
            modCheck = false;
        }
        // Check for playtime-tracker --> changes timeout behavior's (Remove)
        modTarget = "playtime-tracker";
        if (FabricLoader.getInstance().isModLoaded(modTarget)) {
            modData = FabricLoader.getInstance().getModContainer(modTarget).get().getMetadata();
            modVer = modData.getVersion().getFriendlyString();
            modName = modData.getName();
            AfkDisplayLogger.warn(modName + "-" + modVer
                    + " has been found, please remove this mod to avoid AFK timeout/player list confusion.");
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
