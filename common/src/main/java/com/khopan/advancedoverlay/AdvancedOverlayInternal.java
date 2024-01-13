package com.khopan.advancedoverlay;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.khopan.advancedoverlay.common.api.IExtension;
import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.api.annotation.Name;
import com.khopan.advancedoverlay.common.data.Module;
import com.khopan.advancedoverlay.common.screen.ChannelListScreen.ChannelEntry;

import net.minecraft.client.Minecraft;

public final class AdvancedOverlayInternal {
	private AdvancedOverlayInternal() {}

	static final List<ChannelEntry> CHANNEL_LIST = new ArrayList<>();
	static final List<Module> MODULE_LIST = new ArrayList<>();

	public static void initialize(List<Entry<Class<? extends IExtension>, IExtension>> extensions) {
		for(int i = 0; i < extensions.size(); i++) {
			Entry<Class<? extends IExtension>, IExtension> entry = extensions.get(i);
			Class<? extends IExtension> key = entry.getKey();
			IExtension value = entry.getValue();
			AdvancedOverlay.LOGGER.info("Registering Extension {}", key.getName());
			Name nameAnnotation = key.getDeclaredAnnotation(Name.class);

			if(nameAnnotation == null) {
				AdvancedOverlay.LOGGER.info("Class {} is missing required @Name annotation");
				continue;
			}

			String name = nameAnnotation.value();

			if(name == null || name.isEmpty()) {
				continue;
			}

			value.initialize();
			value.registerModules(AdvancedOverlayInternal :: registerModule);
		}
	}

	private static void registerModule(Class<? extends IModule> moduleClass) {
		if(moduleClass == null) {
			return;
		}

		Name nameAnnotation = moduleClass.getDeclaredAnnotation(Name.class);

		if(nameAnnotation == null) {
			return;
		}

		String name = nameAnnotation.value();

		if(name == null || name.isEmpty()) {
			return;
		}

		Constructor<?> constructor;

		try {
			constructor = moduleClass.getConstructor();
		} catch(Throwable Errors) {
			throw new InternalError("Error while getting Module constructor", Errors);
		}

		AdvancedOverlayInternal.MODULE_LIST.add(new Module(name, () -> {
			IModule module;

			try {
				module = (IModule) constructor.newInstance();
			} catch(Throwable Errors) {
				throw new RuntimeException("Error while constructing Module instance", Errors);
			}

			return module;
		}));
	}

	public static void addChannel(ChannelEntry channel) {
		AdvancedOverlayInternal.CHANNEL_LIST.add(channel);
	}

	public static void removeChannel(ChannelEntry channel) {
		AdvancedOverlayInternal.CHANNEL_LIST.remove(channel);
	}

	public static void setChannel(int index, ChannelEntry channel) {
		AdvancedOverlayInternal.CHANNEL_LIST.set(index, channel);
	}

	public static void saveChannelFile() {
		File file = AdvancedOverlayInternal.getChannelFile();
		new Thread(() -> {

		}).start();
	}

	public static void loadChannelFile() {

	}

	private static File getChannelFile() {
		Minecraft minecraft = Minecraft.getInstance();
		return new File(minecraft.gameDirectory, AdvancedOverlay.MOD_ID + ".bin");
	}

	public static void loadInternal() {

	}
}
