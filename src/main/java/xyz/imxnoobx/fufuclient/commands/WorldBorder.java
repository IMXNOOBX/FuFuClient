package xyz.imxnoobx.fufuclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import xyz.imxnoobx.fufuclient.FuFuClient;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static xyz.imxnoobx.fufuclient.FuFuClient.mc;
import static xyz.imxnoobx.fufuclient.FuFuClient.worldBorder;

public class WorldBorder {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("wbbypass")
                .executes(context -> toggleBypass(context.getSource()))
        );
    }
    private static double dmgPerblock;
    private static int warnPerblock;
    private static double size;
    private static double getSafe;
    private static int toggleBypass(FabricClientCommandSource source) {
        worldBorder = !worldBorder;
        if (worldBorder) {
            dmgPerblock = mc.world.getWorldBorder().getDamagePerBlock();
            warnPerblock = mc.world.getWorldBorder().getWarningBlocks();
            size = mc.world.getWorldBorder().getSize();
            getSafe = mc.world.getWorldBorder().getSafeZone();
            mc.world.getWorldBorder().setWarningBlocks(0);
            mc.world.getWorldBorder().setDamagePerBlock(0);
            mc.world.getWorldBorder().setSize(29999984);
            mc.world.getWorldBorder().setSafeZone(29999984);
        } else {
            mc.world.getWorldBorder().setWarningBlocks(warnPerblock);
            mc.world.getWorldBorder().setDamagePerBlock(dmgPerblock);
            mc.world.getWorldBorder().setSize(size);
            mc.world.getWorldBorder().setSafeZone(getSafe);
        }

        FuFuClient.chatLog("WorldBorder: " + (worldBorder ? "\u00a7aEnabled" : "\u00a7cDisabled"));
        return 1;
    }
}
