package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
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

@Mixin(ClientConnection.class)
public class MixinClientConnection {

    @Shadow private Channel channel;

    @Shadow private PacketListener packetListener;

    @ModifyVariable(method = "sendImmediately", at = @At ("HEAD"), ordinal = 0) // Modify outgoing packets
    private Packet<?> modifyPacket(Packet<?> packet) { // Credits https://github.com/jokil123/liveoverflow-utility-mod

        if (FuFuClient.humanbypassSwitch && packet  instanceof PlayerMoveC2SPacket) {
            PlayerMoveC2SPacket bypass = (PlayerMoveC2SPacket) packet;

            return new PlayerMoveC2SPacket.Full(
                    Math.round(bypass.getX(0) * 100.0) / 100.0,
                    bypass.getY(0),
                    Math.round(bypass.getZ(0) * 100.0) / 100.0,
                    FuFuClient.mc.player.getYaw(), // bypass.getYaw(0),
                    FuFuClient.mc.player.getPitch(), // bypass.getPitch(0),
                    bypass.isOnGround()
            );
        }

        if (FuFuClient.humanbypassSwitch && packet instanceof PlayerPositionLookS2CPacket) {
            PlayerPositionLookS2CPacket p = (PlayerPositionLookS2CPacket) packet;
            //FuFuClient.chatLog("Packet Yaw: (" + p.getYaw() + "/"+FuFuClient.mc.player.getYaw()+" / (Pitch: "+ p.getPitch()+"/"+FuFuClient.mc.player.getPitch()+")");

            return new PlayerPositionLookS2CPacket(
                    Math.round(FuFuClient.mc.player.getX() * 100.0) / 100.0,
                    FuFuClient.mc.player.getY(),
                    Math.round(FuFuClient.mc.player.getZ() * 100.0) / 100.0,
                    FuFuClient.mc.player.getYaw(),
                    FuFuClient.mc.player.getPitch(),
                    p.getFlags(),
                    p.getTeleportId(),
                    p.shouldDismount()
            );
        }

        return packet;

    }

    // Read: Incoming packets
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callback) {
        if (this.channel.isOpen() && packet != null) {


            /*if (FuFuClient.fakeCreative && packet instanceof PlayerPositionLookS2CPacket) {
                PlayerPositionLookS2CPacket p = (PlayerPositionLookS2CPacket) packet;
//                p.getYaw().se= FuFuClient.mc.player.getYaw();
                // p.pitch = FuFuClient.mc.player.getPitch();

                new PlayerPositionLookS2CPacket(
                        p.getX(),
                        p.getY(),
                        p.getZ(),
                        FuFuClient.mc.player.getYaw(),
                        FuFuClient.mc.player.getPitch(),
                        p.getFlags(),
                        p.getTeleportId(),
                        p.shouldDismount()
                );
                //FuFuClient.chatLog("Packet Yaw: (" + p.getYaw() + "/"+FuFuClient.mc.player.getYaw()+" / (Pitch: "+ p.getPitch()+"/"+FuFuClient.mc.player.getPitch()+")");
            }*/
        }
    }


    // Send: Outgoing packets
    @Inject(method = "send(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, PacketCallbacks packetCallback, CallbackInfo callback) {
        // https://maven.fabricmc.net/docs/yarn-1.19.2+build.5/net/minecraft/network/ClientConnection.html
        // callback.cancel(); to cancel the packet

        /*if (FuFuClient.humanbypassSwitch && packet instanceof PlayerMoveC2SPacket) {
            PlayerMoveC2SPacket p = (PlayerMoveC2SPacket) packet;
                //callback.cancel();

                FuFuClient.mc.player.networkHandler.sendPacket(
                    new PlayerMoveC2SPacket.Full(
                        Math.round(p.getX(0) * 100.0) / 100.0,
                        p.getY(0),
                        Math.round(p.getZ(0) * 100.0) / 100.0,
                        FuFuClient.mc.player.getYaw(), // bypass.getYaw(0),
                        FuFuClient.mc.player.getPitch(), // bypass.getPitch(0),
                        p.isOnGround()
                    )
                );
            FuFuClient.chatLog("PlayerMoveC2SPacket: Rewritten");
        }*/
    }
}
