package com.khopan.advancedoverlay.data;

import java.util.function.Supplier;

import com.khopan.advancedoverlay.api.IModule;

public class Module {
	private final String name;
	private final Supplier<IModule> supplier;

	public Module(String name, Supplier<IModule> supplier) {
		this.name = name;
		this.supplier = supplier;
	}

	public String getModuleName() {
		return this.name;
	}

	public Supplier<IModule> getSupplier() {
		return this.supplier;
	}

	public IModule newModule() {
		return this.supplier.get();
	}
}
