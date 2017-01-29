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

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerInteractHorseListener implements Listener {
	private final ShareControl main;

	public PlayerInteractHorseListener(ShareControl h) {
		this.main = h;
	}

	private boolean Interact = false;
	private Player player = null;

	@EventHandler
	public void interactWithHorse(PlayerInteractEntityEvent e) {
		Player p = (Player) e.getPlayer();
		if (e.getRightClicked().getType() == EntityType.HORSE && e.getPlayer().getGameMode() == GameMode.CREATIVE
				&& !Permissions.perms(p, "allow.blocking-inventory")) {
			Interact = true;
			player = e.getPlayer();
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				@Override
				public void run() {
					Interact = false;
				}
			}, 20L);
		}
	}

	@EventHandler
	public void interactWithChest(InventoryOpenEvent e) {
		Player p = (Player) e.getPlayer();
		if (player != null)
			if (e.getInventory().getType() == InventoryType.CHEST && Interact && player == e.getPlayer()
					&& e.getPlayer().getGameMode() == GameMode.CREATIVE
					&& !Permissions.perms(p, "allow.blocking-inventory")) {
				e.setCancelled(true);
				Localization.openInv(p);
			}
	}
}
