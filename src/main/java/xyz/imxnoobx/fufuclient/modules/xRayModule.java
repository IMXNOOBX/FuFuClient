package xyz.imxnoobx.fufuclient.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

public class xRayModule {

    private static double gamma;
    private static boolean nv;


    public static void tick(MinecraftClient mc) {
        if(!xRay || mc.player == null)
            return;

        // mc.options.getGamma().setValue(Math.min(mc.options.getGamma().getValue() + 1, 69));
        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 500, 1000));
    }

    public static void onStart(MinecraftClient mc) {
        gamma = mc.options.getGamma().getValue();

        mc.chunkCullingEnabled = false;
        mc.worldRenderer.reload();

        if(nightvisionSwitch)
            nightvisionSwitch = false; nv = true;
    }

    public static void onDisable(MinecraftClient mc) {
        mc.options.getGamma().setValue(gamma);

        mc.chunkCullingEnabled = true;
        mc.worldRenderer.reload();


        if(nv)
            nightvisionSwitch = true; nv = false;
    }
}
