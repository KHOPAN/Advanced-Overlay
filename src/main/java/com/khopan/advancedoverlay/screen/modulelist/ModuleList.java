package com.khopan.advancedoverlay.screen.modulelist;

import java.util.List;
import java.util.function.Consumer;

import com.khopan.advancedoverlay.data.Module;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.ObjectSelectionList;

public class ModuleList extends ObjectSelectionList<ModuleEntry> {
	final MinecraftableScreen screen;

	private final Consumer<Module> onSelected;

	public ModuleList(MinecraftableScreen screen, boolean selection, Consumer<Module> onSelected) {
		super(screen.minecraft(), screen.width, screen.height, 32, screen.height - (selection ? 40 : 64), 20);
		this.screen = screen;
		this.onSelected = onSelected;
	}

	@Override
	public void setSelected(ModuleEntry selected) {
		super.setSelected(selected);

		if(this.onSelected != null) {
			this.onSelected.accept(selected.getModule());
		}
	}

	public void addList(List<Module> moduleList) {
		for(int i = 0; i < moduleList.size(); i++) {
			Module module = moduleList.get(i);
			this.addEntry(new ModuleEntry(this, module));
		}
	}

	@Override
	protected void renderBackground(PoseStack stack) {
		this.screen.renderBackground(stack);
	}
}
