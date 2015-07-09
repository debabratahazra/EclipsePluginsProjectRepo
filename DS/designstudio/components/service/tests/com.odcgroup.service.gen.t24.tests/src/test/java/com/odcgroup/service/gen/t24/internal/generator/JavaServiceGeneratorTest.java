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

public class JavaServiceGeneratorTest {
	@Test
	public void testRefactoredGenerate() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		
		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> paramsOne = new ArrayList<ParamDescriptor>();
		List<ParamDescriptor> returnParams = new ArrayList<ParamDescriptor>();
		paramsOne.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("numbers", "Integer", Cardinality.MULTIPLE, Direction.IN, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("onlyOne", "OnlyOne", Cardinality.SINGLE, Direction.IN, Complexity.COMPLEX, false, null));
		paramsOne.add(new ParamDescriptor("couldBeLots", "CouldBeLots", Cardinality.MULTIPLE, Direction.IN, Complexity.COMPLEX, false, null));

		paramsPerOperation.add(paramsOne);
		
		List<ParamDescriptor> paramsTwo = new ArrayList<ParamDescriptor>();
		paramsPerOperation.add(paramsTwo);
		returnParams.add(new ParamDescriptor("", "Integer", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", paramsOne, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));
		operations.add(new OperationDescriptor("MySpecialService", "operationTwo", paramsTwo, new ParamDescriptor("", "Integer", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));

		//Set up some attributes which we can use for the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClass", "Class", attributes));

		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);

		JavaServiceGenerator generator = new JavaServiceGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("public interface MySpecialService"));
		assertTrue(output.contains("String operationOne(String id, List<Integer> numbers, OnlyOne onlyOne, List<CouldBeLots> couldBeLots, ResponseDetails responseDetails);"));		
		assertTrue(output.contains("Integer operationTwo(ResponseDetails responseDetails);"));
	}
}
