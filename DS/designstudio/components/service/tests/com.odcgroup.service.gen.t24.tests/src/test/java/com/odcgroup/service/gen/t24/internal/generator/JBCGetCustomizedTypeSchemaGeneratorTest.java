package com.odcgroup.service.gen.t24.internal.generator;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public class JBCGetCustomizedTypeSchemaGeneratorTest extends TestCase {
	private ServiceDescriptor serviceDescriptor;
	private ServiceDescriptor serviceDescriptorExternal;
	private JBCCustomizedTypeSchemaGenerator generator;
	
	//construct uml service model
	protected void setUp() {
		generator = new JBCCustomizedTypeSchemaGenerator(new TemplateOutsideJarLoader());
		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		List<AttributeDescriptor> attributes = 
			new ArrayList<AttributeDescriptor>();
		//member is single string
		attributes.add(
				new AttributeDescriptor("strAttr", "String", Cardinality.SINGLE));
		//member is collection of string
		attributes.add(
				new AttributeDescriptor("strListAttr", "String", Cardinality.MULTIPLE));
		attributes.add(
				new AttributeDescriptor("myComplexType2", "MyComplexType2", Cardinality.SINGLE));
		attributes.add(
				new AttributeDescriptor("complexTypeBool", "ComplexTypeBool", Cardinality.SINGLE));

		//define first complex type
		classDefDescriptors.add(new ClassDefDescriptor("MyComplexType", "MyComplexType", attributes));

		List<AttributeDescriptor> attributes2 = 
			new ArrayList<AttributeDescriptor>();
		//member is single integer
		attributes2.add(
				new AttributeDescriptor("intAttr", "Integer", Cardinality.SINGLE));
		//member is collection of string
		attributes2.add(
				new AttributeDescriptor("intListAttr", "Integer", Cardinality.MULTIPLE));		
		classDefDescriptors.add(new ClassDefDescriptor("MyComplexType2", "MyComplexType2", attributes2));
		
		List<AttributeDescriptor> boolTypeAttr = new ArrayList<AttributeDescriptor>();
		boolTypeAttr.add(new AttributeDescriptor("boolAttr", "Boolean", Cardinality.SINGLE));
		boolTypeAttr.add(new AttributeDescriptor("boolListAttr", "Boolean", Cardinality.MULTIPLE));		
		classDefDescriptors.add(new ClassDefDescriptor("ComplexTypeBool", "ComplexTypeBool", boolTypeAttr));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		
		
		serviceDescriptor = new ServiceDescriptor("MyService", operations, classDefDescriptors);
		serviceDescriptorExternal = new ServiceDescriptor("MyService", operations, classDefDescriptors,"1.30.7");
	}	
	
	protected void tearDown() {
		serviceDescriptor = null;
		generator = null;
	}
	
	//test code generator to generate business object .h file
	public void testJBCGetCustomisedTypeSchemas() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();		

	    generator.genSubroutine(serviceDescriptor, outputWriter);
	    
	    String output = outputWriter.toString();
	    //System.out.println(output);
	    
		//test whether each member has been declared correctly
	    assertTrue(output.contains("getMyComplexTypeSchema:"));
	    assertTrue(output.contains("<xsd:schema xmlns=\"http://www.temenos.com/T24/MyService/MyComplexType\""));
		assertTrue(output.contains("targetNamespace=\"http://www.temenos.com/T24/MyService/MyComplexType\""));
		assertTrue(output.contains("<xsd:complexType name=\"MyComplexType\">"));
		assertTrue(output.contains("<xsd:element name=\"strAttr\" type=\"xsd:string\" minOccurs=\"0\" />"));
		assertTrue(output.contains("<xsd:element name=\"strListAttr\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\" />"));
		assertTrue(output.contains("<xsd:element name=\"myComplexType2\" minOccurs=\"0\">"));
		assertTrue(output.contains("<xsd:complexType>"));
		assertTrue(output.contains("<xsd:element name=\"intAttr\" type=\"xsd:integer\" minOccurs=\"0\" />"));
		assertTrue(output.contains("<xsd:element name=\"intListAttr\" type=\"xsd:integer\" minOccurs=\"0\" maxOccurs=\"unbounded\" />"));
		assertTrue(output.contains("<xsd:element name=\"complexTypeBool\" minOccurs=\"0\">"));
		assertTrue(output.contains("<xsd:complexType>"));
		assertTrue(output.contains("<xsd:element name=\"boolAttr\" type=\"xsd:boolean\" minOccurs=\"0\" />"));
		assertTrue(output.contains("<xsd:element name=\"boolListAttr\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"unbounded\" />"));
		
		assertTrue(output.contains("getMyComplexType2Schema:"));
		assertTrue(output.contains("<xsd:schema xmlns=\"http://www.temenos.com/T24/MyService/MyComplexType2\""));
		assertTrue(output.contains("targetNamespace=\"http://www.temenos.com/T24/MyService/MyComplexType2\""));
		
		assertTrue(output.contains("getComplexTypeBoolSchema:"));
		assertTrue(output.contains("<xsd:schema xmlns=\"http://www.temenos.com/T24/MyService/ComplexTypeBool\""));
		assertTrue(output.contains("targetNamespace=\"http://www.temenos.com/T24/MyService/ComplexTypeBool\""));
	}

	//test code generator to generate business object .h file
		public void testJBCGetCustomisedTypeSchemasExternal() throws LoadTemplateException {
			Writer outputWriter = new StringWriter();

		    generator.genSubroutine(serviceDescriptorExternal, outputWriter);

		    String output = outputWriter.toString();
		    //System.out.println(output);

			//test whether each member has been declared correctly
		    assertTrue(output.contains("getMyComplexTypeSchema:"));
		    assertTrue(output.contains("<xsd:schema xmlns=\"http://www.temenos.com/T24/MyService/MyComplexType\""));
			assertTrue(output.contains("targetNamespace=\"http://www.temenos.com/T24/MyService/MyComplexType\""));
			assertTrue(output.contains("<xsd:complexType name=\"MyComplexType\">"));
			assertTrue(output.contains("<xsd:element name=\"strAttr\" type=\"xsd:string\" minOccurs=\"0\" />"));
			assertTrue(output.contains("<xsd:element name=\"strListAttr\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"unbounded\" />"));
			assertTrue(output.contains("<xsd:element name=\"myComplexType2\" minOccurs=\"0\">"));
			assertTrue(output.contains("<xsd:complexType>"));
			assertTrue(output.contains("<xsd:element name=\"intAttr\" type=\"xsd:integer\" minOccurs=\"0\" />"));
			assertTrue(output.contains("<xsd:element name=\"intListAttr\" type=\"xsd:integer\" minOccurs=\"0\" maxOccurs=\"unbounded\" />"));
			assertTrue(output.contains("<xsd:element name=\"complexTypeBool\" minOccurs=\"0\">"));
			assertTrue(output.contains("<xsd:complexType>"));
			assertTrue(output.contains("<xsd:element name=\"boolAttr\" type=\"xsd:boolean\" minOccurs=\"0\" />"));
			assertTrue(output.contains("<xsd:element name=\"boolListAttr\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"unbounded\" />"));

			assertTrue(output.contains("getMyComplexType2Schema:"));
			assertTrue(output.contains("<xsd:schema xmlns=\"http://www.temenos.com/T24/MyService/MyComplexType2\""));
			assertTrue(output.contains("targetNamespace=\"http://www.temenos.com/T24/MyService/MyComplexType2\""));

			assertTrue(output.contains("getComplexTypeBoolSchema:"));
			assertTrue(output.contains("<xsd:schema xmlns=\"http://www.temenos.com/T24/MyService/ComplexTypeBool\""));
			assertTrue(output.contains("targetNamespace=\"http://www.temenos.com/T24/MyService/ComplexTypeBool\""));
		}
}
