package io.github.beabfc.afkdisplay.mixin;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import io.github.beabfc.afkdisplay.AfkPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.beabfc.afkdisplay.AfkDisplay.CONFIG;


@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin extends Entity implements AfkPlayer {
    @Shadow
    @Final
    public MinecraftServer server;
    public ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
    @Unique
    private boolean isAfk;

    public ServerPlayerMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    public boolean isAfk() {
        return this.isAfk;
    }

    public void enableAfk() {
        if (isAfk()) return;
        setAfk(true);
        sendAfkMessage(Placeholders.parseText(Text.of(CONFIG.messageOptions.wentAfk), PlaceholderContext.of(this)));
    }

    public void disableAfk() {
        if (!isAfk) return;
        setAfk(false);
        sendAfkMessage(Placeholders.parseText(Text.of(CONFIG.messageOptions.returned), PlaceholderContext.of(this)));
    }

    private void sendAfkMessage(Text text) {
        if (!CONFIG.messageOptions.enableChatMessages || text.getString().trim().length() == 0) return;
        Formatting color = Formatting.byName(CONFIG.messageOptions.messageColor);
        if (color == null) color = Formatting.RESET;

        server.sendMessage(text);
        for (ServerPlayerEntity player : this.server.getPlayerManager().getPlayerList()) {
            player.sendMessage(text.copy().formatted(color));
        }
    }

    private void setAfk(boolean isAfk) {
        this.isAfk = isAfk;
        this.server
            .getPlayerManager()
            .sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, player));
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
            Formatting color = Formatting.byName(CONFIG.playerListOptions.afkColor);
            if (color == null) color = Formatting.RESET;

            Text listEntry = Placeholders.parseText(
                Text.of(CONFIG.playerListOptions.afkPlayerName),
                PlaceholderContext.of(this));
            cir.setReturnValue(listEntry.copy().formatted(color));
        }

    }
}
