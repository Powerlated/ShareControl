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
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerArmorStandManipulateListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public PlayerArmorStandManipulateListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void armorStand(PlayerArmorStandManipulateEvent e) {
		if (Permissions.perms(e.getPlayer(), "allow.blocking-interact") || e.isCancelled())
			return;
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
			Localization.armorStand(e.getPlayer());
		}
	}
}
