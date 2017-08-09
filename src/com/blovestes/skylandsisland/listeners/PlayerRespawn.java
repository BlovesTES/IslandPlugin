package com.blovestes.skylandsisland.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.blovestes.skylandsisland.Skylands;
import com.blovestes.skylandsisland.island.Island;
import com.blovestes.skylandsisland.island.IslandManager;

public class PlayerRespawn implements Listener {
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().getName().equals(Skylands.getSkyBlock().world.getName())) {
			if (IslandManager.getIslandManager().hasIsland(p)) {
				Island i = IslandManager.getIslandManager().getIsland(p);
				e.setRespawnLocation(i.getSpawnLocation());
			}
		}
	}

}
