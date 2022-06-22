package me.wilko.economy;

import me.wilko.util.PlayerUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerAccount {

	private static final Map<UUID, PlayerAccount> accounts = new HashMap<>();
	private final UUID uuid;
	private final YamlConfiguration config;
	private final File file;
	private double balance = 0;

	public PlayerAccount(java.util.UUID uuid) {
		this.uuid = uuid;

		this.config = new YamlConfiguration();

		// Finds the players file. If this player does not have a file, one will be created for them
		this.file = this.loadFile();

		// The physical file is loaded into the config where data can be retrieved
		this.loadConfig();
		this.saveConfig();
	}

	private void loadConfig() {

		try {

			// Attempts to load a YAML config from a particular file
			this.config.load(this.file);

		} catch (Throwable t) {
			t.printStackTrace();
		}

		// Retrieves the balance property from the config and sets it to the balance of this account
		this.balance = this.config.getDouble("balance");
	}

	private void saveConfig() {

		// Saves the current account balance to file
		this.config.set("balance", this.balance);

		// Saves the config file
		try {
			this.config.save(this.file);
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	private File loadFile() {
		Economy instance = Economy.getInstance();
		String path = "players/" + uuid.toString() + ".yml";

		// Gets file from within the plugin's folder
		File file = new File(instance.getDataFolder(), path);

		// if the players folder doesn't exist, it will be made
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();

		// if a file for this account doesn't exist, it will be created
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		return file;
	}

	/**
	 * Sets the balance of an account
	 *
	 * @param value amount to be set
	 */
	public void setBalance(double value) {
		this.balance = value;

		this.saveConfig();
	}

	/**
	 * Gets the balance of this account
	 *
	 * @return account balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Finds an account from an offline player object
	 *
	 * @param player offline player
	 * @return account of this player
	 */
	public static PlayerAccount findAccount(OfflinePlayer player) {
		return PlayerAccount.findAccount(PlayerUtil.getOnlineUUID(player.getName()));
	}

	/**
	 * Finds an account from an online player object
	 *
	 * @param player online player
	 * @return account of this player
	 */
	public static PlayerAccount findAccount(Player player) {
		return PlayerAccount.findAccount(PlayerUtil.getOnlineUUID(player.getName()));
	}

	/**
	 * Finds an account from the online player UUID
	 *
	 * @param uuid UUID of the player
	 * @return account of this player
	 */
	public static PlayerAccount findAccount(UUID uuid) {

		PlayerAccount account = accounts.get(uuid);

		if (account == null)
			account = new PlayerAccount(uuid);

		accounts.put(uuid, account);

		return account;
	}

	/**
	 * Removes account from hash map to reduce lag for a particular player.
	 * Their account still remains in file and can be retrieved whenever they rejoin
	 *
	 * @param player player to be removed
	 */
	public static void removeAccount(Player player) {
		removeAccount(player.getUniqueId());
	}

	/**
	 * Removes account from hash map to reduce lag for a particualr player UUID.
	 * Their account still remains in file and can be retrived whenever they rejoin
	 *
	 * @param id UUID of player to be removed
	 */
	public static void removeAccount(UUID id) {
		accounts.remove(id);
	}

}
