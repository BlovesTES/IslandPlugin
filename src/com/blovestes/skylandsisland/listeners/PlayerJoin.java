package com.blovestes.skylandsisland.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blovestes.skylandsisland.island.IslandManager;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		IslandManager.getIslandManager().loadPlayer(e.getPlayer());
	}

}
