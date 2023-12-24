package com.khopan.advancedoverlay.screen.modulelist;

import java.util.function.Consumer;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.Text;
import com.khopan.advancedoverlay.data.Module;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;

public class ModuleSelectionScreen extends MinecraftableScreen {
	private final Screen lastScreen;
	private final Consumer<Module> onAdded;

	private ModuleList list;
	private Button doneButton;

	public ModuleSelectionScreen(Screen lastScreen, Consumer<Module> onAdded) {
		super(Text.SELECT_MODULE);
		this.lastScreen = lastScreen;
		this.onAdded = onAdded;
	}

	@Override
	protected void init() {
		this.list = new ModuleList(this, true, this :: update);
		this.list.addList(AdvancedOverlay.MODULE_LIST);
		this.addWidget(this.list);
		ScreenRectangle rectangle = this.list.getRectangle();
		double halfWidth = ((double) this.width) * 0.5d;
		int bottom = rectangle.bottom();
		int y = (int) Math.round(((double) bottom) + (((double) this.height) - ((double) bottom) - 20.0d) * 0.5d);
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.minecraft.setScreen(this.lastScreen)).bounds((int) Math.round(halfWidth - 154.0d), y, 150, 20).build());
		this.doneButton = Button.builder(CommonComponents.GUI_DONE, this :: done).bounds((int) Math.round(halfWidth + 4.0d), y, 150, 20).build();
		this.addRenderableWidget(this.doneButton);
		this.update(null);
	}

	private void update(Module ignoredModule) {
		ModuleEntry selected = this.list.getSelected();
		this.doneButton.active = selected != null;
	}

	private void done(Button button) {
		ModuleEntry selected = this.list.getSelected();

		if(selected == null) {
			return;
		}

		if(this.onAdded != null) {
			Module module = selected.getModule();
			this.onAdded.accept(module);
		}

		this.minecraft.setScreen(this.lastScreen);
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.list.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.list.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTick);
	}
}
