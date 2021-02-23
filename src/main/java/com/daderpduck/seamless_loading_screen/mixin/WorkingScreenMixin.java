package com.daderpduck.seamless_loading_screen.mixin;

import com.daderpduck.seamless_loading_screen.ScreenshotRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.WorkingScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorkingScreen.class)
public class WorkingScreenMixin {
    @Redirect(method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/WorkingScreen;renderBackground(Lcom/mojang/blaze3d/matrix/MatrixStack;)V"))
    private void render(WorkingScreen screen, MatrixStack matrixStack) {
        ScreenshotRenderer.renderScreenshot(screen, matrixStack);
    }
}