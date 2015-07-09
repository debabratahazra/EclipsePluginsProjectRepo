package com.odcgroup.service.gen.t24.internal.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AttributeDescriptorTest {
	
	@Test
	public void testEqualsFailsDifferentName() {
		AttributeDescriptor singleStringInputParam = new AttributeDescriptor("stringParam", "String", Cardinality.SINGLE);
		AttributeDescriptor differentSingleStringInputParam = new AttributeDescriptor("different", "String", Cardinality.SINGLE);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentType() {
		AttributeDescriptor singleStringInputParam = new AttributeDescriptor("param", "String", Cardinality.SINGLE);
		AttributeDescriptor differentSingleStringInputParam = new AttributeDescriptor("param", "Integer", Cardinality.SINGLE);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentCardinality() {
		AttributeDescriptor singleStringInputParam = new AttributeDescriptor("param", "String", Cardinality.SINGLE);
		AttributeDescriptor differentSingleStringInputParam = new AttributeDescriptor("param", "String", Cardinality.MULTIPLE);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentClass() {
		AttributeDescriptor singleStringInputParam = new AttributeDescriptor("param", "String", Cardinality.SINGLE);
		Object object = new Object();
		assertFalse(singleStringInputParam.equals(object));
	}

	@Test
	public void testEqualsSucceedsSameValues() {
		AttributeDescriptor singleStringInputParam = new AttributeDescriptor("param", "String", Cardinality.SINGLE);
		AttributeDescriptor differentSingleStringInputParam = new AttributeDescriptor("param", "String", Cardinality.SINGLE);
		assertTrue(singleStringInputParam.equals(differentSingleStringInputParam));
	}
	
	@Test
	public void testEqualsSucceedsSameObject() {
		AttributeDescriptor singleStringInputParam = new AttributeDescriptor("stringParam", "String", Cardinality.SINGLE);		
		assertTrue(singleStringInputParam.equals(singleStringInputParam));
	}

	@Test
	public void testToString() {
		AttributeDescriptor singleStringInputParam = new AttributeDescriptor("stringParam", "String", Cardinality.SINGLE);		
		assertEquals(singleStringInputParam.getCamelCaseName(), "StringParam");
	}
	
	@Test
	public void testCamelCaseName() {
		AttributeDescriptor singleStringInputParam = new AttributeDescriptor("stringParam", "String", Cardinality.SINGLE);		
		assertEquals(singleStringInputParam.toString(), "AttributeDescriptor: name=stringParam, typeName=String, cardinality=SINGLE, complexity=PRIMITIVE" );
	}	
}
