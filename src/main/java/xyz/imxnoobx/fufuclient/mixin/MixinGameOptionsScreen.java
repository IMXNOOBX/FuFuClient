package xyz.imxnoobx.fufuclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

import xyz.imxnoobx.fufuclient.FuFuClient;
import xyz.imxnoobx.fufuclient.gui.OptionsScreen;

import net.minecraft.text.Text;

@Mixin(GameMenuScreen.class)
public class MixinGameOptionsScreen extends Screen {
    private MixinGameOptionsScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo callback) {
        addDrawableChild(new ButtonWidget(10, 10, 100, 20, Text.literal("FuFu Client"), button -> {
            client.setScreen(new OptionsScreen(this));
            FuFuClient.LOGGER.info("Button Clicked!, Going to the Options screen!");
        }));
    }
}
