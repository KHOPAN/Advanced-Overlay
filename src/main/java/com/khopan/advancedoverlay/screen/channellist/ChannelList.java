package com.khopan.advancedoverlay.screen.channellist;

import java.util.List;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.Channel;
import com.khopan.advancedoverlay.Text;
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
		this.refresh();
	}

	public Channel addNewChannel() {
		Channel channel = new Channel(Text.NEW_CHANNEL.getString());
		AdvancedOverlay.CHANNEL_LIST.add(channel);
		ChannelEntry entry = new ChannelEntry(this, channel);
		this.addEntry(entry);
		this.setSelected(entry);
		this.centerScrollOn(entry);
		return channel;
	}

	public void refresh() {
		ChannelEntry selected = this.getSelected();
		List<ChannelEntry> entryList = this.children();
		int index = entryList.indexOf(selected);
		this.clearEntries();

		for(Channel channel : AdvancedOverlay.CHANNEL_LIST) {
			this.addEntry(new ChannelEntry(this, channel));
		}

		List<ChannelEntry> newList = this.children();

		if(newList.isEmpty() || selected == null) {
			return;
		}

		if(index == -1) {
			this.setSelected(newList.get(0));
			return;
		}

		int maxIndex = newList.size() - 1;

		if(index > maxIndex) {
			this.setSelected(newList.get(maxIndex));
			return;
		}

		this.setSelected(newList.get(index));
	}

	@Override
	protected void renderBackground(PoseStack stack) {
		this.screen.renderBackground(stack);
	}
}
