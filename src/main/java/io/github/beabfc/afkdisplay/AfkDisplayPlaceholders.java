package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.AfkDisplay.*;
import static io.github.beabfc.afkdisplay.ConfigManager.*;

import org.apache.commons.lang3.time.DurationFormatUtils;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

// Renamed the namespace to "MOD_ID" after PatBox's conversation.  He would prefer people use "mod_name" instead of using "player" in their mods.  Using "%afkdisplay:display_name%" also is too many display words, so "name"
public final class AfkDisplayPlaceholders {
    static void registerAfk() {
        Placeholders.register(new Identifier(MOD_ID, "afk"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            Text result = player.isAfk()
                    ? Placeholders.parseText(TextParserUtils.formatText(CONFIG.afkDisplayOptions.afkPlaceholder), ctx)
                    : Text.of("");
            return PlaceholderResult.value(result);
        });
    };

    static void registerAfkDisplayName() {
        Placeholders.register(new Identifier(MOD_ID, "name"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            Text result = player.isAfk()
                    ? Placeholders.parseText(
                            TextParserUtils.formatText(CONFIG.afkDisplayOptions.afkDisplayPlaceholderAfk), ctx)
                    : Placeholders.parseText(TextParserUtils.formatText(CONFIG.afkDisplayOptions.afkDisplayPlaceholder),
                            ctx);
            return PlaceholderResult.value(result);
        });
    };

    static void registerAfkDuration() {
        Placeholders.register(new Identifier(MOD_ID, "duration"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            if (CONFIG.messageOptions.prettyDuration) {
                Text result = player.isAfk()
                        ? TextParserUtils.formatText(DurationFormatUtils.formatDurationWords(Util.getMeasuringTimeMs() -
                                player.afkTimeMs(), true, true))
                        : TextParserUtils.formatText("");
                return PlaceholderResult.value(result);
            } else {
                Text result = player.isAfk()
                        ? TextParserUtils.formatText(DurationFormatUtils.formatDurationHMS(Util.getMeasuringTimeMs() -
                                player.afkTimeMs()))
                        : TextParserUtils.formatText("");
                return PlaceholderResult.value(result);
            }
        });
    };

    static void registerAfkTime() {
        Placeholders.register(new Identifier(MOD_ID, "time"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            Text result = player.isAfk() ? TextParserUtils.formatText(player.afkTimeString())
                    : TextParserUtils.formatText("");
            return PlaceholderResult.value(result);
        });
    };

    static void registerAfkReason() {
        Placeholders.register(new Identifier(MOD_ID, "reason"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            Text result = player.isAfk() ? TextParserUtils.formatText(player.afkReason())
                    : TextParserUtils.formatText("");
            return PlaceholderResult.value(result);
        });
    };

}
