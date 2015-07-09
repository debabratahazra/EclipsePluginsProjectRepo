package com.odcgroup.service.gen.t24.internal.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for ServiceDescriptor
 *
 * @author sjunejo
 *
 */
public class ServiceDescriptorTest {
	
	private static List<ClassDefDescriptor> classes = new ArrayList<ClassDefDescriptor>();
	private static final String SERVICE_NAME = "MyService";
	private static final String SERVICE_NAME_WITH_MODULE = "AA.MyService"; 
	
	@BeforeClass
	public static void setup() {
		// Nested Class Descriptor
		List<AttributeDescriptor> childAttribs = new ArrayList<AttributeDescriptor>();
		childAttribs.add(new AttributeDescriptor("stringParam", "String", Cardinality.SINGLE));
		childAttribs.add(new AttributeDescriptor("stringListParam", "String", Cardinality.MULTIPLE));
		childAttribs.add(new AttributeDescriptor("boolParam", "Boolean", Cardinality.SINGLE));
		childAttribs.add(new AttributeDescriptor("boolListParam", "Boolean", Cardinality.MULTIPLE));
		childAttribs.add(new AttributeDescriptor("intParam", "Integer", Cardinality.SINGLE));
		childAttribs.add(new AttributeDescriptor("intListParam", "Integer", Cardinality.MULTIPLE));
		classes.add(new ClassDefDescriptor("NestedComplex", "NestedComplex", childAttribs));
		
		// Parent Class Descriptor
		List<AttributeDescriptor> parentAttribs = new ArrayList<AttributeDescriptor>();
		parentAttribs.add(new AttributeDescriptor("complexList", "NestedComplex", Cardinality.MULTIPLE));
		parentAttribs.add(new AttributeDescriptor("parentString", "String", Cardinality.MULTIPLE));
		parentAttribs.add(new AttributeDescriptor("parentBool", "Boolean", Cardinality.MULTIPLE));
		parentAttribs.add(new AttributeDescriptor("parentInt", "Integer", Cardinality.MULTIPLE));
		parentAttribs.add(new AttributeDescriptor("complexSingle", "NestedComplex", Cardinality.SINGLE));
		classes.add(new ClassDefDescriptor("ComplexParent", "ComplexParent", parentAttribs));
	}

	@AfterClass
	public static void tearDown() {
		classes.clear();
		classes = null;
	}
	
	@Test
	public void testGetClassDefDescSchema() {
		ServiceDescriptor sd = new ServiceDescriptor(SERVICE_NAME, new ArrayList<OperationDescriptor>(), classes);
		try {
			String actualOutput = sd.getClassDefDescSchema(classes.get(0));
			String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsd:schema xmlns=\"http://www.temenos.com/T24/MyService/NestedComplex\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" targetNamespace=\"http://www.temenos.com/T24/MyService/NestedComplex\" elementFormDefault=\"qualified\"><xsd:complexType name=\"NestedComplex\"><xsd:sequence><xsd:element name=\"stringParam\" type=\"xsd:string\" minOccurs=\"0\" /><xsd:element name=\"stringListParam\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"boolParam\" type=\"xsd:boolean\" minOccurs=\"0\" /><xsd:element name=\"boolListParam\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"intParam\" type=\"xsd:integer\" minOccurs=\"0\" /><xsd:element name=\"intListParam\" type=\"xsd:integer\" minOccurs=\"0\" maxOccurs=\"unbounded\" /></xsd:sequence></xsd:complexType></xsd:schema>";
			assertEquals(expected, actualOutput);
			actualOutput = sd.getClassDefDescSchema(classes.get(1));
			expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsd:schema xmlns=\"http://www.temenos.com/T24/MyService/ComplexParent\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" targetNamespace=\"http://www.temenos.com/T24/MyService/ComplexParent\" elementFormDefault=\"qualified\"><xsd:complexType name=\"ComplexParent\"><xsd:sequence><xsd:element name=\"complexList\" minOccurs=\"0\" maxOccurs=\"unbounded\"><xsd:complexType><xsd:sequence><xsd:element name=\"stringParam\" type=\"xsd:string\" minOccurs=\"0\" /><xsd:element name=\"stringListParam\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"boolParam\" type=\"xsd:boolean\" minOccurs=\"0\" /><xsd:element name=\"boolListParam\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"intParam\" type=\"xsd:integer\" minOccurs=\"0\" /><xsd:element name=\"intListParam\" type=\"xsd:integer\" minOccurs=\"0\" maxOccurs=\"unbounded\" /></xsd:sequence></xsd:complexType></xsd:element><xsd:element name=\"parentString\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"parentBool\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"parentInt\" type=\"xsd:integer\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"complexSingle\" minOccurs=\"0\"><xsd:complexType><xsd:sequence><xsd:element name=\"stringParam\" type=\"xsd:string\" minOccurs=\"0\" /><xsd:element name=\"stringListParam\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"boolParam\" type=\"xsd:boolean\" minOccurs=\"0\" /><xsd:element name=\"boolListParam\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"unbounded\" /><xsd:element name=\"intParam\" type=\"xsd:integer\" minOccurs=\"0\" /><xsd:element name=\"intListParam\" type=\"xsd:integer\" minOccurs=\"0\" maxOccurs=\"unbounded\" /></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:schema>";
			assertEquals(expected, actualOutput);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testComponentNameAndModuleName() {
		ServiceDescriptor sd = new ServiceDescriptor(SERVICE_NAME_WITH_MODULE, new ArrayList<OperationDescriptor>(), classes);
		assertEquals("Service name is not same.", "MyService", sd.getName());
		assertEquals("Module name is not same.", "AA", sd.getModuleName());
		
	}
}
