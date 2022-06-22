package me.wilko.listener;

import me.wilko.economy.PlayerAccount;
import me.wilko.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		// Fail-safe method to get online UUID of player whether the server is in offline mode or not
		if (Bukkit.getOnlineMode())
			PlayerAccount.findAccount(event.getPlayer().getUniqueId());
		else
			PlayerAccount.findAccount(PlayerUtil.getPlayer(event.getPlayer().getName()).getUniqueId());

	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {

		// Removes the player's data from server instance to reduce lag.
		// Fail-safe method to get online UUID of player whether the server is in offline mode or not
		if (Bukkit.getOnlineMode())
			PlayerAccount.removeAccount(event.getPlayer());
		else
			PlayerAccount.removeAccount(PlayerUtil.getPlayer(event.getPlayer().getName()).getUniqueId());
	}
}
