package xyz.imxnoobx.fufuclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import xyz.imxnoobx.fufuclient.FuFuClient;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static xyz.imxnoobx.fufuclient.FuFuClient.mc;

public class FuFuHelp {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("fufuhelp")
                .executes(context -> printHelp(context.getSource()))
        );
    }

    private static int printHelp(FabricClientCommandSource source) {
        FuFuClient.chatLog("Commands:");
        String helpCommands = "\u00a7b/fakegamemode\u00a7f: Sets your game mode clientside.\n"
                + "\u00a7b/hud\u00a7f: \n"
                + "    \u00a79toggle\u00a7f: enables/disables the hud\n"
                + "    \u00a79coords\u00a7f: enables/disables the coordinates in the hud\n"
                + "    \u00a79ip\u00a7f: enables/disables the IP in the hud. Hoping to not leak the server ip again :D\n"
                + "    \u00a79watermak\u00a7f: enables/disables the coolest watermark ever.\n"
                + "\u00a7b/teleport\u00a7f: teleport to close coordinates.\n";

        source.sendFeedback(Text.literal(helpCommands));
        return 1;
    }
}
