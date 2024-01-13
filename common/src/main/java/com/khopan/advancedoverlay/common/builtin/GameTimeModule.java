package com.khopan.advancedoverlay.common.builtin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.api.annotation.Name;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

@Name(GameTimeModule.MODULE_NAME)
public class GameTimeModule implements IModule {
	public static final String MODULE_NAME = "Game Time";

	private final DateFormat format;

	private Minecraft minecraft;
	private String text;

	public GameTimeModule() {
		this.format = new SimpleDateFormat("HH:mm");
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

	@Override
	public void tick(Minecraft minecraft) {
		this.minecraft = minecraft;
		long dayTime = this.minecraft.level.getDayTime();
		long wrappedTime = dayTime % 24000L;
		long realTimeMilliseconds = wrappedTime * 3600L;
		this.text = this.format.format(new Date(realTimeMilliseconds));
	}
}
