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

package com.net.h1karo.sharecontrol.listeners.entity;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;

public class BreedingListener implements Listener {
	private final ShareControl main;

	public BreedingListener(ShareControl h) {
		this.main = h;
	}

	boolean BREEDING = false;

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerInteract(PlayerInteractEntityEvent clk) {
		if (clk.getPlayer().getGameMode() == GameMode.CREATIVE && clk.getRightClicked() instanceof Animals
				&& Permissions.perms(clk.getPlayer(), "allow.creature-interact")) {
			BREEDING = true;

			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(main, new Runnable() {
				@Override
				public void run() {
					BREEDING = false;
				}
			}, 30L);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerInteract(CreatureSpawnEvent e) {
		if (e.isCancelled())
			return;
		if (e.getSpawnReason() == SpawnReason.BREEDING && BREEDING) {
			e.setCancelled(true);
			BREEDING = false;
		}
	}
}
