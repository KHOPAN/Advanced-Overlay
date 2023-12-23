package com.khopan.advancedoverlay;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.khopan.advancedoverlay.api.IExtension;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

public class AdvancedOverlay implements ClientModInitializer {
	public static final String MOD_NAME = "Advanced Overlay";
	public static final String MOD_IDENTIFIER = "advancedoverlay";

	public static final Logger LOGGER = LoggerFactory.getLogger(AdvancedOverlay.MOD_NAME);
	public static final List<Channel> CHANNEL_LIST = new ArrayList<>();

	@Override
	public void onInitializeClient() {
		AdvancedOverlay.LOGGER.info("Advanced Overlay Initialization");
		AdvancedOverlay.LOGGER.info("Loading Extension");
		List<EntrypointContainer<IExtension>> list = FabricLoader.getInstance().getEntrypointContainers(AdvancedOverlay.MOD_IDENTIFIER, IExtension.class);
		List<IExtension> extensionList = new ArrayList<>();

		for(int i = 0; i < list.size(); i++) {
			EntrypointContainer<IExtension> container = list.get(i);
			IExtension extension;

			try {
				extension = container.getEntrypoint();
			} catch(Throwable ignored) {
				continue;
			}

			extensionList.add(extension);
		}

		int size = extensionList.size();

		if(size == 0) {
			AdvancedOverlay.LOGGER.info("No working Extension found");
		} else {
			AdvancedOverlay.LOGGER.info("{} working Extension{} found", size, size == 1 ? "" : "s");
		}

		AdvancedOverlay.LOGGER.info("Initializing Extension");
		int success = 0;

		for(int i = 0; i < size; i++) {
			IExtension extension = extensionList.get(i);
			String name = extension.getName();

			if(name == null) {
				continue;
			}

			AdvancedOverlay.LOGGER.info("Loading Extension '{}'", name);

			try {
				extension.initialize();
				success++;
			} catch(Throwable Errors) {
				AdvancedOverlay.LOGGER.info("'{}' failed to initialize. {}", name, Errors.toString());
			}
		}

		AdvancedOverlay.LOGGER.info("{} out of {} Extension{} were loaded correctly {}", success, size, size == 1 ? "" : "s", String.format("(%.2f%%)", ((double) success) / ((double) size)));
		AdvancedOverlay.LOGGER.info("Finished initializing {}", AdvancedOverlay.MOD_NAME);
	}
}
