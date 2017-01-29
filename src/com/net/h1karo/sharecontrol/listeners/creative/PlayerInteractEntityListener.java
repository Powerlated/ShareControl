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

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerInteractEntityListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public PlayerInteractEntityListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void playerInteractCreature(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		Entity entDamage = e.getRightClicked();
		if (entDamage instanceof Player
				|| (!(entDamage.toString() == "CraftVillager") || !(entDamage.toString() == "CraftSlime"))
						&& p.getInventory().getItemInMainHand().getType() != Material.SADDLE)
			return;
		if (p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.creature-interact")) {
			e.setCancelled(true);
			Localization.monsterInteractNotify(p);
		}
	}

	@EventHandler
	public void playerInteractEntity(PlayerInteractEntityEvent e) {
		if (e.isCancelled())
			return;
		Player p = e.getPlayer();
		if (Permissions.perms(p, "allow.blocking-inventory") || p.getGameMode() != GameMode.CREATIVE)
			return;
		EntityType typeThisEntity = e.getRightClicked().getType();
		if (typeThisEntity == EntityType.ITEM_FRAME || typeThisEntity == EntityType.PAINTING) {
			Localization.entityUseNotify(typeThisEntity, p);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void playerInteractMinecartInventory(PlayerInteractEntityEvent e) {
		if (e.isCancelled())
			return;
		Player p = e.getPlayer();

		if (Permissions.perms(p, "allow.blocking-interact") || p.getGameMode() != GameMode.CREATIVE)
			return;
		if (e.getRightClicked().getType().name().toLowerCase().startsWith("minecart_")) {
			e.setCancelled(true);
			Localization.openInv(p);
		}
	}
}
