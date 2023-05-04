package com.khopan.mods.advancedoverlay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class AdvancedOverlay implements ModInitializer {
	public static final String MOD_NAME = "Advanced Overlay";

	public static final Logger LOGGER = LoggerFactory.getLogger(AdvancedOverlay.MOD_NAME);

	@Override
	public void onInitialize() {
		AdvancedOverlay.LOGGER.info("Initializing " + AdvancedOverlay.MOD_NAME);
	}
}
