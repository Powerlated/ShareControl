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

import net.md_5.bungee.api.ChatColor;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;
import com.net.h1karo.sharecontrol.localization.Localization;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class PlayerInteractListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;

	public PlayerInteractListener(ShareControl h) {
		this.main = h;
	}

	boolean ifOpen = false;

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e) {
		if (e.isCancelled())
			return;

		Player p = e.getPlayer();
		if (Permissions.perms(p, "allow.open-inventory") || e.getPlayer().getGameMode() != GameMode.CREATIVE)
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (!isChestInventory(e.getClickedBlock().getType()))
				return;

			Localization.openInv(p);
			e.setCancelled(true);
		}
	}

	public boolean isChestInventory(Material m) {
		return Material.CHEST == m || Material.TRAPPED_CHEST == m
				|| (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotElevenPlus)
						&& (Material.WHITE_SHULKER_BOX == m || Material.ORANGE_SHULKER_BOX == m
								|| Material.MAGENTA_SHULKER_BOX == m || Material.LIGHT_BLUE_SHULKER_BOX == m
								|| Material.YELLOW_SHULKER_BOX == m || Material.LIME_SHULKER_BOX == m
								|| Material.PINK_SHULKER_BOX == m || Material.GRAY_SHULKER_BOX == m
								|| Material.SILVER_SHULKER_BOX == m || Material.CYAN_SHULKER_BOX == m
								|| Material.PURPLE_SHULKER_BOX == m || Material.BLUE_SHULKER_BOX == m
								|| Material.BROWN_SHULKER_BOX == m || Material.GREEN_SHULKER_BOX == m
								|| Material.RED_SHULKER_BOX == m || Material.BLACK_SHULKER_BOX == m));
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteractJuxeBox(PlayerInteractEvent e) {
		if (e.isCancelled())
			return;

		Player p = e.getPlayer();
		if (Permissions.perms(p, "allow.open-inventory") || e.getPlayer().getGameMode() != GameMode.CREATIVE)
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() != Material.JUKEBOX)
				return;

			Localization.interact(p);
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void blockingInteractWithBlock(PlayerInteractEvent e) {
		if (e.isCancelled() || Configuration.blockingInteractList.contains("none") || e.getClickedBlock() == null)
			return;
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		if (Permissions.perms(p, "allow.blocking-interact.*") || p.getGameMode() != GameMode.CREATIVE)
			return;
		if ((Configuration.blockingInteractList.contains(b.getTypeId())
				&& !Permissions.perms(p, "allow.blocking-interact." + b.getTypeId()))
				|| (Configuration.blockingInteractList.contains(b.getType().toString())
						&& !Permissions.perms(p, "allow.blocking-interact." + b.getType().toString()))) {
			Localization.interact(p);
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void blockingUseItem(PlayerInteractEvent e) {
		if (e.isCancelled() || Configuration.blockingItemsInvList.contains("none") || e.getItem() == null
				|| e.getItem().getType().equals(Material.AIR))
			return;
		Player p = e.getPlayer();
		ItemStack b = e.getItem();
		if (Permissions.perms(p, "allow.blocking-inventory.*") || p.getGameMode() != GameMode.CREATIVE)
			return;
		if ((Configuration.blockingItemsInvList.contains(b.getTypeId())
				&& !Permissions.perms(p, "allow.blocking-inventory." + b.getTypeId()))
				|| (Configuration.blockingItemsInvList.contains(b.getType().toString())
						&& !Permissions.perms(p, "allow.blocking-inventory." + b.getType().toString()))) {
			Localization.invNotify(b.getType(), p);
			p.setItemInHand(new ItemStack(Material.AIR));
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!Configuration.EssentialsSignBlock || e.isCancelled() || Permissions.perms(p, "allow.signs")
				|| p.getGameMode() != GameMode.CREATIVE)
			return;

		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			if (b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) b.getState();
				String[] lines = sign.getLines();

				if (isEssentialsSigns(ChatColor.stripColor(lines[0]))) {
					Localization.defGood(LanguageFiles.OnEssentialsSignUse, p);
					e.setCancelled(true);
					p.setGameMode(GameMode.SURVIVAL);
				}
			}
		}
	}

	public boolean isEssentialsSigns(String s) {
		return s.equalsIgnoreCase("[Buy]") || s.equalsIgnoreCase("[Sell]") || s.equalsIgnoreCase("[Free]")
				|| s.equalsIgnoreCase("[Kit]") || s.equalsIgnoreCase("[Disposal]") || s.equalsIgnoreCase("[Heal]")
				|| s.equalsIgnoreCase("[Trade]");
	}
}
