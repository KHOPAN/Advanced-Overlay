package com.khopan.advancedoverlay.common.api;

@FunctionalInterface
public interface IExtension {
	void registerModules(ModuleRegistry registry);
	default void initialize() {}
}
