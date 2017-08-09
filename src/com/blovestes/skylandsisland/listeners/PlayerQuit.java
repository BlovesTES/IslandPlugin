package com.blovestes.skylandsisland.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.blovestes.skylandsisland.island.IslandManager;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		IslandManager.getIslandManager().unloadPlayer(e.getPlayer());
	}

}
