package io.github.beabfc.afkdisplay.commands;

import static io.github.beabfc.afkdisplay.config.ConfigManager.*;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandManager {
    public static void register() {
        if (CONFIG.afkDisplayOptions.enableAfkCommand) {
            CommandRegistrationCallback.EVENT
                    .register((dispatcher, registryAccess, environment) -> AfkCommand.register(dispatcher));
        }
        if (CONFIG.afkDisplayOptions.enableAfkInfoCommand) {
            CommandRegistrationCallback.EVENT
                    .register((dispatcher, registryAccess, environment) -> AfkInfoCommand.register(dispatcher));
        }
        CommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess, environment) -> AfkDisplayCommand.register(dispatcher));
    }
}
