package com.khopan.advancedoverlay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

public class AdvancedOverlay implements ClientModInitializer {
	public static final String MOD_NAME = "Advanced Overlay";
	public static final String MOD_IDENTIFIER = "advancedoverlay";

	public static final Logger LOGGER = LoggerFactory.getLogger(AdvancedOverlay.MOD_NAME);

	@Override
	public void onInitializeClient() {
		AdvancedOverlay.LOGGER.info("Advanced Overlay Initialization");
	}
}
