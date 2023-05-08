package com.khopan.mods.advancedoverlay.overlay;

import net.minecraft.network.chat.Component;

public enum Latch {
	ON("on", true),
	OFF("off", false);

	private final String key;
	private final boolean value;

	private Latch(String key, boolean value) {
		this.key = key;
		this.value = value;
	}

	public Component getDisplayName() {
		return Component.translatable("options." + this.key);
	}

	public boolean toBoolean() {
		return this.value;
	}
}
