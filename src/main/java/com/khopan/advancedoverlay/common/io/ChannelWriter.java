package com.khopan.advancedoverlay.common.io;

import java.util.List;

import com.khopan.advancedoverlay.common.screen.ChannelListScreen.ChannelEntry;
import com.khopan.advancedoverlay.common.screen.EditModuleScreen.ModuleEntry;

public class ChannelWriter extends FileWriter {
	public void writeChannelList(List<ChannelEntry> list) {
		int size = list.size();
		this.writeInt(size);

		for(int i = 0; i < size; i++) {
			this.writeChannel(list.get(i));
		}
	}

	public void writeChannel(ChannelEntry channel) {
		this.writeString(channel.name);
		this.writeByte((byte) channel.location.ordinal());
		this.writeDouble(channel.verticalSpacing);
		this.writeDouble(channel.horizontalSpacing);
		this.writeModuleList(channel.moduleList);
	}

	public void writeModuleList(List<ModuleEntry> list) {
		int size = list.size();
		this.writeInt(size);

		for(int i = 0; i < size; i++) {
			this.writeModule(list.get(i));
		}
	}

	public void writeModule(ModuleEntry module) {
		this.writeString(module.name);
		this.writeClass(module.instance.getClass());
	}
}
