package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.DemoScreen;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static xyz.imxnoobx.fufuclient.FuFuClient.FuFuMode;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(at = @At("HEAD"), method = "setScreen", cancellable = true)
    public void openScreen(Screen screen, CallbackInfo callback) {
        if(FuFuMode == 1) {
            if(screen instanceof DemoScreen) {
                System.out.println("DemoScreen called and callback blocked!");
                callback.cancel();
            } else if(screen instanceof CreditsScreen) {
                System.out.println("CreditsScreen called and callback blocked!");
                callback.cancel();
            }
        }
    }
}
