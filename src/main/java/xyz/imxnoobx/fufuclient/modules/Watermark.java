package xyz.imxnoobx.fufuclient.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

public class Watermark {
    public static void tick(MinecraftClient mc) {
        if (!waterMark || mc.player == null)
            return;

        // mc.textRenderer.draw(null, name + " - " + version, 5, 5, 1);
    }
}
