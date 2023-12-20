package com.khopan.advancedoverlay.builtin.helloworld;

import com.khopan.advancedoverlay.api.IModule;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

public class HelloWorldModule implements IModule {
	private final Minecraft minecraft;
	private final String text;

	public HelloWorldModule() {
		this.minecraft = Minecraft.getInstance();
		this.text = "Hello, world!";
	}

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
}
