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
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.ShareControl.UpdateResult;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;
import com.net.h1karo.sharecontrol.localization.Localization;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class ShareControlCommandExecutor implements CommandExecutor {

	private ShareControl main;

	public ShareControlCommandExecutor(ShareControl h) {
		this.main = h;
	}

	public static int j = 0;

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sharecontrol")) {

			if (args.length == 0) {
				Localization.helpMenu(sender);
				return true;
			}

			if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
				Localization.reloadMsg(sender);
				return true;
			}

			if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver")) {
				main.pluginInfo(sender);
				return true;
			}

			if (args[0].equalsIgnoreCase("check") && args.length == 1) {
				String command = "/sharecontrol check <nickname>";
				String msg = ChatColor.translateAlternateColorCodes('&',
						LanguageFiles.using.replace("%command%", command));
				MessageManager.getManager().msg(sender, MessageType.USE, msg);
				return true;
			}

			if (args[0].equalsIgnoreCase("check") && args.length == 2) {
				Localization.playerInfo(sender, Bukkit.getPlayer(args[1]));
				return true;
			}

			if (args[0].equalsIgnoreCase("list") && args.length == 1) {
				String command = "/sharecontrol list <gamemode>";
				String msg = ChatColor.translateAlternateColorCodes('&',
						LanguageFiles.using.replace("%command%", command));
				MessageManager.getManager().msg(sender, MessageType.USE, msg);
				return true;
			}

			if (args[0].equalsIgnoreCase("list") && args.length == 2) {
				Localization.getListOfPlayerInGM(sender, args[1]);
				return true;
			}

			if (args[0].equalsIgnoreCase("add") && args.length < 3) {
				String command = "/sc add <break/place/use> <material>";
				String msg = ChatColor.translateAlternateColorCodes('&',
						LanguageFiles.using.replace("%command%", command));
				MessageManager.getManager().msg(sender, MessageType.USE, msg);
				return true;
			}

			if (args[0].equalsIgnoreCase("add") && args.length == 3) {
				Configuration.addToList(sender, args[1], args[2]);
				return true;
			}

			if (args[0].equalsIgnoreCase("remove") && args.length < 3) {
				String command = "/sc remove <break/place/use> <material>";
				String msg = ChatColor.translateAlternateColorCodes('&',
						LanguageFiles.using.replace("%command%", command));
				MessageManager.getManager().msg(sender, MessageType.USE, msg);
				return true;
			}

			if (args[0].equalsIgnoreCase("remove") && args.length == 3) {
				Configuration.removeFromList(sender, args[1], args[2]);
				return true;
			}

			if (args[0].equalsIgnoreCase("tools") && args.length == 1) {
				Localization.infoTools(sender);
				return true;
			}

			if (args[0].equalsIgnoreCase("tools")
					&& (args[1].equalsIgnoreCase("infotool") || args[1].equalsIgnoreCase("info"))) {
				Localization.getInfoTool(sender);
				return true;
			}

			if (args[0].equalsIgnoreCase("tools")
					&& (args[1].equalsIgnoreCase("changetool") || args[1].equalsIgnoreCase("change"))) {
				Localization.getSetTool(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("set") && args.length < 2) {
				String command = "/sc set <" + LanguageFiles.creativeType + "/" + LanguageFiles.caturalType + ">";
				String msg = ChatColor.translateAlternateColorCodes('&',
						LanguageFiles.using.replace("%command%", command));
				MessageManager.getManager().msg(sender, MessageType.USE, msg);
				return true;
			}

			if (args[0].equalsIgnoreCase("set") && args.length == 2) {
				if (!(sender instanceof Player)) {
					main.getLogger().info("This command only for players!");
					return true;
				}

				Player p = (Player) sender;

				if (!ShareControl.getFoundWorldEdit()) {
					Localization.WENotFound(sender);
					return true;
				}

				if (!args[1].startsWith("na") && !args[1].startsWith("su") && !args[1].startsWith("cr")
						&& !args[1].startsWith(LanguageFiles.creativeType.substring(1))
						&& !args[1].startsWith(LanguageFiles.caturalType.substring(1))) {
					Localization.UnknownType(sender, args[1]);
					return true;
				}

				WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager()
						.getPlugin("WorldEdit");
				Selection sel = worldEdit.getSelection(p);

				if (sel == null) {
					Localization.MakeSelection(p);
					return true;
				}
				Localization.PleaseWait(p);

				if (!(sel instanceof CuboidSelection)) {
					Localization.NotCuboid(p);
					return true;
				}

				World w = sel.getWorld();
				int i = 0;

				Location min = sel.getMinimumPoint();
				Location max = sel.getMaximumPoint();

				if (args[1].startsWith("cr") || args[1].startsWith(LanguageFiles.creativeType.substring(1)))
					for (int x = min.getBlockX(); x <= max.getBlockX(); x++)
						for (int y = min.getBlockY(); y <= max.getBlockY(); y++)
							for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++)
								if (w.getBlockAt(x, y, z).getType() != Material.AIR
										&& !Database.isCreative(w.getBlockAt(x, y, z))) {
									Database.addBlock(w.getBlockAt(x, y, z));
									i++;
								}

				if (args[1].startsWith("na") || args[1].startsWith("su")
						|| args[1].startsWith(LanguageFiles.caturalType.substring(1)))
					for (int x = min.getBlockX(); x <= max.getBlockX(); x++)
						for (int y = min.getBlockY(); y <= max.getBlockY(); y++)
							for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++)
								if (w.getBlockAt(x, y, z).getType() != Material.AIR
										&& Database.isCreative(w.getBlockAt(x, y, z))) {
									Database.removeBlock(w.getBlockAt(x, y, z));
									i++;
								}
				Localization.BlocksChanged(p, i);
				return true;
			}

			if (args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("up")) {
				main.updateCheck();
				if (main.result == UpdateResult.UPDATE_AVAILABLE)
					Localization.UpdateFoundPlayer(sender);
				if (main.result == UpdateResult.NO_UPDATE)
					Localization.UpdateNotFound(sender);
				return true;
			}
			String command = "/sharecontrol";
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.using.replace("%command%", command));
			MessageManager.getManager().msg(sender, MessageType.USE, msg);
			return true;
		}
		return false;
	}
}
