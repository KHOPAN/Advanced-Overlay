package com.khopan.advancedoverlay.builtin;

import com.khopan.advancedoverlay.ModuleRegistry;
import com.khopan.advancedoverlay.api.IExtension;
import com.khopan.advancedoverlay.builtin.helloworld.HelloWorldModuleProvider;

public class CoreExtension implements IExtension {
	@Override
	public void initialize() {

	}

	@Override
	public String getName() {
		return "Core";
	}

	@Override
	public void registerModules(ModuleRegistry registry) {
		registry.register(new HelloWorldModuleProvider());
	}
}
