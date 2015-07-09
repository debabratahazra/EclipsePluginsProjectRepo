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

public class JBCGetCustomizedTypeStructureGeneratorTest extends TestCase {
	private ServiceDescriptor serviceDescriptor;
	private JBCCustomizedTypeStructureGenerator generator;
	
	//construct uml service model
	protected void setUp() {
		generator = new JBCCustomizedTypeStructureGenerator(new TemplateOutsideJarLoader());
		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		List<AttributeDescriptor> attributes = 	new ArrayList<AttributeDescriptor>();
		
		//member is single string
		attributes.add(		new AttributeDescriptor("strAttr", "String", Cardinality.SINGLE));
		//member is collection of string
		attributes.add(		new AttributeDescriptor("strListAttr", "String", Cardinality.MULTIPLE));
		attributes.add(		new AttributeDescriptor("myComplexType2", "MyComplexType2", Cardinality.SINGLE));
		attributes.add(		new AttributeDescriptor("complexTypeBool", "ComplexTypeBool", Cardinality.SINGLE));

		//define first complex type
		classDefDescriptors.add(new ClassDefDescriptor("MyComplexType", "MyComplexType", attributes));

		List<AttributeDescriptor> attributes2 = new ArrayList<AttributeDescriptor>();
		//member is single integer
		attributes2.add(new AttributeDescriptor("intAttr", "Integer", Cardinality.SINGLE));
		//member is collection of string
		attributes2.add(new AttributeDescriptor("intListAttr", "Integer", Cardinality.MULTIPLE));		
		classDefDescriptors.add(new ClassDefDescriptor("MyComplexType2", "MyComplexType2", attributes2));
		
		List<AttributeDescriptor> boolTypeAttr = new ArrayList<AttributeDescriptor>();
		boolTypeAttr.add(new AttributeDescriptor("boolAttr", "Boolean", Cardinality.SINGLE));
		boolTypeAttr.add(new AttributeDescriptor("boolListAttr", "Boolean", Cardinality.MULTIPLE));		
		classDefDescriptors.add(new ClassDefDescriptor("ComplexTypeBool", "ComplexTypeBool", boolTypeAttr));
		
		List<AttributeDescriptor> verySimpleTypeAttr = new ArrayList<AttributeDescriptor>();
		verySimpleTypeAttr.add(new AttributeDescriptor("intAttr", "Integer", Cardinality.SINGLE));
		verySimpleTypeAttr.add(new AttributeDescriptor("strAttr", "String", Cardinality.SINGLE));		
		verySimpleTypeAttr.add(new AttributeDescriptor("boolAttr", "Boolean", Cardinality.SINGLE));
		classDefDescriptors.add(new ClassDefDescriptor("VerySimpleType", "VerySimpleType", verySimpleTypeAttr));
		
		
		List<AttributeDescriptor> veryComplexTypeAttr = new ArrayList<AttributeDescriptor>();
		veryComplexTypeAttr.add(new AttributeDescriptor("firstField", "VerySimpleType", Cardinality.MULTIPLE));
		veryComplexTypeAttr.add(new AttributeDescriptor("secondField", "String", Cardinality.MULTIPLE));
		classDefDescriptors.add(new ClassDefDescriptor("VeryComplexType", "VeryComplexType", veryComplexTypeAttr));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		
		serviceDescriptor = new ServiceDescriptor("MyService", operations, classDefDescriptors);
	}	
	
	protected void tearDown() {
		serviceDescriptor = null;
		generator = null;
	}
	
	//test case to verify the customized Structure generated
	public void testJBCGetCustomisedTypeStructures() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();		

	    generator.genSubroutine(serviceDescriptor, outputWriter);
	    
	    String output = outputWriter.toString();
	    //System.out.println(output);
	    
		//test whether each member has been declared correctly
	    assertTrue(output.contains("getMyComplexTypeStructure:"));
	    assertTrue(output.contains("'strAttr':@FM:'PRIMITIVE_LIST':@VM:'strListAttr':@FM:'COMPLEX_SINGLE':@VM:'myComplexType2':@VM:'intAttr':@VM:'PRIMITIVE_LIST':@SVM:'intListAttr':@FM:'COMPLEX_SINGLE':@VM:'complexTypeBool':@VM:'boolAttr':@VM:'PRIMITIVE_LIST':@SVM:'boolListAttr'"));
		
		assertTrue(output.contains("getMyComplexType2Structure:"));
		assertTrue(output.contains("'intAttr':@FM:'PRIMITIVE_LIST':@VM:'intListAttr'"));
		
		assertTrue(output.contains("getComplexTypeBoolStructure:"));
		assertTrue(output.contains("'boolAttr':@FM:'PRIMITIVE_LIST':@VM:'boolListAttr'"));
				
	    assertTrue(output.contains("getVerySimpleTypeStructure:"));
	    assertTrue(output.contains("'intAttr':@FM:'strAttr':@FM:'boolAttr'"));

	    assertTrue(output.contains("getVeryComplexTypeStructure:"));
	    assertTrue(output.contains("'COMPLEX_LIST':@VM:'firstField':@VM:'intAttr':@SVM:'strAttr':@SVM:'boolAttr':@FM:'PRIMITIVE_LIST':@VM:'secondField'"));
	}
}
