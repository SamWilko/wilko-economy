package me.wilko.commands;

import me.wilko.economy.Economy;
import me.wilko.economy.PlayerAccount;
import me.wilko.util.NumberUtil;
import me.wilko.util.PlayerUtil;
import org.bukkit.OfflinePlayer;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class BalCommand extends SimpleCommand {

	public BalCommand() {
		super("bal");

		// This command can be run by anyone
		setPermission(null);

		setTellPrefix("");
	}

	@Override
	protected String[] getMultilineUsageMessage() {
		return new String[]{
				"/bal - Tells you the balance of your account",
				"/bal <name> - Tells you the balance of another player"
		};
	}

	@Override
	protected void onCommand() {

		// Console cannot run this command
		checkConsole();

		// Command usage is either /bal or /bal <name>
		if (args.length > 1) {
			tellError("Too many arguments!");
			return;
		}


		// If the player does /bal with the intention to check their own balance
		if (args.length == 0) {

			// Retrieves the account associated with the player running this command
			PlayerAccount account = PlayerAccount.findAccount(getPlayer());

			// Outputs the players balance
			tellInfo("Balance: " + Economy.CURRENCY + NumberUtil.formatDouble(account.getBalance()));
		}

		// If the player types /bal <name>
		else {

			// Retrieves the target player
			OfflinePlayer targetPlayer = PlayerUtil.getPlayer(args[0]);

			// Player with this username does not exist or has never joined the server
			if (targetPlayer == null) {
				tellError("Player '{0}' does not exist!");
				return;
			}

			// Retrieves account for target player
			PlayerAccount account = PlayerAccount.findAccount(targetPlayer);

			// Outputs the balance of the target player to the command sender
			tellInfo(targetPlayer.getName() + "'s Balance: " + Economy.CURRENCY
					+ NumberUtil.formatDouble(account.getBalance()));
		}
	}
}
