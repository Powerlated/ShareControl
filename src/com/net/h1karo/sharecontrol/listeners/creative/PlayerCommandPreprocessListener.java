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

import java.util.List;

import org.bukkit.GameMode;
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
	public void playerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (!Configuration.blockingCmdsEnabled || Permissions.perms(e.getPlayer(), "allow.commands"))
			return;
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			List<String> list = Configuration.blockingCmdsList;
			for (int i = 0; i < list.size(); i++) {
				String cmd = "/" + list.get(i);
				if (e.getMessage().toLowerCase().contains(cmd)) {
					e.setCancelled(true);
					Localization.ProhibitedCmd(e.getPlayer());
					return;
				}
			}
		}
	}
}
