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

package com.net.h1karo.sharecontrol.listeners.survival;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.localization.Localization;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class BlockBreakListener implements Listener {

	@SuppressWarnings("unused")
	private final ShareControl main;

	public BlockBreakListener(ShareControl h) {
		this.main = h;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void OnBreak(BlockBreakEvent e) {
		if (e.isCancelled())
			return;
		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.CREATIVE)
			return;
		Block b = e.getBlock();

		if (b.getType().equals(Material.PISTON_EXTENSION)) {
			World w = b.getWorld();
			if (!Database.CheckCreative(b))
				return;
			if (b.getData() == 13 || b.getData() == 5) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX() - 1, b.getY(), b.getZ());
				AClearBlock(piston, e);
				ClearBlock(e);
			}

			if (b.getData() == 12 || b.getData() == 4) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX() + 1, b.getY(), b.getZ());
				AClearBlock(piston, e);
				ClearBlock(e);
			}

			if (b.getData() == 11 || b.getData() == 3) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
				AClearBlock(piston, e);
				ClearBlock(e);
			}

			if (b.getData() == 10 || b.getData() == 2) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
				AClearBlock(piston, e);
				ClearBlock(e);
			}

			if (b.getData() == 0 || b.getData() == 8) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX(), b.getY() + 1, b.getZ());
				AClearBlock(piston, e);
				ClearBlock(e);
			}

			if (b.getData() == 1 || b.getData() == 9) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX(), b.getY() - 1, b.getZ());
				AClearBlock(piston, e);
				ClearBlock(e);
			}
			return;
		}

		if (isDoor(b)) {
			World w = b.getWorld();
			Block door;
			if (b.getData() == 8 || b.getData() == 9) {
				e.setCancelled(true);
				door = w.getBlockAt(b.getX(), b.getY() - 1, b.getZ());
				if (Database.CheckCreative(door)) {
					if (!Configuration.BlockingBreak) {
						door.setType(Material.AIR);
						Localization.SurvivalBlockNotDrop(p);
						Database.RemoveBlock(door);
					} else {
						Localization.SurvivalBlockNotBreak(p);
						return;
					}
				}
				return;
			}
		}
		ClearBlock(e);
	}

	public void AClearBlock(Block b, BlockBreakEvent e) {
		if (Database.CheckCreative(b)) {
			e.setCancelled(true);
			if (!Configuration.BlockingBreak)
				b.setType(Material.AIR);
			else
				return;
			Database.RemoveBlock(b);
			return;
		}
	}

	public void ClearBlock(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();

		if (Database.CheckCreative(b)) {
			e.setCancelled(true);
			if (!Configuration.BlockingBreak) {
				b.setType(Material.AIR);
				Localization.SurvivalBlockNotDrop(p);
			} else {
				Localization.SurvivalBlockNotBreak(p);
				return;
			}

			Database.RemoveBlock(b);
			return;
		}
	}

	public boolean isDoor(Block b) {
		if (b.getType().equals(Material.WOODEN_DOOR) || b.getType().equals(Material.IRON_DOOR_BLOCK))
			return true;
		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus))
			if (b.getType().equals(Material.ACACIA_DOOR) || b.getType().equals(Material.SPRUCE_DOOR)
					|| b.getType().equals(Material.BIRCH_DOOR) || b.getType().equals(Material.JUNGLE_DOOR)
					|| b.getType().equals(Material.DARK_OAK_DOOR))
				return true;
		return false;
	}
}
