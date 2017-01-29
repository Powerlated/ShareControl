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

package com.net.h1karo.sharecontrol.listeners.entity;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class EntityChangeBlockListener implements Listener {
	private final ShareControl main;

	public EntityChangeBlockListener(ShareControl h) {
		this.main = h;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void EntityChangeBlock(EntityChangeBlockEvent e) {
		if (e.isCancelled())
			return;
		Block b = e.getBlock();
		Entity eventEntity = e.getEntity();

		Database.dropBlocks(b);

		if (eventEntity.getType().equals(EntityType.FALLING_BLOCK)) {
			if (Database.isCreative(b)) {
				FallingBlock entity = (FallingBlock) eventEntity;
				if (e.getTo() == Material.AIR) {
					entity.setDropItem(false);
					Database.removeBlock(b);
					entity.setMetadata("ShareControl.CREATIVE_FALLING_BLOCK",
							new FixedMetadataValue(main, b.getType().getId()));
				}
			}

			if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotNinePlus)) {
				if (e.getTo().equals(b.getType()) && !e.getTo().equals(Material.AIR)) {
					FallingBlock entity = (FallingBlock) eventEntity;
					if (entity.hasMetadata("ShareControl.CREATIVE_FALLING_BLOCK")) {
						Database.addBlockMoreArguments(b,
								entity.getMetadata("ShareControl.CREATIVE_FALLING_BLOCK").get(0).asInt());
						entity.removeMetadata("ShareControl.CREATIVE_FALLING_BLOCK", main);
					}
				}
			} else if (!e.getTo().equals(Material.AIR) && b.getType().equals(Material.AIR)) {
				FallingBlock entity = (FallingBlock) eventEntity;
				if (entity.hasMetadata("ShareControl.CREATIVE_FALLING_BLOCK")) {
					Database.addBlockMoreArguments(b,
							entity.getMetadata("ShareControl.CREATIVE_FALLING_BLOCK").get(0).asInt());
					entity.removeMetadata("ShareControl.CREATIVE_FALLING_BLOCK", main);
				}
			}
		}
	}
}
