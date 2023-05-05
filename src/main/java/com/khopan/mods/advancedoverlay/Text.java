package com.khopan.mods.advancedoverlay;

import net.minecraft.network.chat.Component;

public class Text {
	private Text() {}

	public static Component config(String key, String fallback, Object... arguments) {
		return Component.translatableWithFallback("config." + AdvancedOverlay.MOD_ID + (key == null || key.isEmpty() ? "" : "." + key), fallback, arguments);
	}
}
