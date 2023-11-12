package io.github.beabfc.afkdisplay;

import static net.minecraft.server.command.CommandManager.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;

public class AfkCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("afk")
                        .requires(Permissions.require("afkdisplay.afk", 0))
                        .executes(ctx -> setAfk(ctx.getSource())));
    }

    private static int setAfk(ServerCommandSource src) throws CommandSyntaxException {
        AfkPlayer player = (AfkPlayer) src.getPlayerOrThrow();
        player.enableAfk();
        return 1;
    }
}
