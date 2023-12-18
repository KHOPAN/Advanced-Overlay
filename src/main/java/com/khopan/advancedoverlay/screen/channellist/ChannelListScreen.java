package com.khopan.advancedoverlay.screen.channellist;

import com.khopan.advancedoverlay.Text;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;

public class ChannelListScreen extends Screen {
	private final Screen lastScreen;

	private ChannelList list;

	public ChannelListScreen(Screen lastScreen) {
		super(Text.CHANNEL_LIST);
		this.lastScreen = lastScreen;
	}

	@Override
	protected void init() {
		this.list = new ChannelList(this);
		this.addWidget(this.list);
		double halfWidth = this.width * 0.5d;
		int top = this.height - 52;
		int left = (int) Math.round(halfWidth - 154.0d);
		int bottom = this.height - 28;
		int right = (int) Math.round(halfWidth + 4.0d);
		this.addRenderableWidget(Button.builder(Text.EDIT_CHANNEL, button -> {}).bounds(left, top, 150, 20).build());
		this.addRenderableWidget(Button.builder(Text.NEW_CHANNEL, button -> {}).bounds(right, top, 150, 20).build());
		this.addRenderableWidget(Button.builder(Text.DELETE_CHANNEL, button -> {}).bounds(left, bottom, 150, 20).build());
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.minecraft.setScreen(this.lastScreen)).bounds(right, bottom, 150, 20).build());
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		this.list.render(poseStack, mouseX, mouseY, partialTick);
		super.render(poseStack, mouseX, mouseY, partialTick);
	}

	public Minecraft minecraft() {
		return this.minecraft;
	}

	public Font font() {
		return this.font;
	}
}
