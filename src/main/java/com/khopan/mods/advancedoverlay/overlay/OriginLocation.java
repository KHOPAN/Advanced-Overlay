package com.khopan.mods.advancedoverlay.overlay;

import com.khopan.mods.advancedoverlay.Text;

import net.minecraft.network.chat.Component;

public enum OriginLocation {
	TOP_LEFT("topLeft"),
	TOP_CENTER("topCenter"),
	TOP_RIGHT("topRight"),
	CENTER_LEFT("centerLeft"),
	CENTER("center"),
	CENTER_RIGHT("centerRight"),
	BOTTOM_LEFT("bottomLeft"),
	BOTTOM_CENTER("bottomCenter"),
	BOTTOM_RIGHT("bottomRight");

	public static final OriginLocation DEFAULT = OriginLocation.CENTER;

	private final String key;

	private OriginLocation(String key) {
		this.key = key;
	}

	public Component getDisplayName() {
		return Text.config("editPanel.originLocation." + this.key);
	}
}
