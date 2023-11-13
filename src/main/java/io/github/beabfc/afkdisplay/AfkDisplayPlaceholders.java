package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.ConfigManager.*;

import org.apache.commons.lang3.time.DurationFormatUtils;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public final class AfkDisplayPlaceholders {
    static void registerAfk() {
        Placeholders.register(new Identifier("player", "afk"), (ctx, arg) -> {
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

    static void registerAfkDisplay() {
        Placeholders.register(new Identifier("player", "afkdisplayname"), (ctx, arg) -> {
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
        // long now = Util.getMeasuringTimeMs();
        Placeholders.register(new Identifier("player", "afkduration"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            Text result = player.isAfk()
                    ? TextParserUtils.formatText(DurationFormatUtils.formatDurationHMS(Util.getMeasuringTimeMs() -
                            player.afkTimeMs()))
                    : TextParserUtils.formatText("");
            return PlaceholderResult.value(result);
        });
    };

    static void registerAfkTime() {
        Placeholders.register(new Identifier("player", "afktime"), (ctx, arg) -> {
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
        Placeholders.register(new Identifier("player", "afkreason"), (ctx, arg) -> {
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
