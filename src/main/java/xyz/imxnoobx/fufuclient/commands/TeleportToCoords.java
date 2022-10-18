package xyz.imxnoobx.fufuclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static xyz.imxnoobx.fufuclient.FuFuClient.*;

public class TeleportToCoords {

   //private static final int ammount = 0;

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("teleport")
                .then(argument("x", integer())
                        .then(argument("y", integer())
                                .then(argument("z", integer())
                .executes(context -> teleporTo(context.getSource(), getInteger(context, "x"), getInteger(context, "y"), getInteger(context, "z")))))));
    }

    private static int teleporTo(FabricClientCommandSource source, int x, int y, int z) {
        //mc.player.setPosition(mc.player.getX() + x, mc.player.getY() + y, mc.player.getZ() + z);
        mc.player.setPosition(x, y, z);
        source.sendFeedback(Text.literal("Teleported to [" + (int) mc.player.getX() + x + " / "+ (int) mc.player.getY() + y + " / " + (int) mc.player.getZ() + z + "]"));
        return 1;
    }
}
