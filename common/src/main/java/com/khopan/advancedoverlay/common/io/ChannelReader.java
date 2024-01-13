package com.khopan.advancedoverlay.common.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.khopan.advancedoverlay.common.screen.ChannelListScreen.ChannelEntry;
import com.khopan.advancedoverlay.common.screen.EditModuleScreen.ModuleEntry;

public class ChannelReader extends FileReader {
	public ChannelReader(File file) throws IOException {
		super(file);
	}

	public List<ChannelEntry> readChannelList() {
		return null;
	}

	public ChannelEntry readChannel() {
		return null;
	}

	public ModuleEntry readModule() {
		return null;
	}
}
