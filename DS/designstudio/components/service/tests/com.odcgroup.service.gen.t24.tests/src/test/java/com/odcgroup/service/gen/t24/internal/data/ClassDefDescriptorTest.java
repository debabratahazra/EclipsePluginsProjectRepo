package com.odcgroup.service.gen.t24.internal.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassDefDescriptorTest {
	
	@Test
	public void testEqualsFailsDifferentName() {
		ClassDefDescriptor singleStringInputParam = new ClassDefDescriptor("stringParam", "String", null);
		ClassDefDescriptor differentSingleStringInputParam = new ClassDefDescriptor("different", "String", null);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentType() {
		ClassDefDescriptor singleStringInputParam = new ClassDefDescriptor("param", "String", null);
		ClassDefDescriptor differentSingleStringInputParam = new ClassDefDescriptor("param", "Integer", null);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentClass() {
		ClassDefDescriptor singleStringInputParam = new ClassDefDescriptor("param", "String", null);
		Object object = new Object();
		assertFalse(singleStringInputParam.equals(object));
	}

	@Test
	public void testEqualsSucceedsSameObject() {
		ClassDefDescriptor singleStringInputParam = new ClassDefDescriptor("stringParam", "String", null);		
		assertTrue(singleStringInputParam.equals(singleStringInputParam));
	}

	@Test
	public void testToString() {
		ClassDefDescriptor singleStringInputParam = new ClassDefDescriptor("stringParam", "String", null);		
		assertEquals(singleStringInputParam.toString(), "ClassDefDescriptor: name=stringParam, typeName=String, attributes=null");
	}
	
	@Test
	public void testToStringBoolean() {
		ClassDefDescriptor singleStringInputParam = new ClassDefDescriptor("stringParam", "Boolean", null);		
		assertEquals(singleStringInputParam.toString(), "ClassDefDescriptor: name=stringParam, typeName=Boolean, attributes=null");
	}
	
	@Test
	public void testUpperCamelCaseName() {
		ClassDefDescriptor singleStringInputParam = new ClassDefDescriptor("stringParam", "String", null);		
		assertEquals(singleStringInputParam.getUpperCamelCaseName(), "StringParam");
	}	
	
	@Test
	public void testLowerCamelCaseName() {
		ClassDefDescriptor singleStringInputParam = new ClassDefDescriptor("StringParam", "String", null);		
		assertEquals(singleStringInputParam.getLowerCamelCaseName(), "stringParam");
	}
}
