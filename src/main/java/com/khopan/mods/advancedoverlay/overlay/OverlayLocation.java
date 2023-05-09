package com.khopan.mods.advancedoverlay.overlay;

import com.khopan.mods.advancedoverlay.Text;

import net.minecraft.network.chat.Component;

public enum OverlayLocation {
	TOP_LEFT("topLeft", (width, size, margin) -> margin, (height, size, margin) -> margin),
	TOP_CENTER("topCenter", (width, size, margin) -> (width - size) / 2, (height, size, margin) -> margin),
	TOP_RIGHT("topRight", (width, size, margin) -> width - size - margin, (height, size, margin) -> margin),
	CENTER_LEFT("centerLeft", (width, size, margin) -> margin, (height, size, margin) -> (height - size) / 2),
	CENTER("center", (width, size, margin) -> (width - size) / 2, (height, size, margin) -> (height - size) / 2),
	CENTER_RIGHT("centerRight", (width, size, margin) -> width - size - margin, (height, size, margin) -> (height - size) / 2),
	BOTTOM_LEFT("bottomLeft", (width, size, margin) -> margin, (height, size, margin) -> height - size - margin),
	BOTTOM_CENTER("bottomCenter", (width, size, margin) -> (width - size) / 2, (height, size, margin) -> height - size - margin),
	BOTTOM_RIGHT("bottomRight", (width, size, margin) -> width - size - margin, (height, size, margin) -> height - size - margin),
	CUSTOM("custom", null, null);

	public static final OverlayLocation DEFAULT = OverlayLocation.TOP_LEFT;
	public static final int MARGIN = 5;

	private final String key;
	private final LocationCalculator xFunction;
	private final LocationCalculator yFunction;

	private OverlayLocation(String key, LocationCalculator xFunction, LocationCalculator yFunction) {
		this.key = key;
		this.xFunction = xFunction;
		this.yFunction = yFunction;
	}

	public int getX(int width, int size) {
		if(this.xFunction == null) {
			return -1;
		}

		return this.xFunction.calculate(width, size, OverlayLocation.MARGIN);
	}

	public int getX(int width, int size, int margin) {
		if(this.xFunction == null) {
			return -1;
		}

		return this.xFunction.calculate(width, size, margin);
	}

	public int getY(int height, int size) {
		if(this.yFunction == null) {
			return -1;
		}

		return this.yFunction.calculate(height, size, OverlayLocation.MARGIN);
	}

	public int getY(int height, int size, int margin) {
		if(this.yFunction == null) {
			return -1;
		}

		return this.yFunction.calculate(height, size, margin);
	}

	public Component getDisplayName() {
		return Text.config("editPanel.overlayLocation." + this.key);
	}

	public int getIndex() {
		if(OverlayLocation.TOP_LEFT.equals(this)) {
			return 0;
		} else if(OverlayLocation.TOP_CENTER.equals(this)) {
			return 1;
		} else if(OverlayLocation.TOP_RIGHT.equals(this)) {
			return 2;
		} else if(OverlayLocation.CENTER_LEFT.equals(this)) {
			return 3;
		} else if(OverlayLocation.CENTER.equals(this)) {
			return 4;
		} else if(OverlayLocation.CENTER_RIGHT.equals(this)) {
			return 5;
		} else if(OverlayLocation.BOTTOM_LEFT.equals(this)) {
			return 6;
		} else if(OverlayLocation.BOTTOM_CENTER.equals(this)) {
			return 7;
		} else if(OverlayLocation.BOTTOM_RIGHT.equals(this)) {
			return 8;
		} else if(OverlayLocation.CUSTOM.equals(this)) {
			return 9;
		} else {
			return -1;
		}
	}

	public static OverlayLocation fromIndex(int index) {
		if(index == 0) {
			return OverlayLocation.TOP_LEFT;
		} else if(index == 1) {
			return OverlayLocation.TOP_CENTER;
		} else if(index == 2) {
			return OverlayLocation.TOP_RIGHT;
		} else if(index == 3) {
			return OverlayLocation.CENTER_LEFT;
		} else if(index == 4) {
			return OverlayLocation.CENTER;
		} else if(index == 5) {
			return OverlayLocation.CENTER_RIGHT;
		} else if(index == 6) {
			return OverlayLocation.BOTTOM_LEFT;
		} else if(index == 7) {
			return OverlayLocation.BOTTOM_CENTER;
		} else if(index == 8) {
			return OverlayLocation.BOTTOM_RIGHT;
		} else if(index == 9) {
			return OverlayLocation.CUSTOM;
		} else {
			throw new IllegalArgumentException("Invalid index, you probably have a broken type stored");
		}
	}

	private interface LocationCalculator {
		public int calculate(int width, int size, int margin);
	}
}
