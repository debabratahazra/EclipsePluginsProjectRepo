package com.odcgroup.service.gen.t24.internal.categories.java.webservice;

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
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;


public class JavaWebServiceGeneratorTest {
	@Test
	public void testRefactoredGenerate() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> paramsOne = new ArrayList<ParamDescriptor>();
		paramsOne.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("numbers", "Integer", Cardinality.MULTIPLE, Direction.IN, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("onlyOne", "OnlyOne", Cardinality.SINGLE, Direction.IN, Complexity.COMPLEX, false, null));
		paramsOne.add(new ParamDescriptor("couldBeLots", "CouldBeLots", Cardinality.MULTIPLE, Direction.IN, Complexity.COMPLEX, false, null));
		paramsOne.add(new ParamDescriptor("couldBeLotsOut", "CouldBeLots", Cardinality.MULTIPLE, Direction.OUT, Complexity.COMPLEX, false, null));

		paramsPerOperation.add(paramsOne);
		
		List<ParamDescriptor> paramsTwo = new ArrayList<ParamDescriptor>();
		paramsTwo.add(new ParamDescriptor("idOut", "Integer", Cardinality.SINGLE, Direction.OUT, Complexity.PRIMITIVE, false, null));
		paramsPerOperation.add(paramsTwo);
		
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

		JavaWebServiceGenerator generator = new JavaWebServiceGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.webservices.myspecial;"));
		assertTrue(output.contains("import javax.jws.WebMethod;"));
		assertTrue(output.contains("import org.springframework.beans.BeansException;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.MySpecialServiceProviderAPI;"));
		assertTrue(output.contains("WebService(name = \"MySpecialServiceWS\", serviceName=\"MySpecialServiceWS\")"));
		
		assertTrue(output.contains("public OperationOneResponse operationOne(T24UserDetails userDetails, String id, Integer[] numbers, OnlyOne onlyOne, CouldBeLots[] couldBeLots) {"));
		assertTrue(output.contains("List<Integer> numbersAsList = new ArrayList<Integer>();"));
		assertTrue(output.contains("numbersAsList.addAll(Arrays.asList(numbers));"));
		assertTrue(output.contains("List<CouldBeLots> couldBeLotsAsList = new ArrayList<CouldBeLots>();"));
		assertTrue(output.contains("couldBeLotsAsList.addAll(Arrays.asList(couldBeLots));"));
		assertTrue(output.contains("OperationOneResponse result = new OperationOneResponse();"));
		assertTrue(output.contains("MySpecialServiceProviderAPI service = getService(responseDetails);"));
		assertTrue(output.contains("service.operationOne(id, numbersAsList, onlyOne, couldBeLotsAsList, couldBeLotsOutAsList, responseDetails);"));
		assertTrue(output.contains("result.setCouldBeLotsOut(couldBeLotsOutAsList.toArray(new CouldBeLots[]{}));"));
		
		assertTrue(output.contains("public OperationTwoResponse operationTwo(T24UserDetails userDetails) {"));
		assertTrue(output.contains("Integer idOut;"));
		assertTrue(output.contains("OperationTwoResponse result = new OperationTwoResponse();"));
		assertTrue(output.contains("MySpecialServiceProviderAPI service = getService(responseDetails);"));
		assertTrue(output.contains("service.operationTwo(idOut, responseDetails);"));
		assertTrue(output.contains("result.setIdOut(idOut);"));
		assertTrue(output.contains("private MySpecialServiceProviderAPI getService(ResponseDetails responseDetails) {"));
		assertTrue(output.contains("return ( (MySpecialServiceProviderAPI) MySpecialServiceSpringContext.getContext().getBean(\"MySpecialServiceProvider\") );"));
	}
}
