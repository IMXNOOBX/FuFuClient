package xyz.imxnoobx.fufuclient;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.imxnoobx.fufuclient.gui.OptionsScreen;
import xyz.imxnoobx.fufuclient.commands.*;
import xyz.imxnoobx.fufuclient.modules.*;

public class FuFuClient implements ModInitializer {
	public static String name = "FuFuClient";
	public static String colorName = "\u00a75F\u00a7du\u00a75F\u00a7du\u00a79C\u00a7blient\u00a7f";
	public static String author = "IMXNOOBX";
	public static String version = "1.1.8";
	public static String game = "1.19.2";
	public static final Logger LOGGER = LoggerFactory.getLogger(name);

	public static final MinecraftClient mc = MinecraftClient.getInstance();

	public static int FuFuMode = 0;
	public static boolean nightvisionSwitch = false;
	//public static boolean fakeCreative = false;
	public static boolean flightSwitch = false;
	//public static boolean worldBorder = false;
	public static boolean xRay = false;
	public static boolean wgBypass = false;
	// hud related
	public static boolean hud = true;
	public static boolean hudCoords = true;
	public static boolean hudIP = false;
	public static boolean hudWatermak = true;


	public void onInitialize() {
		LOGGER.info("Hello from FuFuClient");

		ClientCommandRegistrationCallback.EVENT.register(FuFuClient::registerCommands);

		// Open Key
		KeyBinding modSettingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("Open Key", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "FuFuClient"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (modSettingsKey.wasPressed()) {
				client.getInstance().setScreen(new OptionsScreen(null));
			}
		});

		ClientTickEvents.START_CLIENT_TICK.register(FuFuClient::registerModules);
	}
	// \u00a7 == §
	public static void chatLog(String text) {
		if(mc.player != null) mc.player.sendMessage(Text.literal("[" + colorName+ "] " + text));
	}

	private static void registerModules(MinecraftClient client) {
		Flight.tick(client);
		NightVision.tick(client);
		xRayModule.tick(client);
		WorldGuardBypass.tick(client);
	}

	private static void registerCommands(CommandDispatcher<FabricClientCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess) {
		FuFuHelp.register(commandDispatcher);
		TeleportToCoords.register(commandDispatcher);
		FakeGamemode.register(commandDispatcher);
		CustomizeHud.register(commandDispatcher);
		//WorldBorder.register(commandDispatcher);
	}

}
