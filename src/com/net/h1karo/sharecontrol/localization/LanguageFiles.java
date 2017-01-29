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

package com.net.h1karo.sharecontrol.localization;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.net.h1karo.sharecontrol.ShareControl;

public class LanguageFiles {
	private static ShareControl main;

	public LanguageFiles(ShareControl h) {
		LanguageFiles.main = h;
	}

	private static FileConfiguration languageConfig = null;
	private static File languageConfigFile = null;

	public static void reloadLanguageConfig(String lang) {
		if (languageConfigFile == null)
			languageConfigFile = new File(main.getDataFolder(), "languages" + File.separator + lang + ".yml");

		languageConfig = YamlConfiguration.loadConfiguration(languageConfigFile);
		InputStream defConfigStream = main
				.getResource(main.getDataFolder() + "languages" + File.separator + lang + ".yml");
		if (defConfigStream != null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			languageConfig.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getLangConfig(String lang) {
		if (languageConfig == null)
			reloadLanguageConfig(lang);
		return languageConfig;
	}

	public static void saveLanguageConfig(String lang) {
		if (languageConfig == null || languageConfigFile == null)
			return;

		EnglishStrings(lang);
		if (lang.equalsIgnoreCase("ru"))
			RussianStrings(lang);
		else if (lang.equalsIgnoreCase("de"))
			GermanStrings(lang);
		else if (lang.equalsIgnoreCase("cn"))
			SimplifiedChineseStrings(lang);

		languageConfig.options().header(header);

		getLangConfig(lang).set("Update.UpdateNotFound", UpdateNotFound);
		getLangConfig(lang).set("Update.Available", UpdateAvailable);

		getLangConfig(lang).set("NoPermission", NoPerms);

		getLangConfig(lang).set("Events.Drop", OnDrop);
		getLangConfig(lang).set("Events.MobsInteract", OnMonsterInteract);
		getLangConfig(lang).set("Events.PlayerInteract", OnPlayerInteract);
		getLangConfig(lang).set("Events.Inventory.Message", OnInventoryClick);
		getLangConfig(lang).set("Events.Inventory.Material", OnInventoryClickMaterial);
		getLangConfig(lang).set("Events.OpenInventory", OnOpenOtherInventory);
		getLangConfig(lang).set("Events.BlockBreak.Message", OnBlockBreak);
		getLangConfig(lang).set("Events.BlockPlace.Message", OnBlockPlace);
		getLangConfig(lang).set("Events.BlockBreak.Material", OnBlockBreakMaterial);
		getLangConfig(lang).set("Events.BlockPlace.Material", OnBlockPlaceMaterial);
		getLangConfig(lang).set("Events.ShootBow", OnBowShoot);
		getLangConfig(lang).set("Events.InteractWithWorlds", AnotherWorld);
		getLangConfig(lang).set("Events.NotDropBlock", CreativeBlockNotDrop);
		getLangConfig(lang).set("Events.NotBreakBlock", CreativeBlockNotBreak);
		getLangConfig(lang).set("Events.EntityInteract.Message", EntityInteract);
		getLangConfig(lang).set("Events.EntityInteract.Material", EntityInteractMaterial);
		getLangConfig(lang).set("Events.UseBlock", UseBlocks);
		getLangConfig(lang).set("Events.Fishing", OnFishing);
		getLangConfig(lang).set("Events.ArmorStand", ArmorStand);
		getLangConfig(lang).set("Events.ProhibitedCommand", OnCommand);
		getLangConfig(lang).set("Events.Saplings", Saplings);
		getLangConfig(lang).set("Events.OnEssentialsSignUse", OnEssentialsSignUse);

		getLangConfig(lang).set("GamemodesControl.NotAllowedGamemode", GamemodesControl);

		getLangConfig(lang).set("PlayersInGamemode.List", PlayerListInGamemode);
		getLangConfig(lang).set("PlayersInGamemode.UnknownGamemode", UnknownGamemode);
		getLangConfig(lang).set("PlayersInGamemode.NotFound", PlayerInGamemodeNotFound);
		getLangConfig(lang).set("PlayersInGamemode.More", PlayerListInGamemodeMore);

		getLangConfig(lang).set("Menu.This", menu);
		getLangConfig(lang).set("Menu.Reload", menureload);
		getLangConfig(lang).set("Menu.Version", menuinfo);
		getLangConfig(lang).set("Menu.Update", menuupdate);
		getLangConfig(lang).set("Menu.GetList", menugetlist);
		getLangConfig(lang).set("Menu.AddToList", menuadd);
		getLangConfig(lang).set("Menu.RemoveFromList", menuremove);
		getLangConfig(lang).set("Menu.Tools", menutools);
		getLangConfig(lang).set("Menu.ChangeTool", menusettool);
		getLangConfig(lang).set("Menu.InfoTool", menuinfotool);
		getLangConfig(lang).set("Menu.SelectionSet", menuselectionset);
		getLangConfig(lang).set("Menu.Check", menucheck);

		getLangConfig(lang).set("Using", using);

		getLangConfig(lang).set("Reload.Reloading", reloading);
		getLangConfig(lang).set("Reload.Success", reloadsuccess);

		getLangConfig(lang).set("ChangeConfig.AddToBlockingPlacement", AMtoPlaceList);
		getLangConfig(lang).set("ChangeConfig.AddToBlockingBreakage", AMtoBreakList);
		getLangConfig(lang).set("ChangeConfig.AddToBlockingInventory", AMtoUseList);
		getLangConfig(lang).set("ChangeConfig.RemoveFromBlockingPlacement", RMtoPlaceList);
		getLangConfig(lang).set("ChangeConfig.RemoveFromBlockingBreakage", RMtoBreakList);
		getLangConfig(lang).set("ChangeConfig.RemoveFromBlockingInventory", RMtoUseList);
		getLangConfig(lang).set("ChangeConfig.ThisNotMaterialAndId", ThisNotMaterialandId);

		getLangConfig(lang).set("Tools.ChangeTool.Get", getsettool);
		getLangConfig(lang).set("Tools.ChangeTool.Name", namesettool);
		getLangConfig(lang).set("Tools.ChangeTool.Lore.1", loreST1);
		getLangConfig(lang).set("Tools.ChangeTool.Lore.2", loreST2);
		getLangConfig(lang).set("Tools.ChangeTool.Lore.3", loreST3);
		getLangConfig(lang).set("Tools.InfoTool.Get", getinfotool);
		getLangConfig(lang).set("Tools.InfoTool.Name", nameinfotool);
		getLangConfig(lang).set("Tools.InfoTool.Lore.1", loreIT1);
		getLangConfig(lang).set("Tools.InfoTool.Lore.2", loreIT2);

		getLangConfig(lang).set("Tools.Type", Type);
		getLangConfig(lang).set("Tools.Types.Creative", creativeType);
		getLangConfig(lang).set("Tools.Types.Natural", caturalType);
		getLangConfig(lang).set("Tools.Name", Name);
		getLangConfig(lang).set("Tools.ID", ID);
		getLangConfig(lang).set("Tools.Data", Data);
		getLangConfig(lang).set("Tools.Coordinates", Coordinates);
		getLangConfig(lang).set("Tools.Nickname", Nick);
		getLangConfig(lang).set("Tools.UUID", UUID);
		getLangConfig(lang).set("Tools.Gamemode", GM);
		getLangConfig(lang).set("Tools.Health", Health);
		getLangConfig(lang).set("Tools.Exp", Exp);
		getLangConfig(lang).set("Tools.World", World);
		getLangConfig(lang).set("Tools.BlockHas", BlockHas);
		getLangConfig(lang).set("Tools.BlockNow", BlockNow);

		getLangConfig(lang).set("Gamemodes.Creative", Creative);
		getLangConfig(lang).set("Gamemodes.Survival", Survival);
		getLangConfig(lang).set("Gamemodes.Adventure", Adventure);
		getLangConfig(lang).set("Gamemodes.Spectator", Spectator);

		getLangConfig(lang).set("Selections.WorldEditNotFound", WENotFound);
		getLangConfig(lang).set("Selections.UnknownType", UnknownType);
		getLangConfig(lang).set("Selections.MakeSelection", MakeSelection);
		getLangConfig(lang).set("Selections.NotCuboid", NotCuboid);
		getLangConfig(lang).set("Selections.PleaseWait", PleaseWait);
		getLangConfig(lang).set("Selections.BlocksChanged", BlocksChanged);

		getLangConfig(lang).set("Version", currentVersion);
		getLangConfig(lang).set("DevelopmentTeam", DevelopmentTeam);
		getLangConfig(lang).set("Site", WebSite);
		getLangConfig(lang).set("Author", Author);
		try {
			getLangConfig(lang).save(languageConfigFile);
		} catch (IOException ex) {
			main.getLogger().log(Level.WARNING, "Could not save config to " + languageConfigFile, ex);
		}

		languageConfigFile = null;
		languageConfig = null;
	}

	/** \ * * * * * * * * \ **/
	/** \ DEFAULT STRINGS \ **/
	/** \ * * * * * * * * \ **/

	/** \ ENGLISH \ **/
	/** \---------\ **/

	private static void EnglishStrings(String lang) {
		header = " |-------------------------------| \n" + " | Language file of ShareControl | \n"
				+ " |-------------------------------| \n" + "\n" + " HELP:\n"
				+ " %block% - block that place\\break\\use \n" + " %item% - item that use \n"
				+ " %gamemode% - the gamemode of player \n" + " %type% - type of block (natural or creative) \n"
				+ " %coords% - coordinates of block \n" + " %name% - block name \n" + " %id% - block ID \n"
				+ " %command% - ShareControl's command \n" + " %plugin% - ShareControl \n"
				+ " %update% - new version \n" + " %version% - the current version \n"
				+ " %link% - link to site of plugin \n" + " %development-team% - development team of the plugin \n"
				+ " %nickname% - player \n" + " %uuid% - universally unique identifier of player";

		UpdateNotFound = getLangConfig(lang).getString("Update.UpdateNotFound",
				"&7Updates not found. You have the latest version!");
		UpdateAvailable = getLangConfig(lang).getString("Update.Available",
				"&7An update is available: &9%update%&7, download at &9%link%&7!");

		NoPerms = getLangConfig(lang).getString("NoPermission", "&cYou do not have permission to do this!");

		OnDrop = getLangConfig(lang).getString("Events.Drop", "&cYou can not throw things!");
		OnMonsterInteract = getLangConfig(lang).getString("Events.MobsInteract", "&cYou can not interact with mobs!");
		OnPlayerInteract = getLangConfig(lang).getString("Events.PlayerInteract", "&cYou can not touch the players!");
		OnInventoryClick = getLangConfig(lang).getString("Events.Inventory.Message", "&cYou can not use this item!");
		OnInventoryClickMaterial = getLangConfig(lang).getString("Events.Inventory.Material",
				"&cYou can not use this &6%item%&c!");
		OnOpenOtherInventory = getLangConfig(lang).getString("Events.OpenInventory",
				"&cYou can not used the inventory!");
		OnBlockBreak = getLangConfig(lang).getString("Events.BlockBreak.Message", "&cYou can not break this block!");
		OnBlockPlace = getLangConfig(lang).getString("Events.BlockPlace.Message", "&cYou can not place this block!");
		OnBlockBreakMaterial = getLangConfig(lang).getString("Events.BlockBreak.Material",
				"&cYou can not break this &6%block%&c!");
		OnBlockPlaceMaterial = getLangConfig(lang).getString("Events.BlockPlace.Material",
				"&cYou can not place this &6%block%&c!");
		OnBowShoot = getLangConfig(lang).getString("Events.ShootBow", "&cYou can not shoot a bow!");
		AnotherWorld = getLangConfig(lang).getString("Events.InteractWithWorlds",
				"&cYou can not interact with the world around you!");
		CreativeBlockNotDrop = getLangConfig(lang).getString("Events.NotDropBlock",
				"&7This block is has not dropped because he is from the creative mode!");
		CreativeBlockNotBreak = getLangConfig(lang).getString("Events.NotBreakBlock",
				"&7This block is has not breaked because he is from the creative mode!");
		EntityInteract = getLangConfig(lang).getString("Events.EntityInteract.Message", "&cYou can not use this item!");
		EntityInteractMaterial = getLangConfig(lang).getString("Events.EntityInteract.Material",
				"&cYou can not use this &6%item%&c!");
		UseBlocks = getLangConfig(lang).getString("Events.UseBlock", "&cYou can not use it!");
		OnFishing = getLangConfig(lang).getString("Events.Fishing", "&cYou can not fish!");
		ArmorStand = getLangConfig(lang).getString("Events.ArmorStand", "&cYou not can interact with armor stand!");
		OnCommand = getLangConfig(lang).getString("Events.ProhibitedCommand", "&cYou not can use this command!");
		Saplings = getLangConfig(lang).getString("Events.Saplings",
				"&7This sapling from the creative mode, so you can not grow!");
		OnEssentialsSignUse = getLangConfig(lang).getString("Events.OnEssentialsSignUse",
				"&cYou not can use this signs!");

		GamemodesControl = getLangConfig(lang).getString("GamemodesControl.NotAllowedGamemode",
				"&cYou can not go in gamemode &6%gamemode%&c!");

		PlayerListInGamemode = getLangConfig(lang).getString("PlayersInGamemode.List",
				"&7Players in &9%gamemode%&7 mode: &9%list%&7");
		UnknownGamemode = getLangConfig(lang).getString("PlayersInGamemode.UnknownGamemode",
				"&cUnknown gamemode: &6%gamemode%&c.");
		PlayerInGamemodeNotFound = getLangConfig(lang).getString("PlayersInGamemode.NotFound",
				"&7Players in the gamemode &9%gamemode%&7 not found!");
		PlayerListInGamemodeMore = getLangConfig(lang).getString("PlayersInGamemode.More",
				"&7To find out detailed information about the player, type &9/sc check <nickname>&7!");

		menu = getLangConfig(lang).getString("Menu.This", "&9%command% &f- this menu,");
		menureload = getLangConfig(lang).getString("Menu.Reload", "&9%command% &f- reloading,");
		menuinfo = getLangConfig(lang).getString("Menu.Version", "&9%command% &f- information,");
		menuupdate = getLangConfig(lang).getString("Menu.Update", "&9%command% &f- check updates,");
		menugetlist = getLangConfig(lang).getString("Menu.GetList",
				"&9%command% &f- get a list of players who use this gamemode,");
		menuadd = getLangConfig(lang).getString("Menu.AddToList",
				"&9%command% &f- add block or item to list from config,");
		menuremove = getLangConfig(lang).getString("Menu.RemoveFromList",
				"&9%command% &f- remove block or item from list from config,");
		menutools = getLangConfig(lang).getString("Menu.Tools", "&9%command% &f- list of tools,");
		menusettool = getLangConfig(lang).getString("Menu.ChangeTool", "&9%command% &f- get changing tool,");
		menuinfotool = getLangConfig(lang).getString("Menu.InfoTool", "&9%command% &f- get information tool.");
		menuselectionset = getLangConfig(lang).getString("Menu.SelectionSet",
				"&9%command% &f- change type of the blocks in selection,");
		menucheck = getLangConfig(lang).getString("Menu.Check", "&9%command% &f- see information about player.");

		using = getLangConfig(lang).getString("Using", "&7Using: &c%command%");

		reloading = getLangConfig(lang).getString("Reload.Reloading", "&7Reloading...");
		reloadsuccess = getLangConfig(lang).getString("Reload.Success",
				"&7Reloading the plugin successfully completed!");

		AMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingPlacement",
				"&7The block &9%material%&7 successfully added to list of blocks that are prohibited to place!");
		AMtoBreakList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingBreakage",
				"&7The block &9%material%&7 successfully added to list of blocks that are prohibited to break!");
		AMtoUseList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingInventory",
				"&7The item &9%material%&7 successfully added to list of item that are prohibited to use!");
		RMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingPlacement",
				"&7The block &9%material%&7 successfully removed from list of blocks that are prohibited to place!");
		RMtoBreakList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingBreakage",
				"&7The block &9%material%&7 successfully removed from list of blocks that are prohibited to break!");
		RMtoUseList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingInventory",
				"&7The item &9%material%&7 successfully removed from list of items that are prohibited to use!");
		ThisNotMaterialandId = getLangConfig(lang).getString("ChangeConfig.ThisNotMaterialAndId",
				"&7Error: &9%material%&7 is not a material or id of block or item.");

		getsettool = getLangConfig(lang).getString("Tools.ChangeTool.Get", "&7You got the &9change tool&7!");
		namesettool = getLangConfig(lang).getString("Tools.ChangeTool.Name", "&9&lChange Tool");
		loreST1 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.1",
				"&7Left click to &cSET&7 the Game Mode of a block");
		loreST2 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.2",
				"&7Right click to &cREMOVE&7 the Game Mode of a block");
		loreST3 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.3", "&7Tool by %plugin%");
		getinfotool = getLangConfig(lang).getString("Tools.InfoTool.Get", "&7You got the &9information tool&7!");
		nameinfotool = getLangConfig(lang).getString("Tools.InfoTool.Name", "&9&lInformation Tool");
		loreIT1 = getLangConfig(lang).getString("Tools.InfoTool.Lore.1",
				"&7Left or right click to get the Game Mode of a block or a player");
		loreIT2 = getLangConfig(lang).getString("Tools.InfoTool.Lore.2", "&7Tool by %plugin%");
		Type = getLangConfig(lang).getString("Tools.Type", "&7Type: &9%type%");
		creativeType = getLangConfig(lang).getString("Tools.Types.Creative", "creative");
		caturalType = getLangConfig(lang).getString("Tools.Types.Natural", "natural");
		Name = getLangConfig(lang).getString("Tools.Name", "&7Name: &9%name%");
		ID = getLangConfig(lang).getString("Tools.ID", "&7ID: &9%id%");
		Data = getLangConfig(lang).getString("Tools.Data", "&7Data: &9%data%");
		Coordinates = getLangConfig(lang).getString("Tools.Coordinates", "&7Coordinates: &9%coords%");
		World = getLangConfig(lang).getString("Tools.World", "&7World: &9%world%");
		Nick = getLangConfig(lang).getString("Tools.Nickname", "&7Nickname: &9%nickname%");
		UUID = getLangConfig(lang).getString("Tools.UUID", "&7UUID: &9%uuid%");
		GM = getLangConfig(lang).getString("Tools.Gamemode", "&7Gamemode: &9%gamemode%");
		Health = getLangConfig(lang).getString("Tools.Health", "&7Health: &9%health%");
		Exp = getLangConfig(lang).getString("Tools.Exp", "&7Exp: &9%exp%");
		BlockHas = getLangConfig(lang).getString("Tools.BlockHas", "&7This block is has a &6%type%&7!");
		BlockNow = getLangConfig(lang).getString("Tools.BlockNow", "&7Now this block &6%type%&7!");

		Creative = getLangConfig(lang).getString("Gamemodes.Creative", "creative");
		Survival = getLangConfig(lang).getString("Gamemodes.Survival", "survival");
		Adventure = getLangConfig(lang).getString("Gamemodes.Adventure", "adventure");
		Spectator = getLangConfig(lang).getString("Gamemodes.Spectator", "spectator");

		WENotFound = getLangConfig(lang).getString("Selections.WorldEditNotFound",
				"&6WorldEdit&c was not found, so you can not use this command.");
		UnknownType = getLangConfig(lang).getString("Selections.UnknownType",
				"&cUnknown type: &6%type%&c! Available types: &6%types%.");
		MakeSelection = getLangConfig(lang).getString("Selections.MakeSelection", "&cMake a region selection first!");
		NotCuboid = getLangConfig(lang).getString("Selections.NotCuboid", "&cYour selection isn't a cuboid!");
		PleaseWait = getLangConfig(lang).getString("Selections.PleaseWait", "&7It may take some time.. Please wait!");
		BlocksChanged = getLangConfig(lang).getString("Selections.BlocksChanged",
				"&7Been successfully updated &9%number% blocks&7!");

		currentVersion = getLangConfig(lang).getString("Version",
				"&7The current version of the plugin: &9%version%&7!");
		DevelopmentTeam = getLangConfig(lang).getString("DevelopmentTeam", "&7Development team: &9%development-team%");
		WebSite = getLangConfig(lang).getString("Site", "&7Site: &9%link%");
		Author = getLangConfig(lang).getString("Author", "&9%nickname% &7[&cauthor&7]");
	}

	/** \ RUSSIAN \ **/
	/** \---------\ **/

	private static void RussianStrings(String lang) {
		header = " |-------------------------------| \n" + " | Language file of ShareControl | \n"
				+ " |-------------------------------| \n" + "\n" + " HELP:\n" + " %block% - Ð±Ð»Ð¾Ðº \n"
				+ " %item% - Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚ \n" + " %gamemode% - Ð¸Ð³Ñ€Ð¾Ð²Ð¾Ð¹ Ñ€ÐµÐ¶Ð¸Ð¼ Ð¸Ð³Ñ€Ð¾ÐºÐ°\n"
				+ " %type% - Ñ‚Ð¸Ð¿ Ð±Ð»Ð¾ÐºÐ° (ÐµÑ�Ñ‚ÐµÑ�Ñ‚Ð²ÐµÐ½Ð½Ñ‹Ð¹ Ð¸Ð»Ð¸ Ñ‚Ð²Ð¾Ñ€Ñ‡ÐµÑ�ÐºÐ¸Ð¹) \n"
				+ " %coords% - ÐºÐ¾Ð¾Ñ€Ð´Ð¸Ð½Ð°Ñ‚Ñ‹ \n" + " %name% - Ð½Ð°Ð·Ð²Ð°Ð½Ð¸Ðµ Ð±Ð»Ð¾ÐºÐ° \n"
				+ " %id% - ID Ð±Ð»Ð¾ÐºÐ° \n" + " %command% - ÐºÐ¾Ð¼Ð°Ð½Ð´Ð° \n" + " %plugin% - ShareControl \n"
				+ " %update% - Ð½Ð¾Ð²Ð°Ñ� Ð²ÐµÑ€Ñ�Ð¸Ñ� \n" + " %version% - ÑƒÑ�Ñ‚Ð°Ð½Ð¾Ð²Ð»ÐµÐ½Ð½Ð°Ñ� Ð²ÐµÑ€Ñ�Ð¸Ñ� \n"
				+ " %link% - Ñ�Ñ�Ñ‹Ð»ÐºÐ° Ð½Ð° Ñ�Ð°Ð¹Ñ‚ Ð¿Ð»Ð°Ð³Ð¸Ð½Ð° \n"
				+ " %development-team% - ÐºÐ¾Ð¼Ð°Ð½Ð´Ð° Ñ€Ð°Ð·Ñ€Ð°Ð±Ð¾Ñ‚Ñ‡Ð¸ÐºÐ¾Ð² \n"
				+ " %nickname% - Ð½Ð¸Ðº Ð¸Ð³Ñ€Ð¾ÐºÐ° \n"
				+ " %uuid% - ÑƒÐ½Ð¸Ð²ÐµÑ€Ñ�Ð°Ð»ÑŒÐ½Ñ‹Ð¹ ÑƒÐ½Ð¸ÐºÐ°Ð»ÑŒÐ½Ñ‹Ð¹ Ð¸Ð½Ð´ÐµÑ‚Ð¸Ñ„Ð¸ÐºÐ°Ñ‚Ð¾Ñ€ Ð¸Ð³Ñ€Ð¾ÐºÐ°";

		UpdateNotFound = getLangConfig(lang).getString("Update.UpdateNotFound",
				"&7ÐžÐ±Ð½Ð¾Ð²Ð»ÐµÐ½Ð¸Ð¹ Ð½Ðµ Ð½Ð°Ð¹Ð´ÐµÐ½Ð¾. Ð’Ñ‹ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·ÑƒÐµÑ‚Ðµ Ð¿Ð¾Ñ�Ð»ÐµÐ´Ð½ÑŽÑŽ Ð²ÐµÑ€Ñ�Ð¸ÑŽ!");
		UpdateAvailable = getLangConfig(lang).getString("Update.Available",
				"&7Ð’Ñ‹ÑˆÐ»Ð¾ Ð¾Ð±Ð½Ð¾Ð²Ð»ÐµÐ½Ð¸Ðµ: &9%update%&7, Ñ�ÐºÐ°Ñ‡Ð°Ñ‚ÑŒ Ð¿Ð¾ Ñ�Ñ‚Ð¾Ð¹ Ñ�Ñ�Ñ‹Ð»ÐºÐµ: &9%link%&7!");

		NoPerms = getLangConfig(lang).getString("NoPermission",
				"&cÐ£ Ð’Ð°Ñ� Ð½ÐµÐ´Ð¾Ñ�Ñ‚Ð°Ñ‚Ð¾Ñ‡Ð½Ð¾ Ð¿Ñ€Ð°Ð² Ð´Ð»Ñ� Ñ�Ñ‚Ð¾Ð³Ð¾!");

		OnDrop = getLangConfig(lang).getString("Events.Drop",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð²Ñ‹Ð±Ñ€Ð°Ñ�Ñ‹Ð²Ð°Ñ‚ÑŒ Ð²ÐµÑ‰Ð¸!");
		OnMonsterInteract = getLangConfig(lang).getString("Events.MobsInteract",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð²Ð·Ð°Ð¸Ð¼Ð¾Ð´ÐµÐ¹Ñ�Ñ‚Ð²Ð¾Ð²Ð°Ñ‚ÑŒ Ñ� Ð¼Ð¾Ð±Ð°Ð¼Ð¸!");
		OnPlayerInteract = getLangConfig(lang).getString("Events.PlayerInteract",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð²Ð·Ð°Ð¸Ð¼Ð¾Ð´ÐµÐ¹Ñ�Ñ‚Ð²Ð¾Ð²Ð°Ñ‚ÑŒ Ñ� Ð¸Ð³Ñ€Ð¾ÐºÐ°Ð¼Ð¸!");
		OnInventoryClick = getLangConfig(lang).getString("Events.Inventory.Message",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ Ñ�Ñ‚Ð¾Ñ‚ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚!");
		OnInventoryClickMaterial = getLangConfig(lang).getString("Events.Inventory.Material",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ &6%item%&c!");
		OnOpenOtherInventory = getLangConfig(lang).getString("Events.OpenInventory",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ Ð¸Ð½Ð²ÐµÐ½Ñ‚Ð°Ñ€ÑŒ!");
		OnBlockBreak = getLangConfig(lang).getString("Events.BlockBreak.Message",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð»Ð¾Ð¼Ð°Ñ‚ÑŒ Ñ�Ñ‚Ð¾Ñ‚ Ð±Ð»Ð¾Ðº!");
		OnBlockPlace = getLangConfig(lang).getString("Events.BlockPlace.Message",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ñ�Ñ‚Ð°Ð²Ð¸Ñ‚ÑŒ Ñ�Ñ‚Ð¾Ñ‚ Ð±Ð»Ð¾Ðº!");
		OnBlockBreakMaterial = getLangConfig(lang).getString("Events.BlockBreak.Material",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð»Ð¾Ð¼Ð°Ñ‚ÑŒ &6%block%&c!");
		OnBlockPlaceMaterial = getLangConfig(lang).getString("Events.BlockPlace.Material",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ñ�Ñ‚Ð°Ð²Ð¸Ñ‚ÑŒ &6%block%&c!");
		OnBowShoot = getLangConfig(lang).getString("Events.ShootBow",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ñ�Ñ‚Ñ€ÐµÐ»Ñ�Ñ‚ÑŒ Ð¸Ð· Ð»ÑƒÐºÐ°!");
		AnotherWorld = getLangConfig(lang).getString("Events.InteractWithWorlds",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð²Ð·Ð°Ð¸Ð¼Ð¾Ð´ÐµÐ¹Ñ�Ñ‚Ð²Ð¾Ð²Ð°Ñ‚ÑŒ Ñ� Ñ�Ñ‚Ð¸Ð¼ Ð¼Ð¸Ñ€Ð¾Ð¼!");
		CreativeBlockNotDrop = getLangConfig(lang).getString("Events.NotDropBlock",
				"&7Ð­Ñ‚Ð¾Ñ‚ Ð±Ð»Ð¾Ðº Ð¸Ð· Ñ‚Ð²Ð¾Ñ€Ñ‡ÐµÑ�Ñ‚Ð²Ð°, Ð¿Ð¾Ñ�Ñ‚Ð¾Ð¼Ñƒ Ð¾Ð½ Ð½Ðµ Ð²Ñ‹Ð¿Ð°Ð»!");
		CreativeBlockNotBreak = getLangConfig(lang).getString("Events.NotBreakBlock",
				"&7Ð­Ñ‚Ð¾Ñ‚ Ð±Ð»Ð¾Ðº Ð¸Ð· Ñ‚Ð²Ð¾Ñ€Ñ‡ÐµÑ�Ñ‚Ð²Ð°, Ð¿Ð¾Ñ�Ñ‚Ð¾Ð¼Ñƒ Ð²Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ ÐµÐ³Ð¾ Ñ�Ð»Ð¾Ð¼Ð°Ñ‚ÑŒ!");
		EntityInteract = getLangConfig(lang).getString("Events.EntityInteract.Message",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ Ñ�Ñ‚Ð¾Ñ‚ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚!");
		EntityInteractMaterial = getLangConfig(lang).getString("Events.EntityInteract.Material",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ Ñ�Ñ‚Ð¾Ñ‚ &6%item%&c!");
		UseBlocks = getLangConfig(lang).getString("Events.UseBlock",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ Ñ�Ñ‚Ð¾!");
		OnFishing = getLangConfig(lang).getString("Events.Fishing", "&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ñ€Ñ‹Ð±Ð°Ñ‡Ð¸Ñ‚ÑŒ!");
		ArmorStand = getLangConfig(lang).getString("Events.ArmorStand",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð²Ð·Ð°Ð¸Ð¼Ð¾Ð´ÐµÐ¹Ñ�Ñ‚Ð²Ð¾Ð²Ð°Ñ‚ÑŒ Ñ� Ñ�Ñ‚Ð¾Ð¹ÐºÐ¾Ð¹ Ð´Ð»Ñ� Ð±Ñ€Ð¾Ð½Ð¸!");
		OnCommand = getLangConfig(lang).getString("Events.ProhibitedCommand",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ Ñ�Ñ‚Ñƒ ÐºÐ¾Ð¼Ð°Ð½Ð´Ñƒ!");
		Saplings = getLangConfig(lang).getString("Events.Saplings",
				"&7Ð­Ñ‚Ð¾Ñ‚ Ñ�Ð°Ð¶ÐµÐ½ÐµÑ† Ð¸Ð· Ñ‚Ð²Ð¾Ñ€Ñ‡ÐµÑ�Ñ‚Ð²Ð°, Ð¿Ð¾Ñ�Ñ‚Ð¾Ð¼Ñƒ Ð²Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ ÐµÐ³Ð¾ Ð²Ñ‹Ñ€Ð°Ñ�Ñ‚Ð¸Ñ‚ÑŒ!");
		OnEssentialsSignUse = getLangConfig(lang).getString("Events.OnEssentialsSignUse",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ Ñ�Ñ‚Ð¸ Ñ‚Ð°Ð±Ð»Ð¸Ñ‡ÐºÐ¸!");

		GamemodesControl = getLangConfig(lang).getString("GamemodesControl.NotAllowedGamemode",
				"&cÐ’Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¿ÐµÑ€ÐµÐ¹Ñ‚Ð¸ Ð² Ñ€ÐµÐ¶Ð¸Ð¼ Ð¸Ð³Ñ€Ñ‹ &6%gamemode%&c!");

		PlayerListInGamemode = getLangConfig(lang).getString("PlayersInGamemode.List",
				"&7Ð˜Ð³Ñ€Ð¾ÐºÐ¸ Ð² Ñ€ÐµÐ¶Ð¸Ð¼Ðµ Ð¸Ð³Ñ€Ñ‹ &9%gamemode%&7: &9%list%&7");
		UnknownGamemode = getLangConfig(lang).getString("PlayersInGamemode.UnknownGamemode",
				"&cÐ�ÐµÐ¸Ð·Ð²ÐµÑ�Ñ‚Ð½Ñ‹Ð¹ Ñ‚Ð¸Ð¿ Ð¸Ð³Ñ€Ð¾Ð²Ð¾Ð³Ð¾ Ñ€ÐµÐ¶Ð¸Ð¼Ð°: &6%gamemode%&c.");
		PlayerInGamemodeNotFound = getLangConfig(lang).getString("PlayersInGamemode.NotFound",
				"&7Ð˜Ð³Ñ€Ð¾ÐºÐ¸ Ð² Ð¸Ð³Ñ€Ð¾Ð²Ð¾Ð¼ Ñ€ÐµÐ¶Ð¸Ð¼Ðµ &9%gamemode%&7 Ð½Ðµ Ð½Ð°Ð¹Ð´ÐµÐ½Ñ‹!");
		PlayerListInGamemodeMore = getLangConfig(lang).getString("PlayersInGamemode.More",
				"&7Ð§Ñ‚Ð¾Ð±Ñ‹ ÑƒÐ·Ð½Ð°Ñ‚ÑŒ Ð¿Ð¾Ð´Ñ€Ð¾Ð±Ð½ÑƒÑŽ Ð¸Ð½Ñ„Ð¾Ñ€Ð¼Ð°Ñ†Ð¸ÑŽ Ð¾ Ð¸Ð³Ñ€Ð¾ÐºÐµ, Ð½Ð°Ð¿Ð¸ÑˆÐ¸Ñ‚Ðµ &9/sc check <Ð½Ð¸Ðº Ð¸Ð³Ñ€Ð¾ÐºÐ°>&7!");

		menu = getLangConfig(lang).getString("Menu.This", "&9%command% &f- Ð´Ð°Ð½Ð½Ð¾Ðµ Ð¼ÐµÐ½ÑŽ,");
		menureload = getLangConfig(lang).getString("Menu.Reload", "&9%command% &f- Ð¿ÐµÑ€ÐµÐ·Ð°Ð³Ñ€ÑƒÐ·ÐºÐ°,");
		menuinfo = getLangConfig(lang).getString("Menu.Version", "&9%command% &f- Ð¸Ð½Ñ„Ð¾Ñ€Ð¼Ð°Ñ†Ð¸Ñ�,");
		menuupdate = getLangConfig(lang).getString("Menu.Update",
				"&9%command% &f- Ð¿Ñ€Ð¾Ð²ÐµÑ€Ð¸Ñ‚ÑŒ Ð¾Ð±Ð½Ð¾Ð²Ð»ÐµÐ½Ð¸Ñ�,");
		menugetlist = getLangConfig(lang).getString("Menu.GetList",
				"&9%command% &f- Ð¿Ð¾Ð»ÑƒÑ‡Ð¸Ñ‚ÑŒ Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð¸Ð³Ñ€Ð¾ÐºÐ¾Ð², ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·ÑƒÑŽÑ‚ Ð´Ð°Ð½Ð½Ñ‹Ð¹ Ñ€ÐµÐ¶Ð¸Ð¼,");
		menuadd = getLangConfig(lang).getString("Menu.AddToList",
				"&9%command% &f- Ð´Ð¾Ð±Ð°Ð²Ð¸Ñ‚ÑŒ Ð±Ð»Ð¾Ðº Ð¸Ð»Ð¸ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚ Ð² Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð¸Ð· ÐºÐ¾Ð½Ñ„Ð¸Ð³Ð°,");
		menuremove = getLangConfig(lang).getString("Menu.RemoveFromList",
				"&9%command% &f- ÑƒÐ´Ð°Ð»Ð¸Ñ‚ÑŒ Ð±Ð»Ð¾Ðº Ð¸Ð»Ð¸ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚ Ð¸Ð· Ñ�Ð¿Ð¸Ñ�ÐºÐ° Ð¸Ð· ÐºÐ¾Ð½Ñ„Ð¸Ð³Ð°,");
		menutools = getLangConfig(lang).getString("Menu.Tools",
				"&9%command% &f- Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð¸Ð½Ñ�Ñ‚Ñ€ÑƒÐ¼ÐµÐ½Ñ‚Ð¾Ð²,");
		menusettool = getLangConfig(lang).getString("Menu.ChangeTool",
				"&9%command% &f- Ð¿Ð¾Ð»ÑƒÑ‡Ð¸Ñ‚ÑŒ Ð¸Ð·Ð¼ÐµÐ½Ñ�ÑŽÑ‰Ð¸Ð¹ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚,");
		menuinfotool = getLangConfig(lang).getString("Menu.InfoTool",
				"&9%command% &f- Ð¿Ð¾Ð»ÑƒÑ‡Ð¸Ñ‚ÑŒ Ð¸Ð½Ñ„Ð¾Ñ€Ð¼Ð°Ñ†Ð¸Ð¾Ð½Ð½Ñ‹Ð¹ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚,");
		menuselectionset = getLangConfig(lang).getString("Menu.SelectionSet",
				"&9%command% &f- Ð¸Ð·Ð¼ÐµÐ½Ð¸Ñ‚ÑŒ Ñ‚Ð¸Ð¿ Ð±Ð»Ð¾ÐºÐ¾Ð² Ð² Ð²Ñ‹Ð´ÐµÐ»ÐµÐ½Ð½Ð¾Ð¼ Ñ€ÐµÐ³Ð¸Ð¾Ð½Ðµ,");
		menucheck = getLangConfig(lang).getString("Menu.Check",
				"&9%command% &f- Ð¿Ð¾Ð»ÑƒÑ‡Ð¸Ñ‚ÑŒ Ð¸Ð½Ñ„Ð¾Ñ€Ð¼Ð°Ñ†Ð¸ÑŽ Ð¾ Ð¸Ð³Ñ€Ð¾ÐºÑƒ.");

		using = getLangConfig(lang).getString("Using", "&7Ð˜Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ð½Ð¸Ðµ: &c%command%");

		reloading = getLangConfig(lang).getString("Reload.Reloading", "&7ÐŸÐµÑ€ÐµÐ·Ð°Ð³Ñ€ÑƒÐ·ÐºÐ°...");
		reloadsuccess = getLangConfig(lang).getString("Reload.Success",
				"&7ÐŸÐµÑ€ÐµÐ·Ð°Ð³Ñ€ÑƒÐ·ÐºÐ° Ð¿Ð»Ð°Ð³Ð¸Ð½Ð° Ð·Ð°Ð²ÐµÑ€ÑˆÐµÐ½Ð° ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾!");

		AMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingPlacement",
				"&7Ð‘Ð»Ð¾Ðº &9%material%&7 ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ Ð´Ð¾Ð±Ð°Ð²Ð»ÐµÐ½ Ð² Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð±Ð»Ð¾ÐºÐ¾Ð², ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ðµ Ð·Ð°Ð¿Ñ€ÐµÑ‰ÐµÐ½Ð¾ Ñ�Ñ‚Ð°Ð²Ð¸Ñ‚ÑŒ!");
		AMtoBreakList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingBreakage",
				"&7Ð‘Ð»Ð¾Ðº &9%material%&7 ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ Ð´Ð¾Ð±Ð°Ð²Ð»ÐµÐ½ Ð² Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð±Ð»Ð¾ÐºÐ¾Ð², ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ðµ Ð·Ð°Ð¿Ñ€ÐµÑ‰ÐµÐ½Ð¾ Ð»Ð¾Ð¼Ð°Ñ‚ÑŒ!");
		AMtoUseList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingInventory",
				"&7ÐŸÑ€ÐµÐ´Ð¼ÐµÑ‚ &9%material%&7 ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ Ð´Ð¾Ð±Ð°Ð²Ð»ÐµÐ½ Ð² Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚Ð¾Ð², ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ðµ Ð·Ð°Ð¿Ñ€ÐµÑ‰ÐµÐ½Ð¾ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ!");
		RMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingPlacement",
				"&7Ð‘Ð»Ð¾Ðº &9%material%&7 ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ ÑƒÐ´Ð°Ð»ÐµÐ½ Ð¸Ð· Ñ�Ð¿Ð¸Ñ�ÐºÐ° Ð±Ð»Ð¾ÐºÐ¾Ð², ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ðµ Ð·Ð°Ð¿Ñ€ÐµÑ‰ÐµÐ½Ð¾ Ñ�Ñ‚Ð°Ð²Ð¸Ñ‚ÑŒ!");
		RMtoBreakList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingBreakage",
				"&7Ð‘Ð»Ð¾Ðº &9%material%&7 ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ ÑƒÐ´Ð°Ð»ÐµÐ½ Ð¸Ð· Ñ�Ð¿Ð¸Ñ�ÐºÐ° Ð±Ð»Ð¾ÐºÐ¾Ð², ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ðµ Ð·Ð°Ð¿Ñ€ÐµÑ‰ÐµÐ½Ð¾ Ð»Ð¾Ð¼Ð°Ñ‚ÑŒ!");
		RMtoUseList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingInventory",
				"&7ÐŸÑ€ÐµÐ´Ð¼ÐµÑ‚ &9%material%&7 ÑƒÑ�Ð¿ÐµÑˆÐ½Ð¾ ÑƒÐ´Ð°Ð»ÐµÐ½ Ð¸Ð· Ñ�Ð¿Ð¸Ñ�ÐºÐ° Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚Ð¾Ð², ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ðµ Ð·Ð°Ð¿Ñ€ÐµÑ‰ÐµÐ½Ð¾ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ!");
		ThisNotMaterialandId = getLangConfig(lang).getString("ChangeConfig.ThisNotMaterialAndId",
				"&7ÐžÑˆÐ¸Ð±ÐºÐ°: &9%material%&7 Ð½Ðµ Ñ�Ð²Ð»Ñ�ÐµÑ‚Ñ�Ñ� Ð¼Ð°Ñ‚ÐµÑ€Ð¸Ð°Ð»Ð¾Ð¼ Ð¸Ð»Ð¸ ID Ð±Ð»Ð¾ÐºÐ° Ð¸Ð»Ð¸ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚Ð°.");

		getsettool = getLangConfig(lang).getString("Tools.ChangeTool.Get",
				"&7Ð’Ñ‹ Ð¿Ð¾Ð»ÑƒÑ‡Ð¸Ð»Ð¸ &9Ð¸Ð·Ð¼ÐµÐ½Ñ�ÑŽÑ‰Ð¸Ð¹ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚&7!");
		namesettool = getLangConfig(lang).getString("Tools.ChangeTool.Name", "&9&lÐ˜Ð·Ð¼ÐµÐ½Ñ�ÑŽÑ‰Ð¸Ð¹ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚");
		loreST1 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.1",
				"&7Ð�Ð°Ð¶Ð¼Ð¸Ñ‚Ðµ Ð›ÐšÐœ, Ñ‡Ñ‚Ð¾Ð±Ñ‹ &cÐ˜Ð—ÐœÐ•Ð�Ð˜Ð¢Ð¬&7 Ñ€ÐµÐ¶Ð¸Ð¼ Ð½Ð° &cÑ‚Ð²Ð¾Ñ€Ñ‡ÐµÑ�ÐºÐ¸Ð¹");
		loreST2 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.2",
				"&7Ð�Ð°Ð¶Ð¼Ð¸Ñ‚Ðµ ÐŸÐšÐœ, Ñ‡Ñ‚Ð¾Ð±Ñ‹ &cÐ˜Ð—ÐœÐ•Ð�Ð˜Ð¢Ð¬&7 Ñ€ÐµÐ¶Ð¸Ð¼ Ð½Ð° &cÐµÑ�Ñ‚ÐµÑ�Ñ‚Ð²ÐµÐ½Ð½Ñ‹Ð¹");
		loreST3 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.3",
				"&7Ð˜Ð½Ñ�Ñ‚Ñ€ÑƒÐ¼ÐµÐ½Ñ‚ Ð¿Ð»Ð°Ð³Ð¸Ð½Ð° %plugin%");
		getinfotool = getLangConfig(lang).getString("Tools.InfoTool.Get",
				"&7Ð’Ñ‹ Ð¿Ð¾Ð»ÑƒÑ‡Ð¸Ð»Ð¸ &9Ð¸Ð½Ñ„Ð¾Ñ€Ð¼Ð°Ñ†Ð¸Ð¾Ð½Ð½Ñ‹Ð¹ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚&7!");
		nameinfotool = getLangConfig(lang).getString("Tools.InfoTool.Name",
				"&9&lÐ˜Ð½Ñ„Ð¾Ñ€Ð¼Ð°Ñ†Ð¸Ð¾Ð½Ð½Ñ‹Ð¹ Ð¿Ñ€ÐµÐ´Ð¼ÐµÑ‚");
		loreIT1 = getLangConfig(lang).getString("Tools.InfoTool.Lore.1",
				"&7Ð�Ð°Ð¶Ð¼Ð¸Ñ‚Ðµ &cÐ›ÐšÐœ&7 Ð¸Ð»Ð¸ &cÐŸÐšÐœ&7, Ñ‡Ñ‚Ð¾Ð±Ñ‹ Ð¿Ð¾Ð»ÑƒÑ‡Ð¸Ñ‚ÑŒ Ð¸Ð½Ñ„Ð¾Ñ€Ð¼Ð°Ñ†Ð¸ÑŽ Ð¾ Ð±Ð»Ð¾ÐºÐµ Ð¸Ð»Ð¸ Ð¸Ð³Ñ€Ð¾ÐºÐµ");
		loreIT2 = getLangConfig(lang).getString("Tools.InfoTool.Lore.2",
				"&7Ð˜Ð½Ñ�Ñ‚Ñ€ÑƒÐ¼ÐµÐ½Ñ‚ Ð¿Ð»Ð°Ð³Ð¸Ð½Ð° %plugin%");
		Type = getLangConfig(lang).getString("Tools.Type", "&7Ð¢Ð¸Ð¿: &9%type%");
		creativeType = getLangConfig(lang).getString("Tools.Types.Creative", "Ñ‚Ð²Ð¾Ñ€Ñ‡ÐµÑ�ÐºÐ¸Ð¹");
		caturalType = getLangConfig(lang).getString("Tools.Types.Natural", "ÐµÑ�Ñ‚ÐµÑ�Ñ‚Ð²ÐµÐ½Ð½Ñ‹Ð¹");
		Name = getLangConfig(lang).getString("Tools.Name", "&7Ð�Ð°Ð·Ð²Ð°Ð½Ð¸Ðµ: &9%name%");
		ID = getLangConfig(lang).getString("Tools.ID", "&7ID: &9%id%");
		Data = getLangConfig(lang).getString("Tools.Data", "&7Data: &9%data%");
		Coordinates = getLangConfig(lang).getString("Tools.Coordinates", "&7ÐšÐ¾Ð¾Ñ€Ð´Ð¸Ð½Ð°Ñ‚Ñ‹: &9%coords%");
		World = getLangConfig(lang).getString("Tools.World", "&7ÐœÐ¸Ñ€: &9%world%");
		Nick = getLangConfig(lang).getString("Tools.Nickname", "&7Ð�Ð¸Ðº: &9%nickname%");
		UUID = getLangConfig(lang).getString("Tools.UUID",
				"&7Ð£Ð½Ð¸ÐºÐ°Ð»ÑŒÐ½Ñ‹Ð¹ Ð¸Ð½Ð´ÐµÑ‚Ð¸Ñ„Ð¸ÐºÐ°Ñ‚Ð¾Ñ€ (UUID): &9%uuid%");
		GM = getLangConfig(lang).getString("Tools.Gamemode", "&7Ð ÐµÐ¶Ð¸Ð¼: &9%gamemode%");
		Health = getLangConfig(lang).getString("Tools.Health", "&7Ð—Ð´Ð¾Ñ€Ð¾Ð²ÑŒÐµ: &9%health%");
		Exp = getLangConfig(lang).getString("Tools.Exp", "&7ÐžÐ¿Ñ‹Ñ‚: &9%exp%");
		BlockHas = getLangConfig(lang).getString("Tools.BlockHas", "&7Ð­Ñ‚Ð¾Ñ‚ Ð±Ð»Ð¾Ðº ÑƒÐ¶Ðµ &6%type%&7!");
		BlockNow = getLangConfig(lang).getString("Tools.BlockNow", "&7Ð¢ÐµÐ¿ÐµÑ€ÑŒ Ñ�Ñ‚Ð¾Ñ‚ Ð±Ð»Ð¾Ðº &6%type%&7!");

		Creative = getLangConfig(lang).getString("Gamemodes.Creative", "Ñ‚Ð²Ð¾Ñ€Ñ‡ÐµÑ�Ñ‚Ð²Ð¾");
		Survival = getLangConfig(lang).getString("Gamemodes.Survival", "Ð²Ñ‹Ð¶Ð¸Ð²Ð°Ð½Ð¸Ðµ");
		Adventure = getLangConfig(lang).getString("Gamemodes.Adventure", "Ð¿Ñ€Ð¸ÐºÐ»ÑŽÑ‡ÐµÐ½Ð¸Ðµ");
		Spectator = getLangConfig(lang).getString("Gamemodes.Spectator", "Ð½Ð°Ð±Ð»ÑŽÐ´ÐµÐ½Ð¸Ðµ");

		WENotFound = getLangConfig(lang).getString("Selections.WorldEditNotFound",
				"&6WorldEdit&c Ð½Ðµ Ð±Ñ‹Ð» Ð½Ð°Ð¹Ð´ÐµÐ½, Ð¿Ð¾Ñ�Ñ‚Ð¾Ð¼Ñƒ Ð²Ñ‹ Ð½Ðµ Ð¼Ð¾Ð¶ÐµÑ‚Ðµ Ð¸Ñ�Ð¿Ð¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÑŒ Ñ�Ñ‚Ñƒ ÐºÐ¾Ð¼Ð°Ð½Ð´Ñƒ.");
		UnknownType = getLangConfig(lang).getString("Selections.UnknownType",
				"&cÐ�ÐµÐ¸Ð·Ð²ÐµÑ�Ñ‚Ð½Ñ‹Ð¹ Ñ‚Ð¸Ð¿: &6%type%&c! Ð”Ð¾Ñ�Ñ‚ÑƒÐ¿Ð½Ñ‹Ðµ Ñ‚Ð¸Ð¿Ñ‹: &6%types%.");
		MakeSelection = getLangConfig(lang).getString("Selections.MakeSelection",
				"&cÐŸÐµÑ€Ð²Ñ‹Ð¼ Ð´ÐµÐ»Ð¾Ð¼ Ð²Ñ‹Ð´ÐµÐ»Ð¸ Ñ€ÐµÐ³Ð¸Ð¾Ð½ Ñ‚Ð¾Ð¿Ð¾Ñ€Ð¾Ð¼!");
		NotCuboid = getLangConfig(lang).getString("Selections.NotCuboid",
				"&cÐ”Ð°Ð½Ð½Ñ‹Ð¹ Ñ€ÐµÐ³Ð¸Ð¾Ð½ Ð½Ðµ Ñ�Ð²Ð»Ñ�ÐµÑ‚Ñ�Ñ� ÐºÑƒÐ±Ð¾Ð¸Ð´Ð¾Ð¼!");
		PleaseWait = getLangConfig(lang).getString("Selections.PleaseWait",
				"&7Ð­Ñ‚Ð¾ Ð¼Ð¾Ð¶ÐµÑ‚ Ð·Ð°Ð½Ñ�Ñ‚ÑŒ ÐºÐ°ÐºÐ¾Ðµ-Ñ‚Ð¾ Ð²Ñ€ÐµÐ¼Ñ�.. ÐŸÐ¾Ð¶Ð°Ð»ÑƒÐ¹Ñ�Ñ‚Ð°, Ð¿Ð¾Ð´Ð¾Ð¶Ð´Ð¸Ñ‚Ðµ!");
		BlocksChanged = getLangConfig(lang).getString("Selections.BlocksChanged",
				"&7Ð‘Ð»Ð¾ÐºÐ¾Ð² Ð¸Ð·Ð¼ÐµÐ½ÐµÐ½Ð¾: &9%number%&7!");

		currentVersion = getLangConfig(lang).getString("Version",
				"&7Ð¢ÐµÐºÑƒÑ‰Ð°Ñ� Ð²ÐµÑ€Ñ�Ð¸Ñ� Ð¿Ð»Ð°Ð³Ð¸Ð½Ð°: &9%version%&7!");
		DevelopmentTeam = getLangConfig(lang).getString("DevelopmentTeam",
				"&7ÐšÐ¾Ð¼Ð°Ð½Ð´Ð° Ñ€Ð°Ð·Ñ€Ð°Ð±Ð¾Ñ‚Ñ‡Ð¸ÐºÐ¾Ð²: &9%development-team%");
		WebSite = getLangConfig(lang).getString("Site", "&7Ð¡Ð°Ð¹Ñ‚: &9%link%");
		Author = getLangConfig(lang).getString("Author", "&9%nickname% &7[&cÐ°Ð²Ñ‚Ð¾Ñ€&7]");
	}

	/** \ GERMAN \ **/
	/** \-----------\ **/

	private static void GermanStrings(String lang) {
		header = " |-------------------------------| \n" + " | Language file of ShareControl | \n"
				+ " |-------------------------------| \n" + "\n" + " HELP:\n"
				+ " %block% - block that place\\break\\use \n" + " %item% - item that use \n"
				+ " %gamemode% - the gamemode of player \n" + " %type% - type of block (natural or creative) \n"
				+ " %coords% - coordinates of block \n" + " %name% - block name \n" + " %id% - block ID \n"
				+ " %command% - ShareControl's command \n" + " %plugin% - ShareControl \n"
				+ " %update% - new version \n" + " %version% - the current version \n"
				+ " %link% - link to site of plugin \n" + " %development-team% - development team of the plugin \n"
				+ " %nickname% - player \n" + " %uuid% - universally unique identifier of player";

		UpdateNotFound = getLangConfig(lang).getString("Update.UpdateNotFound",
				"&7Keine Updates gefunden! Du verwendest die neuste Version!");
		UpdateAvailable = getLangConfig(lang).getString("Update.Available",
				"&7Update verfÃ¼gbar: &9%update%&7 hier &9%link%&7!");

		NoPerms = getLangConfig(lang).getString("NoPermission", "&cDu hast keine Berechtigung!");

		OnDrop = getLangConfig(lang).getString("Events.Drop", "&cDu kannst nichts werfen!");
		OnMonsterInteract = getLangConfig(lang).getString("Events.MobsInteract",
				"&cDu kannst mit Monster nicht interagieren!");
		OnPlayerInteract = getLangConfig(lang).getString("Events.PlayerInteract",
				"&cDu kannst mit Spieler nicht interagieren!");
		OnInventoryClick = getLangConfig(lang).getString("Events.Inventory.Message",
				"&cDu kannst dieses Item nicht benutzen!");
		OnInventoryClickMaterial = getLangConfig(lang).getString("Events.Inventory.Material",
				"&cDu kannst &6%item%&c nicht benutzen!");
		OnOpenOtherInventory = getLangConfig(lang).getString("Events.OpenInventory", "&cDu kannst das nicht tun!");
		OnBlockBreak = getLangConfig(lang).getString("Events.BlockBreak.Message",
				"&cDu kannst diesen Block nicht brechen!");
		OnBlockPlace = getLangConfig(lang).getString("Events.BlockPlace.Message",
				"&cDu kannst diesen Block nicht platzieren!");
		OnBlockBreakMaterial = getLangConfig(lang).getString("Events.BlockBreak.Material",
				"&cDu kannst &6%block%&c nicht brechen!");
		OnBlockPlaceMaterial = getLangConfig(lang).getString("Events.BlockPlace.Material",
				"&cDu kannst &6%block%&c nicht platzieren!");
		OnBowShoot = getLangConfig(lang).getString("Events.ShootBow", "&cDu kannst keinen Bogen schieÃŸen!");
		AnotherWorld = getLangConfig(lang).getString("Events.InteractWithWorlds",
				"&cDu kannst nicht mit der Umwelt agieren!");
		CreativeBlockNotDrop = getLangConfig(lang).getString("Events.NotDropBlock",
				"&7Block wurde nicht fallen gelassen, weil er aus dem Kreativ-Modus ist!");
		CreativeBlockNotBreak = getLangConfig(lang).getString("Events.NotBreakBlock",
				"&7Block wurde nicht abgebaut, weil er aus dem Kreativ-Modus ist!");
		EntityInteract = getLangConfig(lang).getString("Events.EntityInteract.Message",
				"&cDu kannst diesen Gegenstand nicht benutzen!");
		EntityInteractMaterial = getLangConfig(lang).getString("Events.EntityInteract.Material",
				"&cDu kannst &6%item% &cnicht benutzen!");
		UseBlocks = getLangConfig(lang).getString("Events.UseBlock", "&cDu kannst dies nicht benutzen!");
		OnFishing = getLangConfig(lang).getString("Events.Fishing", "&cDu kannst nicht fischen!");
		ArmorStand = getLangConfig(lang).getString("Events.ArmorStand",
				"&cDu kannst nicht mit RÃ¼stungsstÃ¤nder agieren!");
		OnCommand = getLangConfig(lang).getString("Events.ProhibitedCommand",
				"&cDu kannst diesen Befehl nicht benutzen!");
		Saplings = getLangConfig(lang).getString("Events.Saplings",
				"&7Dieser Setzling ist aus dem Kreativ-Modus, du kannst ihn nicht wachsen lassen!");

		GamemodesControl = getLangConfig(lang).getString("GamemodesControl.NotAllowedGamemode",
				"&cDu kannst nicht in den Modus &6%gamemode% &cgehen!");

		PlayerListInGamemode = getLangConfig(lang).getString("PlayersInGamemode.List",
				"&7Spieler im &9%gamemode%-Modus&7: &9%list%&7");
		UnknownGamemode = getLangConfig(lang).getString("PlayersInGamemode.UnknownGamemode",
				"&cUnbekannter Modus: &6%gamemode%&c.");
		PlayerInGamemodeNotFound = getLangConfig(lang).getString("PlayersInGamemode.NotFound",
				"&7Spieler im Modus &9%gamemode%&7 nicht gefunden!");
		PlayerListInGamemodeMore = getLangConfig(lang).getString("PlayersInGamemode.More",
				"&7Erweiterte Informationen Ã¼ber ein Spieler, benutze &9/sc check <nickname>&7!");

		menu = getLangConfig(lang).getString("Menu.This", "&9%command% &f- dieses MenÃ¼,");
		menureload = getLangConfig(lang).getString("Menu.Reload", "&9%command% &f- reload,");
		menuinfo = getLangConfig(lang).getString("Menu.Version", "&9%command% &f- Informationen,");
		menuupdate = getLangConfig(lang).getString("Menu.Update", "&9%command% &f- nach Updates prÃ¼fen,");
		menugetlist = getLangConfig(lang).getString("Menu.GetList",
				"&9%command% &f- erhalte eine Liste mit Spielern in diesem Modus,");
		menuadd = getLangConfig(lang).getString("Menu.AddToList", "&9%command% &f- fÃ¼ge einen Block zur Liste hinzu,");
		menuremove = getLangConfig(lang).getString("Menu.RemoveFromList",
				"&9%command% &f- entferne einen Block von der Liste,");
		menutools = getLangConfig(lang).getString("Menu.Tools", "&9%command% &f- Liste der Werkzeuge,");
		menusettool = getLangConfig(lang).getString("Menu.ChangeTool", "&9%command% &f- erhalte das Wechsel-Tool,");
		menuinfotool = getLangConfig(lang).getString("Menu.InfoTool",
				"&9%command% &f- erhalte das Informationen-Tool.");
		menuselectionset = getLangConfig(lang).getString("Menu.SelectionSet",
				"&9%command% &f- Ã¤ndert den Typ des Block in der Markierung,");
		menucheck = getLangConfig(lang).getString("Menu.Check",
				"&9%command% &f- erhalte Informationen Ã¼ber einen Spieler.");

		using = getLangConfig(lang).getString("Using", "&7Benutze: &c%command%");

		reloading = getLangConfig(lang).getString("Reload.Reloading", "&7Reload...");
		reloadsuccess = getLangConfig(lang).getString("Reload.Success", "&7Reload erfolgreich!");

		AMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingPlacement",
				"&7Der Block &9%material%&7 wurde auf die Liste der verbotenen BlÃ¶cke hinzugefÃ¼gt!");
		AMtoBreakList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingBreakage",
				"&7Der Block &9%material%&7 wurde auf die Liste der verbotenen BlÃ¶cke hinzugefÃ¼gt!");
		AMtoUseList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingInventory",
				"&7Der Gegenstand &9%material%&7 wurde auf die Liste der verbotenen GegenstÃ¤nde hinzugefÃ¼gt!");
		RMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingPlacement",
				"&7Der Block &9%material%&7 wurde von der Liste verbotenen BlÃ¶cke entfernt!");
		RMtoBreakList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingBreakage",
				"&7Der Block &9%material%&7 wurde von der Liste verbotenen BlÃ¶cke entfernt!");
		RMtoUseList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingInventory",
				"&7Der Gegenstand &9%material%&7 wurde von der Liste verbotenen GegenstÃ¤nde entfernt!");
		ThisNotMaterialandId = getLangConfig(lang).getString("ChangeConfig.ThisNotMaterialAndId",
				"&7FEHLER: &9%material%&7 ist keine bekannte ID oder kein bekannter Name!");

		getsettool = getLangConfig(lang).getString("Tools.ChangeTool.Get", "&7Du erhÃ¤lst &9Wechsel-Tool&7!");
		namesettool = getLangConfig(lang).getString("Tools.ChangeTool.Name", "&9&lWechsel-Tool");
		loreST1 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.1",
				"&7Linksklick um den Modus des Blocks zu &cSETZEN&7!");
		loreST2 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.2",
				"&7Rechtsklick um den Modus des Blocks zu &cENTFERNEN&7!");
		loreST3 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.3", "&7Tool von %plugin%");
		getinfotool = getLangConfig(lang).getString("Tools.InfoTool.Get", "&7You got the &9information tool&7!");
		nameinfotool = getLangConfig(lang).getString("Tools.InfoTool.Name", "&9&lInformation Tool");
		loreIT1 = getLangConfig(lang).getString("Tools.InfoTool.Lore.1", "&7Links- oder Rechtsklick auf den Block,");
		loreIT2 = getLangConfig(lang).getString("Tools.InfoTool.Lore.2", "&7um Informationen zu erhalten");
		Type = getLangConfig(lang).getString("Tools.Type", "&7Typ: &9%type%");
		creativeType = getLangConfig(lang).getString("Tools.Types.Creative", "kreativ");
		caturalType = getLangConfig(lang).getString("Tools.Types.Natural", "natÃ¼rlich");
		Name = getLangConfig(lang).getString("Tools.Name", "&7Name: &9%name%");
		ID = getLangConfig(lang).getString("Tools.ID", "&7ID: &9%id%");
		Data = getLangConfig(lang).getString("Tools.Data", "&7Data: &9%data%");
		Coordinates = getLangConfig(lang).getString("Tools.Coordinates", "&7Koordinaten: &9%coords%");
		World = getLangConfig(lang).getString("Tools.World", "&7Welt: &9%world%");
		Nick = getLangConfig(lang).getString("Tools.Nickname", "&7Nickname: &9%nickname%");
		UUID = getLangConfig(lang).getString("Tools.UUID", "&7UUID: &9%uuid%");
		GM = getLangConfig(lang).getString("Tools.Gamemode", "&7Modus: &9%gamemode%");
		Health = getLangConfig(lang).getString("Tools.Health", "&7leben: &9%health%");
		Exp = getLangConfig(lang).getString("Tools.Exp", "&7EXP: &9%exp%");
		BlockHas = getLangConfig(lang).getString("Tools.BlockHas", "&7This block is has a &6%type%&7!");
		BlockNow = getLangConfig(lang).getString("Tools.BlockNow", "&7Now this block &6%type%&7!");

		Creative = getLangConfig(lang).getString("Gamemodes.Creative", "Kreativ");
		Survival = getLangConfig(lang).getString("Gamemodes.Survival", "Ãœberleben");
		Adventure = getLangConfig(lang).getString("Gamemodes.Adventure", "Abenteuer");
		Spectator = getLangConfig(lang).getString("Gamemodes.Spectator", "Zuschauer");

		WENotFound = getLangConfig(lang).getString("Selections.WorldEditNotFound",
				"&6WorldEdit&c nicht gefunden, Befehl konnte nicht ausgefÃ¼hrt werden.");
		UnknownType = getLangConfig(lang).getString("Selections.UnknownType",
				"&cUnbekannter Typ: &6%type%&c! Bekannte Typen: &6%types%.");
		MakeSelection = getLangConfig(lang).getString("Selections.MakeSelection", "&cMarkiere zuerst eine Region!");
		NotCuboid = getLangConfig(lang).getString("Selections.NotCuboid", "&cMarkierung ist kein Rechteck!");
		PleaseWait = getLangConfig(lang).getString("Selections.PleaseWait", "&7Dies kann etwas dauern... Bitte warte!");
		BlocksChanged = getLangConfig(lang).getString("Selections.BlocksChanged",
				"&7Erfolgreich &9%number% BlÃ¶cke &7geÃ¤ndert!");

		currentVersion = getLangConfig(lang).getString("Version", "&7Derzeitige Version des Plugins: &9%version%&7!");
		DevelopmentTeam = getLangConfig(lang).getString("DevelopmentTeam", "&7Development team: &9%development-team%");
		WebSite = getLangConfig(lang).getString("Site", "&7Seite: &9%link%");
		Author = getLangConfig(lang).getString("Author", "&9%nickname% &7[&cAutor&7]");
	}

	/** \ Simplified Chinese \ **/
	/** \-----------\ **/

	private static void SimplifiedChineseStrings(String lang) {
		header = " |-------------------------------| \n" + " | Language file of ShareControl | \n"
				+ " |-------------------------------| \n" + "\n" + " HELP:\n"
				+ " %block% - block that place\\break\\use \n" + " %item% - item that use \n"
				+ " %gamemode% - the gamemode of player \n" + " %type% - type of block (natural or creative) \n"
				+ " %coords% - coordinates of block \n" + " %name% - block name \n" + " %id% - block ID \n"
				+ " %command% - ShareControl's command \n" + " %plugin% - ShareControl \n"
				+ " %update% - new version \n" + " %version% - the current version \n"
				+ " %link% - link to site of plugin \n" + " %development-team% - development team of the plugin \n"
				+ " %nickname% - player \n" + " %uuid% - universally unique identifier of player";

		UpdateNotFound = getLangConfig(lang).getString("Update.UpdateNotFound", "&7ç›®å‰�å·²æ˜¯æœ€æ–°ç‰ˆæœ¬!");
		UpdateAvailable = getLangConfig(lang).getString("Update.Available",
				"&7å�‘çŽ°æ–°ç‰ˆæœ¬: &9%update%&7, ä¸‹è½½é“¾æŽ¥: &9%link%&7!");

		NoPerms = getLangConfig(lang).getString("NoPermission", "&cä½ æ²¡æœ‰æ�ƒé™�è¿›è¡Œè¯¥æ“�ä½œ!");

		OnDrop = getLangConfig(lang).getString("Events.Drop", "&cä½ ä¸�èƒ½æ‰”ä¸œè¥¿!");
		OnMonsterInteract = getLangConfig(lang).getString("Events.MobsInteract", "&cä½ ä¸�èƒ½ä¸Žæ€ªç‰©æŽ¥è§¦!");
		OnPlayerInteract = getLangConfig(lang).getString("Events.PlayerInteract", "&cä½ ä¸�èƒ½ä¸ŽçŽ©å®¶æŽ¥è§¦!");
		OnInventoryClick = getLangConfig(lang).getString("Events.Inventory.Message", "&cä½ ä¸�èƒ½ä½¿ç”¨æ­¤ç‰©å“�!");
		OnInventoryClickMaterial = getLangConfig(lang).getString("Events.Inventory.Material",
				"&cä½ ä¸�èƒ½ä½¿ç”¨ &6%item%&c!");
		OnOpenOtherInventory = getLangConfig(lang).getString("Events.OpenInventory", "&cä½ ä¸�èƒ½ä½¿ç”¨èƒŒåŒ…!");
		OnBlockBreak = getLangConfig(lang).getString("Events.BlockBreak.Message", "&cä½ ä¸�èƒ½ç ´å��æ­¤æ–¹å�—!");
		OnBlockPlace = getLangConfig(lang).getString("Events.BlockPlace.Message", "&cä½ ä¸�èƒ½æ”¾ç½®æ­¤æ–¹å�—!");
		OnBlockBreakMaterial = getLangConfig(lang).getString("Events.BlockBreak.Material",
				"&cä½ ä¸�èƒ½ç ´å�� &6%block%&c!");
		OnBlockPlaceMaterial = getLangConfig(lang).getString("Events.BlockPlace.Material",
				"&cä½ ä¸�èƒ½æ”¾ç½® &6%block%&c!");
		OnBowShoot = getLangConfig(lang).getString("Events.ShootBow", "&cä½ ä¸�èƒ½å°„ç®­!");
		AnotherWorld = getLangConfig(lang).getString("Events.InteractWithWorlds", "&cä½ ä¸�èƒ½ä¸Žæ­¤ä¸–ç•ŒæŽ¥è§¦!");
		CreativeBlockNotDrop = getLangConfig(lang).getString("Events.NotDropBlock",
				"&7æ­¤æ–¹å�—æ²¡æœ‰æŽ‰è�½ç‰©,å› ä¸ºå®ƒæ˜¯åœ¨åˆ›é€ æ¨¡å¼�ä¸­æ”¾ç½®çš„!");
		CreativeBlockNotBreak = getLangConfig(lang).getString("Events.NotBreakBlock",
				"&7æ­¤æ–¹å�—æ²¡æœ‰è¢«ç ´å��,å› ä¸ºå®ƒæ˜¯åœ¨åˆ›é€ æ¨¡å¼�ä¸­æ”¾ç½®çš„!");
		EntityInteract = getLangConfig(lang).getString("Events.EntityInteract.Message", "&cä½ ä¸�èƒ½ä½¿ç”¨æ­¤ç‰©å“�!");
		EntityInteractMaterial = getLangConfig(lang).getString("Events.EntityInteract.Material",
				"&cä½ ä¸�èƒ½ä½¿ç”¨ &6%item%&c!");
		UseBlocks = getLangConfig(lang).getString("Events.UseBlock", "&cä½ ä¸�èƒ½ä½¿ç”¨å®ƒ!");
		OnFishing = getLangConfig(lang).getString("Events.Fishing", "&cä½ ä¸�èƒ½é’“é±¼!");
		ArmorStand = getLangConfig(lang).getString("Events.ArmorStand", "&cä½ ä¸�èƒ½æŽ¥è§¦ç›”ç”²æž¶!");
		OnCommand = getLangConfig(lang).getString("Events.ProhibitedCommand", "&cä½ ä¸�èƒ½ä½¿ç”¨æ­¤å‘½ä»¤!");
		Saplings = getLangConfig(lang).getString("Events.Saplings",
				"&7è¿™æ˜¯åˆ›é€ æ¨¡å¼�æ”¾ç½®çš„æ ‘è‹—,ä¸�èƒ½å‚¬ç”Ÿ!");

		GamemodesControl = getLangConfig(lang).getString("GamemodesControl.NotAllowedGamemode",
				"&cä½ ä¸�èƒ½åˆ‡æ�¢æ¸¸æˆ�æ¨¡å¼�åˆ° &6%gamemode%&c!");

		PlayerListInGamemode = getLangConfig(lang).getString("PlayersInGamemode.List",
				"&7çŽ©å®¶åœ¨ &9%gamemode%&7 æ¨¡å¼�: &9%list%&7");
		UnknownGamemode = getLangConfig(lang).getString("PlayersInGamemode.UnknownGamemode",
				"&cæœªçŸ¥çš„æ¸¸æˆ�æ¨¡å¼�: &6%gamemode%&c.");
		PlayerInGamemodeNotFound = getLangConfig(lang).getString("PlayersInGamemode.NotFound",
				"&7çŽ©å®¶çš„æ¸¸æˆ�æ¨¡å¼� &9%gamemode%&7 æœªæ‰¾åˆ°!");
		PlayerListInGamemodeMore = getLangConfig(lang).getString("PlayersInGamemode.More",
				"&7è¦�æ˜¾ç¤ºæ­¤çŽ©å®¶çš„è¯¦ç»†ä¿¡æ�¯, ä½¿ç”¨ &9/sc check <nickname>&7!");

		menu = getLangConfig(lang).getString("Menu.This", "&9%command% &f- æ­¤è�œå�•,");
		menureload = getLangConfig(lang).getString("Menu.Reload", "&9%command% &f- é‡�è½½,");
		menuinfo = getLangConfig(lang).getString("Menu.Version", "&9%command% &f- ç‰ˆæœ¬ä¿¡æ�¯,");
		menuupdate = getLangConfig(lang).getString("Menu.Update", "&9%command% &f- æ£€æŸ¥æ›´æ–°,");
		menugetlist = getLangConfig(lang).getString("Menu.GetList",
				"&9%command% &f- èŽ·å�–ä½¿ç”¨æ­¤æ¸¸æˆ�æ¨¡å¼�çš„çŽ©å®¶åˆ—è¡¨,");
		menuadd = getLangConfig(lang).getString("Menu.AddToList", "&9%command% &f- æ·»åŠ æ–¹å�—æˆ–ç‰©å“�åˆ°åˆ—è¡¨,");
		menuremove = getLangConfig(lang).getString("Menu.RemoveFromList",
				"&9%command% &f- ä»Žåˆ—è¡¨ä¸­ç§»é™¤æ–¹å�—æˆ–ç‰©å“�,");
		menutools = getLangConfig(lang).getString("Menu.Tools", "&9%command% &f- å·¥å…·åˆ—è¡¨,");
		menusettool = getLangConfig(lang).getString("Menu.ChangeTool", "&9%command% &f- èŽ·å�–æ›´æ”¹å·¥å…·,");
		menuinfotool = getLangConfig(lang).getString("Menu.InfoTool", "&9%command% &f- èŽ·å�–ä¿¡æ�¯å·¥å…·,");
		menuselectionset = getLangConfig(lang).getString("Menu.SelectionSet",
				"&9%command% &f- æ›´æ”¹æ­¤æ–¹å�—é€‰æ‹©ç±»åž‹,");
		menucheck = getLangConfig(lang).getString("Menu.Check", "&9%command% &f- æŸ¥çœ‹çŽ©å®¶ä¿¡æ�¯.");

		using = getLangConfig(lang).getString("Using", "&7ä½¿ç”¨: &c%command%");

		reloading = getLangConfig(lang).getString("Reload.Reloading", "&7é‡�æ–°è½½å…¥ä¸­...");
		reloadsuccess = getLangConfig(lang).getString("Reload.Success", "&7é‡�è½½å®Œæˆ�!");

		AMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingPlacement",
				"&7æ­¤æ–¹å�— &9%material%&7 å·²æ·»åŠ åˆ°ç¦�æ­¢æ”¾ç½®æ–¹å�—åˆ—è¡¨!");
		AMtoBreakList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingBreakage",
				"&7æ­¤æ–¹å�— &9%material%&7 å·²æ·»åŠ åˆ°ç¦�æ­¢ç ´å��æ–¹å�—åˆ—è¡¨!");
		AMtoUseList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingInventory",
				"&7æ­¤ç‰©å“� &9%material%&7 å·²æ·»åŠ åˆ°ç¦�æ­¢ä½¿ç”¨ç‰©å“�åˆ—è¡¨!");
		RMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingPlacement",
				"&7æ­¤æ–¹å�— &9%material%&7 å·²ä»Žç¦�æ­¢æ”¾ç½®æ–¹å�—åˆ—è¡¨ä¸­ç§»é™¤!");
		RMtoBreakList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingBreakage",
				"&7æ­¤æ–¹å�— &9%material%&7 å·²ä»Žç¦�æ­¢æ”¾ç½®ç ´å��åˆ—è¡¨ä¸­ç§»é™¤!");
		RMtoUseList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingInventory",
				"&7æ­¤ç‰©å“� &9%material%&7 å·²ä»Žç¦�æ­¢ä½¿ç”¨ç‰©å“�åˆ—è¡¨ä¸­ç§»é™¤!");
		ThisNotMaterialandId = getLangConfig(lang).getString("ChangeConfig.ThisNotMaterialAndId",
				"&7é”™è¯¯: &9%material%&7 é”™è¯¯çš„id/ç‰©å“�/æ–¹å�—.");

		getsettool = getLangConfig(lang).getString("Tools.ChangeTool.Get", "&7ä½ èŽ·å¾— &9æ›´æ”¹å·¥å…·&7!");
		namesettool = getLangConfig(lang).getString("Tools.ChangeTool.Name", "&9&læ›´æ”¹å·¥å…·");
		loreST1 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.1", "&7å·¦é”® &cè®¾ç½® &7æ–¹å�—çš„æ¸¸æˆ�æ¨¡å¼�");
		loreST2 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.2", "&7å�³é”® &cæ¸…é™¤ &7æ–¹å�—çš„æ¸¸æˆ�æ¨¡å¼�");
		loreST3 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.3", "&7%plugin%çš„å·¥å…·");
		getinfotool = getLangConfig(lang).getString("Tools.InfoTool.Get", "&7ä½ èŽ·å¾— &9ä¿¡æ�¯å·¥å…·&7!");
		nameinfotool = getLangConfig(lang).getString("Tools.InfoTool.Name", "&9&lä¿¡æ�¯å·¥å…·");
		loreIT1 = getLangConfig(lang).getString("Tools.InfoTool.Lore.1",
				"&7å·¦é”®æˆ–å�³é”®æŸ¥è¯¢æ–¹å�—æˆ–çŽ©å®¶çš„æ¸¸æˆ�æ¨¡å¼�");
		loreIT2 = getLangConfig(lang).getString("Tools.InfoTool.Lore.2", "&7%plugin%çš„å·¥å…·");
		Type = getLangConfig(lang).getString("Tools.Type", "&7ç±»åž‹: &9%type%");
		creativeType = getLangConfig(lang).getString("Tools.Types.Creative", "åˆ›é€ æ”¾ç½®");
		caturalType = getLangConfig(lang).getString("Tools.Types.Natural", "è‡ªç„¶ç”Ÿæˆ�");
		Name = getLangConfig(lang).getString("Tools.Name", "&7å��ç§°: &9%name%");
		ID = getLangConfig(lang).getString("Tools.ID", "&7ID: &9%id%");
		Data = getLangConfig(lang).getString("Tools.Data", "&7Data: &9%data%");
		Coordinates = getLangConfig(lang).getString("Tools.Coordinates", "&7å��æ ‡: &9%coords%");
		World = getLangConfig(lang).getString("Tools.World", "&7ä¸–ç•Œ: &9%world%");
		Nick = getLangConfig(lang).getString("Tools.Nickname", "&7æ˜µç§°: &9%nickname%");
		UUID = getLangConfig(lang).getString("Tools.UUID", "&7UUID: &9%uuid%");
		GM = getLangConfig(lang).getString("Tools.Gamemode", "&7æ¸¸æˆ�æ¨¡å¼�: &9%gamemode%");
		Health = getLangConfig(lang).getString("Tools.Health", "&7è¡€é‡�: &9%health%");
		Exp = getLangConfig(lang).getString("Tools.Exp", "&7ç»�éªŒ: &9%exp%");
		BlockHas = getLangConfig(lang).getString("Tools.BlockHas", "&7æ­¤æ–¹å�—æ˜¯ &6%type%&7çš„!");
		BlockNow = getLangConfig(lang).getString("Tools.BlockNow", "&7çŽ°åœ¨æ­¤æ–¹å�—æ˜¯ &6%type%&7çš„!");

		Creative = getLangConfig(lang).getString("Gamemodes.Creative", "åˆ›é€ æ¨¡å¼�");
		Survival = getLangConfig(lang).getString("Gamemodes.Survival", "ç”Ÿå­˜æ¨¡å¼�");
		Adventure = getLangConfig(lang).getString("Gamemodes.Adventure", "å†’é™©æ¨¡å¼�");
		Spectator = getLangConfig(lang).getString("Gamemodes.Spectator", "æ—�è§‚æ¨¡å¼�");

		WENotFound = getLangConfig(lang).getString("Selections.WorldEditNotFound",
				"&6WorldEdit&c æœªæ‰¾åˆ°, æ— æ³•ä½¿ç”¨æ­¤å‘½ä»¤.");
		UnknownType = getLangConfig(lang).getString("Selections.UnknownType",
				"&cæœªçŸ¥ç±»åž‹: &6%type%&c! æ­£ç¡®çš„ç±»åž‹: &6%types%.");
		MakeSelection = getLangConfig(lang).getString("Selections.MakeSelection", "&cè¯·å…ˆé€‰æ‹©ä¸€ä¸ªåŒºåŸŸ!");
		NotCuboid = getLangConfig(lang).getString("Selections.NotCuboid", "&cæ‚¨é€‰æ‹©çš„ä¸�æ˜¯ä¸€ä¸ªé•¿æ–¹ä½“!");
		PleaseWait = getLangConfig(lang).getString("Selections.PleaseWait",
				"&7è¿™å�¯èƒ½éœ€è¦�ä¸€äº›æ—¶é—´.. è¯·ç¨�ç­‰!");
		BlocksChanged = getLangConfig(lang).getString("Selections.BlocksChanged",
				"&7å·²æˆ�åŠŸæ›´æ–° &9%number% æ–¹å�—&7!");

		currentVersion = getLangConfig(lang).getString("Version", "&7å½“å‰�ç‰ˆæœ¬: &9%version%&7!");
		DevelopmentTeam = getLangConfig(lang).getString("DevelopmentTeam", "&7å¼€å�‘å›¢é˜Ÿ: &9%development-team%");
		WebSite = getLangConfig(lang).getString("Site", "&7ç½‘å�€: &9%link%");
		Author = getLangConfig(lang).getString("Author", "&9%nickname% &7[&cAutor&7]");
	}

	private static String header;

	public static String UpdateNotFound;
	public static String UpdateAvailable;

	public static String OnDrop, OnMonsterInteract, OnPlayerInteract, OnInventoryClick, OnInventoryClickMaterial,
			OnOpenOtherInventory, OnBlockBreak, OnBlockPlace, OnBlockBreakMaterial, OnBlockPlaceMaterial, OnBowShoot,
			AnotherWorld, CreativeBlockNotDrop, CreativeBlockNotBreak, EntityInteract, EntityInteractMaterial,
			UseBlocks, OnFishing, ArmorStand, OnCommand, Saplings, OnEssentialsSignUse;

	public static String GamemodesControl;

	public static String NoPerms;

	public static String menu, menureload, menuinfo, menuupdate, menutools, menusettool, menuinfotool, menucheck,
			menugetlist, menuadd, menuremove, menuselectionset;

	public static String using;

	public static String AMtoBreakList, AMtoPlaceList, AMtoUseList;
	public static String RMtoBreakList, RMtoPlaceList, RMtoUseList;
	public static String ThisNotMaterialandId;

	public static String reloading, reloadsuccess;

	public static String getinfotool, getsettool;

	public static String namesettool, loreST1, loreST2, loreST3;
	public static String nameinfotool, loreIT1, loreIT2;

	public static String creativeType, caturalType, Name, Coordinates, Type, ID, Data, Nick, GM, Health, Exp, UUID,
			World;

	public static String Creative, Survival, Adventure, Spectator;

	public static String PlayerListInGamemode, PlayerListInGamemodeMore, UnknownGamemode, PlayerInGamemodeNotFound;

	public static String WENotFound, UnknownType, PleaseWait, BlocksChanged, MakeSelection, NotCuboid;

	public static String BlockHas;
	public static String BlockNow;

	public static String currentVersion;
	public static String DevelopmentTeam;
	public static String WebSite;
	public static String Author;
}
