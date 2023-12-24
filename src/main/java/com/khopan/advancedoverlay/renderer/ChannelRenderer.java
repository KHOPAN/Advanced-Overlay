package com.khopan.advancedoverlay.renderer;

import java.awt.Dimension;

import com.khopan.advancedoverlay.api.IModule;
import com.khopan.advancedoverlay.data.Channel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ChannelRenderer {
	private ChannelRenderer() {}

	public static void render(PoseStack stack, float tickDelta, Channel channel) {
		IModule[] moduleList = channel.getModuleInstanceList();

		if(moduleList == null || moduleList.length == 0) {
			return;
		}

		Dimension[] sizeList = new Dimension[moduleList.length];

		for(int i = 0; i < sizeList.length; i++) {
			IModule module = moduleList[i];
			sizeList[i] = new Dimension(module.getWidth(), module.getHeight());
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
		int screenWidth = minecraft.screen.width;
		int screenHeight = minecraft.screen.height;
		int x = screenWidth - maxWidth - 5;
		int y = (int) Math.round((((double) screenHeight) - ((double) totalHeight)) * 0.5d);
		Gui.fill(stack, x - 1, y - 1, x + maxWidth + 1, y + totalHeight + 1, 0x66000000);

		for(int i = 0; i < moduleList.length; i++) {
			Dimension size = sizeList[i];
			moduleList[i].render(stack, tickDelta, x, y, maxWidth, size.height);
			y += size.height;
		}
	}
}
