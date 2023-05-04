package com.khopan.mods.advancedoverlay;

import com.khopan.mods.advancedoverlay.api.extension.IAdvancedOverlayExtension;

public class ExtensionHolder {
	public final IAdvancedOverlayExtension extension;

	ExtensionHolder(IAdvancedOverlayExtension extension) {
		if(extension == null) {
			throw new NullPointerException("'extension' cannot be null");
		}

		this.extension = extension;
	}
}
