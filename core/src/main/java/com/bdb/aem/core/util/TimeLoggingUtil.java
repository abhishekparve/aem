package com.bdb.aem.core.util;

/**
 * The Class TimeLoggingUtil.
 */
public class TimeLoggingUtil {

	/**
	 * Instantiates a new time logging util.
	 */
	TimeLoggingUtil() {
	}

	/**
	 * Log method time.
	 *
	 * @param lStartTime the l start time
	 * @return the long
	 */
	public static long logMethodTime(long lStartTime) {
		return (System.nanoTime() - lStartTime) / 1000000;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public static long getStartTime() {
		return System.nanoTime();
	}
}
