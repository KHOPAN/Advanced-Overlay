package com.khopan.advancedoverlay.forge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.khopan.advancedoverlay.common.ExtensionEntry;
import com.khopan.advancedoverlay.common.api.IExtension;

public class ExtensionManager {
	static final List<Entry<Class<? extends IExtension>, IExtension>> LIST = new ArrayList<>();

	public static void register(IExtension extension) {
		if(extension == null) {
			return;
		}

		Class<? extends IExtension> extensionClass = extension.getClass();
		ExtensionManager.LIST.add(new ExtensionEntry(extensionClass, extension));
	}

	public static void register(Class<? extends IExtension> extensionClass) {
		if(extensionClass == null) {
			return;
		}

		IExtension extension;

		try {
			extension = extensionClass.getConstructor().newInstance();
		} catch(Throwable Errors) {
			throw new ExceptionInInitializerError(Errors);
		}

		ExtensionManager.LIST.add(new ExtensionEntry(extensionClass, extension));
	}
}
