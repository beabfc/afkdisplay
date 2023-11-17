package io.github.beabfc.afkdisplay.commands;

import io.github.beabfc.afkdisplay.util.AfkDisplayInfo;
import io.github.beabfc.afkdisplay.util.AfkDisplayLogger;
import static io.github.beabfc.afkdisplay.config.ConfigManager.*;
import io.github.beabfc.afkdisplay.config.ConfigManager;
import io.github.beabfc.afkdisplay.data.AfkPlayerData;
import static net.minecraft.server.command.CommandManager.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import eu.pb4.placeholders.api.TextParserUtils;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class AfkDisplayCommand {
        public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
                dispatcher.register(
                                literal("afkdisplay")
                                                .requires(Permissions.require("afkdisplay.afkdisplay",
                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                .executes(ctx -> afkAbout(ctx.getSource(), ctx))
                                                .then(literal("reload")
                                                                .requires(Permissions.require(
                                                                                "afkdisplay.afkdisplay.reload",
                                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                                .executes(ctx -> afkReload(ctx.getSource(), ctx)))
                                                .then(literal("set")
                                                                .requires(Permissions.require(
                                                                                "afkdisplay.afkdisplay.set",
                                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                                .then(argument("player", EntityArgumentType.player())
                                                                                .executes((ctx) -> setAfk(
                                                                                                ctx.getSource(),
                                                                                                EntityArgumentType
                                                                                                                .getPlayer(ctx, "player"),
                                                                                                ""))
                                                                                .then(argument("reason",
                                                                                                StringArgumentType
                                                                                                                .greedyString())
                                                                                                .requires(Permissions
                                                                                                                .require(
                                                                                                                                "afkdisplay.afkdisplay.set",
                                                                                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                                                                .executes((ctx) -> setAfk(
                                                                                                                ctx.getSource(),
                                                                                                                EntityArgumentType
                                                                                                                                .getPlayer(ctx, "player"),
                                                                                                                StringArgumentType
                                                                                                                                .getString(ctx, "reason"))))))
                                                .then(literal("clear")
                                                                .requires(Permissions.require(
                                                                                "afkdisplay.afkdisplay.clear",
                                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                                .then(argument("player", EntityArgumentType.player())
                                                                                .executes(ctx -> clearAfk(
                                                                                                ctx.getSource(),
                                                                                                EntityArgumentType
                                                                                                                .getPlayer(ctx,
                                                                                                                                "player")))))
                                                .then(literal("info")
                                                                .requires(Permissions.require(
                                                                                "afkdisplay.afkdisplay.info",
                                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                                .then(argument("player", EntityArgumentType.player())
                                                                                .executes(ctx -> infoAfkPlayer(
                                                                                                ctx.getSource(),
                                                                                                EntityArgumentType
                                                                                                                .getPlayer(ctx,
                                                                                                                                "player"),
                                                                                                ctx))))
                                                .then(literal("update")
                                                                .requires(Permissions.require(
                                                                                "afkdisplay.afkdisplay.update",
                                                                                CONFIG.afkDisplayOptions.afkDisplayCommandPermissions))
                                                                .then(argument("player", EntityArgumentType.player())
                                                                                .executes(ctx -> updatePlayer(
                                                                                                ctx.getSource(),
                                                                                                EntityArgumentType
                                                                                                                .getPlayer(ctx,
                                                                                                                                "player"),
                                                                                                ctx)))));
        }

        private static int afkAbout(ServerCommandSource src, CommandContext<ServerCommandSource> context) {
                Text ModInfo = AfkDisplayInfo.getModInfoText();
                context.getSource().sendFeedback(() -> ModInfo, false);
                return 1;
        }

        private static int afkReload(ServerCommandSource src, CommandContext<ServerCommandSource> context) {
                ConfigManager.reloadConfig();
                context.getSource().sendFeedback(() -> Text.literal("Reloaded config!"), false);
                return 1;
        }

        private static int setAfk(ServerCommandSource src, ServerPlayerEntity player, String reason) {
                AfkPlayerData afkPlayer = (AfkPlayerData) player;
                String user = src.getName();
                String target = player.getEntityName();
                if (reason == null && CONFIG.messageOptions.defaultReason == null) {
                        afkPlayer.enableAfk("via /afkdisplay set");
                        AfkDisplayLogger.info(user + " set player " + target + " as AFK");
                } else if (reason == null || reason == "") {
                        afkPlayer.enableAfk(CONFIG.messageOptions.defaultReason);
                        AfkDisplayLogger.info(user + " set player " + target + " as AFK for reason: "
                                        + CONFIG.messageOptions.defaultReason);
                } else {
                        afkPlayer.enableAfk(reason);
                        AfkDisplayLogger.info(user + " set player " + target + " as AFK for reason: " + reason);
                }
                return 1;
        }

        private static int clearAfk(ServerCommandSource src, ServerPlayerEntity player) {
                AfkPlayerData afkPlayer = (AfkPlayerData) player;
                String user = src.getName();
                String target = player.getEntityName();
                afkPlayer.disableAfk();
                AfkDisplayLogger.info(user + " cleared player " + target + " from AFK");
                return 1;
        }

        private static int infoAfkPlayer(ServerCommandSource src, ServerPlayerEntity player,
                        CommandContext<ServerCommandSource> context) {
                AfkPlayerData afkPlayer = (AfkPlayerData) player;
                String user = src.getName();
                String target = player.getEntityName();
                String AfkStatus = AfkDisplayInfo.getAfkInfoString(afkPlayer, user, target);
                context.getSource().sendFeedback(() -> TextParserUtils.formatText(AfkStatus), false);
                return 1;
        }

        private static int updatePlayer(ServerCommandSource src, ServerPlayerEntity player,
                        CommandContext<ServerCommandSource> context) {
                String user = src.getName();
                String target = player.getEntityName();
                AfkPlayerData afkPlayer = (AfkPlayerData) player;
                afkPlayer.updatePlayerList();
                context.getSource().sendFeedback(() -> Text.literal("Updating player list entry for " + target + ""),
                                false);
                AfkDisplayLogger.info(user + " updated player list entry for " + target + "");
                return 1;
        }
}
