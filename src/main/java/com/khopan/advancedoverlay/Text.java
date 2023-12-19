package com.khopan.advancedoverlay;

import net.minecraft.network.chat.Component;

public interface Text {
	public static final Component ADVANCED_OVERLAY_CONFIG = Component.translatableWithFallback("config.advancedoverlay.title", "Advanced Overlay Config");
	public static final Component CHANNEL_LIST = Component.translatableWithFallback("config.advancedoverlay.channel.list.title", "Channel List");
	public static final Component EDIT_CHANNEL = Component.translatableWithFallback("config.advancedoverlay.channel.edit.title", "Edit Channel");
	public static final Component NEW_CHANNEL = Component.translatableWithFallback("config.advancedoverlay.channel.new.title", "New Channel");
	public static final Component DELETE_CHANNEL = Component.translatableWithFallback("config.advancedoverlay.channel.delete.title", "Delete Channel");
	public static final Component CHANNEL_NAME = Component.translatableWithFallback("config.advancedoverlay.channel.name.title", "Channel Name");
	public static final Component LOCATION = Component.translatableWithFallback("config.advancedoverlay.channel.location.title", "Location");
	public static final Component TOP_LEFT = Component.translatableWithFallback("config.advancedoverlay.channel.location.topleft", "Top Left");
	public static final Component TOP_CENTER = Component.translatableWithFallback("config.advancedoverlay.channel.location.topcenter", "Top Center");
	public static final Component TOP_RIGHT = Component.translatableWithFallback("config.advancedoverlay.channel.location.topright", "Top Right");
	public static final Component CENTER_LEFT = Component.translatableWithFallback("config.advancedoverlay.channel.location.centerleft", "Center Left");
	public static final Component CENTER = Component.translatableWithFallback("config.advancedoverlay.channel.location.center", "Center");
	public static final Component CENTER_RIGHT = Component.translatableWithFallback("config.advancedoverlay.channel.location.centerright", "Center Right");
	public static final Component BOTTOM_LEFT = Component.translatableWithFallback("config.advancedoverlay.channel.location.bottomleft", "Bottom Left");
	public static final Component BOTTOM_CENTER = Component.translatableWithFallback("config.advancedoverlay.channel.location.bottomcenter", "Bottom Center");
	public static final Component BOTTOM_RIGHT = Component.translatableWithFallback("config.advancedoverlay.channel.location.bottomright", "Bottom Right");
	public static final Component LOCATION_CUSTOM = Component.translatableWithFallback("config.advancedoverlay.channel.location.custom", "Custom");
}
