package com.khopan.advancedoverlay.renderer;

import com.khopan.advancedoverlay.api.IModule;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class OverlayRenderer {
	private OverlayRenderer() {}

	public static void render(PoseStack stack, float tickDelta, IModule[] moduleList) {
		if(moduleList == null || moduleList.length == 0) {
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		Window window = minecraft.getWindow();
		int screenWidth = window.getGuiScaledWidth();
		int screenHeight = window.getGuiScaledHeight();
		int maxWidth = 0;
		int totalHeight = 0;

		for(int i = 0; i < moduleList.length; i++) {
			int width = moduleList[i].getWidth();
			maxWidth = Math.max(maxWidth, width);
			totalHeight += moduleList[i].getHeight();
		}

		int x = screenWidth - maxWidth - 5;
		int y = (int) Math.round((((double) screenHeight) - ((double) totalHeight)) * 0.5d);
		Gui.fill(stack, x - 1, y - 1, x + maxWidth + 1, y + totalHeight + 1, 0x66000000);
		
		for(int i = 0; i < moduleList.length; i++) {
			int height = moduleList[i].getHeight();
			moduleList[i].render(stack, tickDelta, x, y, maxWidth, height);
			y += height;
		}
	}
}
