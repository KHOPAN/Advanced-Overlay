package com.khopan.advancedoverlay.common.builtin;

import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.api.annotation.Name;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

@Name(PositionModule.MODULE_NAME)
public class PositionModule implements IModule {
	public static final String MODULE_NAME = "Position";

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
		Vec3 position = this.minecraft.player.position();
		this.text = String.format("X: %d Y: %d Z: %d", (int) Math.round(position.x), (int) Math.round(position.y), (int) Math.round(position.z));
	}
}
