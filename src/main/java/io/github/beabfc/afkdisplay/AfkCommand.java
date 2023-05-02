package io.github.beabfc.afkdisplay;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class AfkCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            literal("afk")
                .executes(ctx -> setAfk(ctx.getSource()))
                .then(argument("player", EntityArgumentType.player())
                    .requires(src -> src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                    .executes(ctx -> setAfk(EntityArgumentType.getPlayer(ctx, "player")))
                ));

    }

    private static int setAfk(ServerCommandSource src) throws CommandSyntaxException {
        AfkPlayer player = (AfkPlayer) src.getPlayerOrThrow();
        player.enableAfk();
        return 1;
    }

    private static int setAfk(ServerPlayerEntity player) {
        AfkPlayer afkPlayer = (AfkPlayer) player;
        afkPlayer.enableAfk();
        return 1;
    }

}
