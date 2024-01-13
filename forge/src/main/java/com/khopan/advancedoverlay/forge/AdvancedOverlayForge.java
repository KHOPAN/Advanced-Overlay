package com.khopan.advancedoverlay.forge;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.AdvancedOverlayInternal;
import com.khopan.advancedoverlay.common.builtin.CoreExtension;
import com.khopan.advancedoverlay.common.renderer.OverlayRenderer;
import com.khopan.advancedoverlay.common.screen.ChannelListScreen;

import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AdvancedOverlay.MOD_ID)
public class AdvancedOverlayForge {
	public AdvancedOverlayForge() {
		AdvancedOverlay.LOGGER.info("Initializing {} Forge", AdvancedOverlay.MOD_NAME);
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenFactory.class, () -> new ConfigScreenFactory((minecraft, screen) -> new ChannelListScreen(screen)));
		FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
		IEventBus bus = context.getModEventBus();
		bus.addListener(this :: onProcessRegistration);
		MinecraftForge.EVENT_BUS.addListener(this :: onRenderGui);
		MinecraftForge.EVENT_BUS.addListener(this :: onRenderTick);
		AdvancedOverlay.LOGGER.info("Registering Core Extension");
		ExtensionManager.register(CoreExtension.class);
	}

	private void onRenderGui(RenderGuiEvent.Post Event) {
		OverlayRenderer.render(Event.getPoseStack(), Event.getPartialTick());
	}

	private void onProcessRegistration(InterModProcessEvent Event) {
		AdvancedOverlayInternal.initialize(ExtensionManager.LIST);
	}

	private void onRenderTick(RenderTickEvent Event) {
		OverlayRenderer.tick();
	}
}
