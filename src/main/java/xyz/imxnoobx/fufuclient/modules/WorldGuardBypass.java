package xyz.imxnoobx.fufuclient.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;
import static xyz.imxnoobx.fufuclient.FuFuClient.mc;

public class WorldGuardBypass {
    private static double gamma;
    private static boolean nv;

    static double movement = 1;
    public static void tick(MinecraftClient mc) {
        if(!wgBypass || mc.player == null)
            return;

        boolean forwardKey = mc.options.forwardKey.isPressed();
        boolean backwardKey = mc.options.backKey.isPressed();
        boolean leftKey = mc.options.leftKey.isPressed();
        boolean rightKey = mc.options.rightKey.isPressed();
        boolean downKey = mc.options.sneakKey.isPressed();
        boolean upKey = mc.options.sprintKey.isPressed();
        
        boolean test = mc.options.attackKey.wasPressed();

        // mc.player.setVelocity(0, 0, 0);

        //Vec3d pos = mc.player.getPos();

        /*if(forwardKey) {
            pos = mc.player.getRotationVector().multiply(movement); // new Vec3d(pos.x, pos.y, pos.z + 1);
        } else if(backwardKey) {
            pos = mc.player.getRotationVector().negate().multiply(movement); // new Vec3d(pos.x, pos.y, pos.z - 1);
        } else if (leftKey) {
            pos = mc.player.getRotationVector().multiply(movement).rotateY(3.1415927F/2); // new Vec3d(pos.x -1, pos.y, pos.z);
        } else if (rightKey) {
            pos = mc.player.getRotationVector().multiply(movement).rotateY(-3.1415927F/2); // new Vec3d(pos.x +1, pos.y, pos.z);
        } else if (downKey) {
            pos = pos.add(new Vec3d(0, 1, 0));
        } else if (upKey) {
            pos = pos.add(new Vec3d(0, -1, 0));
        }*/
        Vec3d pos = new Vec3d(0, 0, 0);

        if(forwardKey) {
            pos = pos.add(new Vec3d(0, 0, 0.5));
        } else if(backwardKey) {
            pos = pos.add(new Vec3d(0, 0, -0.5));
        } else if (leftKey) {
            pos = pos.add(new Vec3d(-0.5, 0, 0));
        } else if (rightKey) {
            pos = pos.add(new Vec3d(0.5, 0, 0));
        } else if (downKey) {
            pos = pos.add(new Vec3d(0, -0.5, 0));
        } else if (upKey) {
            pos = pos.add(new Vec3d(0, 0.5, 0));
        }

        if(pos.length() <= 0)
            return;

        if (!(pos.x == 0 && pos.z == 0)) {  // Rotate by looking yaw (won't change length)
            double moveAngle = Math.atan2(pos.x, pos.z) + Math.toRadians(mc.player.getYaw() + 90);
            double x = Math.cos(moveAngle);
            double z = Math.sin(moveAngle);
            pos = new Vec3d(x, pos.y, z);
        }
        
        pos = pos.normalize();

        pos = pos.multiply(0.05);  // Scale to maxDelta

        boolean moveTick = false;
        Vec3d newPos = new Vec3d(mc.player.getX() + pos.x, mc.player.getY() + pos.y, mc.player.getZ() + pos.z); 

        while (inSameBlock(newPos.add(pos.multiply(1.7)), new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ))) {
            newPos = newPos.add(pos);
            moveTick = true;
        }

        if(forwardKey || backwardKey || leftKey || rightKey || downKey || upKey) {
            mc.player.setPosition(newPos);

            mc.getNetworkHandler().getConnection().send( //
                    new PlayerMoveC2SPacket.Full(
                            mc.player.getX(),// pos.x,
                            mc.player.getY(),// pos.y,
                            mc.player.getZ(),// pos.z,
                            mc.player.getYaw(),
                            mc.player.getPitch(),
                            mc.player.isOnGround())
            );
            LOGGER.info("Sending movement packet, moveTick: "+moveTick);
        }

        if(moveTick) {
            // mc.player.setPosition(mc.player.getX(), -42069, mc.player.getZ());
            mc.getNetworkHandler().getConnection().send(new PlayerMoveC2SPacket.Full( // make the server teleport you back
                    pos.x,
                    pos.y + 1337.0,
                    pos.z,
                    mc.player.getYaw(),
                    mc.player.getPitch(),
                    mc.player.isOnGround())
            );
            LOGGER.info("Sending restore packet, moveTick: "+moveTick);
        }
    }

    public static boolean inSameBlock(Vec3d vector, Vec3d other) {
        return other.x >= Math.floor(vector.x) && other.x <= Math.ceil(vector.x) &&
                other.y >= Math.floor(vector.y) && other.y <= Math.ceil(vector.y) &&
                other.z >= Math.floor(vector.z) && other.z <= Math.ceil(vector.z);
    }

    public static void onStart(MinecraftClient mc) {

    }

    public static void onDisable(MinecraftClient mc) {

    }
}
