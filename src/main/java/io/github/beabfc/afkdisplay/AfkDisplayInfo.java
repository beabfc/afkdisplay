package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.AfkDisplay.*;

import org.apache.commons.lang3.time.DurationFormatUtils;

import eu.pb4.placeholders.api.TextParserUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class AfkDisplayInfo {
    public static void getVersion() {
        final ModContainer CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
        MOD_VERSION = CONTAINER.getMetadata().getVersion().getFriendlyString();
    }

    public static Text getModInfo() {
        String ModInfo1 = MOD_ID + "-" + MOD_VERSION;
        String ModInfo2 = "Author: <green>" + MOD_AUTHO_STRING + "<r>";
        Text info = TextParserUtils.formatText(ModInfo1 + "\n" + ModInfo2);
        return info;
    }

    public static String getAfkInfoString(AfkPlayer afkPlayer, String user, String target) {
        String AfkStatus;
        long duration;
        if (afkPlayer.isAfk()) {
            duration = Util.getMeasuringTimeMs() - afkPlayer.afkTimeMs();
            if (afkPlayer.afkReason() == "") {
                AfkStatus = "Player: " + target + "AFK Information--\nAfk Since: <green>" + afkPlayer.afkTimeString()
                        + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationHMS(duration)
                        + "<r>\nReason: <none>"
                        + "<r>.";
            } else {
                AfkStatus = "Player: " + target + "AFK Information--\nAfk Since: <green>" + afkPlayer.afkTimeString()
                        + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationHMS(duration)
                        + "<r>\nReason: " + afkPlayer.afkReason()
                        + "<r>.";
            }
            AfkDisplayLogger.info(user + " displayed " + target + "'s AFK time/duration.");
        } else {
            AfkStatus = "Player: " + target + " is not marked as AFK.";
            AfkDisplayLogger.info(user + " attempted to display " + target
                    + "'s AFK time/duration, but they aren't AFK.");
        }
        return AfkStatus;
    }
}
