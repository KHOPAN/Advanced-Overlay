package com.khopan.mods.advancedoverlay.core.module;

import java.util.List;

import com.khopan.mods.advancedoverlay.Version;
import com.khopan.mods.advancedoverlay.api.IModule;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DurabilityModule implements IModule {
	@Override
	public void renderOverlay(List<Component> list, Level world, Player player) {

	}

	@Override
	public Component getModuleName() {
		return Component.literal("Durability Module");
	}

	@Override
	public Version getModuleVersion() {
		return new Version("1.0.0");
	}

	@Override
	public Component[] getModuleAuthor() {
		return new Component[] {Component.literal("KHOPAN")};
	}
}
