package com.khopan.advancedoverlay.api;

@FunctionalInterface
public interface ModuleRegistry {
	void register(Class<? extends IModule> moduleClass);
}
