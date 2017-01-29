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
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;

public class PlayerArmorStandSpawnListener implements Listener {

	private final ShareControl main;

	public PlayerArmorStandSpawnListener(ShareControl h) {
		this.main = h;
	}

	boolean sync = false;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerArmorStandSpawn(EntitySpawnEvent e) {
		if (e.isCancelled() || !sync)
			return;
		Entity ent = e.getEntity();
		if (ent instanceof ArmorStand) {
			ent.setMetadata("ShareControl.CREATIVE_ENTITY", new FixedMetadataValue(main, "true"));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerArmorStandSpawn(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE || e.isCancelled() || Permissions.perms(p, "allow.notlogging"))
			return;
		if (p.getInventory().getItemInMainHand().getType() == Material.ARMOR_STAND) {
			sync = true;
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(main, new Runnable() {
				@Override
				public void run() {
					sync = false;
				}
			}, 30L);
		}
	}
}
