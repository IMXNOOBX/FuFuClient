package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static xyz.imxnoobx.fufuclient.FuFuClient.*;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameStateChangeS2CPacket.class)
public class MixinGameStateChangeS2CPacket extends Object {

    private float value;

    @Inject(method = "<init>(Lnet/minecraft/network/packet/s2c/play/GameStateChangeS2CPacket$Reason;F)V", at = @At("TAIL"))
    private void onChange(GameStateChangeS2CPacket.Reason reason, float value, CallbackInfo ci) {
        if (FuFuMode == 1)
            mc.interactionManager.setGameMode(GameMode.byId(0)); // Ty 0skar2
        
    }

}
