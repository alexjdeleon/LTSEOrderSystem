package com.ltse.orders.util;
/**
 * Utility methods for String processing
 * 
 * @author Alex De Leon
 *
 */
public class StringUtils {

	/**
	 * Checks if a given string is null or empty
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s) {
		return s == null || s.length() == 0;
	}

}
