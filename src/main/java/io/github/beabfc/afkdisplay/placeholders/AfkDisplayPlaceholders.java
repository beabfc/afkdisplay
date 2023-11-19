package io.github.beabfc.afkdisplay.placeholders;

import static io.github.beabfc.afkdisplay.config.ConfigManager.*;
import static io.github.beabfc.afkdisplay.data.ModData.*;

import org.apache.commons.lang3.time.DurationFormatUtils;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.TextParserUtils;
import io.github.beabfc.afkdisplay.data.AfkPlayerData;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

// Renamed the namespace to "AFK_MOD_ID" after PatBox's conversation.  He would prefer people use "mod_name" instead of using "player" in their mods.  Using "%afkdisplay:display_name%" also is too many display words, so "name"
public final class AfkDisplayPlaceholders {
    public static void registerAfk() {
        Placeholders.register(new Identifier(AFK_MOD_ID, "afk"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayerData player = (AfkPlayerData) ctx.player();
            assert player != null;
            Text result = player.isAfk()
                    ? Placeholders.parseText(TextParserUtils.formatTextSafe(CONFIG.PlaceholderOptions.afkPlaceholder),
                            ctx)
                    : Text.of("");
            return PlaceholderResult.value(result);
        });
    };

    public static void registerAfkDisplayName() {
        Placeholders.register(new Identifier(AFK_MOD_ID, "name"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayerData player = (AfkPlayerData) ctx.player();
            assert player != null;
            Text result = player.isAfk()
                    ? Placeholders.parseText(
                            TextParserUtils.formatTextSafe(CONFIG.PlaceholderOptions.afkDisplayNamePlaceholderAfk), ctx)
                    : Placeholders.parseText(
                            TextParserUtils.formatTextSafe(CONFIG.PlaceholderOptions.afkDisplayNamePlaceholder),
                            ctx);
            return PlaceholderResult.value(result);
        });
    };

    public static void registerAfkDuration() {
        Placeholders.register(new Identifier(AFK_MOD_ID, "duration"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayerData player = (AfkPlayerData) ctx.player();
            assert player != null;
            if (CONFIG.messageOptions.prettyDuration) {
                Text result = player.isAfk()
                        ? TextParserUtils.formatTextSafe(
                                CONFIG.PlaceholderOptions.afkDurationPlaceholderFormatting
                                        + DurationFormatUtils.formatDurationWords(Util.getMeasuringTimeMs() -
                                                player.afkTimeMs(), true, true)
                                        + "<r>")
                        : TextParserUtils.formatTextSafe("");
                return PlaceholderResult.value(result);
            } else {
                Text result = player.isAfk()
                        ? TextParserUtils.formatTextSafe(CONFIG.PlaceholderOptions.afkDurationPlaceholderFormatting
                                + DurationFormatUtils.formatDurationHMS(Util.getMeasuringTimeMs() -
                                        player.afkTimeMs())
                                + "<r>")
                        : TextParserUtils.formatTextSafe("");
                return PlaceholderResult.value(result);
            }
        });
    };

    public static void registerAfkTime() {
        Placeholders.register(new Identifier(AFK_MOD_ID, "time"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayerData player = (AfkPlayerData) ctx.player();
            assert player != null;
            Text result = player.isAfk()
                    ? TextParserUtils
                            .formatTextSafe(
                                    CONFIG.PlaceholderOptions.afkTimePlaceholderFormatting + player.afkTimeString()
                                            + "<r>")
                    : TextParserUtils.formatTextSafe("");
            return PlaceholderResult.value(result);
        });
    };

    public static void registerAfkReason() {
        Placeholders.register(new Identifier(AFK_MOD_ID, "reason"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayerData player = (AfkPlayerData) ctx.player();
            assert player != null;
            Text result = player.isAfk()
                    ? TextParserUtils
                            .formatTextSafe(
                                    CONFIG.PlaceholderOptions.afkReasonPlaceholderFormatting + player.afkReason()
                                            + "<r>")
                    : TextParserUtils.formatTextSafe("");
            return PlaceholderResult.value(result);
        });
    };
}
