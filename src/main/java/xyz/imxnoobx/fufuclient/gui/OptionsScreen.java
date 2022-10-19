package xyz.imxnoobx.fufuclient.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import xyz.imxnoobx.fufuclient.FuFuClient;

import static xyz.imxnoobx.fufuclient.FuFuClient.*;


public class OptionsScreen extends Screen {
    private Screen parent;

    private static final int TITLE_FULL_BUTTON_WIDTH = 200;
    private static final int INGAME_FULL_BUTTON_WIDTH = 204;
    private static final int HALF_BUTTON_WIDTH = 98;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_VERICAL_SPACING = 24;

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
        addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 6 + 124 - BUTTON_VERICAL_SPACING, INGAME_FULL_BUTTON_WIDTH, 20, Text.literal("NightVision is " + (nightvisionSwitch ? "\u00a7aEnabled" : "\u00a7cDisabled")), button -> {
            FuFuClient.LOGGER.info("Button Clicked, Toggling NightVision!");
            nightvisionSwitch = !nightvisionSwitch;
            if (!nightvisionSwitch && mc.player != null)
                mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
            clearAndInit();
        }));
        addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 6 + 124, INGAME_FULL_BUTTON_WIDTH, 20, Text.literal("HumanBypass is " + (humanbypassSwitch ? "\u00a7aEnabled" : "\u00a7cDisabled")), button -> {
            FuFuClient.LOGGER.info("Button Clicked, Toggling HumanBypass!");
            humanbypassSwitch = !humanbypassSwitch;
            clearAndInit();
        }));

        initIsProcessing = false;
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }
}
