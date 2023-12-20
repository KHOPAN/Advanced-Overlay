package com.khopan.advancedoverlay.builtin.helloworld;

import com.khopan.advancedoverlay.api.IModule;
import com.khopan.advancedoverlay.api.IModuleProvider;

public class HelloWorldModuleProvider implements IModuleProvider {
	@Override
	public String getModuleName() {
		return "Hello World";
	}

	@Override
	public IModule provideModule() {
		return new HelloWorldModule();
	}
}
