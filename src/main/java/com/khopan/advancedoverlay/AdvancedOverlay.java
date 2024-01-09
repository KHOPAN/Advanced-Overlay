package com.khopan.advancedoverlay;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.khopan.advancedoverlay.common.api.IExtension;
import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.api.annotation.Name;
import com.khopan.advancedoverlay.common.data.Module;
import com.khopan.advancedoverlay.common.io.ChannelWriter;
import com.khopan.advancedoverlay.common.screen.ChannelListScreen.ChannelEntry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.client.Minecraft;

public class AdvancedOverlay implements ClientModInitializer {
	public static final String MOD_NAME = "Advanced Overlay";
	public static final String MOD_IDENTIFIER = "advancedoverlay";

	public static final Logger LOGGER = LoggerFactory.getLogger(AdvancedOverlay.MOD_NAME);
	public static final List<ChannelEntry> CHANNELS = new ArrayList<>();
	public static final List<Module> MODULES = new ArrayList<>();

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
			AdvancedOverlay.LOGGER.warn("No working Extension found");
		} else {
			AdvancedOverlay.LOGGER.info("{} working Extension{} found", size, size == 1 ? "" : "s");
		}

		AdvancedOverlay.LOGGER.info("Initializing Extension");
		int success = 0;

		for(int i = 0; i < size; i++) {
			IExtension extension = extensionList.get(i);
			Class<?> extensionClass = extension.getClass();
			Name nameAnnotation = extensionClass.getAnnotation(Name.class);

			if(nameAnnotation == null) {
				AdvancedOverlay.LOGGER.error("Extension class '{}' is missing required @Name annotation", extensionClass.getName());
				continue;
			}

			String name = nameAnnotation.value();

			if(name == null || name.isEmpty()) {
				AdvancedOverlay.LOGGER.error("Extension class '{}' has an empty or null @Name", extensionClass.getName());
				continue;
			}

			AdvancedOverlay.LOGGER.info("Loading Extension '{}'", name);

			try {
				extension.initialize();
				success++;
			} catch(Throwable Errors) {
				AdvancedOverlay.LOGGER.error("'{}' failed to initialize. {}", name, Errors.toString());
			}
		}

		AdvancedOverlay.LOGGER.info("{} out of {} Extension{} were loaded correctly {}", success, size, size == 1 ? "" : "s", String.format("(%.2f%%)", ((double) success) / ((double) size)));
		AdvancedOverlay.LOGGER.info("Registering Modules");

		for(int i = 0; i < size; i++) {
			IExtension extension = extensionList.get(i);
			extension.registerModules(moduleClass -> {
				if(moduleClass == null) {
					return;
				}

				Name nameAnnotation = moduleClass.getAnnotation(Name.class);

				if(nameAnnotation == null) {
					AdvancedOverlay.LOGGER.error("Module class '{}' is missing required @Name annotation", moduleClass.getName());
					return;
				}

				String name = nameAnnotation.value();

				if(name == null || name.isEmpty()) {
					AdvancedOverlay.LOGGER.error("Module class '{}' has an empty or null @Name", moduleClass.getName());
					return;
				}

				Constructor<?> constructor;

				try {
					constructor = moduleClass.getConstructor();
				} catch(Throwable ignored) {
					AdvancedOverlay.LOGGER.error("No empty constructor found for Module class '{}'", moduleClass.getName());
					return;
				}

				AdvancedOverlay.MODULES.add(new Module(name, () -> {
					IModule module;

					try {
						module = (IModule) constructor.newInstance();
					} catch(Throwable Errors) {
						throw new RuntimeException("Error while constructing Module instance", Errors);
					}

					return module;
				}));
			});
		}

		AdvancedOverlay.LOGGER.info("Finished initializing {}", AdvancedOverlay.MOD_NAME);
	}

	public static void saveFile() {
		Minecraft minecraft = Minecraft.getInstance();
		File file = new File(minecraft.gameDirectory, AdvancedOverlay.MOD_IDENTIFIER + ".bin");

		new Thread(() -> {
			ChannelWriter writer = new ChannelWriter();
			writer.writeChannelList(AdvancedOverlay.CHANNELS);

			try {
				writer.write(file);
			} catch(Throwable Errors) {
				Errors.printStackTrace();
			}
		}).start();
	}
}
