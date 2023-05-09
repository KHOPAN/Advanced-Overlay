package com.khopan.mods.advancedoverlay;

import com.khopan.mods.advancedoverlay.overlay.Latch;
import com.khopan.mods.advancedoverlay.overlay.OriginLocation;
import com.khopan.mods.advancedoverlay.overlay.OverlayLocation;

import net.minecraft.network.chat.Component;

public class PanelHolder {
	public static final Component DEFAULT_NAME = Component.literal("New Panel");

	public Component name;
	public Latch visible;
	public OverlayLocation overlayLocation;
	public OriginLocation originLocation;
	public Latch autoInFrame;

	public PanelHolder(Component name) {
		if(name == null) {
			name = PanelHolder.DEFAULT_NAME;
		}

		this.name = name;
		this.visible = Latch.ON;
		this.overlayLocation = OverlayLocation.DEFAULT;
		this.originLocation = OriginLocation.DEFAULT;
		this.autoInFrame = Latch.OFF;
	}

	public PanelHolder() {
		this(PanelHolder.DEFAULT_NAME);
	}
}
