package com.odcgroup.integrationfwk.utils.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * Conducting unit tests on the available methods of {@link Utils} class.
 * 
 * @author sbharathraja
 * 
 */
public class UtilsTest {

    @Test
    public void testContainsInvalidChar() {
	assertTrue(Utils.containsInvalidChar(" "));
	assertTrue(Utils.containsInvalidChar("123"));
	assertTrue(Utils.containsInvalidChar("!Foo@"));
	assertTrue(Utils.containsInvalidChar("******"));
	assertTrue(Utils.containsInvalidChar("123Foo"));
	assertTrue(Utils.containsInvalidChar("Foo.Bar"));
	assertTrue(Utils.containsInvalidChar("Foo Bar"));
	assertTrue(Utils.containsInvalidChar("FooBar "));
	assertTrue(Utils.containsInvalidChar("Foo	Bar"));
	assertTrue(Utils.containsInvalidChar("Foo$Bar"));

	assertFalse(Utils.containsInvalidChar("Foo"));
	assertFalse(Utils.containsInvalidChar("Foo_Bar"));
	assertFalse(Utils.containsInvalidChar("_FooBar"));
	assertFalse(Utils.containsInvalidChar("FooBar1"));
	assertFalse(Utils.containsInvalidChar("Foo1Foo"));
	assertFalse(Utils.containsInvalidChar("enquiryNumber"));
    }

    @Test
    public void testContainsValidChar() {
	assertFalse(Utils.containsValidChar(" "));
	assertFalse(Utils.containsValidChar("!Foo@"));
	assertFalse(Utils.containsValidChar("******"));
	assertFalse(Utils.containsValidChar("Foo.Bar"));
	assertFalse(Utils.containsValidChar("Foo Bar"));
	assertFalse(Utils.containsValidChar("FooBar "));
	assertFalse(Utils.containsValidChar("Foo	Bar"));
	assertFalse(Utils.containsValidChar("Foo$Bar"));

	assertTrue(Utils.containsValidChar("Foo"));
	assertFalse(Utils.containsValidChar("Foo_Bar"));
	assertFalse(Utils.containsValidChar("_FooBar"));
	assertTrue(Utils.containsValidChar("FooBar1"));
	assertTrue(Utils.containsValidChar("Foo1Foo"));
	assertTrue(Utils.containsValidChar("foobar"));
	assertTrue(Utils.containsValidChar("foo"));
	assertTrue(Utils.containsValidChar("enquiryNumber"));
    }

    @Test
    public void testConvertToDoubleQuotedString() {
	assertTrue(Utils.convertToDoubleQuotedString(null).length() == 0);
	assertTrue(Utils.convertToDoubleQuotedString("").equalsIgnoreCase(
		"\"\""));
	assertTrue(Utils.convertToDoubleQuotedString("Foo").equalsIgnoreCase(
		"\"Foo\""));
	assertTrue(Utils.convertToDoubleQuotedString("Foo.Bar")
		.equalsIgnoreCase("\"Foo.Bar\""));
	assertTrue(Utils.convertToDoubleQuotedString("Foo'").equalsIgnoreCase(
		"\"Foo'\""));
	// cases for testing the value which already enclosed with double quotes
	assertTrue(Utils.convertToDoubleQuotedString("\"Foo\"")
		.equalsIgnoreCase("\"Foo\""));
	assertTrue(Utils.convertToDoubleQuotedString("\"Foo\"Bar\"")
		.equalsIgnoreCase("\"Foo\"Bar\""));
    }

    @Test
    public void testConvertVersionToApplication() {
	assertTrue(Utils.convertVersionToApplication(null).equals(""));
	assertTrue(Utils.convertVersionToApplication("").equals(""));
	assertTrue(Utils.convertVersionToApplication("Foo").equals("Foo"));
	assertTrue(Utils.convertVersionToApplication("Foo,").equals("Foo"));
	assertTrue(Utils.convertVersionToApplication("Foo,Bar").equals("Foo"));
	assertTrue(Utils.convertVersionToApplication("Foo, Bar").equals("Foo"));
	assertTrue(Utils.convertVersionToApplication(
		"Foo.Bar,Foo.FooBar,Foo.FooBarBar,").equals("Foo.Bar"));
    }

    @Test
    public void testGetSchemaName() {
	assertNull(Utils.getSchemaName(null));
	assertTrue(Utils.getSchemaName("").equals(""));
	assertTrue(Utils.getSchemaName("  ").equals("  "));
	assertTrue(Utils.getSchemaName(".").equals("."));
	assertTrue(Utils.getSchemaName("..").equals(".."));
	assertTrue(Utils.getSchemaName(".......").equals("......."));

	// not contains . symbol
	assertTrue(Utils.getSchemaName("FOOBAR").equals("foobar"));
	assertTrue(Utils.getSchemaName("@ID").equals("id"));
	assertTrue(Utils.getSchemaName("~`!@#$%^&+{}|\\/?,<>*+-_").equals("_"));

	// . symbol at starting of the string
	assertTrue(Utils.getSchemaName(".FOOBAR").equals("Foobar"));
	// . symbol at end of the string
	assertTrue(Utils.getSchemaName("FOOBAR.").equals("foobar"));
	// . symbol in between of each and every character
	assertTrue(Utils.getSchemaName(".F.O.O.B.A.R").equals("FOOBAR"));
	assertTrue(Utils.getSchemaName("F.O.O.B.A.R").equals("fOOBAR"));

	assertTrue(Utils.getSchemaName("FOO.BAR").equals("fooBar"));
	assertTrue(Utils.getSchemaName("FOO.BAR FOOBAR").equals("fooBarfoobar"));
	assertTrue(Utils.getSchemaName("Foo.#Bar_FooBar").equals(
		"foobar_foobar"));
	assertTrue(Utils.getSchemaName("1Foo.Bar").equals("fooBar"));
	assertTrue(Utils.getSchemaName("Foo.Bar1").equals("fooBar1"));
	assertTrue(Utils.getSchemaName("Foo.Bar$Bar").equals("fooBarbar"));

	// test to some odd field names
	assertTrue(Utils.getSchemaName("HDR.1.040..078").equals("hdr1040078"));
	assertTrue(Utils.getSchemaName("HDR....1.0....40..078").equals(
		"hdr1040078"));
    }

    @Test
    public void testRemoveFileExtention() {
	try {
	    Utils.removeFileExtention("", "");
	    fail("Illegal Argument exception should be thrown!");
	} catch (IllegalArgumentException e) {
	    assertTrue(true);
	}
	try {
	    Utils.removeFileExtention("Foo.event", "eve");
	    fail("Illegal Argument exception should be thrown!");
	} catch (IllegalArgumentException e) {
	    assertTrue(true);
	}
	try {
	    Utils.removeFileExtention("Foo.event", "Event");
	    fail("Illegal Argument exception should be thrown!");
	} catch (IllegalArgumentException e) {
	    assertTrue(true);
	}
	assertTrue(Utils.removeFileExtention("Foo.event", "event")
		.equals("Foo"));
    }

}
