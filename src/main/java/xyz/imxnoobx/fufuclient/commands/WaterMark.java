package xyz.imxnoobx.fufuclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import xyz.imxnoobx.fufuclient.FuFuClient;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static xyz.imxnoobx.fufuclient.FuFuClient.waterMark;

public class WaterMark {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("watermark")
                .executes(context -> toggleBypass(context.getSource()))
        );
    }

    private static int toggleBypass(FabricClientCommandSource source) {
        waterMark = !waterMark;
        FuFuClient.chatLog("Watermark: " + (waterMark ? "\u00a7aEnabled" : "\u00a7cDisabled"));
        return 1;
    }
}
