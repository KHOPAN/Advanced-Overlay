package com.khopan.advancedoverlay.common.screen;

import java.util.function.BiConsumer;

import org.lwjgl.glfw.GLFW;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.Text;
import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.data.Module;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ModuleSelectionScreen extends Screen {
	private final Screen lastScreen;
	private final BiConsumer<IModule, String> onDone;

	private ModuleSpecificationList list;
	private Button doneButton;

	public ModuleSelectionScreen(Screen lastScreen, BiConsumer<IModule, String> onDone) {
		super(Text.SELECT_MODULE);
		this.lastScreen = lastScreen;
		this.onDone = onDone;
	}

	@Override
	protected void init() {
		this.list = new ModuleSpecificationList();
		this.addWidget(this.list);
		ScreenRectangle rectangle = this.list.getRectangle();
		double halfWidth = ((double) this.width) * 0.5d;
		int bottom = rectangle.bottom();
		int y = (int) Math.round(((double) bottom) + (((double) this.height) - ((double) bottom) - 20.0d) * 0.5d);
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.minecraft.setScreen(this.lastScreen)).bounds((int) Math.round(halfWidth - 154.0d), y, 150, 20).build());
		this.doneButton = Button.builder(CommonComponents.GUI_DONE, this :: done).bounds((int) Math.round(halfWidth + 4.0d), y, 150, 20).build();
		this.addRenderableWidget(this.doneButton);
		this.updateButtonActive();
	}

	private void done(Button button) {
		ModuleSpecificationEntry selected = this.list.getSelected();

		if(selected == null) {
			return;
		}

		if(this.onDone != null) {
			IModule module = selected.module.newModule();
			this.onDone.accept(module, selected.name);
		}

		this.minecraft.setScreen(this.lastScreen);
	}

	private void updateButtonActive() {
		this.doneButton.active = this.list.getSelected() != null;
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.list.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.list.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTick);
	}

	class ModuleSpecificationList extends ObjectSelectionList<ModuleSpecificationEntry> {
		private ModuleSpecificationList() {
			super(ModuleSelectionScreen.this.minecraft, ModuleSelectionScreen.this.width, ModuleSelectionScreen.this.height, 32, ModuleSelectionScreen.this.height - 40, 20);

			for(int i = 0; i < AdvancedOverlay.MODULES.size(); i++) {
				Module module = AdvancedOverlay.MODULES.get(i);
				this.addEntry(new ModuleSpecificationEntry(module));
			}
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			ScreenRectangle rectangle = this.getRectangle();

			if(mouseY > rectangle.top() && mouseY < rectangle.bottom() && this.getEntryAtPosition(mouseX, mouseY) == null) {
				this.setSelected(null);
				return true;
			}

			return super.mouseClicked(mouseX, mouseY, button);
		}

		@Override
		public void setSelected(ModuleSpecificationEntry selected) {
			super.setSelected(selected);
			ModuleSelectionScreen.this.updateButtonActive();
		}
	}

	class ModuleSpecificationEntry extends ObjectSelectionList.Entry<ModuleSpecificationEntry> {
		private final Module module;
		private final String name;

		private ModuleSpecificationEntry(Module module) {
			this.module = module;
			this.name = this.module.getModuleName();
		}

		@Override
		public Component getNarration() {
			return Component.literal(this.name);
		}

		@Override
		public void render(PoseStack stack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTick) {
			ModuleSelectionScreen.this.font.drawShadow(stack, this.name, (((float) ModuleSelectionScreen.this.list.getRectangle().width()) - ((float) ModuleSelectionScreen.this.font.width(this.name))) * 0.5f, ((float) top) + (((float) height) - ((float) ModuleSelectionScreen.this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
				ModuleSelectionScreen.this.list.setSelected(this);
				return true;
			}

			return false;
		}
	}
}
