package xyz.imxnoobx.fufuclient.modules;

import net.minecraft.client.MinecraftClient;

import static xyz.imxnoobx.fufuclient.FuFuClient.mc;
import static xyz.imxnoobx.fufuclient.FuFuClient.xRay;

public class xRayModule {

    private static double gamma;

    public static void tick(MinecraftClient mc) {
        if(!xRay || mc.player == null)
            return;

        mc.options.getGamma().setValue(Math.min(mc.options.getGamma().getValue() + 1, 69));
    }

    public static void onStart(MinecraftClient mc) {
        gamma = mc.options.getGamma().getValue();

        mc.chunkCullingEnabled = false;
        mc.worldRenderer.reload();
    }

    public static void onDisable(MinecraftClient mc) {
        mc.options.getGamma().setValue(gamma);

        mc.chunkCullingEnabled = true;
        mc.worldRenderer.reload();
    }
}
