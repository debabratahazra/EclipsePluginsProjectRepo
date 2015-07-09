package com.odcgroup.service.gen.t24.internal.generator;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Complexity;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.model.component.AccessSpecifier;

public class BasicImplementationGeneratorTest {
	@Test
	public void testGenerate() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("aParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("anotherParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, false, null));
		params.add(new ParamDescriptor("anotherParamBoolean", "Boolean", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, false, null));
		params.add(new ParamDescriptor("aStringList", "String", Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		params.add(new ParamDescriptor("mySpecialClassOne", "MySpecialClassOne", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, null));
		params.add(new ParamDescriptor("mySpecialClassTwo", "MySpecialClassTwo", Cardinality.MULTIPLE, Direction.OUT, Complexity.COMPLEX, false, null));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "myOpName", params, null,AccessSpecifier.PUBLIC));

		//Set up some attributes which we can use for the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("myString", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("myInteger", "Integer", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("myBoolean", "Boolean", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("myStrings", "String", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("myIntegers", "Integer", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("myBooleans", "Boolean", Cardinality.MULTIPLE));
		
		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClassOne", "Class", attributes));
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClassTwo", "Class", attributes));
		
		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);
		
		ServiceGenerator generator = new BasicImplementationGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");
		
		String output = outputWriter.toString();
		
//		System.out.println(output);
		assertTrue(output.contains("SUBROUTINE T24MySpecialServiceImpl.myOpName(aParam, anotherParam, anotherParamBoolean, aStringList, mySpecialClassOne, mySpecialClassTwo, responseDetails)"));
		assertTrue(output.contains("* aParam - String, IN"));
		assertTrue(output.contains("* anotherParam - String, IN"));
		assertTrue(output.contains("* anotherParamBoolean - Boolean, IN"));	
		assertTrue(output.contains("* mySpecialClassOne - MySpecialClassOne (Single), OUT"));
		assertTrue(output.contains("* mySpecialClassTwo - MySpecialClassTwo (List), OUT"));
		assertTrue(output.contains("* responseDetails - ResponseDetails, OUT"));
		assertTrue(output.contains("$INSERT I_MySpecialService_MySpecialClassOne"));
		assertTrue(output.contains("$INSERT I_MySpecialService_MySpecialClassTwo"));
		assertTrue(output.contains("mySpecialClassOne ="));
		assertTrue(output.contains("mySpecialClassTwo ="));		
		assertTrue(output.contains("serviceName = \"T24MySpecialServiceImpl.myOpName\""));
		assertTrue(output.contains("IF aParam = '' THEN"));
		assertTrue(output.contains("ETEXT = 'EB-MANDATORY.INPUT':FM:'aParam'"));
	}

	@Test
	public void testGenerateExternal() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();

		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("aParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("anotherParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, false, null));
		params.add(new ParamDescriptor("anotherParamBoolean", "Boolean", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, false, null));
		params.add(new ParamDescriptor("aStringList", "String", Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		params.add(new ParamDescriptor("mySpecialClassOne", "MySpecialClassOne", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, null));
		params.add(new ParamDescriptor("mySpecialClassTwo", "MySpecialClassTwo", Cardinality.MULTIPLE, Direction.OUT, Complexity.COMPLEX, false, null));

		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "MyExternalMethod", params, null,AccessSpecifier.EXTERNAL));

		//Set up some attributes which we can use for the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("myString", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("myInteger", "Integer", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("myBoolean", "Boolean", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("myStrings", "String", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("myIntegers", "Integer", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("myBooleans", "Boolean", Cardinality.MULTIPLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClassOne", "Class", attributes));
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClassTwo", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors,"1.30.7");

		ServiceGenerator generator = new BasicImplementationGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");

		String output = outputWriter.toString();

		//System.out.println(output);

		assertTrue(output.contains("SUBROUTINE T24MySpecialServiceImpl.myExternalMethod(aParam, anotherParam, anotherParamBoolean, aStringList, mySpecialClassOne, mySpecialClassTwo, responseDetails)"));
		assertTrue(output.contains("* aParam - String, IN"));
		assertTrue(output.contains("* anotherParam - String, IN"));
		assertTrue(output.contains("* anotherParamBoolean - Boolean, IN"));
		assertTrue(output.contains("* mySpecialClassOne - MySpecialClassOne (Single), OUT"));
		assertTrue(output.contains("* mySpecialClassTwo - MySpecialClassTwo (List), OUT"));
		assertTrue(output.contains("* responseDetails - ResponseDetails, OUT"));
		assertTrue(output.contains("$INSERT I_MySpecialService_MySpecialClassOne"));
		assertTrue(output.contains("$INSERT I_MySpecialService_MySpecialClassTwo"));
		assertTrue(output.contains("mySpecialClassOne ="));
		assertTrue(output.contains("mySpecialClassTwo ="));
		assertTrue(output.contains("serviceName = \"T24MySpecialServiceImpl.myExternalMethod\""));
		assertTrue(output.contains("IF aParam = '' THEN"));
		assertTrue(output.contains("ETEXT = 'EB-MANDATORY.INPUT':FM:'aParam'"));
	}
}
