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

package com.net.h1karo.sharecontrol.version;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

public enum CoreVersion {
	OlderThanOneDotSeven, OneDotSeven, OneDotEight, OneDotNine, OneDotTen, OneDotEleven, Unknown, OneDotEightPlus, OneDotNinePlus, OneDotTenPlus, OneDotElevenPlus, NewerThanOneDotEleven;

	public static CoreVersion getVersion() {
		String bukkitVersion = Bukkit.getServer().getVersion();
		if (bukkitVersion.contains("(MC: ")) {
			String version = bukkitVersion.substring(bukkitVersion.lastIndexOf("(MC: ") + 5,
					bukkitVersion.lastIndexOf(")"));
			int firstNum = Integer.parseInt(version.split(Pattern.quote("."))[0]);
			int secondNum = Integer.parseInt(version.split(Pattern.quote("."))[1]);
			if (firstNum >= 1) {
				if (secondNum < 7)
					return OlderThanOneDotSeven;
				if (secondNum == 7)
					return OneDotSeven;
				if (secondNum == 8)
					return OneDotEight;
				if (secondNum == 9)
					return OneDotNine;
				if (secondNum == 10)
					return OneDotTen;
				if (secondNum == 11)
					return OneDotEleven;
				if (secondNum > 11)
					return NewerThanOneDotEleven;
				return Unknown;
			} else
				return OlderThanOneDotSeven;
		} else
			return Unknown;
	}

	public static List<CoreVersion> getVersionsArray() {
		String bukkitVersion = Bukkit.getServer().getVersion();
		List<CoreVersion> arr = new ArrayList<CoreVersion>();
		if (bukkitVersion.contains("(MC: ")) {
			String version = bukkitVersion.substring(bukkitVersion.lastIndexOf("(MC: ") + 5,
					bukkitVersion.lastIndexOf(")"));
			int firstNum = Integer.parseInt(version.split(Pattern.quote("."))[0]);
			int secondNum = Integer.parseInt(version.split(Pattern.quote("."))[1]);
			if (firstNum >= 1) {
				if (secondNum < 7)
					arr.add(OlderThanOneDotSeven);
				if (secondNum == 7)
					arr.add(OneDotSeven);
				if (secondNum >= 8)
					arr.add(OneDotEightPlus);
				if (secondNum >= 9)
					arr.add(OneDotNinePlus);
				if (secondNum >= 10)
					arr.add(OneDotTenPlus);
				if (secondNum >= 11)
					arr.add(OneDotElevenPlus);
				if (arr.size() == 0)
					arr.add(Unknown);
			} else
				arr.add(OlderThanOneDotSeven);
		} else
			arr.add(Unknown);
		return arr;
	}
}
