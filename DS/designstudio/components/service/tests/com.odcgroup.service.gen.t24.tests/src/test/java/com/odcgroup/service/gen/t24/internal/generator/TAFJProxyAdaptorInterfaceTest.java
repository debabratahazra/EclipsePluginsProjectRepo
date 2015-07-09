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

public class TAFJProxyAdaptorInterfaceTest {
	
	@Test
	public void testGenerate() throws ResourceNotFoundException, ParseErrorException, Exception {
		Writer outputWriter = new StringWriter();

		//Set up some attributes which we can use for both the service-operation-parameters and the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));

		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("aParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "myOpName", params, null));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClass", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);
		ServiceGenerator generator = new ProxyAdaptorInterfaceGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("public class IMySpecialServiceProxyAdaptor_myOpName_29_cl extends jRunTime"));	
		assertTrue(output.contains("public static jRunTime INSTANCE(jSession session)"));
		assertTrue(output.contains("return com.temenos.services.myspecial.MySpecialServiceProxyAdaptorMyOpName.INSTANCE(session);"));

	}

	@Test
	public void testGenerateExternal() throws ResourceNotFoundException, ParseErrorException, Exception {
		Writer outputWriter = new StringWriter();

		//Set up some attributes which we can use for both the service-operation-parameters and the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));

		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("aParam", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));

		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "myOpName", params, null,AccessSpecifier.EXTERNAL));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClass", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors,"1.30.7");
		ServiceGenerator generator = new ProxyAdaptorInterfaceGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");

		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("public class IMySpecialServiceProxyAdaptor_myOpName_29_cl extends jRunTime"));
		assertTrue(output.contains("public static jRunTime INSTANCE(jSession session)"));
		assertTrue(output.contains("return com.temenos.services.myspecial.MySpecialServiceProxyAdaptorMyOpName.INSTANCE(session);"));

	}

}
