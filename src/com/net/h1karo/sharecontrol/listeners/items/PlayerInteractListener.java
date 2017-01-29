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

package com.net.h1karo.sharecontrol.listeners.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.Items;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerInteractListener implements Listener {

	@SuppressWarnings("unused")
	private final ShareControl main;

	public PlayerInteractListener(ShareControl h) {
		this.main = h;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void InfotoolInteractWithBlocks(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		String nameIT = ChatColor.translateAlternateColorCodes('&', LanguageFiles.nameinfotool);
		String loreStr1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT1);
		String loreStr2;
		if (LanguageFiles.loreIT2.contains("%plugin%"))
			loreStr2 = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.loreIT2.replace("%plugin%", Localization.prefix));
		else
			loreStr2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT2);

		List<String> loreIT = Arrays.asList(loreStr1, loreStr2);
		ItemStack infotool = Items.setMeta(new ItemStack(Material.BLAZE_POWDER), nameIT, loreIT);

		if (e.getItem() == null || p.getItemInHand() == null || e.getItem().getType() != infotool.getType()
				|| e.getItem().getItemMeta().getDisplayName() == null)
			return;
		if (e.getItem().getItemMeta().getDisplayName().compareToIgnoreCase(nameIT) == 0) {
			if (!(e.getClickedBlock() instanceof Block))
				return;
			if (!Permissions.perms(p, "tools.infotool")) {
				Localization.NoPerms(p);
				return;
			}
			Block b = e.getClickedBlock();
			Localization.BlockInfo(p, b);
			if (e.getAction() == Action.LEFT_CLICK_BLOCK)
				e.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void ChangetoolInteractWitchBlocks(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!(e.getClickedBlock() instanceof Block))
			return;

		String nameST = ChatColor.translateAlternateColorCodes('&', LanguageFiles.namesettool);
		String loreST1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST1);
		String loreST2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST2);
		String loreST3;
		if (LanguageFiles.loreST3.contains("%plugin%"))
			loreST3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST3.replace("%plugin%",
					ChatColor.BLUE + "Share" + ChatColor.WHITE + "Control" + ChatColor.RESET));
		else
			loreST3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST3);

		List<String> loreST = Arrays.asList(loreST1, loreST2, loreST3);

		ItemStack settool = Items.setMeta(new ItemStack(Material.MAGMA_CREAM), nameST, loreST);
		if (e.getItem() == null || p.getItemInHand() == null || e.getItem().getType() != settool.getType()
				|| e.getItem().getItemMeta().getDisplayName() == null)
			return;
		if (e.getItem().getItemMeta().getDisplayName().compareToIgnoreCase(nameST) == 0) {
			if (!Permissions.perms(p, "tools.changetool")) {
				Localization.NoPerms(p);
				return;
			}
			Block b = e.getClickedBlock();

			if (Database.CheckCreative(b) && e.getAction() == Action.LEFT_CLICK_BLOCK) {
				e.setCancelled(true);
				Localization.CreativeTypeHas(p);
				return;
			}

			if (!Database.CheckCreative(b) && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Localization.NaturalTypeHas(p);
				return;
			}

			if (Database.CheckCreative(b) && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Localization.NaturalTypeNow(p);
				Database.RemoveBlock(b);
				return;
			}

			if (!Database.CheckCreative(b) && e.getAction() == Action.LEFT_CLICK_BLOCK) {
				e.setCancelled(true);
				Localization.CreativeTypeNow(p);
				Database.AddBlock(b);
				return;
			}
		}
	}

}
