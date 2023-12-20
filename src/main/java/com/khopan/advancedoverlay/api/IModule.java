package com.khopan.advancedoverlay.api;

import com.mojang.blaze3d.vertex.PoseStack;

public interface IModule {
	boolean isVisible();
	int getWidth();
	int getHeight();
	void render(PoseStack stack, float tickDelta, int x, int y, int width, int height);
}
