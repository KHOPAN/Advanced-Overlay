package com.khopan.advancedoverlay.common.screen;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.khopan.advancedoverlay.common.Text;
import com.khopan.advancedoverlay.common.api.IModule;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class EditModuleScreen extends Screen {
	private final Screen lastScreen;
	private final List<ModuleEntry> moduleList;

	private ModuleList list;
	private Button settingsButton;
	private Button removeButton;

	public EditModuleScreen(Screen lastScreen, List<ModuleEntry> moduleList) {
		super(Text.EDIT_MODULE);
		this.lastScreen = lastScreen;
		this.moduleList = moduleList;
	}

	@Override
	protected void init() {
		this.list = new ModuleList();
		this.addWidget(this.list);
		double halfWidth = this.width * 0.5d;
		int top = this.height - 52;
		int left = (int) Math.round(halfWidth - 154.0d);
		int bottom = this.height - 28;
		int right = (int) Math.round(halfWidth + 4.0d);
		this.settingsButton = Button.builder(Text.MODULE_SETTINGS, this :: moduleSettings)
				.bounds(left, top, 150, 20)
				.build();

		this.addRenderableWidget(this.settingsButton);
		this.addRenderableWidget(Button.builder(Text.ADD_MODULE, button -> this.minecraft.setScreen(new ModuleSelectionScreen(this, this :: addModule))).bounds(right, top, 150, 20).build());
		this.removeButton = Button.builder(Text.REMOVE_MODULE, this :: removeModule)
				.bounds(left, bottom, 150, 20)
				.build();

		this.addRenderableWidget(this.removeButton);
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.minecraft.setScreen(this.lastScreen)).bounds(right, bottom, 150, 20).build());
		this.updateButtonActive();
	}

	private void moduleSettings(Button button) {
		ModuleEntry module = this.list.getSelected();

		if(module == null || !module.instance.hasSettings()) {
			return;
		}

		this.minecraft.setScreen(module.instance.openSettingsScreen(this));
	}

	private void removeModule(Button button) {
		ModuleEntry module = this.list.getSelected();

		if(module == null) {
			return;
		}

		this.moduleList.remove(module);
		this.list.remove(module);
		this.list.setSelected(null);
	}

	private void addModule(IModule module, String name) {
		ModuleEntry entry = new ModuleEntry(module, name);
		this.moduleList.add(entry);
		this.list.add(entry);
		this.list.setSelected(entry);
	}

	private void updateButtonActive() {
		ModuleEntry module = this.list.getSelected();
		boolean active = module != null;
		this.removeButton.active = active;
		this.settingsButton.active = active ? module.instance.hasSettings() : false;
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.list.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.list.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTick);
	}

	class ModuleList extends ObjectSelectionList<ModuleEntry> {
		public ModuleList() {
			super(EditModuleScreen.this.minecraft, EditModuleScreen.this.width, EditModuleScreen.this.height, 32, EditModuleScreen.this.height - 64, 20);

			for(int i = 0; i < EditModuleScreen.this.moduleList.size(); i++) {
				ModuleEntry module = EditModuleScreen.this.moduleList.get(i);
				this.addEntry(module);
			}
		}

		private void add(ModuleEntry module) {
			this.addEntry(module);
		}

		private void remove(ModuleEntry module) {
			this.removeEntry(module);
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
		public void setSelected(ModuleEntry selected) {
			super.setSelected(selected);
			EditModuleScreen.this.updateButtonActive();
		}
	}

	public class ModuleEntry extends ObjectSelectionList.Entry<ModuleEntry> {
		public final IModule instance;
		public final String name;

		private ModuleEntry(IModule instance, String name) {
			this.instance = instance;
			this.name = name;
		}

		@Override
		public Component getNarration() {
			return Component.literal(this.name);
		}

		@Override
		public void render(PoseStack stack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTick) {
			EditModuleScreen.this.font.drawShadow(stack, this.name, (((float) EditModuleScreen.this.list.getRectangle().width()) - ((float) EditModuleScreen.this.font.width(this.name))) * 0.5f, ((float) top) + (((float) height) - ((float) EditModuleScreen.this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
				EditModuleScreen.this.list.setSelected(this);
				return true;
			}

			return false;
		}
	}
}
