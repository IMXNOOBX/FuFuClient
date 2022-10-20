package xyz.imxnoobx.fufuclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
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

    @ModifyVariable(method = "sendImmediately", at = @At ("HEAD"), ordinal = 0) // Modify outgoing packets
    private Packet<?> modifyPacket(Packet<?> packet) { // Credits https://github.com/jokil123/liveoverflow-utility-mod

        if (!FuFuClient.humanbypassSwitch)
            return packet;

        if (packet instanceof PlayerMoveC2SPacket) {
            PlayerMoveC2SPacket bypass = (PlayerMoveC2SPacket) packet;

            return new PlayerMoveC2SPacket.Full(
                    Math.round(bypass.getX(0) * 100.0) / 100.0,
                    bypass.getY(0),
                    Math.round(bypass.getZ(0) * 100.0) / 100.0,
                    bypass.getYaw(0), // FuFuClient.mc.player.getYaw(),
                    bypass.getPitch(0), // FuFuClient.mc.player.getPitch(),
                    bypass.isOnGround()
            );
        }

        return packet;
    }

    // Read: Incomming packets
	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	public void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callback) {
		if (this.channel.isOpen() && packet != null) {


			//if (event.isCancelled()) {
				//callback.cancel();
			//} else if (packet instanceof PlayerListS2CPacket) {
				//handlePlayerList((PlayerListS2CPacket) packet);
			//}
            if (FuFuClient.humanbypassSwitch && packet instanceof PlayerPositionLookS2CPacket) {
                PlayerPositionLookS2CPacket p = (PlayerPositionLookS2CPacket) packet;
                // p.yaw = FuFuClient.mc.player.getYaw();
                // p.pitch = FuFuClient.mc.player.getPitch();
                FuFuClient.chatLog("Packet Yaw: " + p.getYaw() + " / Pitch:"+ p.getPitch());
            }
		}
	}

    /*/
    // Send: Outgoing packets
    @Inject(method = "method_10752(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
	private void send(Packet<?> packet, GenericFutureListener<Future<?>> packetCallback, CallbackInfo callback) {
        // https://maven.fabricmc.net/docs/yarn-21w20a+build.9/net/minecraft/network/ClientConnection.html#channelRead0(io.netty.channel.ChannelHandlerContext,net.minecraft.network.Packet)
        // callback.cancel(); to cancell the packet
	}*/
}
