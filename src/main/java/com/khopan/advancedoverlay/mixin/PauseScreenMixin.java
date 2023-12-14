package com.khopan.advancedoverlay.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.khopan.advancedoverlay.Text;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.screens.PauseScreen;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin {
	@Inject(method="createPauseMenu()V", at=@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;isLocalServer()Z", shift=Shift.BY, by=-1), locals=LocalCapture.CAPTURE_FAILSOFT)
	public void onCreatePauseMenu(CallbackInfo info, GridLayout gridLayout, RowHelper rowHelper) {
		rowHelper.addChild(Button.builder(Text.CONFIG_SCREEN_TITLE, button -> {

		}).width(204).build(), 2);
	}
}
