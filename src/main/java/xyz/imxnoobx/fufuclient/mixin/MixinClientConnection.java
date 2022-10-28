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
import net.minecraft.network.packet.s2c.play.*;

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
            VehicleMoveC2SPacket pkt = (VehicleMoveC2SPacket) packet;

            Entity vehicle = FuFuClient.mc.player.getVehicle();

            vehicle.setPos((double) (long)(pkt.getX() * 100.0) / 100.0, pkt.getY(), (double) (long)(pkt.getZ() * 100.0) / 100.0); // Hope this works

            return new VehicleMoveC2SPacket(
                    vehicle
            );
        }

        return packet;

    }

    // Read: Incoming packets
    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener, CallbackInfo callback) {

        if(FuFuMode == 1) {
            if (packet instanceof GameStateChangeS2CPacket) {
                GameStateChangeS2CPacket pkt = (GameStateChangeS2CPacket) packet;

                if (pkt.getReason().equals(GameStateChangeS2CPacket.DEMO_MESSAGE_SHOWN)/* || pkt.getValue() == 104*/) {
                    LOGGER.info("GameStateChangeS2CPacket called with reason DEMO_MESSAGE_SHOWN and callback blocked!");
                    callback.cancel();
                }

                if (pkt.getReason().equals(GameStateChangeS2CPacket.GAME_MODE_CHANGED)) {
                    LOGGER.info("GameStateChangeS2CPacket called with reason GAME_MODE_CHANGED and callback blocked!");
                    callback.cancel();
                }
            }

            if(packet instanceof WorldBorderSizeChangedS2CPacket){
                LOGGER.info("WorldBorderSizeChangedS2CPacket called and callback blocked!");
                callback.cancel();
            }

            if(packet instanceof WorldBorderInitializeS2CPacket){
                LOGGER.info("WorldBorderInitializeS2CPacket called and callback blocked!");
                callback.cancel();
            }
        }
    }


    // Send: Outgoing packets
    @Inject(method = "send(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, PacketCallbacks packetCallback, CallbackInfo callback) {
        // https://maven.fabricmc.net/docs/yarn-1.19.2+build.5/net/minecraft/network/ClientConnection.html
        // callback.cancel(); to cancel the packet
    }
}
