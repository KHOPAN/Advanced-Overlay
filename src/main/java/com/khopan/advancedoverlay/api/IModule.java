package com.khopan.advancedoverlay.api;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public interface IModule {
	boolean isVisible();
	int getWidth();
	int getHeight();
	void render(PoseStack stack, float tickDelta, int x, int y, int width, int height);

	default void tick(Minecraft minecraft) {

	}

	default int getBackgroundColor() {
		return 0x66000000;
	}

	default boolean hasSettings() {
		return false;
	}

	default Screen openSettingsScreen(Screen screen) {
		return null;
	}
}
