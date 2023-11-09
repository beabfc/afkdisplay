package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.ConfigManager.CONFIG;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;

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
            String result = player.isAfk() ? CONFIG.afkDisplayOptions.afkPlaceholder : "";
            return PlaceholderResult.value(result);
        });
        Placeholders.register(new Identifier("player", "afknamedisplay"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            String result = player.isAfk() ? CONFIG.afkDisplayOptions.afkDisplayPlaceholder : "";
            return PlaceholderResult.value(result);
        });
    }
}
