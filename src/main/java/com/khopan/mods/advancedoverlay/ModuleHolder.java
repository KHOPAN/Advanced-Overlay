package com.khopan.mods.advancedoverlay;

import com.khopan.mods.advancedoverlay.api.IModule;
import com.khopan.mods.advancedoverlay.api.extension.IAdvancedOverlayExtension;

import net.minecraft.network.chat.Component;

public class ModuleHolder {
	public final IAdvancedOverlayExtension extension;
	public final IModule module;
	public final Component moduleName;

	ModuleHolder(IAdvancedOverlayExtension extension, IModule module) {
		this.extension = extension;
		this.module = module;
		this.moduleName = this.module.getModuleName();
	}
}
