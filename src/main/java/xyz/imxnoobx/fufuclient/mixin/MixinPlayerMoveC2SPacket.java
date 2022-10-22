package xyz.imxnoobx.fufuclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static xyz.imxnoobx.fufuclient.FuFuClient.humanbypassSwitch;
import static xyz.imxnoobx.fufuclient.FuFuClient.*;

@Mixin(net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.class)
public class MixinPlayerMoveC2SPacket {
    private static double lastModifyX;
    private static double lastModifyZ;

    @ModifyVariable(method = "<init>(DDDFFZZZ)V", at = @At("HEAD"), ordinal = 0, argsOnly = true) // Credits: https://github.com/ProtoByter/kaihack, doesn't glitch pitch and yaw + if rounding error we restore the last packet
    private static double modifyX(double value)
    {
        if(!humanbypassSwitch) return value;

        double modifyX = (double) (long)(value * 100.0) / 100.0;

        if (((long)modifyX * 1000) % 10 != 0) {
            LOGGER.info("Rounding error in \"X\"! Restoring last packet.");
            return lastModifyX;
        }
        lastModifyX = modifyX;
        return modifyX;
    }

    @ModifyVariable(method = "<init>(DDDFFZZZ)V", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    private static double modifyZ(double value)
    {
        if(!humanbypassSwitch) return value;

        double modifyZ = (double) (long)(value * 100.0) / 100.0;

        if (((long)modifyZ * 1000) % 10 != 0) {
            LOGGER.info("Rounding error in \"Z\"! Restoring last packet.");
            return lastModifyZ;
        }
        lastModifyZ = modifyZ;
        return modifyZ;
    }
}
