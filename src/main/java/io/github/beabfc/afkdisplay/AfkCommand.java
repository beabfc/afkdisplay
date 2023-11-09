package io.github.beabfc.afkdisplay;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import static net.minecraft.server.command.CommandManager.literal;

public class AfkCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("afk")
                        .executes(ctx -> setAfk(ctx.getSource())));
    }

    private static int setAfk(ServerCommandSource src) throws CommandSyntaxException {
        AfkPlayer player = (AfkPlayer) src.getPlayerOrThrow();
        player.enableAfk();
        return 1;
    }
}
