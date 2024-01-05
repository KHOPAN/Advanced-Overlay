package com.khopan.advancedoverlay.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.common.renderer.OverlayRenderer;
import com.khopan.advancedoverlay.common.screen.ChannelListScreen.ChannelEntry;
import com.khopan.advancedoverlay.common.screen.EditModuleScreen.ModuleEntry;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

@Mixin(Gui.class)
public abstract class GuiMixin {
	@Inject(at=@At("TAIL"), method="render", slice=@Slice(from=@At(value="INVOKE", target="Lnet/minecraft/client/gui/components/PlayerTabOverlay;render(Lnet/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/scores/Scoreboard;Lnet/minecraft/world/scores/Objective;)V")))
	public void render(PoseStack poseStack, float tickDelta, CallbackInfo info) {
		OverlayRenderer.render(poseStack, tickDelta);
	}

	@Inject(at=@At("TAIL"), method="tick")
	public void tick(CallbackInfo info) {
		Minecraft minecraft = Minecraft.getInstance();

		if(minecraft == null || AdvancedOverlay.CHANNELS == null) {
			return;
		}

		for(int i = 0; i < AdvancedOverlay.CHANNELS.size(); i++) {
			ChannelEntry channel = AdvancedOverlay.CHANNELS.get(i);

			if(channel == null || channel.moduleList == null) {
				continue;
			}

			for(int t = 0; t < channel.moduleList.size(); t++) {
				ModuleEntry module = channel.moduleList.get(t);

				if(module == null || module.instance == null) {
					continue;
				}

				module.instance.tick(minecraft);
			}
		}
	}
}
