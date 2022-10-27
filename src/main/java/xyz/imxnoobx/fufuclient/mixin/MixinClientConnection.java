package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import xyz.imxnoobx.fufuclient.*;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;

@Mixin(ClientConnection.class)
public class MixinClientConnection {

    @Shadow private Channel channel;

    @Shadow private PacketListener packetListener;

    @ModifyVariable(method = "sendImmediately", at = @At ("HEAD"), ordinal = 0) // Modify outgoing packets
    private Packet<?> modifyPacket(Packet<?> packet) {

        if (FuFuMode == 1 && packet instanceof VehicleMoveC2SPacket) {
            VehicleMoveC2SPacket bypass = (VehicleMoveC2SPacket) packet;

            Entity vehicle = FuFuClient.mc.player.getVehicle();

            vehicle.setPos((double) (long)(bypass.getX() * 100.0) / 100.0, bypass.getY(), (double) (long)(bypass.getZ() * 100.0) / 100.0); // Hope this works

            return new VehicleMoveC2SPacket(
                    vehicle
            );
        }

        return packet;

    }

    // Read: Incoming packets
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callback) {
        if (this.channel.isOpen() && packet != null) {

        }
    }


    // Send: Outgoing packets
    @Inject(method = "send(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, PacketCallbacks packetCallback, CallbackInfo callback) {
        // https://maven.fabricmc.net/docs/yarn-1.19.2+build.5/net/minecraft/network/ClientConnection.html
        // callback.cancel(); to cancel the packet
    }
}
