package com.khopan.mods.advancedoverlay.client.screen;

import com.khopan.mods.advancedoverlay.AdvancedOverlay;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public abstract class ReturnableScreen extends Screen {
	private final Screen screen;

	public ReturnableScreen(Component title, Screen screen) {
		super(title);
		this.screen = screen;
	}

	@Override
	public void onClose() {
		this.returnToLastScreen();
	}

	public void returnToLastScreen() {
		this.minecraft.setScreen(this.screen);
	}

	public Button doneButton() {
		return Button.builder(CommonComponents.GUI_DONE, button -> {
			AdvancedOverlay.savePanel();
			this.returnToLastScreen();
		}).width(200).build();
	}

	public Button setScreenButton(Component title, Screen screen) {
		return Button.builder(title, button -> this.minecraft.setScreen(screen)).build();
	}
}
