package com.khopan.advancedoverlay.screen.channellist;

import com.khopan.advancedoverlay.Text;
import com.khopan.advancedoverlay.screen.NewEditChannelScreen;
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
		if(this.list == null) {
			this.list = new ChannelList(this);
		} else {
			this.list.refresh();
		}

		this.addWidget(this.list);
		double halfWidth = this.width * 0.5d;
		int top = this.height - 52;
		int left = (int) Math.round(halfWidth - 154.0d);
		int bottom = this.height - 28;
		int right = (int) Math.round(halfWidth + 4.0d);
		this.addRenderableWidget(Button.builder(Text.EDIT_CHANNEL, button -> {}).bounds(left, top, 150, 20).build());
		this.addRenderableWidget(Button.builder(Text.NEW_CHANNEL, button -> this.minecraft.setScreen(new NewEditChannelScreen(this, this.list.addNewChannel(), false))).bounds(right, top, 150, 20).build());
		this.addRenderableWidget(Button.builder(Text.DELETE_CHANNEL, button -> {}).bounds(left, bottom, 150, 20).build());
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.minecraft.setScreen(this.lastScreen)).bounds(right, bottom, 150, 20).build());
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.list.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.list.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTick);
	}

	public Minecraft minecraft() {
		return this.minecraft;
	}

	public Font font() {
		return this.font;
	}
}
