package com.blovestes.skylandsisland.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.blovestes.skylandsisland.island.IslandManager;

import net.md_5.bungee.api.ChatColor;

public class IslandCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;

		if (sender instanceof Player) {
			if (args.length >= 2) {
				IslandManager im = IslandManager.getIslandManager();
				if (args[0].equalsIgnoreCase("new")) {
					if (!im.hasIsland(p)) {
						p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Generating island... please wait");
						IslandManager.createIsland(p);
					} else {
						p.sendMessage(ChatColor.DARK_RED + "ERROR: You must already have an island. Try /is delete");
					}
				} else if (args[0].equalsIgnoreCase("delete")) {
					if (im.hasIsland(p)) {
						p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD
								+ "Are you sure you want to delete you island? Type " + ChatColor.YELLOW
								+ "/is delete confirm " + ChatColor.AQUA + "" + ChatColor.BOLD
								+ "to confirm that you want to delete your island");
						if (args[1].equalsIgnoreCase("confirm")) {
							IslandManager.deleteIsland(p);
						}
					} else {
						p.sendMessage(ChatColor.RED + "ERROR: You don't have an island. To make one type /is new");
					}
				} else if (args[0].equalsIgnoreCase("go")) {
					Player t = Bukkit.getServer().getPlayer(args[1]);
					PermissionAttachment attachment = t.addAttachment(null);
					HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
					perms.put(t.getUniqueId(), attachment);
					if (im.hasIsland(p)) {
						if (args[1].equals(t)) {
							if (p.hasPermission("island.go." + t)) {
								im.tpToPlayersIsland(p, t);
							} else {
								p.sendMessage(
										ChatColor.RED + "You don't have permission to warp to " + t + "'s island.");
							}

						} else {
							p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
									+ "Please specify who's island you are going to.");
						}
					}
				} else if (args[0].equalsIgnoreCase("invite")) {
					Player t = Bukkit.getServer().getPlayer(args[1]);
					PermissionAttachment attachment = t.addAttachment(null);
					HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
					perms.put(t.getUniqueId(), attachment);
					PermissionAttachment pperms = perms.get(t.getUniqueId());
					if (args[1].equals(t)) {
						pperms.setPermission("island.go." + t, true);
					} else {
						p.sendMessage(ChatColor.RED + "Please specify a player to invite.");
					}
				} else if (args[0].equalsIgnoreCase("kick")) {
					Player t = Bukkit.getServer().getPlayer(args[1]);
					PermissionAttachment attachment = t.addAttachment(null);
					HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
					perms.put(t.getUniqueId(), attachment);
					PermissionAttachment pperms = perms.get(t.getUniqueId());
					if (args[1].equals(t)) {
						pperms.setPermission("island.go." + t, false);
						im.leaveIsland(t);
					} else {
						p.sendMessage(ChatColor.RED + "Please specify a player to kick.");
					}
				}

			}
		} else {
			sender.sendMessage("Must be a player to run this command!");
		}
		return true;
	}

}
