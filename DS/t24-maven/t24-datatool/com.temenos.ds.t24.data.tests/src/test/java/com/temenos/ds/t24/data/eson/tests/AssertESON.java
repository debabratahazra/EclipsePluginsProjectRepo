package com.temenos.ds.t24.data.eson.tests;

import java.util.Iterator;

import javax.jms.IllegalStateException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;

import com.temenos.ds.t24.data.eson.ESONDataModel;
import com.temenos.ds.t24.data.eson.ESONParser;

/**
 * This class helps asserting ESON ignoring formatting differences
 * @author yandenmatten
 */
public class AssertESON {
	
	private static String[] T24_AUDIT_FIELDS = {"OVERRIDE", "RECORD.STATUS", "CURR.NO", "INPUTTER", "DATE.TIME"};

	public static void assertEqualsNormilizedESON(String expected, String actual) {
		assertEqualsNormilizedESON("", expected, actual);
	}
	
	public static void assertEqualsNormilizedESON(String message, String expected, String actual) {
		Assert.assertEquals(
				safeMessage(message),
				normalizeESON(expected), 
				normalizeESON(actual));
	}

	public static void assertEqualsNormilizedESON(String expected, ESONDataModel actual) {
		assertEqualsNormilizedESON("", expected, actual);
	}
	
	public static void assertEqualsNormilizedESON(String message, String expected, ESONDataModel actual) {
		Assert.assertEquals(
				safeMessage(message),
				normalizeESON(expected), 
				actual.toString());
	}
	
	public static void assertEqualsIgnoreT24Audit(String message, String expected, String actual) {
		Assert.assertEquals(
				safeMessage(message),
				removeT24Audit(expected),
				removeT24Audit(actual));
	}
	
	private static String removeT24Audit(String eson) {
		try {
			ESONDataModel esonDataModel = new ESONParser(eson).parse();
			for (Iterator<ESONDataModel.Feature> iter = esonDataModel.root.features.iterator(); iter.hasNext(); ) {
				if (ArrayUtils.contains(T24_AUDIT_FIELDS, iter.next().eFeature)) {
					iter.remove();
				}
			}
			return esonDataModel.toString();
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}

	private static String normalizeESON(String eson) {
		try {
			return new ESONParser(eson).parse().toString();
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String safeMessage(String message) {
		if (message == null) {
			message = "";
		} else if (!message.endsWith(" ")) {
			message = message + " ";
		}
		return message + "(This assert compare normalized ESON)";
	}
	

}
