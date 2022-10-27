package xyz.imxnoobx.fufuclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

@Mixin(net.minecraft.client.gui.screen.DemoScreen.class)
public class MixinDemoScreen {
    @Inject(method = "init()V", at = @At("HEAD"), cancellable = true)
    private void blockDemoScreen(CallbackInfo callback) {
        if(FuFuMode == 1) {
            LOGGER.info("MixinDemoScreen called and callback blocked!");
            callback.cancel();
        }
    }
}
