package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.ConfigManager.CONFIG;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.TextParserUtils;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

public class AfkDisplay implements DedicatedServerModInitializer {
    public static final ModContainer CONTAINER = FabricLoader.getInstance().getModContainer("afkdisplay").get();

    @Override
    public void onInitializeServer() {
        AfkDisplayInfo.build(CONTAINER);
        ConfigManager.initConfig();
        ConfigManager.loadConfig();
        if (CONFIG.afkDisplayOptions.enableAfkCommand) {
            CommandRegistrationCallback.EVENT
                    .register((dispatcher, registryAccess, environment) -> AfkCommand.register(dispatcher));
        }
        CommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess, environment) -> AfkDisplayCommand.register(dispatcher));
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
    }
}
