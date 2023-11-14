package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.ConfigManager.*;
import static net.minecraft.server.command.CommandManager.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;

public class AfkCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("afk")
                        .requires(Permissions.require("afkdisplay.afk", 0))
                        .executes(ctx -> setAfk(ctx.getSource(), ""))
                        .then(argument("reason", StringArgumentType.greedyString())
                                .requires(Permissions.require("afkdisplay.afk", 0))
                                .executes(
                                        ctx -> setAfk(ctx.getSource(), StringArgumentType.getString(ctx, "reason")))));
    }

    private static int setAfk(ServerCommandSource src, String reason) throws CommandSyntaxException {
        AfkPlayer player = (AfkPlayer) src.getPlayerOrThrow();
        if (reason == null && CONFIG.messageOptions.defaultReason == null) {
            player.enableAfk("via /afk");
        } else if (reason == null || reason == "") {
            player.enableAfk(CONFIG.messageOptions.defaultReason);
        } else {
            player.enableAfk(reason);
        }

        return 1;
    }
}
