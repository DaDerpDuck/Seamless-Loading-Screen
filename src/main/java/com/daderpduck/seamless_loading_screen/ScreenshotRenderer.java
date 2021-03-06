package com.daderpduck.seamless_loading_screen;

import com.daderpduck.seamless_loading_screen.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class ScreenshotRenderer {
    public static void renderScreenshot(double screenHeight, double screenWidth, int alpha) {
        Minecraft mc = Minecraft.getInstance();
        float imageRatio = ScreenshotLoader.getImageRatio();
        float windowRatio = (float)mc.getMainWindow().getWidth()/mc.getMainWindow().getHeight();
        float offset = 1 - windowRatio/imageRatio;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        mc.getTextureManager().bindTexture(ScreenshotLoader.SCREENSHOT);
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
        bufferbuilder.pos(0, screenHeight, 0).color(255, 255, 255, alpha).tex(offset/2, 1F).endVertex();
        bufferbuilder.pos(screenWidth, screenHeight, 0).color(255, 255, 255, alpha).tex(1F - offset/2, 1F).endVertex();
        bufferbuilder.pos(screenWidth, 0, 0).color(255, 255, 255, alpha).tex(1F - offset/2, 0).endVertex();
        bufferbuilder.pos(0, 0, 0).color(255, 255, 255, alpha).tex(offset/2, 0.0F).endVertex();
        tessellator.draw();
    }

    public static class Fader {
        private static float elapsedTime = 0;

        public static void tick(float partialTicks) {
            elapsedTime += partialTicks;
        }

        public static float getFadeTime() {
            return Config.FadeTime.get();
        }

        public static float getHoldTime() {
            return Config.HoldTime.get();
        }

        public static float getAlpha() {
            if (getFadeTime() == 0) {
                return elapsedTime < getHoldTime() ? 1F : 0F;
            } else {
                return Math.min(1F - (elapsedTime - getHoldTime())/getFadeTime(), 1F);
            }
        }

        public static boolean isHolding() {
            return elapsedTime <= getHoldTime();
        }

        public static boolean isFading() {
            return elapsedTime != 0 && elapsedTime < getFadeTime() + getHoldTime();
        }

        public static void reset() {
            elapsedTime = 0;
        }
    }
}

