package com.khopan.advancedoverlay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

public class AdvancedOverlay implements ClientModInitializer {
	public static final String MOD_NAME = "Advanced Overlay";
	public static final String MOD_IDENTIFIER = "advancedoverlay";

	public static final Logger LOGGER = LoggerFactory.getLogger(AdvancedOverlay.MOD_NAME);

	@Override
	public void onInitializeClient() {
		AdvancedOverlay.LOGGER.info("Advanced Overlay Initialization");
	}

	/*@Mixin(PauseScreen.class)
	public abstract class PauseScreenMixin {
		@Inject(method="createPauseMenu()V", at=@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;isLocalServer()Z", shift=Shift.BY, by=-1), locals=LocalCapture.CAPTURE_FAILSOFT)
		public void onCreatePauseMenu(CallbackInfo info, GridLayout gridLayout, RowHelper rowHelper) {
			rowHelper.addChild(Button.builder(ConfigScreen.TITLE, button -> {
				Minecraft.getInstance().setScreen(new ConfigScreen((PauseScreen) (Object) this));
			}).width(204).build(), 2);
		}
	}*/
}
