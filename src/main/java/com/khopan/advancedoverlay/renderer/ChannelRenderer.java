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
		int maxIndex = moduleSize - 1;
		Dimension[] sizeList = new Dimension[moduleSize];
		Minecraft minecraft = Minecraft.getInstance();

		for(int i = 0; i < sizeList.length; i++) {
			ModuleEntry entry = channel.moduleList.get(i);
			entry.instance.tick(minecraft);
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

		Window window = minecraft.getWindow();
		int screenWidth = window.getGuiScaledWidth();
		int xLocation = channel.location.getX();
		int remainingWidth = (int) Math.round(((double) screenWidth) - ((double) maxWidth));
		double horizontal = Math.min(1.0d, Math.max(0.0d, channel.horizontalSpacing));
		int x;

		if(xLocation == 0) {
			x = (int) Math.round(((double) remainingWidth) * horizontal);
		} else if(xLocation == 1) {
			x = (int) Math.round((((double) screenWidth) - ((double) maxWidth)) * 0.5d);
		} else {
			x = (int) Math.round(((double) remainingWidth) * (1.0d - horizontal));
		}

		int screenHeight = window.getGuiScaledHeight();
		int yLocation = channel.location.getY();
		int remainingHeight = (int) Math.round(((double) screenHeight) - ((double) totalHeight));
		double vertical = Math.min(1.0d, Math.max(0.0d, channel.verticalSpacing));
		int y;

		if(yLocation == 0) {
			y = (int) Math.round(((double) remainingHeight) * vertical);
		} else if(yLocation == 1) {
			y = (int) Math.round((((double) screenHeight) - ((double) totalHeight)) * 0.5d);
		} else {
			y = (int) Math.round(((double) remainingHeight) * (1.0d - vertical));
		}

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
