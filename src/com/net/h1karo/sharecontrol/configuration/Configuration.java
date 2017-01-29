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

package com.net.h1karo.sharecontrol.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import com.net.h1karo.sharecontrol.MessageManager;
import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.database.InventoriesDatabase;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;
import com.net.h1karo.sharecontrol.localization.Localization;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class Configuration {

	private static ShareControl main;

	public Configuration(ShareControl h) {
		Configuration.main = h;
	}

	public static String errorcode;

	//
	//
	// CONFIGURATION
	//

	public static void saveCfg() {
		main.getConfig().set("General.CheckUpdates", versionCheck);
		main.getConfig().set("General.Version", main.version);
		main.getConfig().set("General.Language", Language);

		main.getConfig().set("General.Database", database);
		main.getConfig().set("General.SaveInterval", DBInterval);
		main.getConfig().set("General.MySQL.TableName", TableName);
		main.getConfig().set("General.MySQL.Host", Host);
		main.getConfig().set("General.MySQL.Port", Port);
		main.getConfig().set("General.MySQL.Database", dbName);
		main.getConfig().set("General.MySQL.Username", Username);
		main.getConfig().set("General.MySQL.Password", Password);

		main.getConfig().set("Notifications.SurvivalNotify", SurvivalNotify);
		main.getConfig().set("Notifications.CreativeNotify", CreativeNotify);
		main.getConfig().set("Notifications.Material", material);
		main.getConfig().set("Notifications.PrefixEnabled", PrefixEnabled);

		main.getConfig().set("Settings.BlockingCreatureInteract", CreatureInteract);
		main.getConfig().set("Settings.BlockingPlayerInteract", PlayerInteract);
		main.getConfig().set("Settings.BlockingBreak", BlockingBreak);
		main.getConfig().set("Settings.ClearDropInInventory", clearDropInInventory);
		main.getConfig().set("Settings.EssentialsSignBlock", EssentialsSignBlock);

		main.getConfig().set("Settings.MultiInventories.Enabled", MultiInventoriesEnabled);
		main.getConfig().set("Settings.MultiInventories.Separation", InventorySeparation);

		main.getConfig().set("Settings.Blocks.BlockingPlacement", blockingBlocksPlaceList);
		main.getConfig().set("Settings.Blocks.BlockingBreakage", blockingBlocksBreakList);
		main.getConfig().set("Settings.Blocks.BlockingInteract", blockingInteractList);

		main.getConfig().set("Settings.Items.BlockingInventory", blockingItemsInvList);

		main.getConfig().set("Settings.BlockingCmds.List", blockingCmdsList);
		main.getConfig().set("Settings.BlockingCmds.Enabled", blockingCmdsEnabled);

		main.getConfig().set("WorldsConfig.Enabled", WorldsCfgEnabled);
		main.getConfig().set("WorldsConfig.BlockingCreativeInWorlds", BlockingCreative);

		main.getConfig().set("GamemodesControl.Enabled", GamemodesControlEnabled);
		main.getConfig().set("GamemodesControl.Full", FullGCEnabled);

		main.saveConfig();

		CheckError();
	}

	public static void loadCfg() {
		main.reloadConfig();

		loadDef();

		dataFolder = new File(main.getDataFolder(), "data");
		if (!dataFolder.exists())
			dataFolder.mkdirs();

		languageFolder = new File(main.getDataFolder(), "languages" + File.separator);
		if (!languageFolder.exists())
			languageFolder.mkdirs();

		versionCheck = main.getConfig().getBoolean("General.CheckUpdates", true);
		Language = main.getConfig().getString("General.Language", "en");

		database = main.getConfig().getString("General.Database", "sqlite");
		DBInterval = main.getConfig().getInt("General.SaveInterval", 5);
		TableName = main.getConfig().getString("General.MySQL.TableName", "blocks");
		Host = main.getConfig().getString("General.MySQL.Host", "localhost");
		Port = main.getConfig().getString("General.MySQL.Port", "3306");
		dbName = main.getConfig().getString("General.MySQL.Database", "minecraft");
		Username = main.getConfig().getString("General.MySQL.Username", "minecraft");
		Password = main.getConfig().getString("General.MySQL.Password", "");

		SurvivalNotify = main.getConfig().getBoolean("Notifications.SurvivalNotify", true);
		CreativeNotify = main.getConfig().getBoolean("Notifications.CreativeNotify", true);
		material = main.getConfig().getBoolean("Notifications.Material", true);
		PrefixEnabled = main.getConfig().getBoolean("Notifications.PrefixEnabled", true);

		main.getConfig().get("settings");

		CreatureInteract = main.getConfig().getBoolean("Settings.BlockingCreatureInteract", true);
		PlayerInteract = main.getConfig().getBoolean("Settings.BlockingPlayerInteract", true);
		BlockingBreak = main.getConfig().getBoolean("Settings.BlockingBreak", true);
		clearDropInInventory = main.getConfig().getBoolean("Settings.ClearDropInInventory", false);
		EssentialsSignBlock = main.getConfig().getBoolean("Settings.EssentialsSignBlock", false);

		MultiInventoriesEnabled = main.getConfig().getBoolean("Settings.MultiInventories.Enabled", true);
		InventorySeparation = main.getConfig().getBoolean("Settings.MultiInventories.Separation", true);

		blockingBlocksPlaceList = main.getConfig().getStringList("Settings.Blocks.BlockingPlacement");
		if (blockingBlocksPlaceList.isEmpty())
			blockingBlocksPlaceList.addAll(defBlockingBlocksPlaceList);
		blockingBlocksBreakList = main.getConfig().getStringList("Settings.Blocks.BlockingBreakage");
		if (blockingBlocksBreakList.isEmpty())
			blockingBlocksBreakList.addAll(defBlockingBlocksBreakList);
		blockingInteractList = main.getConfig().getStringList("Settings.Blocks.BlockingInteract");
		if (blockingInteractList.isEmpty())
			blockingInteractList.addAll(defBlockingInteractList);

		blockingItemsInvList = main.getConfig().getStringList("Settings.Items.BlockingInventory");
		if (blockingItemsInvList.isEmpty())
			blockingItemsInvList.addAll(defBlockingItemsInvList);

		blockingCmdsList = main.getConfig().getStringList("Settings.BlockingCmds.List");
		if (blockingCmdsList.isEmpty())
			blockingCmdsList.addAll(defBlockingCmdsList);
		blockingCmdsEnabled = main.getConfig().getBoolean("Settings.BlockingCmds.Enabled", false);

		WorldsCfgEnabled = main.getConfig().getBoolean("WorldsConfig.Enabled", false);

		BlockingCreative = main.getConfig().getStringList("WorldsConfig.BlockingCreativeInWorlds");
		if (BlockingCreative.isEmpty())
			BlockingCreative.addAll(defBlockingCreative);

		GamemodesControlEnabled = main.getConfig().getBoolean("GamemodesControl.Enabled", false);
		FullGCEnabled = main.getConfig().getBoolean("GamemodesControl.Full", true);

		InventoriesDatabase.reloadInvConfig();
		InventoriesDatabase.saveInvConfig();

		File languageConfigFile = null;

		languageConfigFile = new File(main.getDataFolder(), "languages" + File.separator + "en.yml");
		if (!languageConfigFile.exists()) {
			main.getLogger().info("English language file not found! Loading...");
			LanguageFiles.reloadLanguageConfig("en");
			LanguageFiles.saveLanguageConfig("en");
		}

		languageConfigFile = new File(main.getDataFolder(), "languages" + File.separator + "ru.yml");
		if (!languageConfigFile.exists()) {
			main.getLogger().info("Russian language file not found! Loading...");
			LanguageFiles.reloadLanguageConfig("ru");
			LanguageFiles.saveLanguageConfig("ru");
		}

		languageConfigFile = new File(main.getDataFolder(), "languages" + File.separator + "de.yml");
		if (!languageConfigFile.exists()) {
			main.getLogger().info("German language file not found! Loading...");
			LanguageFiles.reloadLanguageConfig("de");
			LanguageFiles.saveLanguageConfig("de");
		}

		languageConfigFile = new File(main.getDataFolder(), "languages" + File.separator + "cn.yml");
		if (!languageConfigFile.exists()) {
			main.getLogger().info("Simplified Chinese language file not found! Loading...");
			LanguageFiles.reloadLanguageConfig("cn");
			LanguageFiles.saveLanguageConfig("cn");
		}

		LanguageFiles.reloadLanguageConfig(Language);
		LanguageFiles.saveLanguageConfig(Language);
		languageConfigFile = null;

		main.getCommand("sharecontrol").setPermissionMessage(
				MessageManager.prefix + ChatColor.translateAlternateColorCodes('&', LanguageFiles.NoPerms));

		defBlockingBlocksPlaceList.clear();
		defBlockingBlocksBreakList.clear();
		defBlockingItemsInvList.clear();
		defBlockingInteractList.clear();
	}
	//
	//
	// EXTRA
	//

	static boolean ifInt;

	@SuppressWarnings("deprecation")
	public static void addToList(CommandSender sender, String list, String material) {
		Material Material;

		if (isInteger(material)) {
			int ID = Integer.parseInt(material);
			Material = org.bukkit.Material.getMaterial(ID);
		} else
			Material = org.bukkit.Material.getMaterial(material);
		if (!(Material instanceof Material)) {
			Localization.thisNotMaterialAndId(sender, material);
		} else {
			if (list.compareToIgnoreCase("break") == 0) {
				blockingBlocksBreakList.add(material);
				saveCfg();
				Localization.AddSuccess(sender, list, material);
				return;
			}
			if (list.compareToIgnoreCase("place") == 0) {
				blockingBlocksPlaceList.add(material);
				saveCfg();
				Localization.AddSuccess(sender, list, material);
				return;
			}
			if (list.compareToIgnoreCase("use") == 0) {
				blockingItemsInvList.add(material);
				saveCfg();
				Localization.AddSuccess(sender, list, material);
				return;
			}
			if (list.compareToIgnoreCase("interact") == 0) {
				blockingInteractList.add(material);
				saveCfg();
				Localization.AddSuccess(sender, list, material);
				return;
			}

			String command = "/sc add <break/place/use/interact> <material>";
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.using.replace("%command%", command));
			MessageManager.getManager().msg(sender, MessageType.USE, msg);
		}
	}

	@SuppressWarnings("deprecation")
	public static void removeFromList(CommandSender sender, String list, String material) {
		Material Material;

		if (isInteger(material)) {
			int ID = Integer.parseInt(material);
			Material = org.bukkit.Material.getMaterial(ID);
		} else
			Material = org.bukkit.Material.getMaterial(material);
		if (!(Material instanceof Material)) {
			Localization.thisNotMaterialAndId(sender, material);
		} else {
			if (list.compareToIgnoreCase("break") == 0) {
				blockingBlocksBreakList.remove(material);
				saveCfg();
				Localization.RemoveSuccess(sender, list, material);
				return;
			}
			if (list.compareToIgnoreCase("place") == 0) {
				blockingBlocksPlaceList.remove(material);
				saveCfg();
				Localization.RemoveSuccess(sender, list, material);
				return;
			}
			if (list.compareToIgnoreCase("use") == 0) {
				blockingItemsInvList.remove(material);
				saveCfg();
				Localization.RemoveSuccess(sender, list, material);
				return;
			}
			if (list.compareToIgnoreCase("interact") == 0) {
				blockingInteractList.remove(material);
				saveCfg();
				Localization.RemoveSuccess(sender, list, material);
				return;
			}

			String command = "/sc remove <break/place/use/interact> <material>";
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.using.replace("%command%", command));
			MessageManager.getManager().msg(sender, MessageType.USE, msg);
		}
	}

	@SuppressWarnings("deprecation")
	protected static void CheckError() {
		int j = blockingBlocksPlaceList.toArray().length;
		for (int i = 0; i < j; i++) {
			String String = blockingBlocksPlaceList.toArray()[i].toString().replace("'", "");
			Material Material;

			if (isInteger(String)) {
				int ID = Integer.parseInt(String);
				Material = org.bukkit.Material.getMaterial(ID);
			} else
				Material = org.bukkit.Material.getMaterial(String);
			if (!(Material instanceof Material) && !String.equalsIgnoreCase("none")) {
				ShareControl.error = true;
				errorcode = String;
			}
		}
		j = blockingBlocksBreakList.toArray().length;
		for (int i = 0; i < j; i++) {
			String String = blockingBlocksBreakList.toArray()[i].toString().replace("'", "");
			Material Material;

			if (isInteger(String)) {
				int ID = Integer.parseInt(String);
				Material = org.bukkit.Material.getMaterial(ID);
			} else
				Material = org.bukkit.Material.getMaterial(String);

			if (!(Material instanceof Material) && !String.equalsIgnoreCase("none")) {
				ShareControl.error = true;
				errorcode = String;
			}
		}
		j = blockingItemsInvList.toArray().length;
		for (int i = 0; i < j; i++) {
			String String = blockingItemsInvList.toArray()[i].toString().replace("'", "");
			Material Material;

			if (isInteger(String)) {
				int ID = Integer.parseInt(String);
				Material = org.bukkit.Material.getMaterial(ID);
			} else
				Material = org.bukkit.Material.getMaterial(String);
			if (!(Material instanceof Material) && !String.equalsIgnoreCase("none")) {
				ShareControl.error = true;
				errorcode = String;
			}
		}
		j = blockingInteractList.toArray().length;
		for (int i = 0; i < j; i++) {
			String String = blockingInteractList.toArray()[i].toString().replace("'", "");
			Material Material;

			if (isInteger(String)) {
				int ID = Integer.parseInt(String);
				Material = org.bukkit.Material.getMaterial(ID);
			} else
				Material = org.bukkit.Material.getMaterial(String);
			if (!(Material instanceof Material) && !String.equalsIgnoreCase("none")) {
				ShareControl.error = true;
				errorcode = String;
			}
		}
	}

	public static void Error(CommandSender sender) {
		String msg = "CAUTION! An error is found in the file config.yml!";
		String msg2 = "Possible error: " + errorcode;

		if (Language.compareToIgnoreCase("ru") == 0) {
			msg = "Внимание! Найдена ошибка в файле config.yml!";
			msg2 = "Возможная ошибка: " + errorcode;
		}
		if (main.checkSender(sender)) {
			main.getLogger().warning(msg);
			main.getLogger().warning(msg2);
		} else {
			main.getLogger().warning(msg);
			MessageManager.getManager().msg(sender, MessageType.ERROR, msg);

			main.getLogger().warning(msg2);
			MessageManager.getManager().msg(sender, MessageType.ERROR, msg2);
		}
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static void loadDef() {
		defBlockingBlocksPlaceList = new ArrayList<String>();
		defBlockingBlocksPlaceList.add("BEDROCK");
		defBlockingBlocksPlaceList.add("MONSTER_EGGS");
		defBlockingBlocksPlaceList.add("TNT");
		defBlockingBlocksPlaceList.add("ENDER_PORTAL_FRAME");

		defBlockingBlocksBreakList = new ArrayList<String>();
		defBlockingBlocksBreakList.add("BEDROCK");

		defBlockingItemsInvList = new ArrayList<String>();
		defBlockingItemsInvList.add("MONSTER_EGG");
		defBlockingItemsInvList.add("MINECART");
		defBlockingItemsInvList.add("BOAT");
		defBlockingItemsInvList.add("STORAGE_MINECART");
		defBlockingItemsInvList.add("POWERED_MINECART");
		defBlockingItemsInvList.add("EXPLOSIVE_MINECART");
		defBlockingItemsInvList.add("HOPPER_MINECART");
		defBlockingItemsInvList.add("LAVA_BUCKET");
		defBlockingItemsInvList.add("ENDER_PEARL");
		defBlockingItemsInvList.add("EYE_OF_ENDER");
		defBlockingItemsInvList.add("EXP_BOTTLE");
		defBlockingItemsInvList.add("FIREBALL");
		defBlockingItemsInvList.add("FLINT_AND_STEEL");
		defBlockingItemsInvList.add("POTION");
		if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotNinePlus)) {
			defBlockingItemsInvList.add("SPLASH_POTION");
			defBlockingItemsInvList.add("LINGERING_POTION");
			defBlockingItemsInvList.add("CHORUS_PLANT");
			defBlockingItemsInvList.add("CHORUS_FLOWER");
			defBlockingItemsInvList.add("END_CRYSTAL");
			defBlockingItemsInvList.add("BOAT_ACACIA");
			defBlockingItemsInvList.add("BOAT_BIRCH");
			defBlockingItemsInvList.add("BOAT_DARK_OAK");
			defBlockingItemsInvList.add("BOAT_JUNGLE");
			defBlockingItemsInvList.add("BOAT_SPRUCE");
		}

		defBlockingCmdsList = new ArrayList<String>();
		defBlockingCmdsList.add("kit start");

		defBlockingCreative = new ArrayList<String>();
		defBlockingCreative.add("world_nether");
		defBlockingCreative.add("world_the_end");

		defBlockingInteractList = new ArrayList<String>();
		defBlockingInteractList.add("none");
	}

	public static boolean versionCheck;
	public static List<String> blockingBlocksPlaceList, blockingBlocksBreakList, blockingItemsInvList, blockingCmdsList,
			blockingInteractList;
	public static boolean CreatureInteract, PlayerInteract, CreativeNotify, SurvivalNotify, material, BlockingBreak,
			PrefixEnabled, clearDropInInventory, EssentialsSignBlock;
	public static boolean MultiInventoriesEnabled, InventorySeparation;
	public static String Language;

	public static boolean blockingCmdsEnabled;

	public static boolean GamemodesControlEnabled, FullGCEnabled;

	public static boolean WorldsCfgEnabled;
	public static List<String> BlockingCreative;

	private static File languageFolder;
	public static File dataFolder;

	public static String database, Host, Port, dbName, Username, Password, TableName;
	public static int DBInterval;

	public static List<String> defBlockingBlocksPlaceList, defBlockingBlocksBreakList, defBlockingItemsInvList,
			defBlockingCmdsList, defBlockingCreative, defBlockingInteractList;
}
