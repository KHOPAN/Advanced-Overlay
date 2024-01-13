package com.khopan.advancedoverlay.common.renderer;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.common.screen.EditModuleScreen.ModuleEntry;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

public class OverlayRenderer {
	private OverlayRenderer() {}

	public static void render(PoseStack stack, float tickDelta) {
		if(stack == null) {
			return;
		}

		AdvancedOverlay.forEachChannel(channel -> ChannelRenderer.render(stack, tickDelta, channel));
	}

	public static void tick() {
		Minecraft minecraft = Minecraft.getInstance();

		if(minecraft == null) {
			return;
		}

		AdvancedOverlay.forEachChannel(channel -> {
			if(channel == null || channel.moduleList == null) {
				return;
			}

			for(int t = 0; t < channel.moduleList.size(); t++) {
				ModuleEntry module = channel.moduleList.get(t);

				if(module == null || module.instance == null) {
					continue;
				}

				module.instance.tick(minecraft);
			}
		});
	}
}
