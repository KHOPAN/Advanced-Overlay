package com.khopan.advancedoverlay.screen;

import com.khopan.advancedoverlay.Text;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;

public class NewEditChannelScreen extends Screen {
	private final Screen lastScreen;

	private GridLayout layout;

	public NewEditChannelScreen(Screen lastScreen, boolean edit) {
		super(edit ? Text.EDIT_CHANNEL : Text.NEW_CHANNEL);
		this.lastScreen = lastScreen;
	}

	@Override
	protected void init() {
		this.layout = new GridLayout();
		this.layout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
		RowHelper helper = this.layout.createRowHelper(2);
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_DONE, button -> this.minecraft.setScreen(this.lastScreen)).build(), 2);
		this.layout.arrangeElements();
		FrameLayout.alignInRectangle(this.layout, 0, 0, this.width, this.height, 0.5f, 0.5f);
		this.layout.visitWidgets(this :: addRenderableWidget);
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.renderDirtBackground(stack);
		super.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.layout.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
	}
}
