package xyz.imxnoobx.fufuclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.world.GameMode;
import xyz.imxnoobx.fufuclient.FuFuClient;



import static com.mojang.brigadier.arguments.ArgumentType.*;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static xyz.imxnoobx.fufuclient.FuFuClient.*;

public class FakeGamemode {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("fakegamemode")
                .then(literal("survival")
                                .executes(context -> setGameMode(context.getSource(), 0)))
                .then(literal("creative")
                                .executes(context -> setGameMode(context.getSource(), 1)))
                .then(literal("spectator")
                                .executes(context -> setGameMode(context.getSource(), 2)))
                .then(literal("restore")
                        .executes(context -> setGameMode(context.getSource(), -1)))
        );
    }

    private static int setGameMode(FabricClientCommandSource source, int opt) {
        if(opt != -1)
            mc.interactionManager.setGameMode(GameMode.byId(opt));
        else
            mc.interactionManager.setGameMode(mc.interactionManager.getPreviousGameMode());
        FuFuClient.chatLog("Set Clientside game mode: " + (opt == 0 ? "\u00a7aSurvival" : opt == 1 ? "\u00a7aCreative" : opt == 1 ? "\u00a7aSepectator": "\u00a7cDisabled"));
        return 1;
    }
}
