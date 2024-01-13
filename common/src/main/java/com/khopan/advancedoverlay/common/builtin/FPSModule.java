package com.khopan.advancedoverlay.common.builtin;

import java.util.Locale;

import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.api.annotation.Name;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

@Name(FPSModule.MODULE_NAME)
public class FPSModule implements IModule {
	public static final String MODULE_NAME = "FPS";

	private Minecraft minecraft;
	private String text;

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public int getWidth() {
		return this.minecraft.font.width(this.text);
	}

	@Override
	public int getHeight() {
		return this.minecraft.font.lineHeight;
	}

	@Override
	public void render(PoseStack stack, float tickDelta, int x, int y, int width, int height) {
		this.minecraft.font.drawShadow(stack, this.text, x, y, 0xFFFFFF);
	}

	@Override
	public void tick(Minecraft minecraft) {
		this.minecraft = minecraft;
		this.text = String.format(Locale.ROOT, "%d FPS", this.minecraft.getFps());
	}
}
