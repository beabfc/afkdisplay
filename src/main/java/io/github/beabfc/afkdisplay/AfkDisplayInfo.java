package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.AfkDisplay.*;

import org.apache.commons.lang3.time.DurationFormatUtils;

import eu.pb4.placeholders.api.TextParserUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class AfkDisplayInfo {
    public static void getVersionInfo() {
        final ModContainer CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
        MOD_VERSION = CONTAINER.getMetadata().getVersion().getFriendlyString();
    }

    public static Text getModInfo() {
        String modInfo1 = MOD_ID + "-" + MOD_VERSION;
        String modInfo2 = "Author: <light_purple>" + MOD_AUTHO_STRING + "</light_purple>";
        String modInfo3 = "URL: <blue>" + MOD_URL_RESOURCE + "</blue>";
        // I tried using <url:[value]> but it wasnt working ...

        Text info = TextParserUtils.formatText(modInfo1 + "\n" + modInfo2 + "\n" + modInfo3);
        return info;

    }

    public static String getAfkInfoString(AfkPlayer afkPlayer, String user, String target) {
        String AfkStatus;
        long duration;
        if (afkPlayer.isAfk()) {
            duration = Util.getMeasuringTimeMs() - afkPlayer.afkTimeMs();
            // AfkDisplayLogger.info("[DB] getAfkInfoString() duration (diff MS): " +
            // duration + " .");
            if (afkPlayer.afkReason() == "") {
                AfkStatus = "<bold>AFK Information:"
                        + "<r>\nPlayer: " + target + "<r>\nAfk Since: <green>" + afkPlayer.afkTimeString()
                        + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationHMS(duration) + "ms"
                        + "<r>\nReason: none"
                        + "<r>";
            } else {
                AfkStatus = "<bold>AFK Information:"
                        + "<r>\nPlayer: " + target + "<r>\nAfk Since: <green>" + afkPlayer.afkTimeString()
                        + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationHMS(duration) + "ms"
                        + "<r>\nReason: " + afkPlayer.afkReason()
                        + "<r>";
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
