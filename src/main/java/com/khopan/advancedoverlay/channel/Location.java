package com.khopan.advancedoverlay.channel;

import com.khopan.advancedoverlay.Text;

import net.minecraft.network.chat.Component;

public enum Location {
	TOP_LEFT(Text.TOP_LEFT),
	TOP_CENTER(Text.TOP_CENTER),
	TOP_RIGHT(Text.TOP_RIGHT),
	CENTER_LEFT(Text.CENTER_LEFT),
	CENTER(Text.CENTER),
	CENTER_RIGHT(Text.CENTER_RIGHT),
	BOTTOM_LEFT(Text.BOTTOM_LEFT),
	BOTTOM_CENTER(Text.BOTTOM_CENTER),
	BOTTOM_RIGHT(Text.BOTTOM_RIGHT);

	public static final Location DEFAULT = Location.CENTER_RIGHT;

	private final Component text;

	Location(Component text) {
		this.text = text;
	}

	public Component getText() {
		return this.text;
	}
}
