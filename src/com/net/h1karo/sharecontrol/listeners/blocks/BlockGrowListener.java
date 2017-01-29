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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.database.Database;

public class BlockGrowListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public BlockGrowListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void BlockGrow(BlockGrowEvent e) {
		Block newb = e.getBlock();

		World w = newb.getWorld();
		int x = newb.getX(), y = newb.getY(), z = newb.getZ();
		Block b = w.getBlockAt(x, y - 1, z);

		if (Database.isCreative(newb) && ifFood(newb.getType())) {
			e.setCancelled(true);
		}

		if (Database.isCreative(b) && (b.getType() == Material.CACTUS || b.getType() == Material.SUGAR_CANE_BLOCK)) {
			e.setCancelled(true);
		}
	}

	public boolean ifFood(Material m) {
		return m == Material.PUMPKIN_STEM || m == Material.MELON_STEM || m == Material.CROPS || m == Material.CARROT
				|| m == Material.POTATO;
	}
}
