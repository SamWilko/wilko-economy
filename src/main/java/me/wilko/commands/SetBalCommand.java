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
public final class SetBalCommand extends SimpleCommand {

	public SetBalCommand() {
		super("setbal");

		setMinArguments(2);
		setDescription("Sets the balance of this player to the given amount.");
		setUsage("/setbal <name> <amount>");

		setTellPrefix("");

		// Only OPs or players with this permission can run this command
		setPermission("economy.setbal");
		setPermissionMessage("You do not have permission to use this command!");
	}

	@Override
	protected void onCommand() {

		// Checks that the set amount is a number
		if (!NumberUtil.isNumber(args[1])) {
			tellError("'{1}' is not a valid number!");
			return;
		}

		// gets the set amount rounded to two decimal places
		double setAmount = Double.parseDouble(NumberUtil.formatDouble(Double.parseDouble(args[1])));

		// Checks that the set amount is greater than 0
		if (setAmount < 0) {
			tellError("Cannot set a negative balance!");
			return;
		}

		// gets the target player
		OfflinePlayer target = PlayerUtil.getPlayer(args[0]);

		// Checks that the target player exists and has played the server before
		if (target == null) {
			tellError("Player '{0}' does not exist!");
			return;
		}

		// Gets the target player's account
		PlayerAccount account = PlayerAccount.findAccount(target);

		// Sets new balance for target player
		account.setBalance(setAmount);

		// Only show this message if either the console runs the command or a player is setting the balance of another
		// player.
		if (!this.isPlayer() || !getPlayer().getName().equals(target.getName())) {

			tellSuccess("Balance of " + target.getName() + " has been set to " + Economy.CURRENCY
					+ NumberUtil.formatDouble(setAmount));
		}

		// Sends a message to the target player that their balance has been changed only if they are online.
		Player receiver = PlayerUtil.isOnline(target);
		if (receiver != null) {

			Common.tell(receiver, "&8&l[&9&l!&8&l] &7" +
					"Your balance has been set to " + Economy.CURRENCY + NumberUtil.formatDouble(setAmount));
		}

	}
}
