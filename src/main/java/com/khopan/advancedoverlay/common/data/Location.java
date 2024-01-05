package com.khopan.advancedoverlay.common.data;

import com.khopan.advancedoverlay.Text;

import net.minecraft.network.chat.Component;

public enum Location {
	TOP_LEFT(Text.TOP_LEFT, 0, 0),
	TOP_CENTER(Text.TOP_CENTER, 1, 0),
	TOP_RIGHT(Text.TOP_RIGHT, 2, 0),
	CENTER_LEFT(Text.CENTER_LEFT, 0, 1),
	CENTER(Text.CENTER, 1, 1),
	CENTER_RIGHT(Text.CENTER_RIGHT, 2, 1),
	BOTTOM_LEFT(Text.BOTTOM_LEFT, 0, 2),
	BOTTOM_CENTER(Text.BOTTOM_CENTER, 1, 2),
	BOTTOM_RIGHT(Text.BOTTOM_RIGHT, 2, 2);

	public static final Location DEFAULT = Location.CENTER_RIGHT;

	private final Component text;
	private final int x;
	private final int y;

	Location(Component text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}

	public Component getText() {
		return this.text;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
