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
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;

public class BlockPlaceToCreationsListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public BlockPlaceToCreationsListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void antiCreations(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		World world = p.getWorld();

		if (p.getGameMode() != GameMode.CREATIVE)
			return;

		// Anti Whiter Creation

		if ((!Permissions.perms(p, "allow.creatures"))) {
			if (b.getType() == Material.SKULL) {
				if (checkWitherSkull(b)) {
					e.setCancelled(true);
					return;
				}
			}
		}

		// Anti SnowGolem Creation

		if ((!Permissions.perms(p, "allow.creatures"))
				&& ((b.getType() == Material.PUMPKIN) || (b.getType() == Material.JACK_O_LANTERN))
				&& (world.getBlockAt(b.getX(), b.getY() - 1, b.getZ()).getType() == Material.SNOW_BLOCK)
				&& (world.getBlockAt(b.getX(), b.getY() - 2, b.getZ()).getType() == Material.SNOW_BLOCK)) {

			e.setCancelled(true);
			return;
		}

		// Anti IronGolem Creation

		if ((!Permissions.perms(p, "allow.creatures"))
				&& ((b.getType() == Material.PUMPKIN) || (b.getType() == Material.JACK_O_LANTERN))
				&& (world.getBlockAt(b.getX(), b.getY() - 1, b.getZ()).getType() == Material.IRON_BLOCK)
				&& (world.getBlockAt(b.getX(), b.getY() - 2, b.getZ()).getType() == Material.IRON_BLOCK)
				&& (((world.getBlockAt(b.getX() + 1, b.getY() - 1, b.getZ()).getType() == Material.IRON_BLOCK)
						&& (world.getBlockAt(b.getX() - 1, b.getY() - 1, b.getZ()).getType() == Material.IRON_BLOCK))
						|| ((world.getBlockAt(b.getX(), b.getY() - 1, b.getZ() + 1).getType() == Material.IRON_BLOCK)
								&& (world.getBlockAt(b.getX(), b.getY() - 1, b.getZ() - 1)
										.getType() == Material.IRON_BLOCK)))) {
			e.setCancelled(true);
			return;
		}
	}

	// EXTRA
	private boolean checkWitherSkull(Block b) {
		World world = b.getWorld();
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		Material ss = Material.SOUL_SAND;

		/**
		 * IF CENTER SKULL
		 */
		if (world.getBlockAt(x, y - 1, z).getType() == ss && world.getBlockAt(x, y - 2, z).getType() == ss
				&& ((world.getBlockAt(x + 1, y - 1, z).getType() == ss
						&& world.getBlockAt(x - 1, y - 1, z).getType() == ss)
						|| ((world.getBlockAt(x, y - 1, z + 1).getType() == ss
								&& world.getBlockAt(x, y - 1, z - 1).getType() == ss))))
			return true;
		/**
		 * IF LEFT SKULL
		 */
		if (world.getBlockAt(x, y - 1, z).getType() == ss && ((world.getBlockAt(x + 1, y - 1, z).getType() == ss
				&& world.getBlockAt(x + 2, y - 1, z).getType() == ss
				&& world.getBlockAt(x + 1, y - 2, z).getType() == ss)
				|| (world.getBlockAt(x, y - 1, z + 1).getType() == ss
						&& world.getBlockAt(x, y - 1, z + 2).getType() == ss
						&& world.getBlockAt(x, y - 2, z + 1).getType() == ss)))
			return true;
		/**
		 * IF RIGHT SKULL
		 */
		if (world.getBlockAt(x, y - 1, z).getType() == ss && ((world.getBlockAt(x - 1, y - 1, z).getType() == ss
				&& world.getBlockAt(x - 2, y - 1, z).getType() == ss
				&& world.getBlockAt(x - 1, y - 2, z).getType() == ss)
				|| (world.getBlockAt(x, y - 1, z - 1).getType() == ss
						&& world.getBlockAt(x, y - 1, z - 2).getType() == ss
						&& world.getBlockAt(x, y - 2, z - 1).getType() == ss)))
			return true;
		return false;
	}

}
