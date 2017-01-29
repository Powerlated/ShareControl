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

package com.net.h1karo.sharecontrol.listeners.gamemodescontrol;

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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class AccessCheckListener implements Listener {

	@SuppressWarnings("unused")
	private final ShareControl main;

	public AccessCheckListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void BlockBreak(BlockBreakEvent e) {
		if (!Configuration.GamemodesControlEnabled || e.isCancelled()
				|| Permissions.perms(e.getPlayer(), "gamemodescontrol.*"))
			return;
		AccessCheck(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void BlockPlace(BlockPlaceEvent e) {
		if (!Configuration.GamemodesControlEnabled || e.isCancelled()
				|| Permissions.perms(e.getPlayer(), "gamemodescontrol.*"))
			return;
		AccessCheck(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void EntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.isCancelled() || !Configuration.GamemodesControlEnabled)
			return;
		Entity entDamager = e.getDamager();
		Entity entDamage = e.getEntity();
		if (!(entDamager instanceof Player)
				|| (!(entDamage instanceof Creature) && !(entDamage.toString() == "CraftSlime"))
				|| Permissions.perms((Player) e.getDamager(), "gamemodescontrol.*"))
			return;
		Player p = (Player) e.getDamager();
		AccessCheck(p);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerChangedWorld(PlayerChangedWorldEvent e) {
		if (!Configuration.GamemodesControlEnabled || Permissions.perms(e.getPlayer(), "gamemodescontrol.*"))
			return;
		AccessCheck(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void InventoryClick(InventoryClickEvent e) {
		if (e.isCancelled() || !Configuration.GamemodesControlEnabled
				|| Permissions.perms((Player) e.getWhoClicked(), "gamemodescontrol.*"))
			return;
		AccessCheck((Player) e.getWhoClicked());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void InventoryOpen(InventoryOpenEvent e) {
		if (e.isCancelled() || !Configuration.GamemodesControlEnabled
				|| Permissions.perms((Player) e.getPlayer(), "gamemodescontrol.*"))
			return;
		AccessCheck((Player) e.getPlayer());
	}

	public static void AccessCheck(Player p) {
		if (p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "gamemodescontrol.creative")) {
			p.setGameMode(GameMode.SURVIVAL);
			Localization.NotAllowedGamemode(p, "creative");
		}

		if (p.getGameMode() == GameMode.SURVIVAL && !Permissions.perms(p, "gamemodescontrol.survival")) {
			p.setGameMode(GameMode.CREATIVE);
			Localization.NotAllowedGamemode(p, "survival");
		}

		if (p.getGameMode() == GameMode.ADVENTURE && !Permissions.perms(p, "gamemodescontrol.adventure")) {
			p.setGameMode(GameMode.SURVIVAL);
			Localization.NotAllowedGamemode(p, "adventure");
		}
		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus))
			if (p.getGameMode() == GameMode.SPECTATOR && !Permissions.perms(p, "gamemodescontrol.spectator")) {
				p.setGameMode(GameMode.SURVIVAL);
				Localization.NotAllowedGamemode(p, "spectator");
			}
	}
}
