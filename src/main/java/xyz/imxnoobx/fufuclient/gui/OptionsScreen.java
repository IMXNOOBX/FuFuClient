package xyz.imxnoobx.fufuclient.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import xyz.imxnoobx.fufuclient.FuFuClient;
import xyz.imxnoobx.fufuclient.modules.xRayModule;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;


public class OptionsScreen extends Screen {
    private Screen parent;

    private static final int TITLE_FULL_BUTTON_WIDTH = 200;
    private static final int INGAME_FULL_BUTTON_WIDTH = 204;
    private static final int HALF_BUTTON_WIDTH = 98;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_VERICAL_SPACING = 24;

    private static double dmgPerblock;
    private static int warnPerblock;
    private static double size;
    private static double getSafe;

    private boolean initIsProcessing;
    public OptionsScreen(Screen parent) {
        super(Text.literal("Hack Options"));
        this.parent = parent;
    }

    @Override
    protected void init() { // https://github.com/roflmuffin/fabric-title-changer
        super.init();
        if(initIsProcessing) return;
        initIsProcessing = true;

        addDrawableChild(new ButtonWidget(10, 10, 70, 20, Text.literal("Go Back"), button -> {
            client.setScreen(parent);
            FuFuClient.LOGGER.info("Button Clicked, Going back to the Parent screen!");
        }));
        addDrawableChild(new ButtonWidget(this.width / 2 - 102/*half of the button width*/, this.height / 6 + BUTTON_VERICAL_SPACING, INGAME_FULL_BUTTON_WIDTH, 20, Text.literal("Fliying is " + (flightSwitch ? "\u00a7aEnabled" : "\u00a7cDisabled")), button -> {
            FuFuClient.LOGGER.info("Button Clicked, Toggling Flight!");
            flightSwitch = !flightSwitch;
            clearAndInit();
        }));
        addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 6 + BUTTON_VERICAL_SPACING * 2, INGAME_FULL_BUTTON_WIDTH, 20, Text.literal("NightVision is " + (nightvisionSwitch ? "\u00a7aEnabled" : "\u00a7cDisabled")), button -> {
            FuFuClient.LOGGER.info("Button Clicked, Toggling NightVision!");
            nightvisionSwitch = !nightvisionSwitch;
            if (!nightvisionSwitch && mc.player != null)
                mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
            clearAndInit();
        }));
        addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 6 + BUTTON_VERICAL_SPACING * 3, INGAME_FULL_BUTTON_WIDTH, 20, Text.literal("Xray is " + (xRay ? "\u00a7aEnabled" : "\u00a7cDisabled")), button -> {
            FuFuClient.LOGGER.info("Button Clicked, Toggling Xray!");
            xRay = !xRay;
            if(xRay) xRayModule.onStart(mc); else xRayModule.onDisable(mc);
            clearAndInit();
        }));
        // addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 6 + BUTTON_VERICAL_SPACING * 4, INGAME_FULL_BUTTON_WIDTH, 20, Text.literal("HumanBypass is " + (humanbypassSwitch ? "\u00a7aEnabled" : "\u00a7cDisabled")), button -> {
        //     FuFuClient.LOGGER.info("Button Clicked, Toggling HumanBypass!");
        //     humanbypassSwitch = !humanbypassSwitch;
        //     clearAndInit();
        // }));
        addDrawableChild(new ButtonWidget(this.width - 204 + 10, this.height / 6 + BUTTON_VERICAL_SPACING * 4, INGAME_FULL_BUTTON_WIDTH, 20, Text.literal("Mode " + (FuFuMode == 0 ? "\u00a7cDisabled" : "\u00a7aLiveOverflow")), button -> {
            FuFuClient.LOGGER.info("Button Clicked, Switching FuFuMode!");
            FuFuMode++; if(FuFuMode > 1) FuFuMode = 0;

            switch (FuFuMode) {
                case 1: // LiveOverflow
                    mc.interactionManager.setGameMode(GameMode.byId(0)); 
                    FuFuClient.chatLog("Creative Bypass: " + (FuFuMode == 1 ? "\u00a7aEnabled" : "\u00a7cDisabled"));
                    
                    dmgPerblock = mc.world.getWorldBorder().getDamagePerBlock();
                    warnPerblock = mc.world.getWorldBorder().getWarningBlocks();
                    size = mc.world.getWorldBorder().getSize();
                    getSafe = mc.world.getWorldBorder().getSafeZone();
                    mc.world.getWorldBorder().setWarningBlocks(0);
                    mc.world.getWorldBorder().setDamagePerBlock(0);
                    mc.world.getWorldBorder().setSize(Integer.MAX_VALUE);
                    mc.world.getWorldBorder().setSafeZone(Integer.MAX_VALUE);
                    FuFuClient.chatLog("WorldBorder Bypass: " + (FuFuMode == 1 ? "\u00a7aEnabled" : "\u00a7cDisabled"));
                    break;
                default:
                    mc.interactionManager.setGameMode(mc.interactionManager.getPreviousGameMode());
                    FuFuClient.chatLog("Creative Bypass: " + (FuFuMode == 1 ? "\u00a7aEnabled" : "\u00a7cDisabled"));


                    mc.world.getWorldBorder().setWarningBlocks(warnPerblock);
                    mc.world.getWorldBorder().setDamagePerBlock(dmgPerblock);
                    mc.world.getWorldBorder().setSize(size);
                    mc.world.getWorldBorder().setSafeZone(getSafe);
                    FuFuClient.chatLog("WorldBorder Bypass: " + (FuFuMode == 1 ? "\u00a7aEnabled" : "\u00a7cDisabled"));
                    break;
            }
            clearAndInit();
        }));

        initIsProcessing = false;
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }
}
