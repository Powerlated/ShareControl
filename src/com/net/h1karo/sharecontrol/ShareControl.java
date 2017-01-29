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
				"&7&l=================== &9&lShare&f&lControl &7&l==================="));
		if (!CoreVersion.getVersion().equals(CoreVersion.OneDotSeven)
				&& !CoreVersion.getVersion().equals(CoreVersion.OneDotEight)
				&& !CoreVersion.getVersion().equals(CoreVersion.OneDotNine)
				&& !CoreVersion.getVersion().equals(CoreVersion.OneDotTen)
				&& !CoreVersion.getVersion().equals(CoreVersion.OneDotEleven)) {
			console.sendMessage(ChatColor.translateAlternateColorCodes('&',
					" &c&lYou are using an unsupported version! The plugin supports 1.7.x, 1.8.x, 1.9.x and 1.10.x versions."));
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &c&lYou use at your own risk!"));
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
			getLogger().warning("Failed to submit the stats!");
		}

		Permissions.RegisterCustomPermissions();

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
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', " Updates not found!"));
		}

		if (beta)
			console.sendMessage(
					ChatColor.translateAlternateColorCodes('&', " &cWARNING!&r You use beta version of this plugin!"));
		if (alpha)
			console.sendMessage(
					ChatColor.translateAlternateColorCodes('&', " &cWARNING!&r You use alpha version of this plugin!"));

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
				LanguageFiles.CurrentVersion.replace("%version%", stringVersion));
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
		String CBuildString = "", NBuildString = "";

		int CMajor = 0, CMinor = 0, CMaintenance = 0, CBuild = 0, NMajor = 0, NMinor = 0, NMaintenance = 0, NBuild = 0;

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

			String[] CStrings = currentVersion.split(Pattern.quote("."));

			CMajor = Integer.parseInt(CStrings[0]);
			if (CStrings.length > 1)
				if (CStrings[1].contains("-")) {
					CMinor = Integer.parseInt(CStrings[1].split(Pattern.quote("-"))[0]);
					CBuildString = CStrings[1].split(Pattern.quote("-"))[1];
					if (CBuildString.contains("b")) {
						beta = true;
						CBuildString = CBuildString.replace("b", "");
						if (CBuildString != "")
							CBuild = Integer.parseInt(CBuildString) - 1;
					} else if (CBuildString.contains("a")) {
						alpha = true;
						CBuildString = CBuildString.replace("a", "");
						if (CBuildString != "")
							CBuild = Integer.parseInt(CBuildString) - 10;
					} else
						CBuild = Integer.parseInt(CBuildString);
				} else {
					CMinor = Integer.parseInt(CStrings[1]);
					if (CStrings.length > 2)
						if (CStrings[2].contains("-")) {
							CMaintenance = Integer.parseInt(CStrings[2].split(Pattern.quote("-"))[0]);
							CBuildString = CStrings[2].split(Pattern.quote("-"))[1];
							if (CBuildString.contains("b")) {
								beta = true;
								CBuildString = CBuildString.replace("b", "");
								if (CBuildString != "")
									CBuild = Integer.parseInt(CBuildString) - 1;
							} else if (CBuildString.contains("a")) {
								alpha = true;
								CBuildString = CBuildString.replace("a", "");
								if (CBuildString != "")
									CBuild = Integer.parseInt(CBuildString) - 10;
							} else
								CBuild = Integer.parseInt(CBuildString);
						} else
							CMaintenance = Integer.parseInt(CStrings[2]);
				}

			String[] NStrings = newVersion.split(Pattern.quote("."));

			NMajor = Integer.parseInt(NStrings[0]);
			if (NStrings.length > 1)
				if (NStrings[1].contains("-")) {
					NMinor = Integer.parseInt(NStrings[1].split(Pattern.quote("-"))[0]);
					NBuildString = NStrings[1].split(Pattern.quote("-"))[1];
					if (NBuildString.contains("b")) {
						beta = true;
						NBuildString = NBuildString.replace("b", "");
						if (NBuildString != "")
							NBuild = Integer.parseInt(NBuildString) - 1;
					} else if (NBuildString.contains("a")) {
						alpha = true;
						NBuildString = NBuildString.replace("a", "");
						if (NBuildString != "")
							NBuild = Integer.parseInt(NBuildString) - 10;
					} else
						NBuild = Integer.parseInt(NBuildString);
				} else {
					NMinor = Integer.parseInt(NStrings[1]);
					if (NStrings.length > 2)
						if (NStrings[2].contains("-")) {
							NMaintenance = Integer.parseInt(NStrings[2].split(Pattern.quote("-"))[0]);
							NBuildString = NStrings[2].split(Pattern.quote("-"))[1];
							if (NBuildString.contains("b")) {
								beta = true;
								NBuildString = NBuildString.replace("b", "");
								if (NBuildString != "")
									NBuild = Integer.parseInt(NBuildString) - 1;
							} else if (NBuildString.contains("a")) {
								alpha = true;
								NBuildString = NBuildString.replace("a", "");
								if (NBuildString != "")
									NBuild = Integer.parseInt(NBuildString) - 10;
							} else
								NBuild = Integer.parseInt(NBuildString);
						} else
							NMaintenance = Integer.parseInt(NStrings[2]);
				}

			/** \\ **/
			/** \\ **//** \\ **/
			/**
			 * \ CHECK VERSIONS /**\ /**\\
			 **/
			/** \\ **//** \\ **/
			if ((CMajor < NMajor) || (CMajor == NMajor && CMinor < NMinor)
					|| (CMajor == NMajor && CMinor == NMinor && CMaintenance < NMaintenance)
					|| (CMajor == NMajor && CMinor == NMinor && CMaintenance == NMaintenance && CBuild < NBuild))
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