package com.khopan.mods.advancedoverlay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.khopan.mods.advancedoverlay.api.extension.IAdvancedOverlayExtension;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

public class AdvancedOverlay implements ModInitializer {
	public static final String MOD_NAME = "Advanced Overlay";
	public static final String MOD_ID = "advancedoverlay";
	public static final Version MOD_VERSION = new Version("1.0.0");

	public static final Logger LOGGER = LoggerFactory.getLogger(AdvancedOverlay.MOD_NAME);
	public static final List<PanelHolder> PANEL_LIST = new ArrayList<>();

	private static final List<ExtensionHolder> EXTENSION_LIST = new ArrayList<>();
	private static final List<ModuleHolder> MODULE_LIST = new ArrayList<>();

	@Override
	public void onInitialize() {
		AdvancedOverlay.LOGGER.info("Initializing " + AdvancedOverlay.MOD_NAME);
		AdvancedOverlay.extensionRegistration();
		AdvancedOverlay.extensionInitialization();
		AdvancedOverlay.extensionModuleRegistration();
		AdvancedOverlay.LOGGER.info("Finished Initializing " + AdvancedOverlay.MOD_NAME);
	}

	private static void extensionRegistration() {
		AdvancedOverlay.LOGGER.info("Extension Registration Phase");
		List<EntrypointContainer<IAdvancedOverlayExtension>> list = FabricLoader.getInstance().getEntrypointContainers(AdvancedOverlay.MOD_ID, IAdvancedOverlayExtension.class);

		for(int i = 0; i < list.size(); i++) {
			EntrypointContainer<IAdvancedOverlayExtension> container = list.get(i);
			IAdvancedOverlayExtension extension = container.getEntrypoint();
			AdvancedOverlay.EXTENSION_LIST.add(new ExtensionHolder(extension));
		}
	}

	private static void extensionInitialization() {
		AdvancedOverlay.LOGGER.info("Extension Initialization Phase");

		for(int i = 0; i < AdvancedOverlay.EXTENSION_LIST.size(); i++) {
			AdvancedOverlay.EXTENSION_LIST.get(i).extension.initialize();
		}
	}

	private static void extensionModuleRegistration() {
		AdvancedOverlay.LOGGER.info("Extension Module Registration Phase");

		for(int i = 0; i < AdvancedOverlay.EXTENSION_LIST.size(); i++) {
			ExtensionHolder holder = AdvancedOverlay.EXTENSION_LIST.get(i);
			IAdvancedOverlayExtension extension = holder.extension;
			extension.registerModule(module -> {
				if(module == null) {
					throw new NullPointerException("'module' cannot be null");
				}

				AdvancedOverlay.MODULE_LIST.add(new ModuleHolder(extension, module));
			});
		}
	}

	public static List<ExtensionHolder> getExtensionList() {
		return Collections.unmodifiableList(AdvancedOverlay.EXTENSION_LIST);
	}

	public static List<ModuleHolder> getModuleList() {
		return Collections.unmodifiableList(AdvancedOverlay.MODULE_LIST);
	}
}
