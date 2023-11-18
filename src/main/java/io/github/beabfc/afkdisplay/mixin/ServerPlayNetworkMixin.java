package io.github.beabfc.afkdisplay.mixin;

import static io.github.beabfc.afkdisplay.config.ConfigManager.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.beabfc.afkdisplay.data.AfkPlayerData;
import io.github.beabfc.afkdisplay.util.AfkDisplayLogger;
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
        AfkPlayerData afkPlayer = (AfkPlayerData) player;
        int timeoutSeconds = CONFIG.packetOptions.timeoutSeconds;
        long afkDuration = Util.getMeasuringTimeMs() - this.player.getLastActionTime();
        if (afkPlayer.isAfk() || timeoutSeconds <= 0) {
            return;
        } else {
            if (afkDuration > timeoutSeconds * 1000L) {
                if (CONFIG.afkDisplayOptions.afkTimeoutString == ""
                        || CONFIG.afkDisplayOptions.afkTimeoutString == null) {
                    afkPlayer.enableAfk("timeout");
                } else {
                    afkPlayer.enableAfk(CONFIG.afkDisplayOptions.afkTimeoutString);
                }
                AfkDisplayLogger.debug("Setting player " + this.player.getName() + " as AFK (timeout)");
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
