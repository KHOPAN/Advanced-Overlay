package com.khopan.mods.advancedoverlay;

import com.khopan.mods.advancedoverlay.api.IModule;

@FunctionalInterface
public interface ModuleRegistry {
	public void register(IModule module);
}
