package com.khopan.advancedoverlay.builtin;

import com.khopan.advancedoverlay.annotation.Name;
import com.khopan.advancedoverlay.api.IExtension;
import com.khopan.advancedoverlay.api.ModuleRegistry;

@Name(CoreExtension.EXTENSION_NAME)
public class CoreExtension implements IExtension {
	public static final String EXTENSION_NAME = "Core";

	@Override
	public void registerModules(ModuleRegistry registry) {
		registry.register(HelloWorldModule.class);
	}
}
