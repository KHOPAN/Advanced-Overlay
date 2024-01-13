package com.khopan.advancedoverlay.fabric;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.khopan.advancedoverlay.AdvancedOverlay;
import com.khopan.advancedoverlay.AdvancedOverlayInternal;
import com.khopan.advancedoverlay.common.ExtensionEntry;
import com.khopan.advancedoverlay.common.api.IExtension;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

public class AdvancedOverlayFabric implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AdvancedOverlay.LOGGER.info("Initializing {} Fabric", AdvancedOverlay.MOD_NAME);
		FabricLoader loader = FabricLoader.getInstance();
		List<EntrypointContainer<IExtension>> list = loader.getEntrypointContainers(AdvancedOverlay.MOD_ID, IExtension.class);
		List<Entry<Class<? extends IExtension>, IExtension>> result = new ArrayList<>();

		for(int i = 0; i < list.size(); i++) {
			EntrypointContainer<IExtension> container = list.get(i);
			IExtension value;

			try {
				value = container.getEntrypoint();
			} catch(Throwable Errors) {
				Errors.printStackTrace();
				continue;
			}

			Class<? extends IExtension> key = value.getClass();
			result.add(new ExtensionEntry(key, value));
		}

		AdvancedOverlayInternal.initialize(result);
	}
}
