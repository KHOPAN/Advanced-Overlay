package com.khopan.advancedoverlay.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.khopan.advancedoverlay.renderer.OverlayRenderer;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Gui;

@Mixin(Gui.class)
public abstract class GuiMixin {
	@Inject(at=@At("TAIL"), method="render", slice=@Slice(from=@At(value="INVOKE", target="Lnet/minecraft/client/gui/components/PlayerTabOverlay;render(Lnet/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/scores/Scoreboard;Lnet/minecraft/world/scores/Objective;)V")))
	public void render(PoseStack poseStack, float tickDelta, CallbackInfo info) {
		OverlayRenderer.render(poseStack, tickDelta);
	}
}
