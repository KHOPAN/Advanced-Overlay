package com.khopan.advancedoverlay;

import com.khopan.advancedoverlay.api.IModuleProvider;

public interface ModuleRegistry {
	void register(IModuleProvider provider);
}
