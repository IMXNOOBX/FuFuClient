package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import xyz.imxnoobx.fufuclient.FuFuClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.imxnoobx.fufuclient.modules.*;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

@Mixin(TitleScreen.class)
public class MixinMain {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		FuFuClient.LOGGER.info("This line is printed by FuFuClient's mixin!");
	}


	/*@Inject(at = @At("HEAD"), method = "tick()V") // only executed in the main menu (TitleScreen)
	private void onTick(CallbackInfo info) {
		LOGGER.info("tick executed");
	}*/
}
