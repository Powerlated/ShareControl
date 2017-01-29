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

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.net.h1karo.sharecontrol.version.CoreVersion;
import com.net.h1karo.sharecontrol.MessageManager;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.Items;
import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.database.MySQL;

public class Localization {

	private static ShareControl main;

	public Localization(ShareControl h) {
		Localization.main = h;
	}
	//
	// UPDATES MESSAGES
	//

	public static String name = "";
	public static String LatVersion = "";
	public static String link = "http://dev.bukkit.org/bukkit-plugins/sharecontrol/";

	public static void UpdateNotFound(CommandSender p) {
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UpdateNotFound);
		MessageManager.getManager().msg(p, MessageType.UPDATE, msg);
	}

	public static void UpdateFoundPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!Permissions.perms(p, "update") || !Configuration.versionCheck)
				return;
		} else if (!Configuration.versionCheck)
			return;
		name = "ShareControl v" + ShareControl.newVersion;
		String msg = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.UpdateAvailable.replace("%update%", name).replace("%link%", link));
		MessageManager.getManager().msg(sender, MessageType.UPDATE, msg);
	}

	public static String prefix = ChatColor.BLUE + "Share" + ChatColor.WHITE + "Control" + ChatColor.RESET;
	public static String prefixab = ChatColor.BLUE + "S" + ChatColor.WHITE + "C" + ChatColor.RESET;

	//
	// EVENT MESSAGES
	//

	public static void NoPerms(Player p) {
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NoPerms);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void dropNotify(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnDrop);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void monsterInteractNotify(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnMonsterInteract);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void fishing(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnFishing);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void Bow(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnBowShoot);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void PlayerInteractNotify(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnPlayerInteract);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void invNotify(Material typeThisItem, Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg;
		String strThisItem = typeThisItem.toString().toLowerCase().replace("_", " ");
		if (Configuration.material)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.OnInventoryClickMaterial.replace("%item%", strThisItem));
		else
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnInventoryClick);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void openInv(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnOpenOtherInventory);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void PlaceBlock(Material typeThisBlock, Player p) {
		if (!Configuration.creativeNotify)
			return;
		String strThisBlock = typeThisBlock.toString().toLowerCase().replace("_", " ");
		String msg;
		if (Configuration.material)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.OnBlockPlaceMaterial.replace("%block%", strThisBlock));
		else
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnBlockPlace);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void BreakBlock(Material typeThisBlock, Player p) {
		if (!Configuration.creativeNotify)
			return;
		String strThisBlock = typeThisBlock.toString().toLowerCase().replace("_", " ");
		String msg;
		if (Configuration.material)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.OnBlockBreakMaterial.replace("%block%", strThisBlock));
		else
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnBlockBreak);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void InBlockWorld(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.AnotherWorld);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void SurvivalBlockNotDrop(Player p) {
		if (!Configuration.survivalNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeBlockNotDrop);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void SurvivalBlockNotBreak(Player p) {
		if (!Configuration.survivalNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeBlockNotBreak);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void entityUseNotify(EntityType typeThisEntity, Player p) {
		if (!Configuration.creativeNotify)
			return;
		String strThisItem = typeThisEntity.toString().toLowerCase().replace("_", " ");
		String msg;
		if (Configuration.material)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.EntityInteractMaterial.replace("%item%", strThisItem));
		else
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.EntityInteract);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void interact(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UseBlocks);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void ANEntityUseNotify(EntityType typeThisEntity, Player p) {
		if (!Configuration.creativeNotify)
			return;
		String strThisItem = typeThisEntity.toString().toLowerCase().replace("_", " ");
		String msg;
		if (Configuration.material)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.EntityInteractMaterial.replace("%item%", strThisItem));
		else
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.EntityInteract);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void ANinteract(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UseBlocks);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void ProhibitedCmd(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnCommand);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void armorStand(Player p) {
		if (!Configuration.creativeNotify)
			return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.ArmorStand);
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void Saplings(Player p) {
		if ((Configuration.creativeNotify && p.getGameMode() == GameMode.CREATIVE)
				|| (Configuration.survivalNotify && p.getGameMode() != GameMode.CREATIVE)) {
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Saplings);
			MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
		}
	}

	public static void NotAllowedGamemode(Player p, String gamemode) {
		if (gamemode.compareToIgnoreCase("creative") == 0)
			gamemode = LanguageFiles.Creative;
		if (gamemode.compareToIgnoreCase("survival") == 0)
			gamemode = LanguageFiles.Survival;
		if (gamemode.compareToIgnoreCase("adventure") == 0)
			gamemode = LanguageFiles.Adventure;
		if (gamemode.compareToIgnoreCase("spectator") == 0)
			gamemode = LanguageFiles.Spectator;
		String msg = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.GamemodesControl.replace("%gamemode%", gamemode));
		MessageManager.getManager().msg(p, MessageType.PLAYERS, msg);
	}

	public static void getListOfPlayerInGM(CommandSender sender, String gamemode) {
		if (getGamemode(gamemode) == null) {
			String msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.UnknownGamemode.replace("%gamemode%", gamemode));
			MessageManager.getManager().msg(sender, MessageType.PLINFO, msg);
			return;
		}

		String list = "";
		String msg;

		for (Player p : Bukkit.getOnlinePlayers())
			if (p.getGameMode() == getGamemode(gamemode))
				if (list == "")
					list = p.getDisplayName();
				else
					list = list + ", " + p.getDisplayName();

		if (gamemode.compareToIgnoreCase("creative") == 0 || gamemode.compareToIgnoreCase("1") == 0)
			gamemode = LanguageFiles.Creative;
		if (gamemode.compareToIgnoreCase("survival") == 0 || gamemode.compareToIgnoreCase("0") == 0)
			gamemode = LanguageFiles.Survival;
		if (gamemode.compareToIgnoreCase("adventure") == 0 || gamemode.compareToIgnoreCase("2") == 0)
			gamemode = LanguageFiles.Adventure;
		if (gamemode.compareToIgnoreCase("spectator") == 0 || gamemode.compareToIgnoreCase("3") == 0)
			gamemode = LanguageFiles.Spectator;
		if (list != "")
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.PlayerListInGamemode.replace("%gamemode%", gamemode).replace("%list%", list));
		else
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.PlayerInGamemodeNotFound.replace("%gamemode%", gamemode));
		MessageManager.getManager().msg(sender, MessageType.PLINFO, msg);

	}

	@SuppressWarnings("deprecation")
	private static GameMode getGamemode(String gamemode) {
		if (Database.isInteger(gamemode)) {
			return GameMode.getByValue(Integer.parseInt(gamemode));
		} else {
			if (gamemode.compareToIgnoreCase("creative") == 0)
				return GameMode.CREATIVE;
			if (gamemode.compareToIgnoreCase("survival") == 0)
				return GameMode.SURVIVAL;
			if (gamemode.compareToIgnoreCase("adventure") == 0)
				return GameMode.ADVENTURE;
			if (CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus))
				if (gamemode.compareToIgnoreCase("spectator") == 0)
					return GameMode.SPECTATOR;
		}
		return null;
	}

	//
	// MENUES
	//

	public static void helpMenu(CommandSender sender) {
		String command = " /sharecontrol";
		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menu.replace("%command%", command));
		command = " /sc reload";
		String msg2 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menureload.replace("%command%", command));
		command = " /sc version";
		String msg3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menuinfo.replace("%command%", command));
		command = " /sc update";
		String msg4 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menuupdate.replace("%command%", command));
		command = " /sc list <gamemode>";
		String msg5 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menugetlist.replace("%command%", command));
		command = " /sc add <break/place/use> <material/id>";
		String msg6 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menuadd.replace("%command%", command));
		command = " /sc remove <break/place/use> <material/id>";
		String msg7 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menuremove.replace("%command%", command));
		command = " /sc tools";
		String msg8 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menutools.replace("%command%", command));
		command = " /sc tools changetool";
		String msg9 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menusettool.replace("%command%", command));
		command = " /sc tools infotool";
		String msg10 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menuinfotool.replace("%command%", command));
		command = " /sc set <" + LanguageFiles.NaturalType + "/" + LanguageFiles.CreativeType + ">";
		String msg11 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menuselectionset.replace("%command%", command));
		command = " /sc check <nickname>";
		String msg12 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menucheck.replace("%command%", command));
		MessageManager.getManager().msg(sender, MessageType.HELP,
				"\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 " + prefix + ChatColor.BLUE
						+ " Menu" + ChatColor.GRAY
						+ " \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
		MessageManager.getManager().msg(sender, MessageType.HELP, msg1);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg2);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg3);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg4);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg5);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg6);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg7);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg8);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg9);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg10);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg11);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg12);
	}

	public static void reloadMsg(CommandSender sender) {
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&7&l=============== &9&lShare&f&lControl &9Reload &7&l================"));

		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.reloading);
		String msg2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.reloadsuccess);

		if (!main.checkSender(sender)) {
			MessageManager.getManager().msg(sender, MessageType.PLINFO, msg1);
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Reloading..."));
		} else
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Reloading..."));

		MySQL.disconnect();

		Bukkit.getScheduler().cancelTasks(main);

		Configuration.loadCfg();
		Configuration.saveCfg();

		Database.autoSaveDatabase();

		try {
			MySQL.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (ShareControl.error) {
			Configuration.error(sender);
		} else {
			if (!main.checkSender(sender)) {
				MessageManager.getManager().msg(sender, MessageType.PLINFO, msg2);
				console.sendMessage(
						ChatColor.translateAlternateColorCodes('&', " &7Reloading the plugin successfully completed!"));
			} else
				console.sendMessage(
						ChatColor.translateAlternateColorCodes('&', " &7Reloading the plugin successfully completed!"));
		}

		console.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&7&l===================================================="));
	}

	public static void infoTools(CommandSender p) {

		String command = " /sc tools";
		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menu.replace("%command%", command));
		command = " /sc tools changetool";
		String msg2 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menusettool.replace("%command%", command));
		command = " /sc tools infotool";
		String msg3 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.menuinfotool.replace("%command%", command));

		MessageManager.getManager().msg(p, MessageType.HELP,
				"\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 " + prefix + ChatColor.BLUE + " Tool Menu"
						+ ChatColor.GRAY + " \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
		MessageManager.getManager().msg(p, MessageType.HELP, msg1);
		MessageManager.getManager().msg(p, MessageType.HELP, msg2);
		MessageManager.getManager().msg(p, MessageType.HELP, msg3);
	}

	// TOOLS
	public static void getInfoTool(CommandSender p) {
		if (!(p instanceof Player)) {
			MessageManager.getManager().msg(p, MessageType.HELP, "This command only for players!");
			return;
		}

		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.getinfotool);

		MessageManager.getManager().msg(p, MessageType.INFO, msg);

		String nameIT = ChatColor.translateAlternateColorCodes('&', LanguageFiles.nameinfotool);
		String loreStr1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT1);
		String loreStr2;
		if (LanguageFiles.loreIT2.contains("%plugin%"))
			loreStr2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT2.replace("%plugin%", prefix));
		else
			loreStr2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT2);

		List<String> loreIT = Arrays.asList(loreStr1, loreStr2);
		ItemStack infotool = Items.setMeta(new ItemStack(Material.BLAZE_POWDER), nameIT, loreIT);

		((Player) p).getInventory().addItem(infotool);
	}

	public static void getSetTool(CommandSender p) {
		if (!(p instanceof Player)) {
			MessageManager.getManager().msg(p, MessageType.HELP, "This command only for players!");
			return;
		}
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.getsettool);
		MessageManager.getManager().msg(p, MessageType.INFO, msg);

		String nameST = ChatColor.translateAlternateColorCodes('&', LanguageFiles.namesettool);
		String loreST1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST1);
		String loreST2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST2);
		String loreST3;
		if (LanguageFiles.loreST3.contains("%plugin%"))
			loreST3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST3.replace("%plugin%", prefix));
		else
			loreST3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST3);
		List<String> loreST = Arrays.asList(loreST1, loreST2, loreST3);
		ItemStack settool = Items.setMeta(new ItemStack(Material.MAGMA_CREAM), nameST, loreST);

		((Player) p).getInventory().addItem(settool);
	}

	@SuppressWarnings("deprecation")
	public static void BlockInfo(Player p, Block b) {
		String id = b.getTypeId() + "";
		String material = b.getType().toString();
		String coords = b.getX() + ", " + b.getY() + ", " + b.getZ();
		String type;
		String data = b.getData() + "";

		if (Database.isCreative(b))
			type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeType);
		else
			type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NaturalType);

		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Name.replace("%name%", material));
		String msg2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.ID.replace("%id%", id));
		String msg3 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.Coordinates.replace("%coords%", coords));
		String msg4 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Type.replace("%type%", type));
		String msg5 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Data.replace("%data%", data));

		MessageManager.getManager().msg(p, MessageType.HELP,
				"\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 " + ChatColor.BLUE
						+ " Block Information" + ChatColor.GRAY
						+ " \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
		MessageManager.getManager().msg(p, MessageType.HELP, msg1);
		MessageManager.getManager().msg(p, MessageType.HELP, msg2);
		MessageManager.getManager().msg(p, MessageType.HELP, msg3);
		MessageManager.getManager().msg(p, MessageType.HELP, msg4);
		MessageManager.getManager().msg(p, MessageType.HELP, msg5);
	}

	public static void PlayerInfo(CommandSender sender, Player m) {
		String gamemode = m.getGameMode() + "";
		String name = m.getDisplayName();
		String uuid = m.getUniqueId().toString();
		double health = m.getHealthScale();
		float exp = m.getExp();
		String coords = m.getLocation().getBlockX() + ", " + m.getLocation().getBlockY() + ", "
				+ m.getLocation().getBlockZ();
		String world = m.getWorld().getName();

		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Nick.replace("%nickname%", name));
		String msg2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UUID.replace("%uuid%", uuid));
		String msg3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.GM.replace("%gamemode%", gamemode));
		String msg4 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.World.replace("%world%", world + ""));
		String msg5 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.Coordinates.replace("%coords%", coords + ""));
		String msg6 = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.Health.replace("%health%", health + ""));
		String msg7 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Exp.replace("%exp%", exp + ""));

		MessageManager.getManager().msg(sender, MessageType.HELP,
				"\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550 " + ChatColor.BLUE
						+ "Player Information" + ChatColor.GRAY
						+ " \u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
		MessageManager.getManager().msg(sender, MessageType.HELP, msg1);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg2);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg3);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg4);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg5);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg6);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg7);
	}

	public static void CreativeTypeNow(Player p) {
		String type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeType);
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.BlockNow.replace("%type%", type));
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}

	public static void NaturalTypeNow(Player p) {
		String type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NaturalType);
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.BlockNow.replace("%type%", type));
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}

	public static void CreativeTypeHas(Player p) {
		String type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeType);
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.BlockHas.replace("%type%", type));
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}

	public static void NaturalTypeHas(Player p) {
		String type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NaturalType);
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.BlockHas.replace("%type%", type));
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}

	public static void thisNotMaterialAndId(CommandSender sender, String material) {
		String msg = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.ThisNotMaterialandId.replace("%material%", material));
		MessageManager.getManager().msg(sender, MessageType.PLINFO, msg);
	}

	public static void AddSuccess(CommandSender sender, String list, String material) {
		String msg = "";
		if (list.compareToIgnoreCase("break") == 0)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.AMtoBreakList.replace("%material%", material));
		if (list.compareToIgnoreCase("place") == 0)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.AMtoPlaceList.replace("%material%", material));
		if (list.compareToIgnoreCase("use") == 0)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.AMtoUseList.replace("%material%", material));

		MessageManager.getManager().msg(sender, MessageType.PLINFO, msg);
	}

	public static void RemoveSuccess(CommandSender sender, String list, String material) {
		String msg = "";
		if (list.compareToIgnoreCase("break") == 0)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.RMtoBreakList.replace("%material%", material));
		if (list.compareToIgnoreCase("place") == 0)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.RMtoPlaceList.replace("%material%", material));
		if (list.compareToIgnoreCase("use") == 0)
			msg = ChatColor.translateAlternateColorCodes('&',
					LanguageFiles.RMtoUseList.replace("%material%", material));

		MessageManager.getManager().msg(sender, MessageType.PLINFO, msg);
	}

	public static void WENotFound(CommandSender sender) {
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.AMtoUseList);
		MessageManager.getManager().msg(sender, MessageType.BAD, msg);
	}

	public static void UnknownType(CommandSender sender, String type) {
		String types = LanguageFiles.CreativeType + ", " + LanguageFiles.NaturalType;
		String msg = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.UnknownType.replace("%type%", type).replace("%types%", types));
		MessageManager.getManager().msg(sender, MessageType.BAD, msg);
	}

	public static void PleaseWait(Player p) {
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.PleaseWait);
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}

	public static void BlocksChanged(Player p, int number) {
		String msg = ChatColor.translateAlternateColorCodes('&',
				LanguageFiles.BlocksChanged.replace("%number%", number + ""));
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}

	public static void MakeSelection(Player p) {
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.MakeSelection);
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}

	public static void NotCuboid(Player p) {
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NotCuboid);
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}

	public static void defGood(String msg, Player p) {
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
}
