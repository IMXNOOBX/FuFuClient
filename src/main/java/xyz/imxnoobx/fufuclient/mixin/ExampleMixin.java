package xyz.imxnoobx.fufuclient.mixin;

import xyz.imxnoobx.fufuclient.FuFuClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		FuFuClient.LOGGER.info("This line is printed by FuFuClient's mixin!");
	}

	/*
	@Inject(at = @At("HEAD"), method = "tick()V")
	private void onTick(CallbackInfo info) {

	}*/
}
