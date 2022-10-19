package xyz.imxnoobx.fufuclient.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

public class Flight {

    static int tickCount = 0;
    static double aceleration = 0.1;
    public static void tick(MinecraftClient mc) { // Spaghetti carbonara

        if(!flightSwitch || mc.player == null)
            return;

        if (!mc.player.hasVehicle()) {
            mc.player.getAbilities().allowFlying = true;
        } else {
            Vec3d vel = mc.player.getVelocity();
            boolean upKey = mc.options.jumpKey.isPressed();
            boolean forwardKey = mc.options.forwardKey.isPressed();
            boolean backwardKey = mc.options.backKey.isPressed();
            if (upKey) {
                if (forwardKey)
                    vel = mc.player.getRotationVector().multiply(aceleration);
                if (backwardKey)
                    vel = mc.player.getRotationVector().negate().multiply(aceleration);
                mc.player.getVehicle().setVelocity(new Vec3d(vel.x, tickCount < 40 ? 0.3 : -0.04, vel.z)); // just so you don't get kicked while flying up
                if (aceleration < 3.)
                    aceleration += 0.1;
            } else if (aceleration > 0.3) {
                aceleration -= 0.2;
            }
            // mc.player.setVelocity(mc.player.getVelocity().add(0, motionY, 0));
        }

        if(tickCount > 40)
            tickCount = 0;

        if(!mc.player.isOnGround()) {
            LOGGER.info("AntiKick tickCount: "+tickCount);
            tickCount++;
            if (tickCount >= 40) mc.player.setVelocity(mc.player.getVelocity().add(0, -0.04, 0));
        } else tickCount = 0; mc.player.setVelocity(mc.player.getVelocity().add(0, 0.04, 0));

        if (mc.player.fallDistance > 2.5f) { // no fall damage
            if (mc.player.isFallFlying())
                return;
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }

    }
}
