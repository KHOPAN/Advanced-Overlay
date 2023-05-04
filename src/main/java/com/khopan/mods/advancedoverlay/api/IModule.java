package com.khopan.mods.advancedoverlay.api;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IModule {
	public void renderOverlay(List<Component> list, Level world, Player player);
	public Component getModuleName();
}
