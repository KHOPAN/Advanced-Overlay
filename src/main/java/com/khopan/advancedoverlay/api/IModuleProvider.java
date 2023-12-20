package com.khopan.advancedoverlay.api;

public interface IModuleProvider {
	String getModuleName();
	IModule provideModule();
}
