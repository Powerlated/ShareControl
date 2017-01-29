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
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.database.Database;

public class BlockFromToListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public BlockFromToListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void WaterBreakBlock(BlockFromToEvent e) {
		Block b = e.getToBlock();
		if (Database.ifWaterDrop(b) && Database.CheckCreative(b)) {
			e.setCancelled(true);
			b.setType(Material.AIR);
			Database.RemoveBlock(b);
			return;
		}
	}
}
