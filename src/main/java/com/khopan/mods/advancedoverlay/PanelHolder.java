package com.khopan.mods.advancedoverlay;

import com.khopan.mods.advancedoverlay.overlay.OverlayLocation;

import net.minecraft.network.chat.Component;

public class PanelHolder {
	public static final Component DEFAULT_NAME = Component.literal("New Panel");

	public Component name;
	public OverlayLocation overlayLocation;

	public PanelHolder(Component name) {
		if(name == null) {
			name = PanelHolder.DEFAULT_NAME;
		}

		this.name = name;
		this.overlayLocation = OverlayLocation.DEFAULT;
	}

	public PanelHolder() {
		this(PanelHolder.DEFAULT_NAME);
	}
}
