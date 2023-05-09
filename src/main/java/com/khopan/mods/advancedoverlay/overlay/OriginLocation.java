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

	public int getIndex() {
		if(OriginLocation.TOP_LEFT.equals(this)) {
			return 0;
		} else if(OriginLocation.TOP_CENTER.equals(this)) {
			return 1;
		} else if(OriginLocation.TOP_RIGHT.equals(this)) {
			return 2;
		} else if(OriginLocation.CENTER_LEFT.equals(this)) {
			return 3;
		} else if(OriginLocation.CENTER.equals(this)) {
			return 4;
		} else if(OriginLocation.CENTER_RIGHT.equals(this)) {
			return 5;
		} else if(OriginLocation.BOTTOM_LEFT.equals(this)) {
			return 6;
		} else if(OriginLocation.BOTTOM_CENTER.equals(this)) {
			return 7;
		} else if(OriginLocation.BOTTOM_RIGHT.equals(this)) {
			return 8;
		} else {
			return -1;
		}
	}

	public static OriginLocation fromIndex(int index) {
		if(index == 0) {
			return OriginLocation.TOP_LEFT;
		} else if(index == 1) {
			return OriginLocation.TOP_CENTER;
		} else if(index == 2) {
			return OriginLocation.TOP_RIGHT;
		} else if(index == 3) {
			return OriginLocation.CENTER_LEFT;
		} else if(index == 4) {
			return OriginLocation.CENTER;
		} else if(index == 5) {
			return OriginLocation.CENTER_RIGHT;
		} else if(index == 6) {
			return OriginLocation.BOTTOM_LEFT;
		} else if(index == 7) {
			return OriginLocation.BOTTOM_CENTER;
		} else if(index == 8) {
			return OriginLocation.BOTTOM_RIGHT;
		} else {
			throw new IllegalArgumentException("Invalid index, you probably have a broken type stored");
		}
	}
}
