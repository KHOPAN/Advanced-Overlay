package com.khopan.advancedoverlay.screen.modulelist;

import com.khopan.advancedoverlay.Text;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;

public class EditModuleScreen extends Screen {
	private final Screen lastScreen;

	private ModuleList list;

	public EditModuleScreen(Screen lastScreen) {
		super(Text.EDIT_MODULE);
		this.lastScreen = lastScreen;
	}

	@Override
	protected void init() {
		this.list = new ModuleList(this);
		this.addWidget(this.list);
		double halfWidth = this.width * 0.5d;
		int top = this.height - 52;
		int left = (int) Math.round(halfWidth - 154.0d);
		int bottom = this.height - 28;
		int right = (int) Math.round(halfWidth + 4.0d);
		this.addRenderableWidget(Button.builder(Text.MODULE_SETTINGS, button -> {}).bounds(left, top, 150, 20).build());
		this.addRenderableWidget(Button.builder(Text.ADD_MODULE, button -> {}).bounds(right, top, 150, 20).build());
		this.addRenderableWidget(Button.builder(Text.REMOVE_MODULE, button -> {}).bounds(left, bottom, 150, 20).build());
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.minecraft.setScreen(this.lastScreen)).bounds(right, bottom, 150, 20).build());
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.list.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.list.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTick);
	}

	public Minecraft minecraft() {
		return this.minecraft;
	}

	public Font font() {
		return this.font;
	}
}
