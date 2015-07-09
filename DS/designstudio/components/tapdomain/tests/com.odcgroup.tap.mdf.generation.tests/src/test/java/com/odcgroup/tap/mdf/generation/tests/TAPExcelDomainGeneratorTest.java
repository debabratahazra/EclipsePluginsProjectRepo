package com.odcgroup.tap.mdf.generation.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Test;

import com.odcgroup.tap.mdf.generation.xls.TAPExcelDomainGenerator;

public class TAPExcelDomainGeneratorTest {
	
	private static Method method;
	private static final TAPExcelDomainGenerator DUMMY_EXCEL_DOMAIN_GENERATOR = new TAPExcelDomainGenerator(null);
	
	@Test
	public void testInvalidSheetName_DS5519() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals("abc", callCheckEntityName("abc"));
		Assert.assertEquals("duplicatedName", callCheckEntityName("duplicatedName"));
		Assert.assertEquals("duplicatedName(0)", callCheckEntityName("duplicatedName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryVery...", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(0)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(1)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(2)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(3)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(4)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(5)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(6)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(7)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(8)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVeryV...(9)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
		Assert.assertEquals("VeryVeryVeryVeryVeryVery...(10)", callCheckEntityName("VeryVeryVeryVeryVeryVeryVeryVeryLongName"));
	}

	private String callCheckEntityName(String string) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (method == null) {
			method = TAPExcelDomainGenerator.class.getDeclaredMethod("checkEntityName", String.class);
			method.setAccessible(true);
		}
		return (String) method.invoke(DUMMY_EXCEL_DOMAIN_GENERATOR, string);
	}

}
