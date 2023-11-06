package io.github.beabfc.afkdisplay;

import com.mojang.brigadier.CommandDispatcher;
//import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import net.minecraft.text.Text;
//import org.w3c.dom.Text;

public class AfkDisplayCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
            CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
                literal("afkdisplay")
                        .requires(Permissions.require("afkdisplay.main", true))
                        .executes(AfkDisplayCommand::about)

                        .then(literal("reload")
                                .requires(Permissions.require("afkdisplay.reload", 3))
                                .executes(AfkDisplayCommand::reload))
                        .then(literal("set")
                                .requires(Permissions.require("afkdisplay.set", 3))
                                .then(argument("player", EntityArgumentType.player())
                                        .executes(ctx -> setAfkDisplay(EntityArgumentType.getPlayer(ctx, "player")))))
                        .then(literal("clear")
                                .requires(Permissions.require("afkdisplay.clear", 3))
                                .then(argument("player", EntityArgumentType.player())
                                        .executes(ctx -> setAfkDisplay(EntityArgumentType.getPlayer(ctx, "player")))))

        // .executes(ctx -> setAfk(ctx.getSource()))
        // .then(argument("player", EntityArgumentType.player())
        // .requires(src ->
        // src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
        // .executes(ctx -> setAfk(EntityArgumentType.getPlayer(ctx, "player")))
        // )
        );

    }

    private static int about(CommandContext<ServerCommandSource> context) {
        for (var text : context.getSource().getEntity() instanceof ServerPlayerEntity ? AfkDisplayInfo.getAbout()
                : AfkDisplayInfo.getAbout()) {
            context.getSource().sendFeedback(() -> text, false);
        }
        return 1;
    }

    private static int reload(CommandContext<ServerCommandSource> context) {
        Config.load("afkdisplay.toml");
        context.getSource().sendFeedback(() -> Text.literal("Reloaded config!"), false);
        return 1;
    }

    // private static int setAfkDisplay(ServerCommandSource src) throws
    // CommandSyntaxException {
    // AfkPlayer player = (AfkPlayer) src.getPlayerOrThrow();
    // player.enableAfk();
    // return 1;
    // }

    private static int setAfkDisplay(ServerPlayerEntity player) {
        AfkPlayer afkPlayer = (AfkPlayer) player;
        afkPlayer.enableAfk();
        return 1;
    }

}
