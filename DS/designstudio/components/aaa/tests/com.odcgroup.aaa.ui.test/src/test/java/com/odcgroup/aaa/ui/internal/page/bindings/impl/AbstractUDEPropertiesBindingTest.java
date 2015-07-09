package com.odcgroup.aaa.ui.internal.page.bindings.impl;

import org.junit.Assert;
import org.junit.Test;

public class AbstractUDEPropertiesBindingTest {

	@Test
	public void testNullSafeTrimmedString() {
		// Given
		AbstractUDEPropertiesBinding test = createMockAbstractBinding();
		
		// Then
		Assert.assertEquals("Should return the value", "test", test.nullSafeTrimmedString("test"));
		Assert.assertEquals("Should be an empty string", "", test.nullSafeTrimmedString(""));
		Assert.assertEquals("Should be an empty string", "", test.nullSafeTrimmedString(null));
		Assert.assertEquals("Should return the value", "test", test.nullSafeTrimmedString(" test"));
		Assert.assertEquals("Should return the value", "test", test.nullSafeTrimmedString("test "));
		Assert.assertEquals("Should return the value", "test", test.nullSafeTrimmedString(" test "));
	}
	
	@Test
	public void testNullIfEmptyOrTrimmed() {
		// Given
		AbstractUDEPropertiesBinding test = createMockAbstractBinding();
		
		// Then
		Assert.assertEquals("Should return the value", "test", test.nullIfEmptyOrTrimmed("test"));
		Assert.assertNull("Should be null", test.nullIfEmptyOrTrimmed(""));
		Assert.assertNull("Should be null", test.nullIfEmptyOrTrimmed(null));
		Assert.assertEquals("Should return the value", "test", test.nullIfEmptyOrTrimmed(" test"));
		Assert.assertEquals("Should return the value", "test", test.nullIfEmptyOrTrimmed("test "));
		Assert.assertEquals("Should return the value", "test", test.nullIfEmptyOrTrimmed(" test "));
	}
	
	@Test
	public void testIsInitializing() {
		// Given
		AbstractUDEPropertiesBinding test = new AbstractUDEPropertiesBinding() {
			@Override
			public String getDescription() {
				Assert.assertFalse(isInitializing());
				return null;
			}
			@Override
			public void doUpdateTextToModel(boolean modifyEvent) {
				Assert.assertFalse(isInitializing());
			}
			@Override
			public void doUpdateModelToText() {
				Assert.assertFalse(isInitializing());
			}
		};
		
		// Then
		Assert.assertFalse(test.isInitializing());
		test.getDescription();
		Assert.assertFalse(test.isInitializing());
		test.doUpdateModelToText();
		Assert.assertFalse(test.isInitializing());
		test.doUpdateTextToModel(true);
		Assert.assertFalse(test.isInitializing());
	}
	
	private AbstractUDEPropertiesBinding createMockAbstractBinding() {
		return new AbstractUDEPropertiesBinding() {
			@Override
			public String getDescription() {return null;}
			@Override
			public void doUpdateTextToModel(boolean modifyEvent) {}
			@Override
			public void doUpdateModelToText() {}
		};
	}

}
