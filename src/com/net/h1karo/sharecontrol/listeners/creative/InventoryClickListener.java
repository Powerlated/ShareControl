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

package com.net.h1karo.sharecontrol.listeners.creative;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.localization.Localization;

public class InventoryClickListener implements Listener {
	private final ShareControl main;

	public InventoryClickListener(ShareControl h) {
		this.main = h;
	}

	public static List<Player> cache = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void inventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		cache.add((Player) e.getWhoClicked());
		if (p.getGameMode() != GameMode.CREATIVE || Permissions.perms(p, "allow.blocking-inventory.*")
				|| Configuration.blockingItemsInvList.toArray().length == 0
				|| Configuration.blockingItemsInvList.get(0).toString().compareToIgnoreCase("[none]") == 0)
			return;
		for (int i = 0; i < Configuration.blockingItemsInvList.toArray().length; i++) {
			String StrListItem = (String) Configuration.blockingItemsInvList.toArray()[i];
			if (!Permissions.perms(p, "allow.blocking-placement." + StrListItem)) {
				Material typeThisItem = e.getCursor().getType();
				Material typeListItem;
				if (Database.isInteger(StrListItem)) {
					String NewStr = StrListItem.replace("'", "");
					int ID = Integer.parseInt(NewStr);
					typeListItem = Material.getMaterial(ID);
				} else
					typeListItem = Material.getMaterial(StrListItem);

				if (typeThisItem == typeListItem) {
					Localization.invNotify(typeThisItem, p);
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void InventoryDrop(InventoryCreativeEvent e) {
		if (!Configuration.clearDropInInventory)
			return;
		final Player p = (Player) e.getWhoClicked();
		if (p.getGameMode() != GameMode.CREATIVE || Permissions.perms(p, "allow.drop")
				|| !e.getAction().equals(InventoryAction.PLACE_ALL) || !e.getClick().equals(ClickType.CREATIVE)
				|| e.getCursor().getType().equals(Material.AIR) || e.getCurrentItem() != null)
			return;
		cache.add(p);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				cache.remove(p);
			}
		}, 20L);
	}
}
