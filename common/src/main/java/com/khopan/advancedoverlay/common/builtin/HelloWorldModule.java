package com.khopan.advancedoverlay.common.builtin;

import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.api.annotation.Name;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

@Name(HelloWorldModule.MODULE_NAME)
public class HelloWorldModule implements IModule {
	public static final String MODULE_NAME = "Hello World";
	public static final String TEXT = "Hello, world!";

	private final Minecraft minecraft;

	public HelloWorldModule() {
		this.minecraft = Minecraft.getInstance();
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public int getWidth() {
		return this.minecraft.font.width(HelloWorldModule.TEXT);
	}

	@Override
	public int getHeight() {
		return this.minecraft.font.lineHeight;
	}

	@Override
	public void render(PoseStack stack, float tickDelta, int x, int y, int width, int height) {
		this.minecraft.font.drawShadow(stack, HelloWorldModule.TEXT, x, y, 0xFFFFFF);
	}
}
