package com.khopan.advancedoverlay.api;

import com.khopan.advancedoverlay.ModuleRegistry;

public interface IExtension {
	void initialize();
	String getName();
	void registerModules(ModuleRegistry registry);
}
