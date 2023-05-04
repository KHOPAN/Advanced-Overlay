package com.khopan.mods.advancedoverlay.api.extension;

import com.khopan.mods.advancedoverlay.ModuleRegistry;

public interface IAdvancedOverlayExtension {
	public void initialize();
	public void registerModule(ModuleRegistry registry);
}
