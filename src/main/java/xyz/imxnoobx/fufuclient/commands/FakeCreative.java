package xyz.imxnoobx.fufuclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import xyz.imxnoobx.fufuclient.FuFuClient;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static xyz.imxnoobx.fufuclient.FuFuClient.fakeCreative;
import static xyz.imxnoobx.fufuclient.FuFuClient.mc;


public class FakeCreative {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("fakecreative")
                .executes(context -> toggleCreative(context.getSource())));
    }

    private static int toggleCreative(FabricClientCommandSource source) {
        fakeCreative = !fakeCreative;
        FuFuClient.chatLog("FakeCreative: " + (fakeCreative ? "\u00a7aEnabled" : "\u00a7cDisabled"));

        if (mc.player == null)
            return 0;

        if (fakeCreative)
            mc.interactionManager.setGameMode(GameMode.byId(1));
        else
            mc.interactionManager.setGameMode(mc.interactionManager.getPreviousGameMode());

        return 1;
    }
}
