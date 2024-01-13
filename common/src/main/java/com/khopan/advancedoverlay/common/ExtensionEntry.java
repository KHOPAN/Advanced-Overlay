package com.khopan.advancedoverlay.common;

import java.util.Map.Entry;

import com.khopan.advancedoverlay.common.api.IExtension;

public class ExtensionEntry implements Entry<Class<? extends IExtension>, IExtension> {
	private final Class<? extends IExtension> key;
	private final IExtension value;

	public ExtensionEntry(Class<? extends IExtension> key, IExtension value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public Class<? extends IExtension> getKey() {
		return this.key;
	}

	@Override
	public IExtension getValue() {
		return this.value;
	}

	@Override
	public IExtension setValue(IExtension value) {
		throw new UnsupportedOperationException("This Entry cannot set its value");
	}
}
