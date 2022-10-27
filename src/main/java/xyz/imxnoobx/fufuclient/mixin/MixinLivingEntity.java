package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static xyz.imxnoobx.fufuclient.FuFuClient.wgBypass;

@Mixin(net.minecraft.entity.LivingEntity.class)
public class MixinLivingEntity {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isImmobile()Z"))
    private boolean isImmobile(LivingEntity ent) { // Blocks player vanilla movements
        return wgBypass;
    }
}
