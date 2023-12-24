package com.khopan.advancedoverlay.screen.modulelist;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.ObjectSelectionList;

public class ModuleList extends ObjectSelectionList<ModuleEntry> {
	final EditModuleScreen screen;

	public ModuleList(EditModuleScreen screen) {
		super(screen.minecraft(), screen.width, screen.height, 32, screen.height - 64, 20);
		this.screen = screen;
	}

	@Override
	protected void renderBackground(PoseStack stack) {
		this.screen.renderBackground(stack);
	}
}
