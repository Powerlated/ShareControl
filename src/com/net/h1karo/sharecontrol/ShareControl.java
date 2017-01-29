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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.mcstats.MetricsLite;

import com.earth2me.essentials.Essentials;
import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.database.InventoriesDatabase;
import com.net.h1karo.sharecontrol.database.MySQL;
import com.net.h1karo.sharecontrol.listeners.multiinventories.PlayerGameModeChangeListener;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;
import com.net.h1karo.sharecontrol.localization.Localization;
import com.net.h1karo.sharecontrol.version.CoreVersion;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class ShareControl extends JavaPlugin implements Listener {
	private static ShareControl instance;

	public static boolean error;

	public static ShareControl instance() {
		return instance;
	}

	public String version = getDescription().getVersion();

	private static Configuration mainconfig;
	@SuppressWarnings("unused")
	private static LanguageFiles lang;
	@SuppressWarnings("unused")
	private static Localization local;
	@SuppressWarnings("unused")
	private static Database database;
	@SuppressWarnings("unused")
	private static InventoriesDatabase invbase;
	@SuppressWarnings("unused")
	private static MySQL db;
	private ShareControlCommandExecutor Executor;

	public String web = getDescription().getWebsite();
	String stringVersion = ChatColor.BLUE + getDescription().getVersion();
	public String authors = getDescription().getAuthors().toString().replace("[", "").replace("]", "");

	private static boolean foundMA = false, foundPVP = false, foundEss = false, foundWE = false;
	private static boolean alpha = false, beta = false;

	ConsoleCommandSender console = null;

	@Override
	public void onEnable() {
		console = Bukkit.getConsoleSender();
		console.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&7&l=================== &f&lPowerlated's Fork of &9&lShare&f&lControl &7&l==================="));
		if (!CoreVersion.getVersion().equals(CoreVersion.OneDotSeven)
				&& !CoreVersion.getVersion().equals(CoreVersion.OneDotEight)
				&& !CoreVersion.getVersion().equals(CoreVersion.OneDotNine)
				&& !CoreVersion.getVersion().equals(CoreVersion.OneDotTen)
				&& !CoreVersion.getVersion().equals(CoreVersion.OneDotEleven)) {
			console.sendMessage(ChatColor.translateAlternateColorCodes('&',
					" &c&lYou are using an unsupported version! The plugin supports 1.7.x, 1.8.x, 1.9.x and 1.10.x."));
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &c&lUse at your own risk!"));
		}
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', " Loading configuration..."));

		instance = this;
		setupListeners();

		currentVersion = getDescription().getVersion();

		Executor = new ShareControlCommandExecutor(this);
		getCommand("sharecontrol").setExecutor(Executor);

		Configuration.loadCfg();
		Configuration.saveCfg();
		try {
			MySQL.connect();
			MySQL.loadCache();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		Database.autoSaveDatabase();
		if (error)
			Configuration.error(null);

		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			getLogger().warning("Failed to submit statistics.");
		}

		Permissions.registerCustomPermissions();

		console.sendMessage(ChatColor.translateAlternateColorCodes('&', " Configuration successfully uploaded!"));

		if (Configuration.versionCheck) {
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', " Check updates..."));
			updateCheck();
			if (result == UpdateResult.UPDATE_AVAILABLE) {
				String name = newVersion;
				console.sendMessage(
						ChatColor.translateAlternateColorCodes('&', " &fAn update is available: &9ShareControl v"
								+ name.replace(" Alpha", "").replace(" Beta", "") + "&f,"));
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &fdownload at"));
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &f" + Localization.link));
			}
			if (result == UpdateResult.NO_UPDATE)
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', " No updates found."));
		}

		if (beta)
			console.sendMessage(ChatColor.translateAlternateColorCodes('&',
					" &cWARNING!&r You are using a beta version of this plugin!"));
		if (alpha)
			console.sendMessage(ChatColor.translateAlternateColorCodes('&',
					" &cWARNING!&r You are using an alpha version of this plugin!"));

		console.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&7&l===================================================="));
	}

	@Override
	public void onDisable() {
		console.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&7&l=================== &f&lPowerlated's Fork of &9&lShare&f&lControl &7&l==================="));
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Stopping tasks..."));
		Bukkit.getScheduler().cancelTasks(this);
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Saving inventories and block database..."));
		Database.syncSaveDatabase();
		PlayerGameModeChangeListener.saveMultiInv();
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Disconnecting from SQLite..."));
		MySQL.disconnect();
		instance = null;
		console.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&7&l===================================================="));
	}

	public boolean checkSender(CommandSender sender) {
		if (sender instanceof Player) {
			return false;
		}
		return true;
	}

	public static Configuration getMainConfig() {
		return mainconfig;
	}

	protected void pluginInfo(CommandSender sender) {
		String version = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.currentVersion.replace("%version%", stringVersion));
		String Author = "\n  %author%";
		String team = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.DevelopmentTeam.replace("%development-team%",
						Author.replace("%author%", LanguageFiles.Author.replace("%nickname%", "H1KaRo"))));
		String site = ChatColor.translateAlternateColorCodes('&', LanguageFiles.WebSite.replace("%link%", web));
		MessageManager.getManager().msg(sender, MessageType.HELP,
				"\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 " + Localization.prefix + ChatColor.BLUE
						+ " Information" + ChatColor.GRAY
						+ " \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
		MessageManager.getManager().msg(sender, MessageType.HELP, version);
		MessageManager.getManager().msg(sender, MessageType.HELP, team);
		MessageManager.getManager().msg(sender, MessageType.HELP, site);
	}

	public static String newVersion = "";
	public static String currentVersion;
	public UpdateResult result;

	public enum UpdateResult {
		NO_UPDATE, UPDATE_AVAILABLE, ERROR
	}

	public void updateCheck() {
		String cBuildString = "", nBuildString = "";

		int cMajor = 0, cMinor = 0, cMaintenance = 0, cBuild = 0, nMajor = 0, nMinor = 0, nMaintenance = 0, nBuild = 0;

		try {
			URL url = new URL("https://api.curseforge.com/servermods/files?projectids=90354");
			URLConnection conn = url.openConnection();
			conn.setReadTimeout(5000);
			conn.addRequestProperty("User-Agent", "ShareControl Update Checker");
			conn.setDoOutput(true);
			final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final String response = reader.readLine();
			final JSONArray array = (JSONArray) JSONValue.parse(response);

			if (array.size() == 0) {
				this.getLogger().warning("No files found, or Feed URL is bad.");
				result = UpdateResult.ERROR;
				return;
			}
			String newVersionTitle = ((String) ((JSONObject) array.get(array.size() - 1)).get("name"));
			newVersion = newVersionTitle.replace("ShareControl v", "").trim();

			/** \\ **/
			/** \\ **//** \\ **/
			/**
			 * \ GET VERSIONS /**\ /**\\
			 **/
			/** \\ **//** \\ **/

			String[] cStrings = currentVersion.split(Pattern.quote("."));

			cMajor = Integer.parseInt(cStrings[0]);
			if (cStrings.length > 1)
				if (cStrings[1].contains("-")) {
					cMinor = Integer.parseInt(cStrings[1].split(Pattern.quote("-"))[0]);
					cBuildString = cStrings[1].split(Pattern.quote("-"))[1];
					if (cBuildString.contains("b")) {
						beta = true;
						cBuildString = cBuildString.replace("b", "");
						if (cBuildString != "")
							cBuild = Integer.parseInt(cBuildString) - 1;
					} else if (cBuildString.contains("a")) {
						alpha = true;
						cBuildString = cBuildString.replace("a", "");
						if (cBuildString != "")
							cBuild = Integer.parseInt(cBuildString) - 10;
					} else
						cBuild = Integer.parseInt(cBuildString);
				} else {
					cMinor = Integer.parseInt(cStrings[1]);
					if (cStrings.length > 2)
						if (cStrings[2].contains("-")) {
							cMaintenance = Integer.parseInt(cStrings[2].split(Pattern.quote("-"))[0]);
							cBuildString = cStrings[2].split(Pattern.quote("-"))[1];
							if (cBuildString.contains("b")) {
								beta = true;
								cBuildString = cBuildString.replace("b", "");
								if (cBuildString != "")
									cBuild = Integer.parseInt(cBuildString) - 1;
							} else if (cBuildString.contains("a")) {
								alpha = true;
								cBuildString = cBuildString.replace("a", "");
								if (cBuildString != "")
									cBuild = Integer.parseInt(cBuildString) - 10;
							} else
								cBuild = Integer.parseInt(cBuildString);
						} else
							cMaintenance = Integer.parseInt(cStrings[2]);
				}

			String[] nStrings = newVersion.split(Pattern.quote("."));

			nMajor = Integer.parseInt(nStrings[0]);
			if (nStrings.length > 1)
				if (nStrings[1].contains("-")) {
					nMinor = Integer.parseInt(nStrings[1].split(Pattern.quote("-"))[0]);
					nBuildString = nStrings[1].split(Pattern.quote("-"))[1];
					if (nBuildString.contains("b")) {
						beta = true;
						nBuildString = nBuildString.replace("b", "");
						if (nBuildString != "")
							nBuild = Integer.parseInt(nBuildString) - 1;
					} else if (nBuildString.contains("a")) {
						alpha = true;
						nBuildString = nBuildString.replace("a", "");
						if (nBuildString != "")
							nBuild = Integer.parseInt(nBuildString) - 10;
					} else
						nBuild = Integer.parseInt(nBuildString);
				} else {
					nMinor = Integer.parseInt(nStrings[1]);
					if (nStrings.length > 2)
						if (nStrings[2].contains("-")) {
							nMaintenance = Integer.parseInt(nStrings[2].split(Pattern.quote("-"))[0]);
							nBuildString = nStrings[2].split(Pattern.quote("-"))[1];
							if (nBuildString.contains("b")) {
								beta = true;
								nBuildString = nBuildString.replace("b", "");
								if (nBuildString != "")
									nBuild = Integer.parseInt(nBuildString) - 1;
							} else if (nBuildString.contains("a")) {
								alpha = true;
								nBuildString = nBuildString.replace("a", "");
								if (nBuildString != "")
									nBuild = Integer.parseInt(nBuildString) - 10;
							} else
								nBuild = Integer.parseInt(nBuildString);
						} else
							nMaintenance = Integer.parseInt(nStrings[2]);
				}

			/** \\ **/
			/** \\ **//** \\ **/
			/**
			 * \ CHECK VERSIONS /**\ /**\\
			 **/
			/** \\ **//** \\ **/
			if ((cMajor < nMajor) || (cMajor == nMajor && cMinor < nMinor)
					|| (cMajor == nMajor && cMinor == nMinor && cMaintenance < nMaintenance)
					|| (cMajor == nMajor && cMinor == nMinor && cMaintenance == nMaintenance && cBuild < nBuild))
				result = UpdateResult.UPDATE_AVAILABLE;
			else
				result = UpdateResult.NO_UPDATE;
			return;
		} catch (Exception e) {
			console.sendMessage(" There was an issue attempting to check for the latest version.");
		}
		result = UpdateResult.ERROR;
	}

	private void setupListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockFromToListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockGrowListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockMoveByPistonListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockPlaceListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.StructureGrowListener(this), this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.BlockBreakListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.BlockPlaceListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.BlockPlaceToCreationsListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.EntityDamageByEntityListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.EntityShootBowListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.InventoryClickListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.InventoryOpenListener(this), this);
		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus)) {
			pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerArmorStandDestroyListener(this),
					this);
			pm.registerEvents(
					new com.net.h1karo.sharecontrol.listeners.creative.PlayerArmorStandManipulateListener(this), this);
			pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerArmorStandSpawnListener(this),
					this);
		}
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerCommandPreprocessListener(this),
				this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerDeathListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerDropItemListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerFishListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerInteractEntityListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerInteractHorseListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerInteractListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerItemConsumeListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerLeashEntityListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerLevelChangeListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerPickupItemListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerShearEntityListener(this), this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.hanging.HangingBreakListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.hanging.HangingPlaceListener(this), this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.entity.BreedingListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.entity.EntityChangeBlockListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.entity.EntityExplodeListener(this), this);

		pm.registerEvents(
				new com.net.h1karo.sharecontrol.listeners.gamemodescontrol.PlayerCommandPreprocessListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.gamemodescontrol.PlayerGameModeChangeListener(this),
				this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.gamemodescontrol.AccessCheckListener(this), this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.multiinventories.PlayerGameModeChangeListener(this),
				this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.survival.BlockBreakListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.survival.BlockPlaceListener(this), this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.update.PlayerJoinListener(this), this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.items.PlayerInteractEntityListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.items.PlayerInteractListener(this), this);

		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.world.WorldListener(this), this);

		mainconfig = new Configuration(this);
		lang = new LanguageFiles(this);
		database = new Database(this);
		db = new MySQL(this);
		invbase = new InventoriesDatabase(this);
		local = new Localization(this);

		Essentials ess = (Essentials) pm.getPlugin("Essentials");
		WorldEditPlugin we = (WorldEditPlugin) pm.getPlugin("WorldEdit");

		if (ess != null && ess.isEnabled()) {
			foundEss = true;
		}

		if (we != null && we.isEnabled()) {
			foundWE = true;
		}
	}

	public void log(String s) {
		getLogger().info(s);
	}

	public static boolean getFoundMA() {
		return foundMA;
	}

	public static boolean getFoundPVP() {
		return foundPVP;
	}

	public static boolean getFoundEssentials() {
		return foundEss;
	}

	public static boolean getFoundWorldEdit() {
		return foundWE;
	}
}