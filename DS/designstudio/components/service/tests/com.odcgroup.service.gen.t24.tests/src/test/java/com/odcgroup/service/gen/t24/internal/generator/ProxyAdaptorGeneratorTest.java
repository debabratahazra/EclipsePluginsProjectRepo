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

public class ProxyAdaptorGeneratorTest {
	@Test
	public void testGenerateImpl1() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();		
		
		List<ParamDescriptor> paramsOne = new ArrayList<ParamDescriptor>();
		List<ParamDescriptor> returnParams = new ArrayList<ParamDescriptor>();
		paramsOne.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("numbers", "Integer", Cardinality.MULTIPLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("boolList", "Boolean", Cardinality.MULTIPLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("onlyOne", "OnlyOne", Cardinality.SINGLE, Direction.IN, Complexity.COMPLEX, false, null));
		paramsOne.add(new ParamDescriptor("couldBeLots", "CouldBeLots", Cardinality.MULTIPLE, Direction.IN, Complexity.COMPLEX, false, null));
		
		returnParams.add(new ParamDescriptor("", "Integer", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", paramsOne, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));

		//Set up some attributes which we can use for the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("OnlyOne", "Class", attributes));
		classDefDescriptors.add(new ClassDefDescriptor("CouldBeLots", "Class", attributes));

		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);
		ProxyAdaptorGenerator generator = new ProxyAdaptorGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("import com.temenos.services.myspecial.data.OnlyOne"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.converter.OnlyOneConverter"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.CouldBeLots"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.converter.CouldBeLotsConverter"));
		assertTrue(output.contains("import com.temenos.soa.services.data.ResponseDetails;"));
		assertTrue(output.contains("import com.temenos.soa.services.data.converter.ResponseDetailsConverter;"));
		assertTrue(output.contains("public class MySpecialServiceProxyAdaptorOperationOne extends BasicReplacement"));
		assertTrue(output.contains("public jVar invoke(Object... args)"));
		assertTrue(output.contains("String id = JVarClientUtils.getInstance().getString( jVarClientFactory.get((jVar) args[0]));"));
		assertTrue(output.contains("List<Integer> numbers = new ArrayList<Integer>();"));
		assertTrue(output.contains("JVarClientUtils.getInstance().getIntegerList(jVarClientFactory.get((jVar) args[1]), numbers);"));
		assertTrue(output.contains("List<Boolean> boolList = new ArrayList<Boolean>()"));
		assertTrue(output.contains("JVarClientUtils.getInstance().getBooleanList(jVarClientFactory.get((jVar) args[2]), boolList);"));
		assertTrue(output.contains("OnlyOneConverter onlyOneConverterObj = new OnlyOneConverter();"));
		assertTrue(output.contains("onlyOneConverterObj.fromJVarClient(onlyOne, jVarClientFactory.get((jVar) args[3]));"));
		assertTrue(output.contains("CouldBeLotsConverter couldBeLotsConverterObj = new CouldBeLotsConverter();"));
		assertTrue(output.contains("List<CouldBeLots> couldBeLots = new ArrayList<CouldBeLots>();"));
		assertTrue(output.contains("couldBeLotsConverterObj.fromJVarClient(couldBeLots, jVarClientFactory.get((jVar) args[4]));"));
		assertTrue(output.contains("ResponseDetailsConverter responseDetailsConverterObj = new ResponseDetailsConverter();"));
		assertTrue(output.contains("responseDetailsConverterObj.fromJVarClient(this.myResponseDetails, jVarClientFactory.get((jVar) args[5]));"));
		assertTrue(output.contains("service.operationOne(id, numbers, boolList, onlyOne, couldBeLots, this.myResponseDetails);"));
		assertTrue(output.contains("private MySpecialServiceProxyAPI createService()"));
		assertTrue(output.contains("private static final String className = \"MySpecialServiceProxyAdaptorOperationOne\";"));
		assertTrue(output.contains("prg = new MySpecialServiceProxyAdaptorOperationOne();"));
	}
	
	@Test
	public void testGenerateImpl2() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();		
		
		List<ParamDescriptor> returnParams = new ArrayList<ParamDescriptor>();
		
		List<ParamDescriptor> paramsTwo = new ArrayList<ParamDescriptor>();
		returnParams.add(new ParamDescriptor("", "Integer", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationTwo", paramsTwo, new ParamDescriptor("", "Integer", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));

		//Set up some attributes which we can use for the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("MySpecialClass", "Class", attributes));

		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);

		ProxyAdaptorGenerator generator = new ProxyAdaptorGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("public class MySpecialServiceProxyAdaptorOperationTwo extends BasicReplacement"));
		assertTrue(output.contains("public jVar invoke(Object... args)"));
		assertTrue(output.contains("ResponseDetailsConverter responseDetailsConverterObj = new ResponseDetailsConverter();"));
		assertTrue(output.contains("responseDetailsConverterObj.fromJVarClient(this.myResponseDetails, jVarClientFactory.get((jVar) args[0]));"));
		assertTrue(output.contains("service.operationTwo(this.myResponseDetails);"));
		assertTrue(output.contains("private MySpecialServiceProxyAPI createService()"));
		assertTrue(output.contains("private static final String className = \"MySpecialServiceProxyAdaptorOperationTwo\";"));
		assertTrue(output.contains("prg = new MySpecialServiceProxyAdaptorOperationTwo();"));
	}
}
