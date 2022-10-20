package xyz.imxnoobx.fufuclient.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import xyz.imxnoobx.fufuclient.FuFuClient;

import java.util.ArrayList;
import java.util.List;

import static xyz.imxnoobx.fufuclient.FuFuClient.mc;
import static xyz.imxnoobx.fufuclient.FuFuClient.waterMark;
@Environment(EnvType.CLIENT)
public class GameHud {
    private final MinecraftClient client;
    private final TextRenderer fontRenderer;
    private ClientPlayerEntity player;
    private MatrixStack matrixStack;

    private float width, height;
    //private final ItemRenderer itemRenderer;
    public GameHud(MinecraftClient client) {
        this.client = client;
        this.fontRenderer = client.textRenderer;
        // this.itemRenderer = client.getItemRenderer();

        this.width = client.getWindow().getWidth();
        this.height = client.getWindow().getHeight();

    }

    public void draw(MatrixStack matrixStack) {
        if (!waterMark) return;

        this.player = this.client.player;

        this.matrixStack = matrixStack;

        RenderSystem.enableBlend();

        this.drawWatermak();
        this.drawInfo();

        this.client.getProfiler().pop();
    }

    private void drawInfo() {
        // Draw lines of Array of Game info in the screen
        List<String> gameInfo = getGameInfo();

        int lineHeight = this.fontRenderer.fontHeight;
        int top = client.getWindow().getHeight();
        int left = 4;

        for (String line : gameInfo) {
            this.fontRenderer.drawWithShadow(this.matrixStack, line, left, top / 2 - (lineHeight + 4), 0x00E0E0E0);
            top -= lineHeight + 12;
        }
    }

    private void drawWatermak() {
        // Draw lines of Array of Game info in the screen
        int top = 0;
        int left = 4;

        this.fontRenderer.drawWithShadow(this.matrixStack, FuFuClient.colorName, left, top + 4, 0x00E0E0E0);
    }

    private List<String> getGameInfo() {
        List<String> gameInfo = new ArrayList<>();

        String coordDirectionStatus = "";
        Direction facing = this.player.getHorizontalFacing();
        String direction = facing.asString() + " " + getOffset(facing);

        if (waterMark) {
            String coordsFormat = "%.0f, %.0f, %.0f";
            coordDirectionStatus += String.format(coordsFormat, this.player.getX(), this.player.getY(), this.player.getZ());

            if (waterMark) {
                coordDirectionStatus += " (" + direction + ")";
            }
        } else if (!waterMark) {
            coordDirectionStatus += direction;
        }

        gameInfo.add(coordDirectionStatus);


        if (waterMark) {
            String coordsFormat = "X: %.0f, Y: %.0f, Z: %.0f";
            if (this.player.getWorld().getRegistryKey().getValue().toString().equals("minecraft:overworld")) {
                gameInfo.add("Nether: " + String.format(coordsFormat, this.player.getX() / 8, this.player.getY() / 8, this.player.getZ() / 8));
            } else if (this.player.getWorld().getRegistryKey().getValue().toString().equals("minecraft:the_nether")) {
                gameInfo.add("Overworld: " + String.format(coordsFormat, this.player.getX() * 8, this.player.getY() * 8, this.player.getZ() * 8));
            }
        }

        if (false) {
            // Get everything from fps debug string until the 's' from 'fps'
            // gameInfo.add(client.fpsDebugString.substring(0, client.fpsDebugString.indexOf("s") + 1));
            //gameInfo.add(String.format("%d fps", ((GameClientMixin) MinecraftClient.getInstance()).getCurrentFps()));
        }

        if (waterMark) {
            gameInfo.add(player.getEntityName());
        }

        if (waterMark) {
            String serverName = "Singleplayer";
            try {
                serverName = client.getCurrentServerEntry().name;
            } catch (Exception e) { }
            gameInfo.add(serverName);
        }
        /*
        if (waterMark) {
            String serverIp = "N/A";
            try {
                serverIp = client.getCurrentServerEntry().address;
            } catch (Exception e) {

            }
            gameInfo.add(serverIp);
        }*/

        return gameInfo;
    }

    private String getOffset(Direction facing) {
        String offset = "";

        if (facing.getOffsetX() > 0) {
            offset += "+X";
        } else if (facing.getOffsetX() < 0) {
            offset += "-X";
        }

        if (facing.getOffsetZ() > 0) {
            offset += " +Z";
        } else if (facing.getOffsetZ() < 0) {
            offset += " -Z";
        }

        return offset.trim();
    }

}
