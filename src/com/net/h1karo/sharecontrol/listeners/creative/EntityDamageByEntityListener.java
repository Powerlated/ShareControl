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
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class EntityDamageByEntityListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public EntityDamageByEntityListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void creativeDamageCreature(EntityDamageByEntityEvent e) {
		Entity player = e.getDamager();
		Entity entity = e.getEntity();
		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus)) {
			if (!(player instanceof Player) || !(entity instanceof LivingEntity)
					|| entity instanceof org.bukkit.entity.ArmorStand)
				return;
		} else if (!(player instanceof Player) || !(entity instanceof LivingEntity))
			return;
		Player p = (Player) e.getDamager();
		if (p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.creature-interact")
				&& Configuration.creatureInteract) {
			e.setCancelled(true);
			Localization.monsterInteractNotify(p);
		}
	}

	@EventHandler
	public void creativeDamagePlayer(EntityDamageByEntityEvent e) {
		Entity entDamager = e.getDamager();
		Entity entDamage = e.getEntity();
		if (!(entDamager instanceof Player) || !(entDamage instanceof Player))
			return;
		Player p = (Player) e.getDamager();
		if (p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.player-interact")
				&& Configuration.playerInteract) {
			e.setCancelled(true);
			Localization.PlayerInteractNotify(p);
		}
	}
}
