package com.blovestes.skylandsisland.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.blovestes.skylandsisland.Skylands;
import com.blovestes.skylandsisland.island.Island;
import com.blovestes.skylandsisland.island.IslandManager;

import net.md_5.bungee.api.ChatColor;

public class PlayerMove implements Listener{
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().getName().equals(Skylands.getSkyBlock().world.getName())) {
			Island i = IslandManager.getIslandManager().getIsland(p);
			if (i != null) {
				if (i.isAt(e.getFrom()) && !i.isAt(e.getTo())) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Sorry, but you can't leave the bounds of your island.");
				}
			}
		}
	}

}
