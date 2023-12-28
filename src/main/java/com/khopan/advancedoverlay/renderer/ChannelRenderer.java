package com.khopan.advancedoverlay.renderer;

import java.awt.Dimension;

import com.khopan.advancedoverlay.screen.ChannelListScreen.ChannelEntry;
import com.khopan.advancedoverlay.screen.EditModuleScreen.ModuleEntry;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ChannelRenderer {
	private ChannelRenderer() {}

	public static void render(PoseStack stack, float tickDelta, ChannelEntry channel) {
		if(stack == null || channel == null || channel.moduleList.isEmpty()) {
			return;
		}

		int moduleSize = channel.moduleList.size();
		Dimension[] sizeList = new Dimension[moduleSize];

		for(int i = 0; i < sizeList.length; i++) {
			ModuleEntry entry = channel.moduleList.get(i);
			sizeList[i] = new Dimension(entry.instance.getWidth(), entry.instance.getHeight());
		}

		int maxWidth = 0;
		int totalHeight = 0;

		for(int i = 0; i < sizeList.length; i++) {
			Dimension size = sizeList[i];
			int width = size.width;
			maxWidth = Math.max(maxWidth, width);
			totalHeight += size.height;
		}

		Minecraft minecraft = Minecraft.getInstance();
		Window window = minecraft.getWindow();
		int screenWidth = window.getGuiScaledWidth();
		int screenHeight = window.getGuiScaledHeight();
		int x = screenWidth - maxWidth - 5;
		int y = (int) Math.round((((double) screenHeight) - ((double) totalHeight)) * 0.5d);
		int maxIndex = moduleSize - 1;
		int left = x - 1;
		int right = x + maxWidth + 1;

		for(int i = 0; i < moduleSize; i++) {
			Dimension size = sizeList[i];
			ModuleEntry entry = channel.moduleList.get(i);
			int background = entry.instance.getBackgroundColor();
			Gui.fill(stack, left, y - (i == 0 ? 1 : 0), right, y + size.height + (i == maxIndex ? 1 : 0), background);
			entry.instance.render(stack, tickDelta, x, y, maxWidth, size.height);
			y += size.height;
		}
	}
}
