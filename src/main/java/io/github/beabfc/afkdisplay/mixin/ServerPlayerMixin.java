package io.github.beabfc.afkdisplay.mixin;

import static io.github.beabfc.afkdisplay.ConfigManager.*;

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

    public void enableAfk() {
        if (isAfk())
            return;
        setAfk(true);
        setAfkTime();
        sendAfkMessage(Placeholders.parseText(TextParserUtils.formatText(CONFIG.messageOptions.wentAfk),
                PlaceholderContext.of(this)));
    }

    public void disableAfk() {
        if (!isAfk)
            return;
        setAfk(false);
        sendAfkMessage(Placeholders.parseText(TextParserUtils.formatText(CONFIG.messageOptions.returned),
                PlaceholderContext.of(this)));
        clearAfkTime();
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
