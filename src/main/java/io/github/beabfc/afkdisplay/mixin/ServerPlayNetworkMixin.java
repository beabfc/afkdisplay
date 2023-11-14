package io.github.beabfc.afkdisplay.mixin;

import static io.github.beabfc.afkdisplay.ConfigManager.*;

import java.time.Duration;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.beabfc.afkdisplay.AfkPlayer;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "tick", at = @At("HEAD"))
    private void updateAfkStatus(CallbackInfo ci) {
        AfkPlayer afkPlayer = (AfkPlayer) player;
        int timeoutSeconds = CONFIG.packetOptions.timeoutSeconds;
        long afkDuration = Util.getMeasuringTimeMs() - this.player.getLastActionTime();
        if (afkPlayer.isAfk()) {
            if (CONFIG.playerListOptions.afkUpdateTime > 0) {
                // Setting this afkTime value also has a positive side effect of updating the
                // player list every now and then alone, but throwing updatePlayerList() is what
                // we are trying to catch here to force an update every 60 seconds regardless.
                // But without this, the player list will remain stale unless you /afkdisplay
                // update [Player],
                // Which also incidently can fix display update bugs in Styled Player List.
                Duration afkTime = Duration.ofMillis(afkDuration);
                Duration configTime = Duration.ofSeconds(CONFIG.playerListOptions.afkUpdateTime);
                if (afkTime.toSeconds() > 0) {
                    boolean isDiv = afkTime.toSeconds() % configTime.toSeconds() == 0;
                    if (isDiv) {
                        afkPlayer.updatePlayerList();
                    }
                }
            }
            return;
        } else if (timeoutSeconds <= 0) {
            return;
        } else {
            if (afkDuration > timeoutSeconds * 1000L) {
                afkPlayer.enableAfk("timeout");
            }
        }
    }

    @Inject(method = "onPlayerMove", at = @At("HEAD"))
    private void checkPlayerLook(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        // just checking for changesLook on the packet doesn't work
        if (CONFIG.packetOptions.resetOnLook && packet.changesLook()) {
            float yaw = player.getYaw();
            float pitch = player.getPitch();
            if (pitch != packet.getPitch(pitch) || yaw != packet.getYaw(yaw))
                player.updateLastActionTime();
        }
    }
}
