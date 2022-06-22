package me.wilko.commands;

import me.wilko.economy.Economy;
import me.wilko.economy.PlayerAccount;
import me.wilko.util.NumberUtil;
import me.wilko.util.PlayerUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class GiveCommand extends SimpleCommand {

	public GiveCommand() {
		super("give");

		setMinArguments(2);
		setDescription("Give money to another player!");
		setUsage("/give <name> <amount>");

		// Anyone can use this command
		setPermission(null);

		setTellPrefix("");
	}

	@Override
	protected void onCommand() {

		// The console cannot give money as it has no account
		checkConsole();

		Player sender = getPlayer();

		// Player cannot give money to themselves
		if (sender.getName().equalsIgnoreCase(args[0])) {
			tellError("You cannot send money to yourself!");
			return;
		}

		// Retrives the target player
		OfflinePlayer targetPlayer = PlayerUtil.getPlayer(args[0]);

		// Error if the target player does not exist or has never joined the server
		if (targetPlayer == null) {
			tellError("Player '{0}' does not exist!");
			return;
		}

		// Checks that the user entered a valid number as the give amount
		if (!NumberUtil.isNumber(args[1])) {
			tellError("{1} is not a valid number!");
			return;
		}

		// Rounds the give amount to two decimal places
		double giveAmount = Double.parseDouble(NumberUtil.formatDouble(Double.parseDouble(args[1])));

		// Checks that the amount isn't 0 or a negative number
		if (giveAmount < 0.01) {
			tellError("You must send at least " + Economy.CURRENCY + "0.01!");
			return;
		}

		// Retrieves the account of the sender
		PlayerAccount senderAccount = PlayerAccount.findAccount(sender);

		// Checks that the sender has enough money to make this transaction
		if (senderAccount.getBalance() - giveAmount < 0) {
			tellError("You cannot afford this!");
			return;
		}

		// Retrieves the account for the target player
		PlayerAccount targetAccount = PlayerAccount.findAccount(targetPlayer);

		// Making the transaction
		senderAccount.setBalance(senderAccount.getBalance() - giveAmount);
		targetAccount.setBalance(targetAccount.getBalance() + giveAmount);

		// Tells the sender that they gave the money to the target player
		tellSuccess("You gave " + targetPlayer.getName() + " " + Economy.CURRENCY
				+ NumberUtil.formatDouble(giveAmount) + "!");

		// Checks if the target player is online and if they are, send them a message saying they received the money
		// from the sender
		Player onlinePlayer = PlayerUtil.isOnline(targetPlayer);
		if (onlinePlayer != null) {
			Common.tell(onlinePlayer, "&8&l[&9&l!&8&l] &7" +
					"You received " + Economy.CURRENCY + NumberUtil.formatDouble(giveAmount)
					+ " from " + sender.getName());
		}
	}
}
