package com.khopan.advancedoverlay.common.renderer;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.common.screen.ChannelListScreen.ChannelEntry;
import com.mojang.blaze3d.vertex.PoseStack;

public class OverlayRenderer {
	private OverlayRenderer() {}

	public static void render(PoseStack stack, float tickDelta) {
		if(stack == null) {
			return;
		}

		for(int i = 0; i < AdvancedOverlay.CHANNELS.size(); i++) {
			ChannelEntry channel = AdvancedOverlay.CHANNELS.get(i);
			ChannelRenderer.render(stack, tickDelta, channel);
		}
	}
}
