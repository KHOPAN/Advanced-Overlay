package com.khopan.mods.advancedoverlay.client.screen;

import com.khopan.mods.advancedoverlay.Text;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends ReturnableScreen {
	public static final Component TITLE = Text.config("title");

	public ConfigScreen(Screen screen) {
		super(ConfigScreen.TITLE, screen);
	}

	@Override
	protected void init() {
		GridLayout layout = new GridLayout();
		layout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
		RowHelper row = layout.createRowHelper(2);
		row.addChild(this.setScreenButton(PanelSettingsScreen.TITLE, new PanelSettingsScreen(this)));
		row.addChild(this.doneButton(), 2, layout.newCellSettings().paddingTop(6));
		layout.arrangeElements();
		FrameLayout.alignInRectangle(layout, 0, 0, this.width, this.height, 0.5f, 0.5f);
		layout.visitWidgets(this :: addRenderableWidget);
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float tickDelta) {
		this.renderBackground(stack);
		GuiComponent.drawCenteredString(stack, this.font, this.title, (int) Math.round(this.width * 0.5d), 15, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, tickDelta);
	}
}
