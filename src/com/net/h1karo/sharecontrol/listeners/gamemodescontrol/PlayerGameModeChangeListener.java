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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerGameModeChangeListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public PlayerGameModeChangeListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void ChangeGameMode(PlayerGameModeChangeEvent e) {
		if (!Configuration.fullGcEnabled || !Configuration.gamemodesControlEnabled
				|| Permissions.perms(e.getPlayer(), "gamemodescontrol.*") || e.isCancelled())
			return;
		if (e.getNewGameMode() == GameMode.CREATIVE && !Permissions.perms(e.getPlayer(), "gamemodescontrol.creative")) {
			e.setCancelled(true);
			Localization.NotAllowedGamemode(e.getPlayer(), "creative");
		}

		if (e.getNewGameMode() == GameMode.SURVIVAL && !Permissions.perms(e.getPlayer(), "gamemodescontrol.survival")) {
			e.setCancelled(true);
			Localization.NotAllowedGamemode(e.getPlayer(), "survival");
		}

		if (e.getNewGameMode() == GameMode.ADVENTURE
				&& !Permissions.perms(e.getPlayer(), "gamemodescontrol.adventure")) {
			e.setCancelled(true);
			Localization.NotAllowedGamemode(e.getPlayer(), "adventure");
		}

		if (e.getNewGameMode() == GameMode.SPECTATOR
				&& !Permissions.perms(e.getPlayer(), "gamemodescontrol.spectator")) {
			e.setCancelled(true);
			Localization.NotAllowedGamemode(e.getPlayer(), "spectator");
		}
	}
}
