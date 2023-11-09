package io.github.beabfc.afkdisplay;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import net.minecraft.text.Text;

public class AfkDisplayCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("afkdisplay")
                        .requires(src -> src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                        .executes(AfkDisplayCommand::about)

                        .then(literal("reload")
                                .requires(src -> src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                                .executes(AfkDisplayCommand::reload))
                        .then(literal("set")
                                .requires(src -> src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                                .then(argument("player", EntityArgumentType.player())
                                        .executes(ctx -> setAfk(EntityArgumentType.getPlayer(ctx, "player")))))
                        .then(literal("clear")
                                .requires(src -> src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                                .then(argument("player", EntityArgumentType.player())
                                        .executes(ctx -> clearAfk(EntityArgumentType.getPlayer(ctx, "player"))))));

    }

    private static int about(CommandContext<ServerCommandSource> context) {
        for (var text : context.getSource().getEntity() instanceof ServerPlayerEntity ? AfkDisplayInfo.getAbout()
                : AfkDisplayInfo.getAbout()) {
            context.getSource().sendFeedback(() -> text, false);
        }
        return 1;
    }

    private static int reload(CommandContext<ServerCommandSource> context) {
        ConfigManager.reloadConfig();
        context.getSource().sendFeedback(() -> Text.literal("Reloaded config!"), false);
        return 1;
    }

    private static int setAfk(ServerPlayerEntity player) {
        AfkPlayer afkPlayer = (AfkPlayer) player;

        if (afkPlayer.isAfk()) {
            afkPlayer.disableAfk();
        } else {
            afkPlayer.enableAfk();
        }
        return 1;
    }

    private static int clearAfk(ServerPlayerEntity player) {
        AfkPlayer afkPlayer = (AfkPlayer) player;

        afkPlayer.disableAfk();
        return 1;
    }
}
