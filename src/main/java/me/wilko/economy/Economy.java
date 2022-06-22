package me.wilko.economy;

import me.wilko.listener.PlayerListener;
import org.mineacademy.fo.plugin.SimplePlugin;

public class Economy extends SimplePlugin {

	private static Economy instance;

	/**
	 * Currency symbol to be used in messages
	 */
	public final static String CURRENCY = "£";

	@Override
	protected void onPluginStart() {

		instance = this;

		// Registers Listeners
		registerEvents(new PlayerListener());
	}

	/**
	 * Gets the instance of this plugin
	 *
	 * @return plugin instance
	 */
	public static Economy getInstance() {
		return instance;
	}
}
