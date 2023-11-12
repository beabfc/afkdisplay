package io.github.beabfc.afkdisplay;

import static net.minecraft.server.command.CommandManager.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class AfkDisplayCommand {
        public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
                dispatcher.register(
                                literal("afkdisplay")
                                                .requires(src -> src.hasPermissionLevel(
                                                                src.getServer().getOpPermissionLevel()))
                                                .executes(AfkDisplayCommand::about)
                                                .then(literal("reload")
                                                                // .requires(src ->
                                                                // src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                                                                .executes(AfkDisplayCommand::reload))
                                                .then(literal("set")
                                                                // .requires(src ->
                                                                // src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                                                                .then(argument("player", EntityArgumentType.player())
                                                                                .executes(ctx -> setAfk(ctx.getSource(),
                                                                                                EntityArgumentType
                                                                                                                .getPlayer(ctx, "player")))))
                                                .then(literal("clear")
                                                                // .requires(src ->
                                                                // src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                                                                .then(argument("player", EntityArgumentType.player())
                                                                                .executes(ctx -> clearAfk(
                                                                                                ctx.getSource(),
                                                                                                EntityArgumentType
                                                                                                                .getPlayer(ctx, "player")))))
                                                .then(literal("fix")
                                                                // .requires(src ->
                                                                // src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                                                                .then(argument("player", EntityArgumentType.player())
                                                                                .executes(ctx -> fixPlayer(
                                                                                                ctx.getSource(),
                                                                                                EntityArgumentType
                                                                                                                .getPlayer(ctx, "player"))))));
        }

        private static int about(CommandContext<ServerCommandSource> context) {
                Text ModInfo = AfkDisplayInfo.getModInfo();
                context.getSource().sendFeedback(() -> ModInfo, false);
                return 1;
        }

        private static int reload(CommandContext<ServerCommandSource> context) {
                ConfigManager.reloadConfig();
                context.getSource().sendFeedback(() -> Text.literal("Reloaded config!"), false);
                return 1;
        }

        private static int setAfk(ServerCommandSource src, ServerPlayerEntity player) {
                AfkPlayer afkPlayer = (AfkPlayer) player;
                String user = src.getDisplayName().toString();
                String target = player.getEntityName();

                afkPlayer.enableAfk();
                AfkDisplayLogger.info(user + " set player " + target + " as AFK");
                return 1;
        }

        private static int clearAfk(ServerCommandSource src, ServerPlayerEntity player) {
                AfkPlayer afkPlayer = (AfkPlayer) player;
                String user = src.getDisplayName().toString();
                String target = player.getEntityName();
                afkPlayer.disableAfk();
                AfkDisplayLogger.info(user + " cleared player " + target + " from AFK");
                return 1;
        }

        private static int fixPlayer(ServerCommandSource src, ServerPlayerEntity player) {
                String user = src.getDisplayName().toString();
                String target = player.getEntityName();
                src.getServer()
                                .getPlayerManager()
                                .sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME,
                                                player));
                AfkDisplayLogger.info(user + " fixed player list entry for " + target + "");
                return 1;
        }
}
