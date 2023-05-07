package com.khopan.mods.advancedoverlay.client.screen;

import com.khopan.mods.advancedoverlay.AdvancedOverlay;
import com.khopan.mods.advancedoverlay.PanelHolder;
import com.khopan.mods.advancedoverlay.Text;
import com.khopan.mods.advancedoverlay.client.screen.PanelSettingsScreen.PanelSelectionList.PanelEntry;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList.Entry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class PanelSettingsScreen extends ReturnableScreen {
	public static final Component TITLE = Text.config("panelSettings", "Panel Settings");
	public static final Component EDIT = Text.config("panelSettings.edit", "Edit");
	public static final Component DELETE = Text.config("panelSettings.delete", "Delete");

	private PanelSelectionList list;
	private Button editButton;
	private Button deleteButton;

	public PanelSettingsScreen(Screen screen) {
		super(PanelSettingsScreen.TITLE, screen);
	}

	@Override
	protected void init() {
		this.list = new PanelSelectionList(this.minecraft);
		this.editButton = Button.builder(PanelSettingsScreen.EDIT, this :: editButton).bounds(this.width / 2 - 155, this.height - 53, 150, 20).build();
		this.deleteButton = Button.builder(PanelSettingsScreen.DELETE, this :: deleteButton).bounds(this.width / 2 - 155 + 160, this.height - 53, 150, 20).build();
		this.addWidget(this.list);
		this.addRenderableWidget(this.editButton);
		this.addRenderableWidget(this.deleteButton);
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.returnToLastScreen()).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
	}

	private void editButton(Button button) {
		this.list.edit();
	}

	private void deleteButton(Button button) {
		this.list.delete();
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float tickDelta) {
		this.list.render(stack, mouseX, mouseY, tickDelta);
		GuiComponent.drawCenteredString(stack, this.font, this.title, this.width / 2, 16, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, tickDelta);
	}

	public class PanelSelectionList extends ObjectSelectionList<PanelEntry> {
		private final AddButtonEntry addButton;

		public PanelSelectionList(Minecraft minecraft) {
			super(minecraft, PanelSettingsScreen.this.width, PanelSettingsScreen.this.height, 32, PanelSettingsScreen.this.height - 65 + 4, 18);
			this.addButton = new AddButtonEntry();
			this.reset();
		}

		private void reset() {
			PanelEntry entry = this.getSelected();
			this.clearEntries();

			for(int i = 0; i < AdvancedOverlay.PANEL_LIST.size(); i++) {
				this.addEntry(new PanelEntry(AdvancedOverlay.PANEL_LIST.get(i)));
			}

			this.addEntry(this.addButton);
			this.setSelected(entry);
		}

		private void edit() {
			PanelEntry entry = this.getSelected();

			if(entry == null || entry.equals(this.addButton)) {
				return;
			}

			this.minecraft.setScreen(new EditPanelScreen(PanelSettingsScreen.this, AdvancedOverlay.PANEL_LIST.indexOf(entry.holder)));
		}

		private void delete() {
			PanelEntry entry = this.getSelected();

			if(entry == null || entry.equals(this.addButton)) {
				if(AdvancedOverlay.PANEL_LIST.size() != 0) {
					AdvancedOverlay.PANEL_LIST.remove(0);
					this.reset();
				}

				return;
			}

			AdvancedOverlay.PANEL_LIST.remove(entry.holder);
			this.reset();
		}

		@Override
		protected void renderBackground(PoseStack stack) {
			PanelSettingsScreen.this.renderBackground(stack);
		}

		public class PanelEntry extends Entry<PanelEntry> {
			private final PanelHolder holder;
			private final Component title;

			public PanelEntry(PanelHolder holder) {
				this.holder = holder;
				this.title = this.holder.name;
			}

			public PanelEntry(Component title) {
				this.holder = null;
				this.title = title;
			}

			@Override
			public Component getNarration() {
				return this.title;
			}

			@Override
			public void render(PoseStack stack, int index, int rowTop, int rowLeft, int rowWidth, int height, int mouseX, int mouseY, boolean hovered, float tickDelta) {
				PanelSettingsScreen.this.font.drawShadow(stack, this.title, (PanelSettingsScreen.this.width - PanelSettingsScreen.this.font.width(this.title)) * 0.5f, rowTop + (height - PanelSettingsScreen.this.font.lineHeight) * 0.5f, 0xFFFFFF);
			}

			@Override
			public boolean mouseClicked(double mouseX, double mouseY, int button) {
				if(button == 0) {
					this.onPress();
					PanelSettingsScreen.this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
					return true;
				}

				return false;
			}

			public void onPress() {

			}
		}

		public class AddButtonEntry extends PanelEntry {
			public AddButtonEntry() {
				super(Component.literal("+"));
			}

			@Override
			public void onPress() {
				AdvancedOverlay.PANEL_LIST.add(new PanelHolder());
				PanelSelectionList.this.reset();
				PanelSelectionList.this.setSelected(this);
			}
		}
	}
}
