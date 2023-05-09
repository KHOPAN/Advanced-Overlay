package com.khopan.mods.advancedoverlay.client.screen;

import com.khopan.mods.advancedoverlay.AdvancedOverlay;
import com.khopan.mods.advancedoverlay.PanelHolder;
import com.khopan.mods.advancedoverlay.Text;
import com.khopan.mods.advancedoverlay.overlay.Latch;
import com.khopan.mods.advancedoverlay.overlay.OriginLocation;
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
	public static final Component VISIBLE = Text.config("editPanel.visible");
	public static final Component OVERLAY_LOCATION = Text.config("editPanel.overlayLocation");
	public static final Component EDIT_CUSTOM_LOCATION = Text.config("editPanel.editCustomLocation");
	public static final Component ORIGIN_LOCATION = Text.config("editPanel.originLocation");
	public static final Component AUTO_IN_FRAME = Text.config("editPanel.autoInFrame");

	private final int index;

	private CycleButton<Latch> visibleButton;
	private CycleButton<OverlayLocation> locationButton;
	private Button editCustomLocationButton;
	private CycleButton<OriginLocation> originButton;
	private CycleButton<Latch> autoInFrameButton;

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

		this.visibleButton = CycleButton.builder(Latch :: getDisplayName).withValues(Latch.values()).withInitialValue(holder.visible).create(0, 0, 150, 20, EditPanelScreen.VISIBLE, (button, visible) -> {
			holder.visible = visible;
		});

		this.locationButton = CycleButton.builder(OverlayLocation :: getDisplayName).withValues(OverlayLocation.values()).withInitialValue(holder.overlayLocation).create(0, 0, 150, 20, EditPanelScreen.OVERLAY_LOCATION, (button, location) -> {
			holder.overlayLocation = location;
			this.buttonActivation(location);
		});

		this.editCustomLocationButton = Button.builder(EditPanelScreen.EDIT_CUSTOM_LOCATION, button -> {

		})/*.tooltip(Tooltip.create(null))*/.build();

		this.originButton = CycleButton.builder(OriginLocation :: getDisplayName).withValues(OriginLocation.values()).withInitialValue(OriginLocation.DEFAULT).create(0, 0, 150, 20, EditPanelScreen.ORIGIN_LOCATION, (button, location) -> {
			holder.originLocation = location;
		});

		this.autoInFrameButton = CycleButton.builder(Latch :: getDisplayName).withValues(Latch.values()).withInitialValue(Latch.OFF).create(0, 0, 150, 20, EditPanelScreen.AUTO_IN_FRAME, (button, autoInFrame) -> {
			holder.autoInFrame = autoInFrame;
		});

		this.buttonActivation(this.locationButton.getValue());
		row.addChild(box);
		row.addChild(this.visibleButton);
		row.addChild(this.locationButton);
		row.addChild(this.editCustomLocationButton);
		row.addChild(this.originButton);
		row.addChild(this.autoInFrameButton);
		row.addChild(this.doneButton(), 2, layout.newCellSettings().paddingTop(6));
		layout.arrangeElements();
		FrameLayout.alignInRectangle(layout, 0, 0, this.width, this.height, 0.5f, 0.5f);
		layout.visitWidgets(this :: addRenderableWidget);
	}

	private void buttonActivation(OverlayLocation location) {
		if(OverlayLocation.CUSTOM.equals(location)) {
			this.editCustomLocationButton.active = true;
			this.originButton.active = true;
			this.autoInFrameButton.active = true;
		} else {
			this.editCustomLocationButton.active = false;
			this.originButton.active = false;
			this.autoInFrameButton.active = false;
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
