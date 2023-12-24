package com.khopan.advancedoverlay.renderer;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.data.Channel;
import com.mojang.blaze3d.vertex.PoseStack;

public class OverlayRenderer {
	private OverlayRenderer() {}

	public static void render(PoseStack stack, float tickDelta) {
		for(int i = 0; i < AdvancedOverlay.CHANNEL_LIST.size(); i++) {
			Channel channel = AdvancedOverlay.CHANNEL_LIST.get(i);
			ChannelRenderer.render(stack, tickDelta, channel);
		}
	}
}
