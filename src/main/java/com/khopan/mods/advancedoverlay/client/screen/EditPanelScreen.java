package com.khopan.mods.advancedoverlay.client.screen;

import com.khopan.mods.advancedoverlay.AdvancedOverlay;
import com.khopan.mods.advancedoverlay.PanelHolder;
import com.khopan.mods.advancedoverlay.Text;
import com.khopan.mods.advancedoverlay.overlay.OverlayLocation;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class EditPanelScreen extends ReturnableScreen {
	public static final Component PANEL_NAME = Text.config("editPanel.panelName");
	public static final Component OVERLAY_LOCATION = Text.config("editPanel.overlayLocation");
	public static final Component EDIT_CUSTOM_LOCATION = Text.config("editPanel.editCustomLocation");

	private final int index;

	private CycleButton<OverlayLocation> locationButton;
	private Button editCustomLocationButton;

	public EditPanelScreen(Screen screen, int index) {
		super(EditPanelScreen.getTitle(index), screen);
		this.index = index;
	}

	@Override
	protected void init() {
		PanelHolder holder = AdvancedOverlay.PANEL_LIST.get(this.index);
		GridLayout layout = new GridLayout();
		layout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
		RowHelper row = layout.createRowHelper(2);
		EditBox box = new EditBox(this.font, 0, 0, 150, 20, EditPanelScreen.PANEL_NAME);
		box.setHint(MutableComponent.create(EditPanelScreen.PANEL_NAME.getContents()).withStyle(ChatFormatting.DARK_GRAY));
		box.setValue(holder.name.getString());
		box.setResponder(text -> {
			holder.name = Component.literal(text);
		});

		this.locationButton = CycleButton.builder(OverlayLocation :: getDisplayName).withValues(OverlayLocation.values()).withInitialValue(holder.overlayLocation).create(0, 0, 150, 20, EditPanelScreen.OVERLAY_LOCATION, (button, location) -> {
			holder.overlayLocation = location;
			this.buttonActivation(location);
		});

		this.editCustomLocationButton = Button.builder(EditPanelScreen.EDIT_CUSTOM_LOCATION, button -> {

		})/*.tooltip(Tooltip.create(null))*/.build();

		this.buttonActivation(this.locationButton.getValue());
		row.addChild(box);
		row.addChild(this.locationButton);
		row.addChild(this.editCustomLocationButton);
		row.addChild(this.doneButton(), 2, layout.newCellSettings().paddingTop(6));
		layout.arrangeElements();
		FrameLayout.alignInRectangle(layout, 0, 0, this.width, this.height, 0.5f, 0.5f);
		layout.visitWidgets(this :: addRenderableWidget);
	}

	private void buttonActivation(OverlayLocation location) {
		if(OverlayLocation.CUSTOM.equals(location)) {
			this.editCustomLocationButton.active = true;
		} else {
			this.editCustomLocationButton.active = false;
		}
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float tickDelta) {
		this.renderBackground(stack);
		GuiComponent.drawCenteredString(stack, this.font, this.title, this.width / 2, 15, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, tickDelta);
	}

	private static Component getTitle(int index) {
		return Text.config("editPanel", AdvancedOverlay.PANEL_LIST.get(index).name);
	}
}
