package com.khopan.mods.advancedoverlay;

import net.minecraft.network.chat.Component;

public class PanelHolder {
	public static final Component DEFAULT_NAME = Text.config("panelSettings.newPanel", "New Panel");

	public Component name;

	public PanelHolder(Component name) {
		if(name == null) {
			name = PanelHolder.DEFAULT_NAME;
		}

		this.name = name;
	}

	public PanelHolder() {
		this(PanelHolder.DEFAULT_NAME);
	}
}
