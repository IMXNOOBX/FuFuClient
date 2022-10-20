package xyz.imxnoobx.fufuclient.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.*;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

public class NightVision {
    public static void tick(MinecraftClient mc) {

        if(!nightvisionSwitch || mc.player == null)
            return;

        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 500, 0));
    }
}
