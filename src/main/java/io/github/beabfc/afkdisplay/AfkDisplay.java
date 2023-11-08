package io.github.beabfc.afkdisplay;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;

public class AfkDisplay implements DedicatedServerModInitializer {
    public static final ModContainer CONTAINER = FabricLoader.getInstance().getModContainer("afkdisplay").get();
    public static final ConfigData CONFIG = ConfigManager.getConfig();

    @Override
    public void onInitializeServer() {
        AfkDisplayInfo.build(CONTAINER);
        if (CONFIG.enableAfkCommand) {
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
            String result = player.isAfk() ? CONFIG.afkPlaceholder : "";
            return PlaceholderResult.value(result);
        });
        Placeholders.register(new Identifier("player", "afknamedisplay"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            String result = player.isAfk() ? CONFIG.afkDisplayPlaceholder : "";
            return PlaceholderResult.value(result);
        });
    }
}
