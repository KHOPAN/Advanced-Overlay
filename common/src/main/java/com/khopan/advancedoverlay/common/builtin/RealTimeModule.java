package com.khopan.advancedoverlay.common.builtin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ibm.icu.util.Calendar;
import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.api.annotation.Name;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

@Name(RealTimeModule.MODULE_NAME)
public class RealTimeModule implements IModule {
	public static final String MODULE_NAME = "Real Time";

	private final DateFormat format;

	private Minecraft minecraft;
	private String text;

	public RealTimeModule() {
		this.format = new SimpleDateFormat("HH:mm.ss.SSS");
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
		if(this.minecraft == null) {
			return;
		}

		this.minecraft.font.drawShadow(stack, this.text, x, y, 0xFFFFFF);
	}

	@Override
	public void tick(Minecraft minecraft) {
		this.minecraft = minecraft;
		Calendar calendar = Calendar.getInstance();
		this.text = this.format.format(calendar.getTime());
	}
}
