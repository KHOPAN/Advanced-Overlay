package com.khopan.advancedoverlay.screen.channellist;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.Channel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.ObjectSelectionList;

@Environment(EnvType.CLIENT)
public class ChannelList extends ObjectSelectionList<ChannelEntry> {
	final ChannelListScreen screen;

	public ChannelList(ChannelListScreen screen) {
		super(screen.minecraft(), screen.width, screen.height, 32, screen.height - 64, 20);
		this.screen = screen;

		for(Channel channel : AdvancedOverlay.CHANNEL_LIST) {
			this.addEntry(new ChannelEntry(this, channel));
		}
	}

	@Override
	protected void renderBackground(PoseStack stack) {
		this.screen.renderBackground(stack);
	}
}
