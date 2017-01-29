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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.localization.Localization;

public class BlockPlaceListener implements Listener {

	@SuppressWarnings("unused")
	private final ShareControl main;

	public BlockPlaceListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void creativeBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		if (p.getGameMode() != GameMode.CREATIVE || e.isCancelled())
			return;
		Database.cactusClear(b);
		if (!Permissions.perms(p, "allow.notlogging"))
			Database.AddBlock(b);
		else
			Database.removeBlock(b);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void disableBlockPlace(BlockPlaceEvent e) {
		if (e.isCancelled() || Configuration.BlockingBlocksPlaceList.contains("none") || e.getBlock() == null)
			return;
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if (Permissions.perms(p, "allow.blocking-placement.*") || p.getGameMode() != GameMode.CREATIVE)
			return;
		if ((Configuration.BlockingBlocksPlaceList.contains(b.getTypeId())
				&& !Permissions.perms(p, "allow.blocking-placement." + b.getTypeId()))
				|| (Configuration.BlockingBlocksPlaceList.contains(b.getType().toString())
						&& !Permissions.perms(p, "allow.blocking-placement." + b.getType().toString()))) {
			Localization.PlaceBlock(b.getType(), p);
			e.setCancelled(true);
		}
	}
}
