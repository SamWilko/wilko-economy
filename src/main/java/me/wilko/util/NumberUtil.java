package me.wilko.util;

public final class NumberUtil {

	/**
	 * Checks whether a string value is a number or not
	 *
	 * @param value value to check
	 * @return true if string is a number, false if it is not
	 */
	public static boolean isNumber(String value) {
		try {
			Double.valueOf(value);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Formats a double value into 2 decimal places
	 *
	 * @param val double to format
	 * @return formatted double in string
	 */
	public static String formatDouble(double val) {

		if (val % 1 == 0) {
			return String.valueOf((int) val);
		}

		return String.format("%.2f", val);
	}

}
