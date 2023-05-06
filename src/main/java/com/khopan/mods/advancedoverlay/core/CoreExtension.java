package com.khopan.mods.advancedoverlay.core;

import com.khopan.mods.advancedoverlay.AdvancedOverlay;
import com.khopan.mods.advancedoverlay.ModuleRegistry;
import com.khopan.mods.advancedoverlay.api.extension.IAdvancedOverlayExtension;

public class CoreExtension implements IAdvancedOverlayExtension {
	@Override
	public void initialize() {
		AdvancedOverlay.LOGGER.info("Initializing Core Extension");
	}

	@Override
	public void registerModule(ModuleRegistry registry) {

	}
}
