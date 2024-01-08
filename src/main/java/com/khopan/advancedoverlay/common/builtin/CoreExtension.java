package com.khopan.advancedoverlay.common.builtin;

import com.khopan.advancedoverlay.common.api.IExtension;
import com.khopan.advancedoverlay.common.api.ModuleRegistry;
import com.khopan.advancedoverlay.common.api.annotation.Name;

@Name(CoreExtension.EXTENSION_NAME)
public class CoreExtension implements IExtension {
	public static final String EXTENSION_NAME = "Core";

	@Override
	public void registerModules(ModuleRegistry registry) {
		registry.register(HelloWorldModule.class);
		registry.register(FPSModule.class);
		registry.register(PositionModule.class);
		registry.register(GameTimeModule.class);
		registry.register(RealTimeModule.class);
	}
}
