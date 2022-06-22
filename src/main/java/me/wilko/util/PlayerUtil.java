package me.wilko.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class PlayerUtil {

	/**
	 * Gets any player that has has ever joined the server by name whether they are online or not
	 *
	 * @param name name to retieve
	 * @return player with matching name
	 */
	public static OfflinePlayer getPlayer(String name) {

		for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {

			if (offlinePlayer.hasPlayedBefore() && offlinePlayer.getName().equalsIgnoreCase(name))
				return offlinePlayer;
		}

		return null;
	}

	/**
	 * Gets online UUID of a player by name whether the server is in online mode or not
	 *
	 * @param name name of player
	 * @return online UUID of player
	 */
	public static UUID getOnlineUUID(String name) {
		return getPlayer(name).getUniqueId();
	}

	/**
	 * Checks whether a player is online or not
	 *
	 * @param target player to check
	 * @return online player object
	 */
	public static Player isOnline(OfflinePlayer target) {

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			if (getOnlineUUID(onlinePlayer.getName()).equals(getOnlineUUID(target.getName())))
				return onlinePlayer;
		}

		return null;
	}
}
