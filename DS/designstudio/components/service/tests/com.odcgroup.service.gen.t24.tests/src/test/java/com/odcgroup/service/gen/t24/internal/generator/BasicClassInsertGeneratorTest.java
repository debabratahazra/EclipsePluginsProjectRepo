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

public class BasicClassInsertGeneratorTest {
	@Test
	public void testGenerate() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();

		//Set up some attributes which we'll use for both the service-operation-parameters and the class-definition-attributes.
		//Here, we only really interested in the latter.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));
		
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("aComplexParam", "ComplexClass", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, attributes));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "MyOpName", params, null));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MyComplexType", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);
		
		BasicClassInsertGenerator generator = new BasicClassInsertGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");
		
		String output = outputWriter.toString();
		
		//System.out.println(output);

		assertTrue(output.contains("* Insert describing the complex type: MyComplexType used by the service: MySpecialService"));
		assertTrue(output.contains("EQU MyComplexType.anAttribute TO 1"));
		assertTrue(output.contains("EQU MyComplexType.anotherAttribute TO 2"));
	}

	@Test
	public void testGenerateExternal() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();

		//Set up some attributes which we'll use for both the service-operation-parameters and the class-definition-attributes.
		//Here, we only really interested in the latter.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("aComplexParam", "ComplexClass", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, attributes));

		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "MyOpName", params, null,AccessSpecifier.EXTERNAL));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MyComplexType", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors,"1.30.7");

		BasicClassInsertGenerator generator = new BasicClassInsertGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");

		String output = outputWriter.toString();

		//System.out.println(output);

		assertTrue(output.contains("* Insert describing the complex type: MyComplexType used by the service: MySpecialService"));
		assertTrue(output.contains("EQU MyComplexType.anAttribute TO 1"));
		assertTrue(output.contains("EQU MyComplexType.anotherAttribute TO 2"));
	}
}
