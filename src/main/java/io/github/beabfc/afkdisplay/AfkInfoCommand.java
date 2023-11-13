package io.github.beabfc.afkdisplay;

import static io.github.beabfc.afkdisplay.AfkDisplayInfo.*;
import static io.github.beabfc.afkdisplay.ConfigManager.*;
import static net.minecraft.server.command.CommandManager.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import eu.pb4.placeholders.api.TextParserUtils;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class AfkInfoCommand {
        public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
                dispatcher.register(
                                literal("afkinfo")
                                                .requires(Permissions.require("afkdisplay.afkinfo",
                                                                CONFIG.afkDisplayOptions.afkInfoCommandPermissions))
                                                .then(argument("player", EntityArgumentType.player())
                                                                .executes(ctx -> infoAfkPlayer(ctx.getSource(),
                                                                                EntityArgumentType.getPlayer(ctx,
                                                                                                "player"),
                                                                                ctx))));
        }

        private static int infoAfkPlayer(ServerCommandSource src, ServerPlayerEntity player,
                        CommandContext<ServerCommandSource> context) {
                AfkPlayer afkPlayer = (AfkPlayer) player;
                String user = src.getDisplayName().toString();
                String target = player.getEntityName();
                String AfkStatus = getAfkInfoString(afkPlayer, user, target);
                context.getSource().sendFeedback(() -> TextParserUtils.formatText(AfkStatus), false);
                return 1;
        }
}
