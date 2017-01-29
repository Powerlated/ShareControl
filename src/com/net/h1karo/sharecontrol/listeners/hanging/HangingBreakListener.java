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

package com.net.h1karo.sharecontrol.listeners.hanging;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;

import com.net.h1karo.sharecontrol.ShareControl;

public class HangingBreakListener implements Listener {

	@SuppressWarnings("unused")
	private final ShareControl main;

	public HangingBreakListener(ShareControl h) {
		this.main = h;
	}

	@EventHandler
	public void HangingBreak(HangingBreakEvent e) {
		Entity ent = e.getEntity();
		if (ent.hasMetadata("ShareControl.CREATIVE_ENTITY"))
			e.getEntity().remove();
	}
}
