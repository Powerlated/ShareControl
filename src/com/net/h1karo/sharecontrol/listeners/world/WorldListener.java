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

package com.net.h1karo.sharecontrol.listeners.world;

import org.bukkit.GameMode;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class WorldListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public WorldListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBreakWorld(BlockBreakEvent e) {
		if (e.isCancelled())
			return;

		if (!Configuration.worldsCfgEnabled)
			return;

		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.CREATIVE) {
			for (int i = 0; i < Configuration.blockingCreative.toArray().length; i++) {
				if (Permissions.perms(p, "allow.blocking-creative")
						|| Configuration.blockingCreative.toArray()[i].toString() == "[none]")
					return;
				String ListWorld = Configuration.blockingCreative.toArray()[i].toString();
				if (e.getBlock().getLocation().getWorld().getName().toString().compareToIgnoreCase(ListWorld) == 0) {
					e.setCancelled(true);
					p.setGameMode(GameMode.SURVIVAL);

					Localization.InBlockWorld(p);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlaceWorld(BlockPlaceEvent e) {
		if (e.isCancelled())
			return;

		if (!Configuration.worldsCfgEnabled)
			return;

		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.CREATIVE) {
			for (int i = 0; i < Configuration.blockingCreative.toArray().length; i++) {
				if (Permissions.perms(p, "allow.blocking-creative")
						|| Configuration.blockingCreative.toArray()[i].toString() == "[none]")
					return;
				String ListWorld = Configuration.blockingCreative.toArray()[i].toString();
				if (e.getBlock().getLocation().getWorld().getName().toString().compareToIgnoreCase(ListWorld) == 0) {
					e.setCancelled(true);
					p.setGameMode(GameMode.SURVIVAL);

					Localization.InBlockWorld(p);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteractWorld(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;

		if (!Configuration.worldsCfgEnabled)
			return;

		Entity entDamager = e.getDamager();
		Entity entDamage = e.getEntity();
		if (!(entDamager instanceof Player)
				|| (!(entDamage instanceof Creature) && !(entDamage.toString() == "CraftSlime")))
			return;
		Player p = (Player) e.getDamager();
		if (p.getGameMode() == GameMode.CREATIVE) {
			for (int i = 0; i < Configuration.blockingCreative.toArray().length; i++) {
				if (Permissions.perms(p, "allow.blocking-creative")
						|| Configuration.blockingCreative.toArray()[i].toString() == "[none]")
					return;
				String ListWorld = Configuration.blockingCreative.toArray()[i].toString();
				if (e.getDamager().getLocation().getWorld().getName().toString().compareToIgnoreCase(ListWorld) == 0) {
					e.setCancelled(true);
					p.setGameMode(GameMode.SURVIVAL);

					Localization.InBlockWorld(p);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void ChangeWorld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		if (!(Configuration.worldsCfgEnabled || p.getGameMode() == GameMode.CREATIVE))
			return;

		for (int i = 0; i < Configuration.blockingCreative.toArray().length; i++) {
			if (Permissions.perms(p, "allow.blocking-creative")
					|| Configuration.blockingCreative.toArray()[i].toString() == "[none]")
				return;
			String ListWorld = Configuration.blockingCreative.toArray()[i].toString();
			if (p.getLocation().getWorld().getName().toString().compareToIgnoreCase(ListWorld) == 0)
				p.setGameMode(GameMode.SURVIVAL);
		}
	}
}
