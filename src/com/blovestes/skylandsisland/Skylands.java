package com.blovestes.skylandsisland;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.blovestes.skylandsisland.commands.IslandCommand;
import com.blovestes.skylandsisland.island.IslandManager;
import com.blovestes.skylandsisland.listeners.PlayerJoin;
import com.blovestes.skylandsisland.listeners.PlayerMove;
import com.blovestes.skylandsisland.listeners.PlayerQuit;
import com.blovestes.skylandsisland.listeners.PlayerRespawn;
import com.blovestes.skylandsisland.world.SkyBlockGen;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import net.md_5.bungee.api.ChatColor;

public class Skylands extends JavaPlugin {

	String worldName = "world_skyblock";
	public World world;
	public WorldEditPlugin worldedit;

	private static Skylands skyBlock;

	@Override
	public void onEnable() {
		skyBlock = this;
		this.worldedit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if (worldedit == null) {
			sendMessage("You must have WorldEdit on your server!");
		} else {
			makeWorld();
			new IslandManager();
			registerPermissions();
			registerCommands();
			registerListeners();
		}
	}

	private void registerPermissions() {
		PluginManager pm = Bukkit.getPluginManager();
		Permission p = new Permission("skyblock.admin");
		p.setDefault(PermissionDefault.OP);
		pm.addPermission(p);
	}

	@Override
	public void onDisable() {
		IslandManager.getIslandManager().onDisable();
	}

	private void registerCommands() {
		getCommand("island").setExecutor(new IslandCommand());
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new PlayerRespawn(), this);
	}

	private void makeWorld() {
		if (Bukkit.getWorld(worldName) == null) {
			sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Generating island..." + ChatColor.GRAY + ""
					+ ChatColor.ITALIC + " please wait");
			WorldCreator wc = new WorldCreator(worldName);
			wc.generateStructures(false);
			wc.generator(new SkyBlockGen());
			Bukkit.getServer().createWorld(wc);
		}
		sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Success!" + ChatColor.GRAY + " Welcome to your new home!");
		world = Bukkit.getWorld(worldName);
		world.setDifficulty(Difficulty.EASY);
	}

	public static Skylands getSkyBlock() {
		return skyBlock;
	}

	public void sendMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}

}
