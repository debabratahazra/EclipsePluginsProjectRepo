package com.temenos.ds.t24.data.eson;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author yandenmatten
 */
public class ESONTokenizerTest {
	
	@Test
	public void testSimpleEson() {
		ESONTokenizer esonTokenizer = new ESONTokenizer(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {\n" +
				"\tMNEMONIC: \"BKAM'EXGVA\"\n" +
				"   REL.CUSTOMER: \"\"\n" +
				"}");
		
		assertExpectedTokensFound("Token mismatch", new String[] {
				"import",
				"t24.applications.*", 
				"CUSTOMER" ,
				"{",
				"MNEMONIC", 
				":", 
				"\"",
				"BKAM'EXGVA",
				"\"",
				"REL.CUSTOMER",
				":",
				"\"",
				"",
				"\"",
				"}"
				}, esonTokenizer.tokens);
	}
	
	@Test
	public void testSimpleSingleQuoteEson() {
		ESONTokenizer esonTokenizer = new ESONTokenizer(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {\n" +
				"\tMNEMONIC: 'BKAM\"EXGVA'\n" +
				"   REL.CUSTOMER: ''\n" +
				"}");
		
		assertExpectedTokensFound("Token mismatch", new String[] {
				"import",
				"t24.applications.*", 
				"CUSTOMER" ,
				"{",
				"MNEMONIC", 
				":", 
				"'",
				"BKAM\"EXGVA",
				"'",
				"REL.CUSTOMER",
				":",
				"'",
				"",
				"'",
				"}"
				}, esonTokenizer.tokens);
	}
	
	@Test
	public void testMultiValueSubValueEson() {
		ESONTokenizer esonTokenizer = new ESONTokenizer(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {" + 
					"MNEMONIC: \"BKAMEXGVA\"" +
					"SHORT.NAME: [" +
						"CUSTOMER__SHORT_NAME {" +
							"SHORT.NAME: \"American Express Geneva\" " +
						"}" +
					"]" +
				"}");
		
		assertExpectedTokensFound("Token mismatch", new String[] {
				"import",
				"t24.applications.*",
				"CUSTOMER",
				"{",
				"MNEMONIC",
				":",
				"\"",
				"BKAMEXGVA",
				"\"",
				"SHORT.NAME",
				":",
				"[",
				"CUSTOMER__SHORT_NAME",
				"{",
				"SHORT.NAME",
				":",
				"\"",
				"American Express Geneva",
				"\"",
				"}",
				"]",
				"}"
			}, esonTokenizer.tokens);
	}

	@Test
	public void testSingleCommentLineInEson() {
		ESONTokenizer esonTokenizer = new ESONTokenizer(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {" + 
					"//MNEMONIC: \"BKAMEXGVA\"\n" +
					"SHORT.NAME: [" +
						"CUSTOMER__SHORT_NAME {" +
							"SHORT.NAME: \"American Express Geneva\" " +
							"}" +
					"]" +
				"}");
		
		assertExpectedTokensFound("Token mismatch", new String[] {
				"import",
				"t24.applications.*",
				"CUSTOMER",
				"{",
				"SHORT.NAME",
				":",
				"[",
				"CUSTOMER__SHORT_NAME",
				"{",
				"SHORT.NAME",
				":",
				"\"",
				"American Express Geneva",
				"\"",
				"}",
				"]",
				"}"
			}, esonTokenizer.tokens);
	}

	@Test
	public void testMultiLineCommentInEson() {
		ESONTokenizer esonTokenizer = new ESONTokenizer(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {" + 
					"MNEMONIC: \"BKAMEXGVA\"" +
					"/*SHORT.NAME: [" +
						"CUSTOMER__SHORT_NAME {" +
							"SHORT.NAME: \"American Express Geneva\" +" +
							"}" +
					"]*/" +
				"}");
		
		assertExpectedTokensFound("Token mismatch", new String[] {
				"import",
				"t24.applications.*",
				"CUSTOMER",
				"{",
				"MNEMONIC",
				":",
				"\"",
				"BKAMEXGVA",
				"\"",
				"}"
			}, esonTokenizer.tokens);
	}


	@Test(expected=IllegalStateException.class)
	public void testNoEndingQuote() {
		new ESONTokenizer(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {\n" +
				"\tMNEMONIC: 'BKAM\"EXGVA\n" +
				"}");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testIllegalCharInString() {
		new ESONTokenizer(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {\n" +
				"\tMNEMONIC: 'abc\ndef'" +
				"}");
	}

	@Test(expected=IllegalStateException.class)
	public void testIllegalLastCharEson() {
		new ESONTokenizer(
				"import t24.applications.*\n" +
				"\n" +
				"CUSTOMER {\n" +
				"\tMNEMONIC: \"BKAM'EXGVA\"\n" +
				"   REL.CUSTOMER: \"\"\n" +
				"}/");
	}

	private void assertExpectedTokensFound(String message, String[] expected, String[] actual) {
		StringBuilder errorMessage = new StringBuilder();
		if (expected.length != actual.length) {
			errorMessage.append("The number of token is not equals " +
					"(expected: " + expected.length + ", actual: " + actual.length + ")\n");
		}
		for (int i=0; i<expected.length; i++) {
			if (i < actual.length) {
				if (!expected[i].equals(actual[i])) {
					errorMessage.append("The expected token with index " + i + " and value " + expected[i] + " doesn't match the actual value at the same index: " + actual[i] + "\n");
				}
			} else {
				errorMessage.append("The expected token with index " + i + " and value + " + expected[i] + " has not corresponding token in actual. (Further diff ignored.)\n");
				break;
			}
		}
		if (errorMessage.length() != 0) {
			Assert.fail(message + "\n" + errorMessage.toString() + "Tokens found: " + StringUtils.join(actual, ", "));
		}
	}


}
