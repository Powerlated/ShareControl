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

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.net.h1karo.sharecontrol.configuration.Configuration;

public class MessageManager {

	private MessageManager() {
	};

	private static MessageManager manager = new MessageManager();

	public static MessageManager getManager() {
		return manager;
	}

	String latestmsg = "";

	public enum MessageType {
		INFO(ChatColor.GRAY), BAD(ChatColor.RED), USE(ChatColor.GRAY), PLAYERS(ChatColor.GRAY), PLINFO(
				ChatColor.GRAY), HELP(ChatColor.GRAY), ERROR(ChatColor.DARK_RED), UPDATE(ChatColor.GRAY);

		private ChatColor color;

		MessageType(ChatColor color) {
			this.color = color;
		}

		public ChatColor getColor() {
			return color;
		}
	}

	public static String prefix = ChatColor.GRAY + "[" + ChatColor.BLUE + "Share" + ChatColor.WHITE + "Control"
			+ ChatColor.GRAY + "] " + ChatColor.RESET;

	public void msg(CommandSender sender, MessageType type, String... msgs) {
		for (String msg : msgs) {
			if (Configuration.prefixEnabled) {
				if (type != MessageType.HELP)
					sender.sendMessage(prefix + type.getColor() + msg);
				else
					sender.sendMessage(type.getColor() + msg);
			} else {
				if (type != MessageType.HELP && type != MessageType.PLAYERS)
					sender.sendMessage(prefix + type.getColor() + msg);
				else
					sender.sendMessage(type.getColor() + msg);
			}
		}
	}
}
