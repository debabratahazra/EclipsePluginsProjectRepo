package com.odcgroup.service.gen.t24.internal.generator;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Complexity;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.model.component.AccessSpecifier;

public class BasicApiGeneratorTest {

	@Test
	public void testGenerate() throws ResourceNotFoundException, ParseErrorException, Exception {
		Writer outputWriter = new StringWriter();
		//Set up some attributes which we can use for both the service-operation-parameters and the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("aParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("anotherParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("anotherParamBoolean", "Boolean", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("aComplexParam", "Class", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, attributes));
		params.add(new ParamDescriptor("anotherComplexParam", "Class", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, attributes));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService","myOpName",params, null,AccessSpecifier.PUBLIC));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClass", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);
		ServiceGenerator generator = new BasicApiGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("SUBROUTINE MySpecialService.myOpName(aParam, anotherParam, anotherParamBoolean, aComplexParam, anotherComplexParam)"));	
		assertTrue(output.contains("CALL @proxyAdaptorInterfaceName((aParam), (anotherParam), (anotherParamBoolean), aComplexParam, anotherComplexParam, responseDetails)"));
		assertTrue(output.contains("CALL T24MySpecialServiceImpl.myOpName((aParam), (anotherParam), (anotherParamBoolean), aComplexParam, anotherComplexParam, responseDetails)"));
		assertTrue(output.contains("$INSERT I_MySpecialService_Class"));
		assertTrue(output.contains("CALL GetServiceResponseHandler (responseHandlerName)"));
		assertTrue(output.contains("CALL @responseHandlerName (responseDetails)"));
	}

	@Test
	public void testGenerateExternal() throws ResourceNotFoundException, ParseErrorException, Exception {
		Writer outputWriter = new StringWriter();
		//Set up some attributes which we can use for both the service-operation-parameters and the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("aParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("anotherParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("anotherParamBoolean", "Boolean", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("aComplexParam", "Class", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, attributes));
		params.add(new ParamDescriptor("anotherComplexParam", "Class", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, attributes));

		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "myExternalMethod", params, null,AccessSpecifier.EXTERNAL));
		operations.add(new OperationDescriptor("MySpecialService", "myPublicMethod", params, null,AccessSpecifier.PUBLIC));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClass", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors,"1.30.7");
		ServiceGenerator generator = new BasicApiGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");

		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("SUBROUTINE MySpecialService.myExternalMethod(aParam, anotherParam, anotherParamBoolean, aComplexParam, anotherComplexParam)"));
		assertTrue(output.contains("CALL @proxyAdaptorInterfaceName((aParam), (anotherParam), (anotherParamBoolean), aComplexParam, anotherComplexParam, responseDetails)"));
		assertTrue(output.contains("CALL T24MySpecialServiceImpl.myExternalMethod((aParam), (anotherParam), (anotherParamBoolean), aComplexParam, anotherComplexParam, responseDetails)"));
		assertTrue(output.contains("$INSERT I_MySpecialService_Class"));
		assertTrue(output.contains("CALL GetServiceResponseHandler (responseHandlerName)"));
		assertTrue(output.contains("CALL @responseHandlerName (responseDetails)"));
	}

	@Test
	public void testNoArgs() throws ResourceNotFoundException, ParseErrorException, Exception {
		Writer outputWriter = new StringWriter();

		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "myNoArgOp", params, null));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, null);
		ServiceGenerator generator = new BasicApiGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("SUBROUTINE MySpecialService.myNoArgOp"));
		assertTrue(output.contains("CALL @proxyAdaptorInterfaceName(responseDetails)"));
		assertTrue(output.contains("CALL T24MySpecialServiceImpl.myNoArgOp(responseDetails)"));
	}
}
