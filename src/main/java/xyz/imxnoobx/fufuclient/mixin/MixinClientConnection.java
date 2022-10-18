package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xyz.imxnoobx.fufuclient.*;

@Mixin(ClientConnection.class)
public class MixinClientConnection {

    @ModifyVariable(method = "sendImmediately", at = @At("HEAD"), ordinal = 0)
    private Packet<?> modifyPacket(Packet<?> packet) { // Credits https://github.com/jokil123/liveoverflow-utility-mod

        if (!FuFuClient.humanbypassSwitch)
            return packet;

        if (packet instanceof PlayerMoveC2SPacket) {
            PlayerMoveC2SPacket bypass = (PlayerMoveC2SPacket) packet;

            return new PlayerMoveC2SPacket.Full(
                    Math.round(bypass.getX(0) * 100.0) / 100.0,
                    bypass.getY(0),
                    Math.round(bypass.getZ(0) * 100.0) / 100.0,
                    FuFuClient.mc.player.getYaw(),
                    FuFuClient.mc.player.getPitch(),
                    bypass.isOnGround()
            );
        }

        return packet;
    }
}
