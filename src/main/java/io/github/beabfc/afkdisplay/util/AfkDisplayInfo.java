package io.github.beabfc.afkdisplay.util;

import static io.github.beabfc.afkdisplay.config.ConfigManager.*;
import static io.github.beabfc.afkdisplay.data.ModData.*;
//import static net.fabricmc.loader.impl.FabricLoaderImpl.*;

import io.github.beabfc.afkdisplay.data.AfkPlayerData;

import org.apache.commons.lang3.time.DurationFormatUtils;

import eu.pb4.placeholders.api.TextParserUtils;
//import net.fabricmc.loader.impl.FabricLoaderImpl;
//import net.fabricmc.loader.impl.game.minecraft.McVersion;
//import net.fabricmc.loader.impl.game.minecraft.McVersionLookup;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class AfkDisplayInfo {
    public static void initModInfo() {
        final FabricLoader AFK_INST = FabricLoader.getInstance();
        final ModContainer AFK_CONTAINER = AFK_INST.getModContainer(AFK_MOD_ID).get();
        // McVersion MC_VERSION =
        // McVersionLookup.getVersionExceptClassVersion(AFK_INST.getGameDir());
        // AFK_MC_VERSION = MC_VERSION.getNormalized();
        // AFK_MC_VERSION = "1.20.2";
        // String test = FabricLoaderImpl.provider.getNormalizedGameVersion();
        AFK_MOD_VERSION = AFK_CONTAINER.getMetadata().getVersion().getFriendlyString();
        AFK_MOD_NAME = AFK_CONTAINER.getMetadata().getName();
        AFK_MOD_DESC = AFK_CONTAINER.getMetadata().getDescription();
    }

    public static Text getModInfo() {
        String modInfo1 = AFK_MOD_ID + "-" + AFK_MC_VERSION + "-" + AFK_MOD_VERSION;
        String modInfo2 = "Author: <light_purple>" + AFK_MOD_AUTHO_STRING + "</light_purple>";
        String modInfo3 = "URL: <url:'" + AFK_MOD_URL_RESOURCE + "'>" + AFK_MOD_URL_RESOURCE + "</url>";
        String modInfo4 = "Description: " + AFK_MOD_DESC;
        // I tried combining these in one String, but it wasnt working ...

        Text info = TextParserUtils
                .formatText(modInfo1 + "\n" + modInfo2 + "\n" + modInfo3 + "\n" + modInfo4);
        return info;

    }

    public static String getAfkInfoString(AfkPlayerData afkPlayer, String user, String target) {
        String AfkStatus;
        long duration;
        if (afkPlayer.isAfk()) {
            duration = Util.getMeasuringTimeMs() - afkPlayer.afkTimeMs();
            if (CONFIG.messageOptions.prettyDuration) {
                if (afkPlayer.afkReason() == "") {
                    AfkStatus = "<bold><light_purple>AFK Information:"
                            + "<r>\nPlayer: " + target
                            + "<r>\nAfk Since: <green>" + afkPlayer.afkTimeString() + " (Format:yyyy-MM-dd_HH.mm.ss)"
                            + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationWords(duration, true, true)
                            + "<r>\nReason: none";
                } else {
                    AfkStatus = "<bold><light_purple>AFK Information:"
                            + "<r>\nPlayer: " + target
                            + "<r>\nAfk Since: <green>" + afkPlayer.afkTimeString() + " (Format:yyyy-MM-dd_HH.mm.ss)"
                            + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationWords(duration, true, true)
                            + "<r>\nReason: " + afkPlayer.afkReason();
                }
            } else {
                if (afkPlayer.afkReason() == "") {
                    AfkStatus = "<bold><light_purple>AFK Information:"
                            + "<r>\nPlayer: " + target
                            + "<r>\nAfk Since: <green>" + afkPlayer.afkTimeString() + " (Format:yyyy-MM-dd_HH.mm.ss)"
                            + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationHMS(duration)
                            + "ms (Format:HH:mm:ss)"
                            + "<r>\nReason: none";
                } else {
                    AfkStatus = "<bold><light_purple>AFK Information:"
                            + "<r>\nPlayer: " + target
                            + "<r>\nAfk Since: <green>" + afkPlayer.afkTimeString() + " (Format:yyyy-MM-dd_HH.mm.ss)"
                            + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationHMS(duration)
                            + "ms (Format:HH:mm:ss)"
                            + "<r>\nReason: " + afkPlayer.afkReason();
                }
            }
            AfkDisplayLogger.info(user + " displayed " + target + "'s AFK info.");
        } else {
            AfkStatus = "Player: " + target + "<r>\n ... is not marked as AFK.";
            AfkDisplayLogger.info(user + " attempted to display " + target
                    + "'s AFK time/duration, but they wern't AFK.");
        }
        return AfkStatus;
    }
}
