package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static xyz.imxnoobx.fufuclient.FuFuClient.FuFuMode;
import static xyz.imxnoobx.fufuclient.FuFuClient.mc;

@Mixin(net.minecraft.client.network.ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Shadow
    private GameMode gameMode;

    @Inject(method = "setGameMode(Lnet/minecraft/world/GameMode;)V", at = @At("HEAD"), cancellable = true)
    private void gameMode(GameMode GM, CallbackInfo callback) {
        if (FuFuMode == 1) {
            gameMode = GameMode.SURVIVAL;
        }
    }
}
