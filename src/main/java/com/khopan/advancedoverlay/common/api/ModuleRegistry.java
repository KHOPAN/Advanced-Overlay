package com.khopan.advancedoverlay.common.api;

@FunctionalInterface
public interface ModuleRegistry {
	void register(Class<? extends IModule> moduleClass);
}
