package com.khopan.advancedoverlay.screen.modulelist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class MinecraftableScreen extends Screen {
	public MinecraftableScreen(Component component) {
		super(component);
	}

	public Minecraft minecraft() {
		return this.minecraft;
	}

	public Font font() {
		return this.font;
	}
}
