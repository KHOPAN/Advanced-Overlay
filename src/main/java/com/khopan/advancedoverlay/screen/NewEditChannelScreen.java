package com.khopan.advancedoverlay.screen;

import java.util.function.Consumer;

import com.khopan.advancedoverlay.Text;
import com.khopan.advancedoverlay.channel.Channel;
import com.khopan.advancedoverlay.channel.Location;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class NewEditChannelScreen extends Screen {
	private final Screen lastScreen;
	private final Channel channel;
	private final boolean edit;
	private final Consumer<Channel> onDone;

	private GridLayout layout;
	private EditBox channelNameBox;
	private CycleButton<Location> locationButton;

	private double verticalSpacing;
	private double horizontalSpacing;

	public NewEditChannelScreen(Screen lastScreen, Channel channel, boolean edit, Consumer<Channel> onDone) {
		super(edit ? Text.EDIT_CHANNEL : Text.NEW_CHANNEL);
		this.lastScreen = lastScreen;
		this.channel = channel;
		this.edit = edit;
		this.onDone = onDone;
		this.verticalSpacing = this.channel.getVerticalSpacing();
		this.horizontalSpacing = this.channel.getHorizontalSpacing();
	}

	@Override
	protected void init() {
		this.layout = new GridLayout();
		this.layout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
		RowHelper helper = this.layout.createRowHelper(2);
		this.channelNameBox = new EditBox(this.font, 0, 0, 150, 20, Text.CHANNEL_NAME);
		this.channelNameBox.setHint(Text.CHANNEL_NAME);

		if(this.edit) {
			this.channelNameBox.setValue(this.channel.getChannelName());
		}

		helper.addChild(this.channelNameBox);
		this.locationButton = CycleButton.<Location>builder(location -> location.getText())
				.withValues(Location.values())
				.withInitialValue(this.channel.getLocation())
				.create(0, 0, 150, 20, Text.LOCATION);

		helper.addChild(this.locationButton);
		helper.addChild(new SpacingSlider(true));
		helper.addChild(new SpacingSlider(false));
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(Button.builder(CommonComponents.GUI_CONTINUE, button -> {}).build());
		helper.addChild(SpacerElement.height(20), 2);
		helper.addChild(Button.builder(CommonComponents.GUI_CANCEL, button -> this.minecraft.setScreen(this.lastScreen)).build());
		helper.addChild(Button.builder(CommonComponents.GUI_DONE, this :: done).build());
		this.layout.arrangeElements();
		FrameLayout.alignInRectangle(this.layout, 0, 0, this.width, this.height, 0.5f, 0.5f);
		this.layout.visitWidgets(this :: addRenderableWidget);
	}

	private void done(Button button) {
		String name = this.channelNameBox.getValue();
		this.channel.setChannelName(name == null || name.isEmpty() ? Text.NEW_CHANNEL.getString() : name);
		this.channel.setLocation(this.locationButton.getValue());
		this.channel.setVerticalSpacing(this.verticalSpacing);
		this.channel.setHorizontalSpacing(this.horizontalSpacing);
		this.minecraft.setScreen(this.lastScreen);

		if(this.onDone != null) {
			this.onDone.accept(this.channel);
		}
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.renderDirtBackground(stack);
		super.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.layout.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
	}

	private class SpacingSlider extends AbstractSliderButton {
		private final boolean vertical;

		public SpacingSlider(boolean vertical) {
			super(0, 0, 150, 20, vertical ? Text.VERTICAL_SPACING : Text.HORIZONTAL_SPACING, 0.0d);
			this.vertical = vertical;
			this.value = this.vertical ? NewEditChannelScreen.this.verticalSpacing : NewEditChannelScreen.this.horizontalSpacing;
			this.updateMessage();
		}

		@Override
		protected void updateMessage() {
			String value = Integer.toString((int) Math.round(this.value * 100.0d));
			Component message;

			if(this.vertical) {
				message = Text.verticalSpacing(value);
			} else {
				message = Text.horizontalSpacing(value);
			}

			this.setMessage(message);
		}

		@Override
		protected void applyValue() {
			if(this.vertical) {
				NewEditChannelScreen.this.verticalSpacing = this.value;
			} else {
				NewEditChannelScreen.this.horizontalSpacing = this.value;
			}
		}
	}
}
