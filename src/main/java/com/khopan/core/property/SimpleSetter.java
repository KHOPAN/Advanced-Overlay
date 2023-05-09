package com.khopan.core.property;

@FunctionalInterface
public interface SimpleSetter<T> {
	public void set(T value);
}
