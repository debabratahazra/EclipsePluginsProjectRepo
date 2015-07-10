/**
 * 
 */
package com.zealcore.se.core.ifw.assertions.internal;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author stch
 * 
 */
public class SearchMatcherTest extends TestCase {

	private static final double DOUBLE_0_1 = 0.1d;

	private static final float FLOAT_0_1 = 0.1f;

	private static final String GTEQ = ">=";

	private static final String LT = "<";

	@Test
	public void testFloats() {

		// Tests for public static boolean match(final Number value, final Number val1,
		// final String operator1, final Number val2, final String operator2, boolean wildcard)

		assertTrue("5.0f should be in the range of 3 to 10", 
				SearchMatcher.match(5.0f, 3.0f, 10.0f, false));
		assertTrue("1.0f should not fit in the range of 3 to 10 ", 
				SearchMatcher.match(1.0f , 3.0f, 10.0f, true));
		assertFalse("2.0f is not between 3 and 10", 
				SearchMatcher.match(2.0f, 3.0f, 10.0f, false));
		assertFalse("For wildcard 5.0f should be discarded because it comes between 5 to 10.", 
				SearchMatcher.match(5.0f, 3.0f, 10.0f, true));

		//Tests for public static boolean match(final Number value, final Number val,
		// final String operator1, boolean wildcard)

		assertFalse("Float value 0.1f can not be greater then 0.1f",
				SearchMatcher.match(0.1f, FLOAT_0_1, LT ,false));
		assertFalse("Float values should be ignored if condition 4.0f > 0.0f is satisfied for wildcard case.",
				SearchMatcher.match(4.0f, 0.0f, GTEQ, true));

	}

	@Test
	public void testDoubles() {
		assertTrue("Double value 5.0d should be in the range of 3 to 10",
				SearchMatcher.match(5.0d, 3.0d, 10.0d, false));
		assertTrue("Double value 1.0d should be considered for wildcard case.",
				SearchMatcher.match(1.0d , 3.0d, 10.0d, true));
		assertFalse("Double value 2.0d is not in the range of 3 to 10d.",
				SearchMatcher.match(2.0d, 3.0d, 10.0d, false));
		assertFalse("Double value in the range 3 to 10 should be ignored for wildcard.",
				SearchMatcher.match(5.0d, 3.0d, 10.0d, true));
		assertFalse("Double value 0.1d can not be greater than 0.1d",
				SearchMatcher.match(0.1d, DOUBLE_0_1, LT ,false));
		assertFalse("For wildcard case it should return false for double value 4.0 which is greater than 0",
				SearchMatcher.match(4.0d, 0.0d, GTEQ, true));

	}

	@Test
	public void testInts() {

		assertTrue("5 should be in the range of 3 to 6.",
				SearchMatcher.match(5, 3, 6, false));
		assertTrue("4 should be in the range of 1 to 20",
				SearchMatcher.match(4, 1, 20, false));
		assertFalse("3 can not be greater than 5.", 
				SearchMatcher.match(3, 5, 3, false));
		assertTrue("3 can not b greater than 4.",
				SearchMatcher.match(3, 3, 4, false));		
		assertTrue("0 should be equal to 0.",
				SearchMatcher.match(0, 0, GTEQ, false));
	}

	@Test
	public void testLongs() {

		assertTrue("5 should be in the range of 0 to 10.",
				SearchMatcher.match(5L, 0L, 10L, false));
		assertFalse("For wildcard 5 should be ignored for 0 to 10 range.",
				SearchMatcher.match(5L, 0L, 10L, true));
		assertFalse("5 is not greater than 45.",
				SearchMatcher.match(5L, 45, 0L, false ));
		assertTrue("Long value 0 should be equal to 0.",
				SearchMatcher.match(0L, 0, GTEQ, false));
		assertFalse("For wildcard case 1L == 1L should return false.",
				SearchMatcher.match(1L, 1L, GTEQ, true));
		assertTrue("Long value 5L is less than 10L.",
				SearchMatcher.match(5L, 10L, LT, false));
	}

	@Test
	public void testString() {
		final String testValue = "foobar";
		assertTrue("The Sring should match the expression.",
				SearchMatcher.match("fOo.*|bAr.*", testValue,false));
		assertTrue("The String should match the expression.",
				SearchMatcher.match("fOofoo.*|.*bAr.*", testValue,false));
		assertFalse("The String should not match the regular expression.",
				SearchMatcher.match("fOofoo.*|bAr.*", testValue,false));

	}

	@Test
	public void testStringNoRegexp() {
		final String concrete = "barfoo";
		final String testValue = concrete;
		assertTrue("Strings are not equal.",
				SearchMatcher.match(concrete, testValue, false));
		assertFalse("Strings barfoo and foobar are not equal.",
				SearchMatcher.match("foobar", testValue, false));
		assertTrue("For wildcard it should return true if strings are not matching.",
				SearchMatcher.match(concrete, "foobar", true));
	}

