package com.khopan.advancedoverlay.screen;

import com.khopan.advancedoverlay.Channel;
import com.khopan.advancedoverlay.Location;
import com.khopan.advancedoverlay.Text;
import com.khopan.advancedoverlay.screen.channellist.ChannelListScreen;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;

public class NewEditChannelScreen extends Screen {
	private final Screen lastScreen;
	private final Channel channel;

	private GridLayout layout;

	public NewEditChannelScreen(Screen lastScreen, Channel channel, boolean edit) {
		super(edit ? Text.EDIT_CHANNEL : Text.NEW_CHANNEL);
		this.lastScreen = lastScreen;
		this.channel = channel;
	}

	@Override
	protected void init() {
		this.layout = new GridLayout();
		this.layout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
		RowHelper helper = this.layout.createRowHelper(2);
		EditBox editBox = new EditBox(this.font, 0, 0, 150, 20, Text.CHANNEL_NAME);
		editBox.setHint(Text.CHANNEL_NAME);
		helper.addChild(editBox);
		helper.addChild(CycleButton.<Location>builder(location -> location.getText()).withValues(Location.values()).withInitialValue(Location.CENTER_RIGHT).create(0, 0, 150, 20, Text.LOCATION));
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_DONE, this :: done).build(), 2);
		this.layout.arrangeElements();
		FrameLayout.alignInRectangle(this.layout, 0, 0, this.width, this.height, 0.5f, 0.5f);
		this.layout.visitWidgets(this :: addRenderableWidget);
	}

	private void done(Button button) {
		this.minecraft.setScreen(this.lastScreen);

		if(this.lastScreen instanceof ChannelListScreen screen) {
			screen.refreshList();
		}
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.renderDirtBackground(stack);
		super.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.layout.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
	}
}
