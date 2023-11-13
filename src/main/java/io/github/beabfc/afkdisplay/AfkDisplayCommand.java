package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.ConfigManager.*;
import static net.minecraft.server.command.CommandManager.*;

import org.apache.commons.lang3.time.DurationFormatUtils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import eu.pb4.placeholders.api.TextParserUtils;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class AfkDisplayCommand {
        public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
                dispatcher.register(literal("afkdisplay")
                                .requires(Permissions.require("afkdisplay.about",
                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                .executes(ctx -> afkAbout(ctx.getSource(), ctx))
                                .then(literal("reload")
                                                .requires(Permissions.require("afkdisplay.reload",
                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                .executes(ctx -> afkReload(ctx.getSource(), ctx)))
                                .then(literal("set")
                                                .requires(Permissions.require("afkdisplay.set",
                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                .then(argument("player", EntityArgumentType.player())
                                                                .executes(ctx -> setAfk(ctx.getSource(),
                                                                                EntityArgumentType.getPlayer(ctx,
                                                                                                "player")))))
                                .then(literal("clear")
                                                .requires(Permissions.require("afkdisplay.clear",
                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                .then(argument("player", EntityArgumentType.player())
                                                                .executes(ctx -> clearAfk(ctx.getSource(),
                                                                                EntityArgumentType.getPlayer(ctx,
                                                                                                "player")))))
                                .then(literal("get")
                                                .requires(Permissions.require("afkdisplay.get",
                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                .then(argument("player", EntityArgumentType.player())
                                                                .executes(ctx -> getAfkPlayer(ctx.getSource(),
                                                                                EntityArgumentType.getPlayer(ctx,
                                                                                                "player"),
                                                                                ctx))))
                                .then(literal("update")
                                                .requires(Permissions.require("afkdisplay.update",
                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                .then(argument("player", EntityArgumentType.player())
                                                                .executes(ctx -> updatePlayer(ctx.getSource(),
                                                                                EntityArgumentType.getPlayer(ctx,
                                                                                                "player"))))));
        }

        private static int afkAbout(ServerCommandSource src, CommandContext<ServerCommandSource> context) {
                Text ModInfo = AfkDisplayInfo.getModInfo();
                context.getSource().sendFeedback(() -> ModInfo, false);
                return 1;
        }

        private static int afkReload(ServerCommandSource src, CommandContext<ServerCommandSource> context) {
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

        private static int getAfkPlayer(ServerCommandSource src, ServerPlayerEntity player,
                        CommandContext<ServerCommandSource> context) {
                AfkPlayer afkPlayer = (AfkPlayer) player;
                String user = src.getDisplayName().toString();
                String target = player.getEntityName();
                String AfkStatus;
                long duration;
                if (afkPlayer.isAfk()) {
                        duration = Util.getMeasuringTimeMs() - afkPlayer.afkTimeMs();
                        AfkStatus = "Player: " + target + "\nAfk Since: <green>" + afkPlayer.afkTimeString()
                                        + "<r>\nDuration: <green>" + DurationFormatUtils.formatDurationHMS(duration)
                                        + "<r>.";
                        AfkDisplayLogger.info(user + " displayed " + target + "'s AFK time/duration.");
                } else {
                        AfkStatus = "Player: " + target + " is not marked as AFK.";
                        AfkDisplayLogger.info(user + " attempted to display " + target
                                        + "'s AFK time/duration, but they aren't AFK.");
                }
                context.getSource().sendFeedback(() -> TextParserUtils.formatText(AfkStatus), false);
                return 1;
        }

        private static int updatePlayer(ServerCommandSource src, ServerPlayerEntity player) {
                String user = src.getDisplayName().toString();
                String target = player.getEntityName();
                src.getServer()
                                .getPlayerManager()
                                .sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME,
                                                player));
                AfkDisplayLogger.info(user + " updated player list entry for " + target + "");
                return 1;
        }
}
