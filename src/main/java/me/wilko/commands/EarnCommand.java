package me.wilko.commands;

import me.wilko.economy.Economy;
import me.wilko.economy.PlayerAccount;
import me.wilko.util.NumberUtil;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@AutoRegister
public final class EarnCommand extends SimpleCommand {

	public EarnCommand() {
		super("earn");

		setMinArguments(0);

		// Anyone can use this command
		setPermission(null);

		setDescription("Earn some money!");

		setTellPrefix("");

		// 60 second command cooldown
		setCooldown(60, TimeUnit.SECONDS);
	}

	@Override
	protected void onCommand() {

		// Console cannot run this command
		checkConsole();

		// Retrieves account for command sender
		PlayerAccount account = PlayerAccount.findAccount(getPlayer());

		// Calculates a random amount to earn between 1 and 5
		int addAmount = new Random().nextInt(5) + 1;

		// Adds the earn amount to their account
		account.setBalance(account.getBalance() + addAmount);

		// Tell the player how much they earned
		tellSuccess("You earned " + Economy.CURRENCY + NumberUtil.formatDouble(addAmount) + "!");
	}
}
