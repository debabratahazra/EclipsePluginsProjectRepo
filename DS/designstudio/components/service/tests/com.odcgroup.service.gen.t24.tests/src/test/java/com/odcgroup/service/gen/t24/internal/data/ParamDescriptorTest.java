package com.odcgroup.service.gen.t24.internal.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParamDescriptorTest {
	
	@Test
	public void testEqualsFailsDifferentName() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("stringParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);
		ParamDescriptor differentSingleStringInputParam = new ParamDescriptor("different", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, false, null);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentType() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("param", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);
		ParamDescriptor differentSingleStringInputParam = new ParamDescriptor("param", "Integer", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, false, null);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentCardinality() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("param", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);
		ParamDescriptor differentSingleStringInputParam = new ParamDescriptor("param", "String", Cardinality.MULTIPLE, Direction.IN, Complexity.PRIMITIVE, false, null);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentParamType() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("param", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);
		ParamDescriptor differentSingleStringInputParam = new ParamDescriptor("param", "String", Cardinality.SINGLE, Direction.OUT, Complexity.PRIMITIVE, false, null);
		assertFalse(singleStringInputParam.equals(differentSingleStringInputParam));
	}

	@Test
	public void testEqualsFailsDifferentClass() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("param", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);
		Object object = new Object();
		assertFalse(singleStringInputParam.equals(object));
	}

	@Test
	public void testEqualsSucceedsSameValues() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("param", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);
		ParamDescriptor differentSingleStringInputParam = new ParamDescriptor("param", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, false, null);
		assertTrue(singleStringInputParam.equals(differentSingleStringInputParam));
	}
	
	@Test
	public void testEqualsSucceedsSameObject() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("stringParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);		
		assertTrue(singleStringInputParam.equals(singleStringInputParam));
	}

	@Test
	public void testToString() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("stringParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);		
		assertEquals(singleStringInputParam.toString(), "ParamDescriptor: name=stringParam, typeName=String, cardinality=SINGLE, type=IN, mandatory=true, attributes=null");
	}
	
	@Test
	public void testUpperCamelCaseName() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("stringParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);		
		assertEquals(singleStringInputParam.getUpperCamelCaseName(), "StringParam");
	}	
	
	@Test
	public void testLowerCamelCaseName() {
		ParamDescriptor singleStringInputParam = new ParamDescriptor("stringParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, false, null);		
		assertEquals(singleStringInputParam.getLowerCamelCaseName(), "stringParam");
	}
}
