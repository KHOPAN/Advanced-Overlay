package com.khopan.advancedoverlay.screen.channellist;

import org.lwjgl.glfw.GLFW;

import com.khopan.advancedoverlay.channel.Channel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class ChannelEntry extends ObjectSelectionList.Entry<ChannelEntry> {
	private final ChannelList list;
	private final Channel channel;

	private String name;

	public ChannelEntry(ChannelList list, Channel channel) {
		this.list = list;
		this.channel = channel;
		this.name = this.channel.getChannelName();
	}

	@Override
	public Component getNarration() {
		return Component.literal(this.name);
	}

	@Override
	public void render(PoseStack stack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTick) {
		Font font = this.list.screen.font();
		font.drawShadow(stack, this.name, (((float) this.list.screen.width) - ((float) font.width(this.name))) * 0.5f, ((float) top) + (((float) height) - ((float) font.lineHeight)) * 0.5f, 0xFFFFFF);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			this.list.setSelected(this);
			return true;
		}

		return false;
	}

	public Channel getChannel() {
		return this.channel;
	}
}
