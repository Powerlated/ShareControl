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

package com.net.h1karo.sharecontrol.listeners.multiinventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.InventoriesDatabase;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class PlayerGameModeChangeListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public PlayerGameModeChangeListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void ChangeGameMode(PlayerGameModeChangeEvent e) {
		Player p = e.getPlayer();
		if (Permissions.perms(p, "allow.multi-inventories") || e.isCancelled()
				|| !Configuration.MultiInventoriesEnabled)
			return;
		if (!Configuration.InventorySeparation) {
			clear(p);
			return;
		}

		PlayerInventory Inventory = p.getInventory();

		caching(Inventory, p, e.getNewGameMode(), e.getPlayer().getGameMode());
	}

	public static HashMap<List<String>, List<List<ItemStack>>> cache = new HashMap<List<String>, List<List<ItemStack>>>();

	public static void clear(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(AIR);
		p.getInventory().setBoots(AIR);
		p.getInventory().setLeggings(AIR);
		p.getInventory().setChestplate(AIR);

		if (p.getOpenInventory() != null)
			p.getOpenInventory().close();

		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
	}

	static ItemStack AIR = new ItemStack(Material.AIR, 1);

	public static void saveMultiInv() {
		InventoriesDatabase.reloadInvConfig();

		for (List<String> key : cache.keySet()) {
			String uuid = key.get(0);
			String gamemode = key.get(1);
			if (!CoreVersion.getVersionsArray().contains(CoreVersion.OneDotNinePlus)) {
				for (int i = 0; i < cache.get(key).get(0).size(); i++)
					InventoriesDatabase.getInvConfig().set(uuid + "." + gamemode + ".armor." + i,
							cache.get(key).get(0).get(i));
				for (int i = 0; i < cache.get(key).get(1).size(); i++)
					InventoriesDatabase.getInvConfig().set(uuid + "." + gamemode + ".inventory.slot" + i,
							cache.get(key).get(1).get(i));
			} else
				for (int i = 0; i < cache.get(key).get(0).size(); i++)
					InventoriesDatabase.getInvConfig().set(uuid + "." + gamemode + ".inventory.slot" + i,
							cache.get(key).get(0).get(i));
		}
		InventoriesDatabase.saveInvConfig();
		cache.clear();
	}

	public static void caching(PlayerInventory inv, Player p, GameMode newgm, GameMode gm) {

		if (gm == GameMode.CREATIVE)
			UniversalCaching(p, inv, "creative");

		if (gm == GameMode.SURVIVAL)
			UniversalCaching(p, inv, "survival");

		if (gm == GameMode.ADVENTURE)
			UniversalCaching(p, inv, "adventure");

		if (newgm == GameMode.CREATIVE)
			UniversalPaste(p, "creative");

		if (newgm == GameMode.SURVIVAL)
			UniversalPaste(p, "survival");

		if (newgm == GameMode.ADVENTURE)
			UniversalPaste(p, "adventure");

		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus))
			if (newgm == GameMode.SPECTATOR)
				clear(p);
	}

	// HANDLERS

	@SuppressWarnings("deprecation")
	public static void UniversalCaching(Player p, PlayerInventory inv, String gamemode) {
		List<String> key = new ArrayList<String>();
		key.add(p.getUniqueId().toString());
		key.add(gamemode);
		List<List<ItemStack>> value = new ArrayList<List<ItemStack>>();

		if (!CoreVersion.getVersionsArray().contains(CoreVersion.OneDotNinePlus)) {
			List<ItemStack> armor = new ArrayList<ItemStack>();
			List<ItemStack> inventory = new ArrayList<ItemStack>();

			ItemStack[] ArmorStack = inv.getArmorContents();
			ItemStack[] InventoryStack = inv.getContents();

			// INVENTORY
			for (int i = 0; i < InventoryStack.length; i++) {
				ItemStack item = null;
				if (InventoryStack[i] != null) {
					item = InventoryStack[i].clone();
					if (item.getType().equals(Material.SKULL_ITEM))
						if (((SkullMeta) item.getItemMeta()).getOwner() == null && item.getData().getData() == 3) {
							ItemStack skull = new ItemStack(Material.SKULL_ITEM);
							skull.setData(new MaterialData(3));
							item = skull.clone();
						}
				}
				inventory.add(item);
				item = null;
			}
			// ARMOR
			for (int i = 0; i < ArmorStack.length; i++) {
				ItemStack item = null;
				if (ArmorStack[i] != null) {
					item = ArmorStack[i].clone();
					if (item.getType().equals(Material.SKULL_ITEM))
						if (((SkullMeta) item.getItemMeta()).getOwner() == null && item.getData().getData() == 3) {
							ItemStack skull = new ItemStack(Material.SKULL_ITEM);
							skull.setData(new MaterialData(3));
							item = skull.clone();
						}
				}
				armor.add(item);
				item = null;
			}

			value.add(armor);
			value.add(inventory);

			cache.put(key, value);

			ArmorStack = null;
			InventoryStack = null;
		} else {
			List<ItemStack> inventory = new ArrayList<ItemStack>();

			// INVENTORY
			for (int i = 0; i < inv.getSize(); i++) {
				ItemStack item = null;
				if (inv.getItem(i) != null) {
					item = inv.getItem(i);
					if (item.getType().equals(Material.SKULL_ITEM))
						if (((SkullMeta) item.getItemMeta()).getOwner() == null && item.getData().getData() == 3) {
							ItemStack skull = new ItemStack(Material.SKULL_ITEM);
							skull.setData(new MaterialData(3));
							item = skull;
						}
				}
				inventory.add(item);
			}

			value.add(inventory);
			cache.put(key, value);
		}
	}

	public static void UniversalPaste(Player p, String gamemode) {
		List<String> key = new ArrayList<String>();
		key.add(p.getUniqueId().toString());
		key.add(gamemode);

		clear(p);
		if (!CoreVersion.getVersionsArray().contains(CoreVersion.OneDotNinePlus)) {
			ItemStack[] ArmorStack = new ItemStack[4];
			ItemStack[] InventoryStack = new ItemStack[36];

			if (cache.containsKey(key))
				for (int i = 0; i < cache.get(key).get(0).size(); i++)
					ArmorStack[i] = cache.get(key).get(0).get(i);
			else
				for (int i = 0; i < 4; i++)
					ArmorStack[i] = InventoriesDatabase.getInvConfig()
							.getItemStack(p.getUniqueId() + "." + gamemode + ".armor." + i, AIR);

			if (cache.containsKey(key))
				for (int i = 0; i < cache.get(key).get(1).size(); i++)
					InventoryStack[i] = cache.get(key).get(1).get(i);
			else
				for (int i = 0; i < 36; i++)
					InventoryStack[i] = InventoriesDatabase.getInvConfig()
							.getItemStack(p.getUniqueId() + "." + gamemode + ".inventory.slot" + i, AIR);

			p.getInventory().setContents(InventoryStack);
			p.getInventory().setArmorContents(ArmorStack);
			ArmorStack = null;
			InventoryStack = null;
		} else if (cache.containsKey(key))
			for (int i = 0; i < cache.get(key).get(0).size(); i++)
				p.getInventory().setItem(i, cache.get(key).get(0).get(i));
		else
			for (int i = 0; i < 41; i++)
				p.getInventory().setItem(i, InventoriesDatabase.getInvConfig()
						.getItemStack(p.getUniqueId() + "." + gamemode + ".inventory.slot" + i, AIR));
	}
}
