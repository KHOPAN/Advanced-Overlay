package com.khopan.advancedoverlay.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

public class OverlayRenderer {
	private OverlayRenderer() {}

	public static void render(PoseStack stack, float tickDelta) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.font.drawShadow(stack, "Hello, world!", 0.0f, 0.0f, 0xFFFFFF);
	}
}
