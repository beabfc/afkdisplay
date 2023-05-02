package io.github.beabfc.afkdisplay;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;

public class AfkDisplay implements DedicatedServerModInitializer {
    public static final Config CONFIG = Config.load("afkdisplay.toml");

    @Override
    public void onInitializeServer() {
        if (CONFIG.enableAfkCommand) {
            CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> AfkCommand.register(dispatcher));
        }

        Placeholders.register(new Identifier("player", "afk"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            AfkPlayer player = (AfkPlayer) ctx.player();
            assert player != null;
            String result = player.isAfk() ? CONFIG.afkPlaceholder : "";
            return PlaceholderResult.value(result);
        });
    }
}
