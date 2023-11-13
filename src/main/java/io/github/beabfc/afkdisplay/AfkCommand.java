package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.ConfigManager.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class AfkCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("afk")
                        .requires(Permissions.require("afkdisplay.afk", 0))
                        .then(CommandManager.argument("reason", StringArgumentType.greedyString()))
                        .executes(ctx -> setAfk(ctx.getSource(), StringArgumentType.getString(ctx, "reason"))));
    }

    private static int setAfk(ServerCommandSource src, String reason) throws CommandSyntaxException {
        AfkPlayer player = (AfkPlayer) src.getPlayerOrThrow();
        if (reason == "" && CONFIG.messageOptions.defaultReason == "") {
            player.enableAfk("");
        } else if (reason == "") {
            player.enableAfk(CONFIG.messageOptions.defaultReason);
        } else {
            player.enableAfk(reason);
        }
        return 1;
    }
}
