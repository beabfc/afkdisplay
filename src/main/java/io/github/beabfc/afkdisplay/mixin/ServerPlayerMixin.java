package io.github.beabfc.afkdisplay.mixin;

import static io.github.beabfc.afkdisplay.ConfigManager.*;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.TextParserUtils;
//import io.github.beabfc.afkdisplay.AfkDisplayLogger;
import io.github.beabfc.afkdisplay.AfkPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin extends Entity implements AfkPlayer {
    @Shadow
    @Final
    public MinecraftServer server;
    public ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
    @Unique
    private boolean isAfk;
    private long afkTimeMs;
    private String afkTimeString;
    private String afkReason;

    public ServerPlayerMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    public boolean isAfk() {
        return this.isAfk;
    }

    public long afkTimeMs() {
        return this.afkTimeMs;
    }

    public String afkTimeString() {
        return this.afkTimeString;
    }

    public String afkReason() {
        return this.afkReason;
    }

    public void enableAfk(String reason) {
        if (isAfk())
            return;
        setAfkTime();
        if (reason == null && CONFIG.messageOptions.defaultReason == null) {
            setAfkReason("<red>none");
        } else if (reason == null || reason == "") {
            setAfkReason("<red>none");
            sendAfkMessage(Placeholders.parseText(TextParserUtils.formatText(CONFIG.messageOptions.wentAfk),
                    PlaceholderContext.of(this)));
        } else {
            setAfkReason(reason);
            sendAfkMessage(
                    Placeholders.parseText(TextParserUtils.formatText(CONFIG.messageOptions.wentAfk
                            + "<yellow>,<r> " + reason),
                            PlaceholderContext.of(this)));
        }
        setAfk(true);
    }

    public void disableAfk() {
        if (!isAfk)
            return;
        if (CONFIG.messageOptions.prettyDuration) {
            long duration = Util.getMeasuringTimeMs() - (this.afkTimeMs);
            String ret = CONFIG.messageOptions.returned + " <gray>(Gone for: <green>"
                    + DurationFormatUtils.formatDurationWords(duration, true, true) + "<gray>)<r>";
            sendAfkMessage(Placeholders.parseText(TextParserUtils.formatText(ret), PlaceholderContext.of(this)));
        } else {
            long duration = Util.getMeasuringTimeMs() - (this.afkTimeMs);
            String ret = CONFIG.messageOptions.returned + " <gray>(Gone for: <green>"
                    + DurationFormatUtils.formatDurationHMS(duration) + "<gray>)<r>";
            sendAfkMessage(Placeholders.parseText(TextParserUtils.formatText(ret), PlaceholderContext.of(this)));
        }
        setAfk(false);
        clearAfkTime();
        clearAfkReason();
    }

    public void updatePlayerList() {
        this.server
                .getPlayerManager()
                .sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, player));
        // AfkDisplayLogger.info("sending player list update for " + player.getName());

    }

    private void sendAfkMessage(Text text) {
        if (!CONFIG.messageOptions.enableChatMessages || text.getString().trim().length() == 0)
            return;
        server.sendMessage(text);
        for (ServerPlayerEntity player : this.server.getPlayerManager().getPlayerList()) {
            player.sendMessage(text);
        }
    }

    private void setAfk(boolean isAfk) {
        this.isAfk = isAfk;
        this.server
                .getPlayerManager()
                .sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, player));
    }

    private void setAfkTime() {
        this.afkTimeMs = Util.getMeasuringTimeMs();
        this.afkTimeString = Util.getFormattedCurrentTime();
    }

    private void clearAfkTime() {
        this.afkTimeMs = 0;
        this.afkTimeString = "";
    }

    private void setAfkReason(String reason) {
        if (reason == null || reason == "") {
            this.afkReason = "<red>none";
        } else {
            this.afkReason = reason;
        }
    }

    private void clearAfkReason() {
        this.afkReason = "";
    }

    @Inject(method = "updateLastActionTime", at = @At("TAIL"))
    private void onActionTimeUpdate(CallbackInfo ci) {
        disableAfk();
    }

    public void setPosition(double x, double y, double z) {
        if (CONFIG.packetOptions.resetOnMovement && (this.getX() != x || this.getY() != y || this.getZ() != z)) {
            player.updateLastActionTime();
        }
        super.setPosition(x, y, z);
    }

    @Inject(method = "getPlayerListName", at = @At("RETURN"), cancellable = true)
    private void replacePlayerListName(CallbackInfoReturnable<Text> cir) {
        if (CONFIG.playerListOptions.enableListDisplay && isAfk) {
            Text listEntry = Placeholders.parseText(
                    TextParserUtils.formatText(CONFIG.playerListOptions.afkPlayerName),
                    PlaceholderContext.of(this));
            cir.setReturnValue(listEntry.copy());
        }
    }
}
