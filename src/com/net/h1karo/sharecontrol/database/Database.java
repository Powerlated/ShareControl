/*******************************************************************************
 * Copyright (C) 2016 H1KaRo (h1karo)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.net.h1karo.sharecontrol.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.listeners.multiinventories.PlayerGameModeChangeListener;
import com.net.h1karo.sharecontrol.version.CoreVersion;

import net.royawesome.jlibnoise.MathHelper;

public class Database {

	private static ShareControl main;

	public Database(ShareControl h) {
		Database.main = h;
	}

	/** CACHE **/

	static HashMap<List<Integer>, Integer> cache = new HashMap<List<Integer>, Integer>();
	static HashMap<List<Integer>, Integer> extracache = new HashMap<List<Integer>, Integer>();
	static HashMap<List<Integer>, Integer> fullcache = new HashMap<List<Integer>, Integer>();
	static boolean saveStatus = false;
	static BukkitTask AsyncSave = null;
	static BukkitTask AsyncSaveInv = null;

	public static void saveDatabase() {
		saveStatus = true;
		sqlSave();
		cache.clear();
		cache.putAll(extracache);
		extracache.clear();
		saveStatus = false;
	}

	public static void syncSaveDatabase() {
		saveDatabase();
	}

	public static void asyncSaveDatabase() {
		AsyncSave = Bukkit.getServer().getScheduler().runTaskAsynchronously(main, new Runnable() {
			@Override
			public void run() {
				if (cache.size() != 0)
					saveDatabase();
			}
		});
	}

	public static void asyncSaveInv() {
		AsyncSaveInv = Bukkit.getServer().getScheduler().runTaskAsynchronously(main, new Runnable() {
			@Override
			public void run() {
				PlayerGameModeChangeListener.saveMultiInv();
			}
		});
	}

	public static void autoSaveDatabase() {
		Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
			@Override
			public void run() {
				if (cache.size() > 0 || PlayerGameModeChangeListener.cache.size() > 0) {
					if (cache.size() > 0)
						asyncSaveDatabase();
					if (PlayerGameModeChangeListener.cache.size() > 0)
						asyncSaveInv();
					main.log("Database have been background saved!");
				}
			}
		}, Configuration.dbInterval * 1200, Configuration.dbInterval * 1200);
	}

	/** GENERAL FUNCTION OF HANDLER **/

	public static void addBlockMoreArguments(Block b, int id) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		List<Integer> key = new ArrayList<Integer>();
		key.add(x);
		key.add(y);
		key.add(z);
		key.add(w);

		if (!saveStatus)
			cache.put(key, id);
		else
			extracache.put(key, id);
		fullcache.put(key, id);
	}

	@SuppressWarnings("deprecation")
	public static void addBlock(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), w = Bukkit.getWorlds().indexOf(b.getWorld()), id = b.getTypeId();
		List<Integer> key = new ArrayList<Integer>();
		key.add(x);
		key.add(y);
		key.add(z);
		key.add(w);

		if (!saveStatus)
			cache.put(key, id);
		else
			extracache.put(key, id);
		fullcache.put(key, id);
	}

	public static void addLocation(Location l) {
		World w = l.getWorld();
		Block b = w.getBlockAt(l);
		addBlock(b);
	}

	public static void removeBlock(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		List<Integer> key = new ArrayList<Integer>();
		key.add(x);
		key.add(y);
		key.add(z);
		key.add(w);

		if (!saveStatus)
			cache.put(key, 0);
		else
			extracache.put(key, 0);
		fullcache.remove(key);
	}

	@SuppressWarnings("deprecation")
	public static boolean isCreative(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), id = b.getTypeId(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		List<Integer> key = new ArrayList<Integer>();
		key.add(x);
		key.add(y);
		key.add(z);
		key.add(w);

		if (fullcache.containsKey(key) && fullcache.get(key) == id && id != 0)
			return true;
		else {
			if (fullcache.containsKey(key) && fullcache.get(key) != id)
				removeBlock(b);
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static int checkCreativeRough(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), id = b.getTypeId(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		List<Integer> key = new ArrayList<Integer>();
		key.add(x);
		key.add(y);
		key.add(z);
		key.add(w);

		if (fullcache.containsKey(key) && id != 0)
			if (fullcache.get(key) == id)
				return 1;
			else
				return 2;
		return 0;
	}

	public static boolean listCheckCreative(List<Block> Blocks) {
		for (Block b : Blocks) {
			if (isCreative(b))
				return true;
		}
		return false;
	}

	/** EXTRA **/

	public static void updateBlockToLocation(Block b, Location l) {
		if (!isCreative(b))
			return;
		World w = l.getWorld();
		addBlock(w.getBlockAt(l));
	}

	public static void fullClear(Block b) {
		if (!isCreative(b))
			return;
		b.setType(Material.AIR);
		removeBlock(b);
	}

	public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
		List<Entity> entities = new ArrayList<Entity>();
		World world = location.getWorld();

		// To find chunks we use chunk coordinates (not block coordinates!)
		int smallX = MathHelper.floor((location.getX() - radius) / 16.0D);
		int bigX = MathHelper.floor((location.getX() + radius) / 16.0D);
		int smallZ = MathHelper.floor((location.getZ() - radius) / 16.0D);
		int bigZ = MathHelper.floor((location.getZ() + radius) / 16.0D);

		for (int x = smallX; x <= bigX; x++) {
			for (int z = smallZ; z <= bigZ; z++) {
				if (world.isChunkLoaded(x, z)) {
					entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities())); // Add
																							// all
																							// entities
																							// from
																							// this
																							// chunk
																							// to
																							// the
																							// list
				}
			}
		}

		// Remove the entities that are within the box above but not actually in
		// the sphere we defined with the radius and location
		// This code below could probably be replaced in Java 8 with a stream ->
		// filter
		Iterator<Entity> entityIterator = entities.iterator(); // Create an
																// iterator so
																// we can loop
																// through the
																// list while
																// removing
																// entries
		while (entityIterator.hasNext()) {
			if (entityIterator.next().getLocation().distanceSquared(location) > radius * radius) { // If
																									// the
																									// entity
																									// is
																									// outside
																									// of
																									// the
																									// sphere...
				entityIterator.remove(); // Remove it
			}
		}
		return entities;
	}

	@SuppressWarnings("deprecation")
	public static void dropBlocks(Block b) {
		// Get entities around the block and if it is an item, remove the item.
		// new BukkitRunnable() {
		//
		// @Override
		// public void run() {
		// // What you want to schedule goes here
		// Bukkit.broadcastMessage(b.getLocation().add(new
		// Location(b.getWorld(), 0.5, 0.5, 0.5)).toString());
		// for (Entity e : getEntitiesAroundPoint(b.getLocation(), 1)) {
		// if (e.getType() == EntityType.DROPPED_ITEM) {
		// Item i = (Item) e;
		// i.remove();
		// }
		// }
		// }
		//
		// }.runTaskLater(main, 1);

		int h = b.getLocation().getBlockY();
		World w = b.getWorld();
		for (int j = b.getLocation().getBlockY() + 1; j <= Bukkit.getWorlds().get(0).getMaxHeight(); j++) {
			h++;
			Block thish = w.getBlockAt(b.getX(), j, b.getZ());
			if (!ifUpDrop(thish) && !ifOneUpDrop(thish)) {
				j = Bukkit.getWorlds().get(0).getMaxHeight() + 1;
			}
		}

		for (int j = h; j > b.getLocation().getBlockY(); j--) {
			Block newB = w.getBlockAt(b.getLocation().getBlockX(), j, b.getLocation().getBlockZ());
			if (ifUpDrop(newB))
				fullClear(newB);
		}

		Block newB = w.getBlockAt(b.getX(), b.getY() + 1, b.getZ());
		if (ifOneUpDrop(newB)) {
			if (ifLaterallyDrop(newB) == 1 && newB.getData() == 12)
				fullClear(newB);
			if (ifLaterallyDrop(newB) == 2 && newB.getData() == 5)
				fullClear(newB);
			if (ifLaterallyDrop(newB) == 4
					&& (newB.getData() == 6 || newB.getData() == 14 || newB.getData() == 5 || newB.getData() == 13))
				fullClear(newB);
			if (ifLaterallyDrop(newB) == 5 && (newB.getData() == 5 || newB.getData() == 13))
				fullClear(newB);
		}

		newB = w.getBlockAt(b.getX() + 1, b.getY(), b.getZ());
		if (ifLaterallyDrop(newB) == 1 && newB.getData() == 5)
			fullClear(newB);
		if (ifLaterallyDrop(newB) == 2 && newB.getData() == 1)
			fullClear(newB);
		if (ifLaterallyDrop(newB) == 3 && newB.getData() == 3)
			fullClear(newB);
		if ((ifLaterallyDrop(newB) == 4 || ifLaterallyDrop(newB) == 5) && (newB.getData() == 1 || newB.getData() == 9))
			fullClear(newB);

		newB = w.getBlockAt(b.getX() - 1, b.getY(), b.getZ());
		if (ifLaterallyDrop(newB) == 1 && newB.getData() == 4)
			fullClear(newB);
		if (ifLaterallyDrop(newB) == 2 && newB.getData() == 2)
			fullClear(newB);
		if (ifLaterallyDrop(newB) == 3 && newB.getData() == 1)
			fullClear(newB);
		if ((ifLaterallyDrop(newB) == 4 || ifLaterallyDrop(newB) == 5) && (newB.getData() == 2 || newB.getData() == 10))
			fullClear(newB);

		newB = w.getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
		if ((ifLaterallyDrop(newB) == 1 || ifLaterallyDrop(newB) == 2) && newB.getData() == 3)
			fullClear(newB);
		if (ifLaterallyDrop(newB) == 3 && newB.getData() == 0)
			fullClear(newB);
		if ((ifLaterallyDrop(newB) == 4 || ifLaterallyDrop(newB) == 5) && (newB.getData() == 3 || newB.getData() == 11))
			fullClear(newB);

		newB = w.getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
		if (ifLaterallyDrop(newB) == 1 && newB.getData() == 2)
			fullClear(newB);
		if (ifLaterallyDrop(newB) == 2 && newB.getData() == 4)
			fullClear(newB);
		if (ifLaterallyDrop(newB) == 3 && newB.getData() == 2)
			fullClear(newB);
		if ((ifLaterallyDrop(newB) == 4 || ifLaterallyDrop(newB) == 5) && (newB.getData() == 4 || newB.getData() == 12))
			fullClear(newB);
	}

	public static boolean checkBlock(Block b) {
		if (ifWaterDrop(b) && isCreative(b)) {
			fullClear(b);
			return true;
		}
		return false;
	}

	public static void cactusClear(Block b) {
		Block cactus = null;

		cactus = b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ());
		if (cactus.getType().equals(Material.CACTUS) && isCreative(cactus)) {
			dropBlocks(cactus);
			fullClear(cactus);
		}

		cactus = b.getWorld().getBlockAt(b.getX() - 1, b.getY(), b.getZ());
		if (cactus.getType().equals(Material.CACTUS) && isCreative(cactus)) {
			dropBlocks(cactus);
			fullClear(cactus);
		}

		cactus = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
		if (cactus.getType().equals(Material.CACTUS) && isCreative(cactus)) {
			dropBlocks(cactus);
			fullClear(cactus);
		}

		cactus = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
		if (cactus.getType().equals(Material.CACTUS) && isCreative(cactus)) {
			dropBlocks(cactus);
			fullClear(cactus);
		}
	}

	/** EXTRA **/

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean ifUpDrop(Block b) {
		return b.getType() == Material.CARPET || b.getType() == Material.SIGN_POST || b.getType() == Material.CACTUS
				|| b.getType() == Material.SUGAR_CANE_BLOCK;
	}

	public static boolean ifOneUpDrop(Block b) {
		if (b.getType() == Material.BED_BLOCK || b.getType() == Material.LEVER || b.getType() == Material.TORCH
				|| b.getType() == Material.SAPLING || b.getType() == Material.REDSTONE_TORCH_ON
				|| b.getType() == Material.REDSTONE_TORCH_OFF || b.getType() == Material.WOODEN_DOOR
				|| b.getType() == Material.IRON_DOOR_BLOCK || b.getType() == Material.REDSTONE
				|| b.getType() == Material.REDSTONE_COMPARATOR || b.getType() == Material.REDSTONE_COMPARATOR_OFF
				|| b.getType() == Material.REDSTONE_COMPARATOR_ON || b.getType() == Material.REDSTONE_WIRE
				|| b.getType() == Material.DIODE_BLOCK_ON || b.getType() == Material.DIODE_BLOCK_OFF
				|| b.getType() == Material.DIODE || b.getType() == Material.RAILS
				|| b.getType() == Material.DETECTOR_RAIL || b.getType() == Material.ACTIVATOR_RAIL
				|| b.getType() == Material.POWERED_RAIL || b.getType() == Material.BED
				|| b.getType() == Material.FLOWER_POT || b.getType() == Material.DOUBLE_PLANT
				|| b.getType() == Material.RED_ROSE || b.getType() == Material.YELLOW_FLOWER
				|| b.getType() == Material.STONE_BUTTON || b.getType() == Material.WOOD_BUTTON
				|| b.getType() == Material.BROWN_MUSHROOM || b.getType() == Material.RED_MUSHROOM
				|| b.getType() == Material.STONE_PLATE || b.getType() == Material.WOOD_PLATE
				|| b.getType() == Material.GOLD_PLATE || b.getType() == Material.IRON_PLATE
				|| b.getType() == Material.POTATO || b.getType() == Material.CARROT || b.getType() == Material.CROPS
				|| b.getType() == Material.MELON_STEM || b.getType() == Material.PUMPKIN_STEM)
			return true;
		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus))
			if (b.getType() == Material.ACACIA_DOOR || b.getType() == Material.SPRUCE_DOOR
					|| b.getType() == Material.BIRCH_DOOR || b.getType() == Material.JUNGLE_DOOR
					|| b.getType() == Material.DARK_OAK_DOOR || b.getType() == Material.STANDING_BANNER)
				return true;
		return false;
	}

	public static int ifLaterallyDrop(Block b) {
		if (b.getType().equals(Material.LADDER) || b.getType().equals(Material.WALL_SIGN))
			return 1;
		if (b.getType().equals(Material.REDSTONE_TORCH_ON) || b.getType().equals(Material.REDSTONE_TORCH_OFF))
			return 2;
		if (b.getType().equals(Material.TRIPWIRE_HOOK) || b.getType().equals(Material.TRIPWIRE))
			return 3;
		if (b.getType().equals(Material.LEVER))
			return 4;
		if (b.getType().equals(Material.STONE_BUTTON) || b.getType().equals(Material.WOOD_BUTTON))
			return 5;
		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus)) {
			if (b.getType().equals(Material.STANDING_BANNER))
				return 1;
		}
		return 0;
	}

	public static boolean ifWaterDrop(Block b) {
		if (b.getType() == Material.LEVER || b.getType() == Material.WOOD_BUTTON || b.getType() == Material.TORCH
				|| b.getType() == Material.REDSTONE_TORCH_ON || b.getType() == Material.REDSTONE_TORCH_OFF
				|| b.getType() == Material.REDSTONE || b.getType() == Material.REDSTONE_COMPARATOR
				|| b.getType() == Material.REDSTONE_COMPARATOR_OFF || b.getType() == Material.REDSTONE_COMPARATOR_ON
				|| b.getType() == Material.REDSTONE_WIRE || b.getType() == Material.DIODE_BLOCK_ON
				|| b.getType() == Material.DIODE_BLOCK_OFF || b.getType() == Material.DIODE
				|| b.getType() == Material.RAILS || b.getType() == Material.DETECTOR_RAIL
				|| b.getType() == Material.ACTIVATOR_RAIL || b.getType() == Material.POWERED_RAIL
				|| b.getType() == Material.FLOWER_POT || b.getType() == Material.FLOWER_POT_ITEM
				|| b.getType() == Material.DOUBLE_PLANT || b.getType() == Material.RED_ROSE
				|| b.getType() == Material.YELLOW_FLOWER || b.getType() == Material.STONE_BUTTON
				|| b.getType() == Material.CARPET || b.getType() == Material.SAPLING || b.getType() == Material.WEB
				|| b.getType() == Material.SKULL || b.getType() == Material.SKULL_ITEM
				|| b.getType() == Material.ITEM_FRAME || b.getType() == Material.BROWN_MUSHROOM
				|| b.getType() == Material.RED_MUSHROOM)
			return true;
		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotNinePlus))
			if (b.getType() == Material.END_ROD)
				return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void sqlSave() {
		Integer id;
		Set<List<Integer>> keys = cache.keySet();
		for (int i = 0; i < keys.size(); i++) {
			List<Integer> key = (List<Integer>) keys.toArray()[i];
			Integer x = key.get(0), y = key.get(1), z = key.get(2), w = key.get(3);

			if (cache.get(key) == 0)
				id = null;
			else
				id = cache.get(key);

			MySQL.sqlUpdate(x, y, z, id, w);
			MySQL.sqlUpdate(x, y, z, id, w);
		}
		id = null;
	}
}