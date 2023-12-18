package com.khopan.advancedoverlay;

import net.minecraft.network.chat.Component;

public interface Text {
	public static final Component ADVANCED_OVERLAY_CONFIG = Component.translatableWithFallback("config.advancedoverlay.title", "Advanced Overlay Config");
	public static final Component CHANNEL_LIST = Component.translatableWithFallback("config.advancedoverlay.channel.list.title", "Channel List");
	public static final Component EDIT_CHANNEL = Component.translatableWithFallback("config.advancedoverlay.channel.edit.title", "Edit Channel");
	public static final Component NEW_CHANNEL = Component.translatableWithFallback("config.advancedoverlay.channel.new.title", "New Channel");
	public static final Component DELETE_CHANNEL = Component.translatableWithFallback("config.advancedoverlay.channel.delete.title", "Delete Channel");
}
