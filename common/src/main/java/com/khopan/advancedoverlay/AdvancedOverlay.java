package com.khopan.advancedoverlay;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.khopan.advancedoverlay.common.data.Module;
import com.khopan.advancedoverlay.common.screen.ChannelListScreen.ChannelEntry;

public final class AdvancedOverlay {
	public static final String MOD_NAME = "Advanced Overlay";
	public static final String MOD_ID = "advancedoverlay";

	public static final Logger LOGGER = LoggerFactory.getLogger(AdvancedOverlay.MOD_NAME);

	public static List<ChannelEntry> getChannelList() {
		return Collections.unmodifiableList(AdvancedOverlayInternal.CHANNEL_LIST);
	}

	public static List<Module> getModuleList() {
		return Collections.unmodifiableList(AdvancedOverlayInternal.MODULE_LIST);
	}

	public static void forEachChannel(Consumer<ChannelEntry> consumer) {
		if(consumer == null) {
			return;
		}

		for(int i = 0; i < AdvancedOverlayInternal.CHANNEL_LIST.size(); i++) {
			consumer.accept(AdvancedOverlayInternal.CHANNEL_LIST.get(i));
		}
	}

	public static void forEachModule(Consumer<Module> consumer) {
		if(consumer == null) {
			return;
		}

		for(int i = 0; i < AdvancedOverlayInternal.MODULE_LIST.size(); i++) {
			consumer.accept(AdvancedOverlayInternal.MODULE_LIST.get(i));
		}
	}
}
