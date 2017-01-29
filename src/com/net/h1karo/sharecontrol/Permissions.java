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

package com.net.h1karo.sharecontrol;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import com.net.h1karo.sharecontrol.configuration.Configuration;

public class Permissions {

	public static boolean perms(Player p, String per) {
		if (p.hasPermission("sharecontrol." + per))
			return true;
		else
			return false;
	}

	public static void RegisterCustomPermissions() {
		PluginManager pm = Bukkit.getPluginManager();

		/** \\ **/
		/** \\ **//** \\ **/
		/**
		 * \ BREAK LIST /**\ /**\\
		 **/
		/** \\ **//** \\ **/
		if (Configuration.BlockingBlocksBreakList.toArray().length != 0
				&& Configuration.BlockingBlocksBreakList.get(0).toString() != "[none]")
			for (String material : Configuration.BlockingBlocksBreakList) {
				Permission newperm = new Permission("sharecontrol.allow.blocking-breakage." + material.toLowerCase());
				pm.addPermission(newperm);
				newperm.setDefault(PermissionDefault.OP);
				newperm.setDescription("Allow break block " + material + " from list of blocking blocks");
			}

		/** \\ **/
		/** \\ **//** \\ **/
		/**
		 * \ PLACE LIST /**\ /**\\
		 **/
		/** \\ **//** \\ **/
		if (Configuration.BlockingBlocksPlaceList.toArray().length != 0
				&& Configuration.BlockingBlocksPlaceList.get(0).toString() != "[none]")
			for (String material : Configuration.BlockingBlocksPlaceList) {
				Permission newperm = new Permission("sharecontrol.allow.blocking-placement." + material.toLowerCase());
				pm.addPermission(newperm);
				newperm.setDefault(PermissionDefault.OP);
				newperm.setDescription("Allow place block " + material + " from list of blocking blocks");
			}

		/** \\ **/
		/** \\ **//** \\ **/
		/**
		 * \ ITEM LIST /**\ /**\\
		 **/
		/** \\ **//** \\ **/
		if (Configuration.blockingItemsInvList.toArray().length != 0
				&& Configuration.blockingItemsInvList.get(0).toString() != "[none]")
			for (String material : Configuration.blockingItemsInvList) {
				Permission newperm = new Permission("sharecontrol.allow.blocking-inventory." + material.toLowerCase());
				pm.addPermission(newperm);
				newperm.setDefault(PermissionDefault.OP);
				newperm.setDescription("Allow use item " + material + " from list of blocking items");
			}
	}
}
