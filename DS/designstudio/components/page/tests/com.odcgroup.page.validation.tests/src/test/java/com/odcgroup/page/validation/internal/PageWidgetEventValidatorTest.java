package com.odcgroup.page.validation.internal;

import org.junit.Assert;
import org.junit.Test;

public class PageWidgetEventValidatorTest {

	@Test
	public void testParamHasHasValidValue_returnsTrue_WhenNoParamIsPresent() throws Exception {
		String testParamAndValue = "";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertTrue(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsTrue_WhenParamIsNull() throws Exception {
		String testParamAndValue = null;
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertTrue(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsTrue_WhenFlowActionHasValidValue() throws Exception {
		String testParamAndValue = "flow-action=xxx";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertTrue(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsTrue_WhenFlowActionAndFlowChangeHaveValidValues() throws Exception {
		String testParamAndValue = "flow-action=yyy;flow-change=yyy";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertTrue(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsFalse_WhenOnlyFlowChangeSpecified() throws Exception {
		String testParamAndValue = "flow-change=yyy";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertFalse(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsFalse_WhenOnlyFlowChangeSpecifiedWithNoValue() throws Exception {
		String testParamAndValue = "flow-change=";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertFalse(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsFalse_WhenOnlyFlowChangeSpecifiedWithNoValueAndSemiColonPresent() throws Exception {
		String testParamAndValue = "flow-change=;";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertFalse(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsFalse_WhenFlowActionAndFlowChangeSeparatorIsMissing() throws Exception {
		String testParamAndValue = "flow-action=yyy flow-change=yyy";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertFalse(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsFalse_WhenFlowActionValueIsMissing() throws Exception {
		String testParamAndValue = "flow-action=";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertFalse(validParamAndValue);
	}
	@Test
	public void testParamHasHasValidValue_returnsFalse_WhenFlowActionValueIsMissingAndSemiColonIsPresent() throws Exception {
		String testParamAndValue = "flow-action=;";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertFalse(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsFalse_WhenInvalidTextEntered() throws Exception {
		String testParamAndValue = "sdfsdfjsldkfjlsdksd6546sd5f46";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertFalse(validParamAndValue);
	}
	
	@Test
	public void testParamHasHasValidValue_returnsFalse_WhenFlowActionNotPresent() throws Exception {
		String testParamAndValue = "my-nice-flow=aaa";
		
		boolean validParamAndValue = PageWidgetEventValidator.paramHasValidValue(testParamAndValue);
		Assert.assertFalse(validParamAndValue);
	}
}
