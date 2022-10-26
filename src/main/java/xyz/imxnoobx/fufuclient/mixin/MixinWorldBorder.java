package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.network.listener.ClientPlayPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.imxnoobx.fufuclient.FuFuClient;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

@Mixin(net.minecraft.network.packet.s2c.play.WorldBorderInitializeS2CPacket.class)
public class MixinWorldBorder {
    @Inject(method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V", at=@At("HEAD"), cancellable = true)
    private void cancelApply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo callback) {
        if(FuFuMode == 1)
            callback.cancel();
    }
}
