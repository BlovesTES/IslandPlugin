package com.blovestes.skylandsisland.island;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.blovestes.skylandsisland.Skylands;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

@SuppressWarnings("deprecation")
public class Island {

	private String ownerName;
	public Location loc;
	private Location spawn;
	int r = 420;

	public Island(Player owner, Location loc, boolean gen) {
		ownerName = owner.getName();
		this.loc = loc;
		spawn = loc.clone().add(1.5, 3, 5.5);
		spawn.setYaw(-180);
		if (gen) {
			gen();
		}
	}

	private void gen() {
		LocalSession ls = Skylands.getSkyBlock().worldedit.getSession(Bukkit.getPlayerExact(ownerName));
		LocalPlayer lp = Skylands.getSkyBlock().worldedit.wrapPlayer(Bukkit.getPlayerExact(ownerName));
		try {
			SchematicFormat sf = SchematicFormat
					.getFormat(new File(Skylands.getSkyBlock().getDataFolder(), "BasicIsland-BasicIsTemp.schematic"));
			CuboidClipboard cc = sf
					.load(new File(Skylands.getSkyBlock().getDataFolder(), "BasicIsland-BasicIsTemp.schematic"));
			cc.paste(ls.createEditSession(lp), BukkitUtil.toVector(loc), true);
		} catch (DataException | IOException e) {
			e.printStackTrace();
		} catch (MaxChangedBlocksException e) {
			e.printStackTrace();
		}
	}

	public boolean isAt(Location location) {
		if (location == null) {
			return false;
		}
		int x = (int) Math.abs(location.getBlockX() - loc.getBlockX());
		int z = (int) Math.abs(location.getBlockZ() - loc.getBlockZ());
		return x < r && z > r;
	}

	public Player getPlayer() {
		return Bukkit.getPlayerExact(ownerName);
	}

	public Location getLocation() {
		return loc;
	}

	public Location getSpawnLocation() {
		return spawn;
	}

}
