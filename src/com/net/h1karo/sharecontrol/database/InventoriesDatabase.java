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

package com.net.h1karo.sharecontrol.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;

public class InventoriesDatabase {

	private static ShareControl main;

	public InventoriesDatabase(ShareControl h) {
		InventoriesDatabase.main = h;
	}

	private static FileConfiguration InvConfig = null;
	private static File InvConfigFile = null;

	public static void reloadInvConfig() {
		if (InvConfigFile == null)
			InvConfigFile = new File(Configuration.dataFolder + File.separator + "inventories.yml");

		InvConfig = YamlConfiguration.loadConfiguration(InvConfigFile);

		InputStream defConfigStream = main.getResource(Configuration.dataFolder + File.separator + "inventories.yml");
		if (defConfigStream != null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			InvConfig.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getInvConfig() {
		if (InvConfig == null)
			reloadInvConfig();
		return InvConfig;
	}

	public static void saveInvConfig() {
		if (InvConfig == null || InvConfigFile == null)
			return;
		try {
			getInvConfig().save(InvConfigFile);
		} catch (IOException ex) {
			main.getLogger().log(Level.WARNING, "Could not save config to " + InvConfigFile, ex);
		}
	}
}
