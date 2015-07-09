package com.odcgroup.aaa.generation.gateway.validator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.aaa.generation.gateway.line.ATT;
import com.odcgroup.aaa.generation.gateway.line.CMD;
import com.odcgroup.aaa.generation.gateway.line.CMDType;
import com.odcgroup.aaa.generation.gateway.line.DAT;
import com.odcgroup.aaa.generation.gateway.line.value.DATStringValue;
import com.odcgroup.aaa.generation.gateway.line.value.DATValue;
import com.odcgroup.aaa.generation.gateway.writer.Settings;


public class GatewayValidatorTest {

	@Test
	public void testCMDTypeUndefined() {
		CMD cmd = new CMD(null, "someTable");
		List<GatewayValidationException> exceptions = new ArrayList<GatewayValidationException>();
		try {
			new GatewayValidator(new Settings()).validate(cmd);
		} catch (GatewayMultiValidationException e) {
			exceptions = e.getValidationExceptions();
		}
		Assert.assertEquals("Wrong number of validation exception", 2, exceptions.size());
		Assert.assertEquals("Wrong error message", GatewayValidator.CMD_TYPE_UNDEFINED, exceptions.get(0).getMessage());
		Assert.assertEquals("Wrong error message", GatewayValidator.NO_ATT_IN_CMD, exceptions.get(1).getMessage());
	}
	
	@Test
	public void testCMDTableNameUndefined() {
		CMD cmd = new CMD(CMDType.INSUPDDEF, null);
		List<GatewayValidationException> exceptions = new ArrayList<GatewayValidationException>();
		try {
			new GatewayValidator(new Settings()).validate(cmd);
		} catch (GatewayMultiValidationException e) {
			exceptions = e.getValidationExceptions();
		}
		Assert.assertEquals("Wrong number of validation exception", 2, exceptions.size());
		Assert.assertEquals("Wrong error message", GatewayValidator.CMD_TABLE_NAME_UNDEFINED, exceptions.get(0).getMessage());
		Assert.assertEquals("Wrong error message", GatewayValidator.NO_ATT_IN_CMD, exceptions.get(1).getMessage());
	}
	
	@Test
	public void testNoColumnDefinedInATT() {
		// Given
		CMD cmd = new CMD(CMDType.INSUPDDEF, null);
		ATT att = new ATT(new String[]{});
		cmd.setATT(att);

		
		// When
		List<GatewayValidationException> exceptions = new ArrayList<GatewayValidationException>();
		try {
			new GatewayValidator(new Settings()).validate(cmd);
		} catch (GatewayMultiValidationException e) {
			exceptions = e.getValidationExceptions();
		}
		
		// Then
		Assert.assertEquals("Wrong number of validation exception", 2, exceptions.size());
		Assert.assertEquals("Wrong error message", GatewayValidator.CMD_TABLE_NAME_UNDEFINED, exceptions.get(0).getMessage());
		Assert.assertEquals("Wrong error message", GatewayValidator.NO_COLUMN_DEFINED_IN_ATT, exceptions.get(1).getMessage());
	}
	
	@Test
	public void testWrongNumberOfColumns() {
		// Given
		CMD cmd = new CMD(CMDType.INSUPDDEF, "someTableName");
		ATT att = new ATT(new String[]{"col1"});
		cmd.setATT(att);
		DAT dat = new DAT(new DATValue[] {new DATStringValue("test"), new DATStringValue("test")});
		att.addDAT(dat);

		// When
		List<GatewayValidationException> exceptions = new ArrayList<GatewayValidationException>();
		try {
			new GatewayValidator(new Settings()).validate(cmd);
		} catch (GatewayMultiValidationException e) {
			exceptions = e.getValidationExceptions();
		}
		
		// Then
		Assert.assertEquals("Wrong number of validation exception", 1, exceptions.size());
		Assert.assertTrue("Wrong error message", exceptions.get(0).getMessage().startsWith(GatewayValidator.WRONG_NUMBER_OF_COLUMNS));
	}
	
	@Test
	public void testNoDataDefinedInDAT() {
		// Given
		CMD cmd = new CMD(CMDType.INSUPDDEF, "someTable");
		ATT att = new ATT(new String[]{"col1", "col2"});
		cmd.setATT(att);
		DAT dat = new DAT(new DATValue[] {});
		att.addDAT(dat);

		// When
		List<GatewayValidationException> exceptions = new ArrayList<GatewayValidationException>();
		try {
			new GatewayValidator(new Settings()).validate(cmd);
		} catch (GatewayMultiValidationException e) {
			exceptions = e.getValidationExceptions();
		}
		
		// Then
		Assert.assertEquals("Wrong number of validation exception", 2, exceptions.size());
		Assert.assertEquals("Wrong error message", GatewayValidator.NO_DATA_DEFINED_IN_DAT, exceptions.get(0).getMessage());
	}
	
	@Test
	public void testDataContainsSeparator_withSpace() {
		// Given
		CMD cmd = new CMD(CMDType.INSUPDDEF, "someTableName");
		ATT att = new ATT(new String[]{"col1", "col2"});
		cmd.setATT(att);
		DAT dat = new DAT(new DATValue[] {new DATStringValue("te;st"), new DATStringValue("test")});
		att.addDAT(dat);

		// When
		List<GatewayValidationException> exceptions = new ArrayList<GatewayValidationException>();
		try {
			new GatewayValidator(new Settings()).validate(cmd);
		} catch (GatewayMultiValidationException e) {
			exceptions = e.getValidationExceptions();
		}
		
		// Then
		Assert.assertEquals("Wrong number of validation exception", 1, exceptions.size());
		Assert.assertTrue("Wrong error message", exceptions.get(0).getMessage().startsWith(GatewayValidator.DATA_CONTAINS_SEPARATOR));
	}

	@Test
	public void testDataContainsSeparator_withQuote() {
		// Given
		Settings settings = new Settings();
		settings.setQuote('~');
		
		CMD cmd = new CMD(CMDType.INSUPDDEF, "someTableName");
		ATT att = new ATT(new String[]{"col1", "col2"});
		cmd.setATT(att);
		DAT dat = new DAT(new DATValue[] {new DATStringValue("te~st"), new DATStringValue("test")});
		att.addDAT(dat);

		// When
		List<GatewayValidationException> exceptions = new ArrayList<GatewayValidationException>();
		try {
			new GatewayValidator(settings).validate(cmd);
		} catch (GatewayMultiValidationException e) {
			exceptions = e.getValidationExceptions();
		}
		
		// Then
		Assert.assertEquals("Wrong number of validation exception", 1, exceptions.size());
		Assert.assertTrue("Wrong error message", exceptions.get(0).getMessage().startsWith(GatewayValidator.DATA_CONTAINS_QUOTE));
	}
	
}