	@Test
	public void testRegExp() {

		// Matches a complete line that contains the word 'error'
		String regExpr = ".*error.*";
		String testValue = "Error fgdsf\t  ";
		assertTrue("String should match regular expression which contains it.",
				SearchMatcher.match(regExpr, testValue,false));

		// Matches a complete line that ends with the word 'error'
		regExpr = "^.*error$";
		testValue = " fgd\t  error";
		assertTrue("String should match regular expression if it ends with it.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = " fgd\t  error ";
		assertFalse("String should not match regular expression if it ends with it and a space.",
				SearchMatcher.match(regExpr, testValue,false));

		// Matches a complete line that starts with the word 'error'
		regExpr = "error.*";
		testValue = "error was found here.";
		assertTrue("String should match regular expression if it starts with it.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = "ErRor was found here.";
		assertTrue("String should match regular expression if it starts with case insensitive it.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = " error, nooot!\t.  error ";
		assertFalse("String should not match regular expression, if it startes with a space.",
				SearchMatcher.match(regExpr, testValue,false));

		regExpr = "^error.*$";
		testValue = "error was found here";
		assertTrue("String should match regular expression.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = "ErRor was found here";
		assertTrue("String should match regular expression starting with case insensitive it.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = " error, nooot!\t  error ";
		assertFalse("String should not match regular expression starting with it but a space before it.",
				SearchMatcher.match(regExpr, testValue,false));

		// Matches a complete line that does not contain the word ok
		regExpr = "((?!ok).)*";
		testValue = "ok was found here..";
		assertFalse("String containing a substring should not match regular expression.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = "OK was found here**";
		assertFalse("String containing a case sensitive substring should not match regular expression.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = "no o k here,* !";
		assertTrue("The string should match regular expression.",
				SearchMatcher.match(regExpr, testValue,true));

		regExpr = "^((?!ok)).*$";
		testValue = "ok was found here.";
		assertFalse("String containing a substring should not match regular expression.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = "OK was found here";
		assertFalse("String containing a case sensitive substring should not match regular expression.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = "no o k here, !";
		assertTrue("The string should match regular expression.",
				SearchMatcher.match(regExpr, testValue,true));

		// Matches a complete line that contains any digit number
		regExpr = ".*[0-9]+.*";
		testValue = "ok was found here-";
		assertFalse("The string should contain any digit.",
				SearchMatcher.match(regExpr, testValue,false));
		testValue = "errrocode = 579";
		assertTrue("The String containg digit should match regular expression.",
				SearchMatcher.match(regExpr, testValue,false));


		// Matches a line that contains any digit (^ means beginning of line and $ end of line)
		regExpr = "^.*[0-9]+.*$";
		testValue = "ok was found here";

		assertFalse("The String should not match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		assertTrue("The match method should return true if string does not match regular expression.",
				SearchMatcher.match(regExpr, testValue, true));
		testValue = "errrocode = 57";
		assertTrue("The string containing digit should match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));

		//Matches a line that does not contain any digit  
		regExpr = "^.*[\\D]+.*$";
		testValue = "ok was found here";

		assertTrue("The String should match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		assertFalse("The match method should return false if string matches regular expression.",
				SearchMatcher.match(regExpr, testValue, true));
		testValue = "57";
		assertFalse("The string containing digit should not match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));


		// Matches a complete line that contains an asterisk.
		// Backslash is needed since asterisk '*'is a special character
		regExpr = ".*\\*.*";
		testValue = "ok * found here ";
		assertTrue("The string containing * should match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		testValue = "ok search found her e";
		assertFalse("The string which does not contain * should not match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		assertTrue("The match method should return true if the string does not match for wildcard.",
				SearchMatcher.match(regExpr, testValue, true));

		regExpr = "^.*\\*.*$";
		testValue = "ok * found here";
		assertTrue("The string containing * should match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		testValue = "ok search found here";
		assertFalse("The string should not match regular expression if it contains *.",
				SearchMatcher.match(regExpr, testValue, false));

		// Matches a complete line that contains one of the specified words
		regExpr = ".*(apa|banan|(Status -)).*";
		testValue = "apa ";
		assertTrue("The String should match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		testValue = " banan ";
		assertTrue("The String should match the regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		testValue = "apa banan";
		assertTrue("The String should match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		testValue = "aappaa bbaannaann";
		assertFalse("The String should not match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));

		testValue = "aappaa bbaannaann Status";
		assertFalse("The String should not match regular expression.",
				SearchMatcher.match(regExpr, testValue, false));
		assertTrue("It should return true if string dose not match regular expression for wild card.",
				SearchMatcher.match(regExpr, testValue, true));

		testValue = "aappaa bbaannaann Status -";
		assertTrue("String should match regular expression.",
				SearchMatcher.match(regExpr, testValue,false));

	}
}
