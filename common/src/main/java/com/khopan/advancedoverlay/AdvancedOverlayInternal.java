package com.khopan.advancedoverlay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khopan.advancedoverlay.common.api.IExtension;
import com.khopan.advancedoverlay.common.api.IModule;
import com.khopan.advancedoverlay.common.api.annotation.Name;
import com.khopan.advancedoverlay.common.data.Location;
import com.khopan.advancedoverlay.common.data.Module;
import com.khopan.advancedoverlay.common.screen.ChannelListScreen;
import com.khopan.advancedoverlay.common.screen.ChannelListScreen.ChannelEntry;
import com.khopan.advancedoverlay.common.screen.EditModuleScreen;
import com.khopan.advancedoverlay.common.screen.EditModuleScreen.ModuleEntry;

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

		AdvancedOverlayInternal.loadFile();
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

	public static void saveFile() {
		new Thread(() -> {
			JsonArray root = new JsonArray();

			for(int x = 0; x < AdvancedOverlayInternal.CHANNEL_LIST.size(); x++) {
				JsonObject channelEntry = new JsonObject();
				ChannelEntry channel = AdvancedOverlayInternal.CHANNEL_LIST.get(x);
				channelEntry.addProperty("name", channel.name);
				channelEntry.addProperty("location", channel.location.name().toLowerCase());
				channelEntry.addProperty("verticalSpacing", channel.verticalSpacing);
				channelEntry.addProperty("horizontalSpacing", channel.horizontalSpacing);
				JsonArray moduleArray = new JsonArray();

				for(int y = 0; y < channel.moduleList.size(); y++) {
					JsonObject moduleEntry = new JsonObject();
					ModuleEntry module = channel.moduleList.get(y);
					moduleEntry.addProperty("name", module.name);
					moduleEntry.addProperty("className", module.instance.getClass().getName());
					moduleArray.add(moduleEntry);
				}

				channelEntry.add("modules", moduleArray);
				root.add(channelEntry);
			}

			byte[] byteArray = root.toString().getBytes(StandardCharsets.UTF_8);
			File file = AdvancedOverlayInternal.getChannelFile();

			try {
				FileOutputStream stream = new FileOutputStream(file);
				stream.write(byteArray);
				stream.close();
			} catch(Throwable Errors) {
				Errors.printStackTrace();
			}
		}).start();
	}

	public static void loadFile() {
		new Thread(() -> {
			File file = AdvancedOverlayInternal.getChannelFile();
			byte[] byteArray;

			try {
				FileInputStream stream = new FileInputStream(file);
				byteArray = stream.readAllBytes();
				stream.close();
			} catch(Throwable Errors) {
				Errors.printStackTrace();
				return;
			}

			JsonElement element = JsonParser.parseString(new String(byteArray, StandardCharsets.UTF_8));

			if(!element.isJsonArray()) {
				throw new InternalError("Root must be JSON Array");
			}

			JsonArray root = element.getAsJsonArray();
			ChannelListScreen channelListScreen = new ChannelListScreen(null);
			EditModuleScreen editModuleScreen = new EditModuleScreen(null, null);
			List<ChannelEntry> list = new ArrayList<>();

			for(int x = 0; x < root.size(); x++) {
				JsonElement rootElement = root.get(x);

				if(!rootElement.isJsonObject()) {
					throw new InternalError("Root array element must be JSON Object");
				}

				JsonObject channelEntry = rootElement.getAsJsonObject();
				ChannelEntry channelEntryInstance = ChannelEntry.constructInvalid(channelListScreen);
				channelEntryInstance.name = channelEntry.get("name").getAsString();
				channelEntryInstance.location = Location.valueOf(channelEntry.get("location").getAsString().toUpperCase());
				channelEntryInstance.verticalSpacing = channelEntry.get("verticalSpacing").getAsDouble();
				channelEntryInstance.horizontalSpacing = channelEntry.get("horizontalSpacing").getAsDouble();
				JsonElement modulesElement = channelEntry.get("modules");

				if(!modulesElement.isJsonArray()) {
					throw new InternalError("'modules' must be JSON Array");
				}

				JsonArray moduleList = modulesElement.getAsJsonArray();

				for(int y = 0; y < moduleList.size(); y++) {
					JsonElement moduleListElement = moduleList.get(y);

					if(!moduleListElement.isJsonObject()) {
						throw new InternalError("'modules' element must be JSON Object");
					}

					JsonObject moduleEntry = moduleListElement.getAsJsonObject();
					String moduleName = moduleEntry.get("name").getAsString();
					String className = moduleEntry.get("className").getAsString();
					IModule instance;

					try {
						Class<?> moduleClass = Class.forName(className);
						instance = (IModule) moduleClass.getConstructor().newInstance();
					} catch(Throwable Errors) {
						throw new InternalError("Error while loading class from JSON file", Errors);
					}

					ModuleEntry moduleEntryInstance = ModuleEntry.constructInvalid(editModuleScreen, instance, moduleName);
					channelEntryInstance.moduleList.add(moduleEntryInstance);
				}

				list.add(channelEntryInstance);
			}

			AdvancedOverlayInternal.CHANNEL_LIST.clear();
			AdvancedOverlayInternal.CHANNEL_LIST.addAll(list);
		}).start();
	}

	private static File getChannelFile() {
		Minecraft minecraft = Minecraft.getInstance();
		return new File(minecraft.gameDirectory, AdvancedOverlay.MOD_ID + ".json");
	}
}
