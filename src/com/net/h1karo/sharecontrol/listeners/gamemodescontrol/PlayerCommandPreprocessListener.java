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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerCommandPreprocessListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public PlayerCommandPreprocessListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void PlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (!Configuration.GamemodesControlEnabled || Permissions.perms(e.getPlayer(), "gamemodescontrol.*")
				|| e.isCancelled())
			return;
		if (!Permissions.perms(e.getPlayer(), "gamemodescontrol.survival")) {
			if (e.getMessage().equalsIgnoreCase("/gamemode 0") || e.getMessage().equalsIgnoreCase("/gamemode s")
					|| e.getMessage().toLowerCase().contains("/gamemode survi")
					|| e.getMessage().equalsIgnoreCase("/gm 0") || e.getMessage().equalsIgnoreCase("/gm s")
					|| e.getMessage().toLowerCase().contains("/gm survi"))
				if (!ShareControl.getFoundEssentials()
						|| (ShareControl.getFoundEssentials() && e.getPlayer().hasPermission("essentials.gamemode"))) {
					Localization.NotAllowedGamemode(e.getPlayer(), "survival");
					e.setCancelled(true);
				}
		}

		if (!Permissions.perms(e.getPlayer(), "gamemodescontrol.creative")) {
			if (e.getMessage().equalsIgnoreCase("/gamemode 1") || e.getMessage().equalsIgnoreCase("/gamemode c")
					|| e.getMessage().toLowerCase().contains("/gamemode creat")
					|| e.getMessage().equalsIgnoreCase("/gm 1") || e.getMessage().equalsIgnoreCase("/gm c")
					|| e.getMessage().toLowerCase().contains("/gm creat"))
				if (!ShareControl.getFoundEssentials()
						|| (ShareControl.getFoundEssentials() && e.getPlayer().hasPermission("essentials.gamemode"))) {
					Localization.NotAllowedGamemode(e.getPlayer(), "creative");
					e.setCancelled(true);
				}
		}

		if (!Permissions.perms(e.getPlayer(), "gamemodescontrol.adventure")) {
			if (e.getMessage().equalsIgnoreCase("/gamemode 2") || e.getMessage().equalsIgnoreCase("/gamemode a")
					|| e.getMessage().toLowerCase().contains("/gamemode advent")
					|| e.getMessage().equalsIgnoreCase("/gm 2") || e.getMessage().equalsIgnoreCase("/gm a")
					|| e.getMessage().toLowerCase().contains("/gm advent"))
				if (!ShareControl.getFoundEssentials()
						|| (ShareControl.getFoundEssentials() && e.getPlayer().hasPermission("essentials.gamemode"))) {
					Localization.NotAllowedGamemode(e.getPlayer(), "adventure");
					e.setCancelled(true);
				}
		}

		if (!Permissions.perms(e.getPlayer(), "gamemodescontrol.spectator")) {
			if (e.getMessage().equalsIgnoreCase("/gamemode 3") || e.getMessage().equalsIgnoreCase("/gamemode sp")
					|| e.getMessage().toLowerCase().contains("/gamemode spect")
					|| e.getMessage().equalsIgnoreCase("/gm 3") || e.getMessage().equalsIgnoreCase("/gm sp")
					|| e.getMessage().toLowerCase().contains("/gm spect"))
				if (!ShareControl.getFoundEssentials()
						|| (ShareControl.getFoundEssentials() && e.getPlayer().hasPermission("essentials.gamemode"))) {
					Localization.NotAllowedGamemode(e.getPlayer(), "spectator");
					e.setCancelled(true);
				}
		}
	}
}
