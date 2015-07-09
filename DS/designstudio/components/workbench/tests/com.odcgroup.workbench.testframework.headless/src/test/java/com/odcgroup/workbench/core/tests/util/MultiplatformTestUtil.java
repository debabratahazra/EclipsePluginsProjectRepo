package com.odcgroup.workbench.core.tests.util;

import org.junit.Assert;

/**
 * Utils for more easily writing cross platform (Win/*NIX) tests.
 *
 * @author Michael Vorburger
 */
public class MultiplatformTestUtil {

	/**
	 * Asserts that expected and actual are equals,
	 * but ignores platform line ending differences.
	 */
	public static void assertEqualsIgnoringEOL(String message, String expected, String actual) {
		Assert.assertEquals(message, neutralizeEOL(expected), neutralizeEOL(actual));
	}

	public static void assertEqualsIgnoringEOL(String expected, String actual) {
		Assert.assertEquals(neutralizeEOL(expected), neutralizeEOL(actual));
	}

	public static String neutralizeEOL(String s) {
		// Could use also \\b to handle trailing whitespace at line end..
		// Beware, the order of the replaceAll() is significant here
		s = s.replaceAll("\\s+", " ");
		return s.replaceAll("\\r\\n", "\r").replaceAll("\\n", "\r");
	}
	
}
