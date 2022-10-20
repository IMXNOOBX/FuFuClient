package xyz.imxnoobx.fufuclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import xyz.imxnoobx.fufuclient.FuFuClient;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import static xyz.imxnoobx.fufuclient.FuFuClient.humanbypassSwitch;

public class HumanBypass {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("humanbypass")
                .executes(context -> toggleBypass(context.getSource())
                ));
    }

    private static int toggleBypass(FabricClientCommandSource source) {
        humanbypassSwitch = !humanbypassSwitch;
        FuFuClient.chatLog("HumanBypass: " + (humanbypassSwitch ? "\u00a7aEnabled" : "\u00a7cDisabled"));
        return 1;
    }
}
