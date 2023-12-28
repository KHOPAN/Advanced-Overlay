package com.khopan.advancedoverlay.screen;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.Text;
import com.khopan.advancedoverlay.data.Location;
import com.khopan.advancedoverlay.screen.EditModuleScreen.ModuleEntry;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ChannelListScreen extends Screen {
	private final Screen lastScreen;

	private ChannelList list;
	private Button editButton;
	private Button deleteButton;

	public ChannelListScreen(Screen lastScreen) {
		super(Text.CHANNEL_LIST);
		this.lastScreen = lastScreen;
	}

	@Override
	protected void init() {
		this.list = new ChannelList();
		this.addWidget(this.list);
		double halfWidth = this.width * 0.5d;
		int top = this.height - 52;
		int left = (int) Math.round(halfWidth - 154.0d);
		int bottom = this.height - 28;
		int right = (int) Math.round(halfWidth + 4.0d);
		this.editButton = Button.builder(Text.EDIT_CHANNEL, this :: editChannel)
				.bounds(left, top, 150, 20)
				.build();

		this.addRenderableWidget(this.editButton);
		this.addRenderableWidget(Button.builder(Text.NEW_CHANNEL, button -> this.minecraft.setScreen(new NewEditChannelScreen(this, false, new ChannelEntry(), this :: addChannel))).bounds(right, top, 150, 20).build());
		this.deleteButton = Button.builder(Text.DELETE_CHANNEL, this :: deleteChannel)
				.bounds(left, bottom, 150, 20)
				.build();

		this.addRenderableWidget(this.deleteButton);
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.minecraft.setScreen(this.lastScreen)).bounds(right, bottom, 150, 20).build());
		this.updateButtonActive();
	}

	private void editChannel(Button button) {
		ChannelEntry channel = this.list.getSelected();

		if(channel == null) {
			return;
		}

		this.minecraft.setScreen(new NewEditChannelScreen(this, true, channel, this :: updateChannel));
	}

	private void deleteChannel(Button button) {
		ChannelEntry channel = this.list.getSelected();

		if(channel == null) {
			return;
		}

		AdvancedOverlay.CHANNELS.remove(channel);
		this.list.remove(channel);
		this.list.setSelected(null);
	}

	private void addChannel(ChannelEntry channel) {
		AdvancedOverlay.CHANNELS.add(channel);
		this.list.add(channel);
		this.list.setSelected(channel);
	}

	private void updateChannel(ChannelEntry channel) {
		ChannelEntry selected = this.list.getSelected();

		if(selected == null) {
			return;
		}

		int index = this.list.indexOf(selected);

		if(index == -1) {
			return;
		}

		AdvancedOverlay.CHANNELS.set(index, channel);
		this.list.set(index, channel);
	}

	private void updateButtonActive() {
		boolean active = this.list.getSelected() != null;
		this.editButton.active = active;
		this.deleteButton.active = active;
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
		this.list.render(stack, mouseX, mouseY, partialTick);
		this.font.drawShadow(stack, this.title, (((float) this.width) - ((float) this.font.width(this.title))) * 0.5f, (((float) this.list.getRectangle().top()) - ((float) this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTick);
	}

	class ChannelList extends ObjectSelectionList<ChannelEntry> {
		private ChannelList() {
			super(ChannelListScreen.this.minecraft, ChannelListScreen.this.width, ChannelListScreen.this.height, 32, ChannelListScreen.this.height - 64, 20);

			for(int i = 0; i < AdvancedOverlay.CHANNELS.size(); i++) {
				ChannelEntry entry = AdvancedOverlay.CHANNELS.get(i);
				this.addEntry(entry);
			}
		}

		private void add(ChannelEntry channel) {
			this.addEntry(channel);
		}

		private void remove(ChannelEntry channel) {
			this.removeEntry(channel);
		}

		private void set(int inedx, ChannelEntry channel) {
			this.children().set(inedx, channel);
		}

		private int indexOf(ChannelEntry channel) {
			return this.children().indexOf(channel);
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
		public void setSelected(ChannelEntry selected) {
			super.setSelected(selected);
			ChannelListScreen.this.updateButtonActive();
		}
	}

	public class ChannelEntry extends ObjectSelectionList.Entry<ChannelEntry> {
		public final List<ModuleEntry> moduleList;

		public String name;
		public Location location;
		public double verticalSpacing;
		public double horizontalSpacing;

		private ChannelEntry() {
			this.moduleList = new ArrayList<>();
			this.name = Text.NEW_CHANNEL.getString();
			this.location = Location.DEFAULT;
			this.verticalSpacing = 0.0d;
			this.horizontalSpacing = 0.0d;
		}

		@Override
		public Component getNarration() {
			return Component.literal(this.name);
		}

		@Override
		public void render(PoseStack stack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTick) {
			ChannelListScreen.this.font.drawShadow(stack, this.name, (((float) ChannelListScreen.this.width) - ((float) ChannelListScreen.this.font.width(this.name))) * 0.5f, ((float) top) + (((float) height) - ((float) ChannelListScreen.this.font.lineHeight)) * 0.5f, 0xFFFFFF);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
				ChannelListScreen.this.list.setSelected(this);
				return true;
			}

			return false;
		}
	}
}
