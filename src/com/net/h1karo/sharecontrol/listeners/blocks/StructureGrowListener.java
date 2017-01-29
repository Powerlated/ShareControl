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

package com.net.h1karo.sharecontrol.listeners.blocks;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.localization.Localization;

public class StructureGrowListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public StructureGrowListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void StructureGrow(StructureGrowEvent e) {
		for (BlockState b : e.getBlocks())
			if (Database.CheckCreative(b.getBlock()) && b.getBlock().getType() == Material.SAPLING) {
				e.setCancelled(true);
				if (e.getPlayer() != null)
					Localization.Saplings(e.getPlayer());
				return;
			}
	}
}
