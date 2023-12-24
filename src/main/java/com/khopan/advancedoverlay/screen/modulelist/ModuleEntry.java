package com.khopan.advancedoverlay.screen.modulelist;

import org.lwjgl.glfw.GLFW;

import com.khopan.advancedoverlay.data.Module;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class ModuleEntry extends ObjectSelectionList.Entry<ModuleEntry> {
	private final ModuleList list;
	private final Module module;

	private String name;

	public ModuleEntry(ModuleList list, Module module) {
		this.list = list;
		this.module = module;
		this.name = this.module.getModuleName();
	}

	@Override
	public Component getNarration() {
		return Component.literal(this.name);
	}

	@Override
	public void render(PoseStack stack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTick) {
		Font font = this.list.screen.font();
		font.drawShadow(stack, this.name, (((float) this.list.screen.width) - ((float) font.width(this.name))) * 0.5f, ((float) top) + (((float) height) - ((float) font.lineHeight)) * 0.5f, 0xFFFFFF);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			this.list.setSelected(this);
			return true;
		}

		return false;
	}

	public Module getModule() {
		return this.module;
	}
}
