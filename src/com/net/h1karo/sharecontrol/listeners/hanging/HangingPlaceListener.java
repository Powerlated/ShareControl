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

package com.net.h1karo.sharecontrol.listeners.hanging;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;

public class HangingPlaceListener implements Listener {

	private final ShareControl main;

	public HangingPlaceListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void HangingPlace(HangingPlaceEvent e) {
		Player p = e.getPlayer();
		Entity ent = e.getEntity();
		if (p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.notlogging")) {
			ent.setMetadata("ShareControl.CREATIVE_ENTITY", new FixedMetadataValue(main, "1"));
		}
	}
}
