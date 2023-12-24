package com.khopan.advancedoverlay.api;

@FunctionalInterface
public interface IExtension {
	void registerModules(ModuleRegistry registry);
	default void initialize() {}
}
