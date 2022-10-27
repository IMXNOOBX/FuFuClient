package xyz.imxnoobx.fufuclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import xyz.imxnoobx.fufuclient.FuFuClient;


import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static xyz.imxnoobx.fufuclient.FuFuClient.*;

public class CustomizeHud {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("hud")
                .then(literal("toggle")
                    .then(argument("toggle", bool())
                        .executes(context -> hud(context.getSource(), getBool(context, "toggle")))))
                .then(literal("coords")
                    .then(argument("coords", bool())
                        .executes(context -> hudInfoCoords(context.getSource(), getBool(context, "coords")))))
                .then(literal("ip")
                    .then(argument("ip", bool())
                        .executes(context -> hudInfoIp(context.getSource(), getBool(context, "ip")))))
                .then(literal("watermark")
                    .then(argument("watermark", bool())
                        .executes(context -> hudInfoWatermark(context.getSource(), getBool(context, "watermark")))))
        );
    }

    private static int hudInfoCoords(FabricClientCommandSource source, boolean opt) {
        hudCoords = opt;
        FuFuClient.chatLog("Hud Coordinates: " + (hudCoords ? "\u00a7aEnabled" : "\u00a7cDisabled"));
        return 1;
    }

    private static int hudInfoIp(FabricClientCommandSource source, boolean opt) {
        hudIP = opt;
        FuFuClient.chatLog("Hud Ip Address: " + (hudIP ? "\u00a7aEnabled" : "\u00a7cDisabled"));
        return 1;
    }

    private static int hudInfoWatermark(FabricClientCommandSource source, boolean opt) {
        hudWatermak = opt;
        FuFuClient.chatLog("Hud Watermak: " + (hudWatermak ? "\u00a7aEnabled" : "\u00a7cDisabled"));
        return 1;
    }

    private static int hud(FabricClientCommandSource source, boolean opt) {
        hud = opt;
        FuFuClient.chatLog("Hud: " + (hudWatermak ? "\u00a7aEnabled" : "\u00a7cDisabled"));
        return 1;
    }

}
