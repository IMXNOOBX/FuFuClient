package xyz.imxnoobx.fufuclient.modules;

import net.minecraft.entity.effect.*;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

public class NightVision {
    public void onTick() {
        if(!nightvisionSwitch)
            return;

        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 500, 0));
    }
}
