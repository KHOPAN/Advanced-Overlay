package com.khopan.core.property;

@FunctionalInterface
public interface Updater<T> {
	public void valueUpdated(T value);
}
