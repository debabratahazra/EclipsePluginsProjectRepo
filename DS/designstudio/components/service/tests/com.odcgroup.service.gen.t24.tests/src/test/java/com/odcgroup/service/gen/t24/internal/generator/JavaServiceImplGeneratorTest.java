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

public class JavaServiceImplGeneratorTest {

	@Test
	public void testGenerateImpl() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();

		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> paramsOne = new ArrayList<ParamDescriptor>();
		List<ParamDescriptor> returnParams = new ArrayList<ParamDescriptor>();
		paramsOne.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("name", "String", Cardinality.SINGLE,	Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("names", "String", Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("number", "Integer", Cardinality.SINGLE, Direction.INOUT, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("numbers", "Integer",	Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("myInOutBoolean", "Boolean", Cardinality.SINGLE, Direction.INOUT, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("boolList", "Boolean",	Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("onlyOne", "OnlyOne",	Cardinality.SINGLE, Direction.INOUT, Complexity.COMPLEX, false, null));
		paramsOne.add(new ParamDescriptor("couldBeLots", "CouldBeLots",	Cardinality.MULTIPLE, Direction.INOUT, Complexity.COMPLEX, true, null));
		paramsOne.add(new ParamDescriptor("class1", "ComplexClass",	Cardinality.SINGLE, Direction.INOUT, Complexity.COMPLEX, false, null));
		paramsOne.add(new ParamDescriptor("class2", "ComplexClass",	Cardinality.SINGLE, Direction.INOUT, Complexity.COMPLEX, false, null));
		paramsPerOperation.add(paramsOne);

		List<ParamDescriptor> paramsTwo = new ArrayList<ParamDescriptor>();
		paramsPerOperation.add(paramsTwo);
		returnParams.add(new ParamDescriptor("", "Integer", Cardinality.SINGLE,	Direction.RETURN, Complexity.PRIMITIVE, false, null));

		List<ParamDescriptor> paramsThree = new ArrayList<ParamDescriptor>(); // Allows us to test List is not imported more than once into the Impl class
		paramsThree.add(new ParamDescriptor("severalStrings", "String",	Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, true, null));
		paramsThree.add(new ParamDescriptor("severalNumbers", "Integer", Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsThree.add(new ParamDescriptor("severalBooleanss", "Boolean", Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsPerOperation.add(paramsThree);

		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", paramsOne, new ParamDescriptor("", "String",	Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));
		operations.add(new OperationDescriptor("MySpecialService", "operationTwo", paramsTwo, new ParamDescriptor("", "Integer", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE,	false, null)));
		operations.add(new OperationDescriptor("MySpecialService", "operationThree", paramsThree, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));

		// Set up some attributes which we can use for the
		// class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String",	Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("OnlyOne", "Class", attributes));
		classDefDescriptors.add(new ClassDefDescriptor("CouldBeLots", "Class", attributes));
		classDefDescriptors.add(new ClassDefDescriptor("ComplexClass", "Class",	attributes));

		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);

		JavaServiceImplGenerator generator = new JavaServiceImplGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");

		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("import java.util.List;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.OnlyOne;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.converter.OnlyOneConverter;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.CouldBeLots;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.converter.CouldBeLotsConverter;"));
		assertTrue(output.contains("public class MySpecialServiceImpl implements MySpecialServiceAPI"));
		assertTrue(output.contains("public String operationOne(String id, String name, List<String> names, Integer number, List<Integer> numbers, Boolean myInOutBoolean, List<Boolean> boolList, OnlyOne onlyOne, List<CouldBeLots> couldBeLots, ComplexClass class1, ComplexClass class2, ResponseDetails responseDetails) {"));
		assertTrue(output.contains("jVarClient jVarClientCouldBeLots = couldBeLotsConverterObj.toJVarClient(couldBeLots);"));
		assertTrue(output.contains("jVarClient jVarClientClass1 = class1ConverterObj.toJVarClient(class1);"));
		assertTrue(output.contains("getRuntime().callJBC(\"T24MySpecialServiceImpl.operationOne\","));
		assertTrue(output.contains("if ( ( id == null ) || ( id.equals(\"\") ) ) {"));
		assertTrue(output.contains("String responseDetailsResponseText = \"'id' is a mandatory field\";"));
		assertTrue(output.contains("responseDetails.addError(new Response(CFConstants.T24_EB_MANDATORY_FIELD_MISSING_CODE, CFConstants.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, uUId));"));
		assertTrue(output.contains("public Integer operationTwo(ResponseDetails responseDetails) {"));
		assertTrue(output.contains("getRuntime().callJBC(\"T24MySpecialServiceImpl.operationTwo\","));
		assertTrue(output.contains("return Integer.parseInt( resultString );"));
	}

	@Test
	public void testGenerateImplExternal() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();

		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> paramsOne = new ArrayList<ParamDescriptor>();
		List<ParamDescriptor> returnParams = new ArrayList<ParamDescriptor>();
		paramsOne.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("name", "String", Cardinality.SINGLE,	Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("names", "String", Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("number", "Integer", Cardinality.SINGLE, Direction.INOUT, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("numbers", "Integer",	Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("myInOutBoolean", "Boolean", Cardinality.SINGLE, Direction.INOUT, Complexity.PRIMITIVE, true, null));
		paramsOne.add(new ParamDescriptor("boolList", "Boolean",	Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsOne.add(new ParamDescriptor("onlyOne", "OnlyOne",	Cardinality.SINGLE, Direction.INOUT, Complexity.COMPLEX, false, null));
		paramsOne.add(new ParamDescriptor("couldBeLots", "CouldBeLots",	Cardinality.MULTIPLE, Direction.INOUT, Complexity.COMPLEX, true, null));
		paramsOne.add(new ParamDescriptor("class1", "ComplexClass",	Cardinality.SINGLE, Direction.INOUT, Complexity.COMPLEX, false, null));
		paramsOne.add(new ParamDescriptor("class2", "ComplexClass",	Cardinality.SINGLE, Direction.INOUT, Complexity.COMPLEX, false, null));
		paramsPerOperation.add(paramsOne);

		List<ParamDescriptor> paramsTwo = new ArrayList<ParamDescriptor>();
		paramsPerOperation.add(paramsTwo);
		returnParams.add(new ParamDescriptor("", "Integer", Cardinality.SINGLE,	Direction.RETURN, Complexity.PRIMITIVE, false, null));

		List<ParamDescriptor> paramsThree = new ArrayList<ParamDescriptor>(); // Allows us to test List is not imported more than once into the Impl class
		paramsThree.add(new ParamDescriptor("severalStrings", "String",	Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, true, null));
		paramsThree.add(new ParamDescriptor("severalNumbers", "Integer", Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsThree.add(new ParamDescriptor("severalBooleanss", "Boolean", Cardinality.MULTIPLE, Direction.INOUT, Complexity.PRIMITIVE, false, null));
		paramsPerOperation.add(paramsThree);

		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", paramsOne, new ParamDescriptor("", "String",	Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null),AccessSpecifier.EXTERNAL));
		operations.add(new OperationDescriptor("MySpecialService", "operationTwo", paramsTwo, new ParamDescriptor("", "Integer", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE,	false, null),AccessSpecifier.EXTERNAL));
		operations.add(new OperationDescriptor("MySpecialService", "operationThree", paramsThree, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null),AccessSpecifier.EXTERNAL));

		// Set up some attributes which we can use for the
		// class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String",	Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("OnlyOne", "Class", attributes));
		classDefDescriptors.add(new ClassDefDescriptor("CouldBeLots", "Class", attributes));
		classDefDescriptors.add(new ClassDefDescriptor("ComplexClass", "Class",	attributes));

		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors,"1.30.7");

		JavaServiceImplGenerator generator = new JavaServiceImplGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");

		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("import java.util.List;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.OnlyOne;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.converter.OnlyOneConverter;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.CouldBeLots;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.converter.CouldBeLotsConverter;"));
		assertTrue(output.contains("public class MySpecialServiceImpl implements MySpecialServiceAPI"));
		assertTrue(output.contains("public String operationOne(String id, String name, List<String> names, Integer number, List<Integer> numbers, Boolean myInOutBoolean, List<Boolean> boolList, OnlyOne onlyOne, List<CouldBeLots> couldBeLots, ComplexClass class1, ComplexClass class2, ResponseDetails responseDetails) {"));
		assertTrue(output.contains("jVarClient jVarClientCouldBeLots = couldBeLotsConverterObj.toJVarClient(couldBeLots);"));
		assertTrue(output.contains("jVarClient jVarClientClass1 = class1ConverterObj.toJVarClient(class1);"));
		assertTrue(output.contains("getRuntime().callJBC(\"T24MySpecialServiceImpl.operationOne\","));
		assertTrue(output.contains("if ( ( id == null ) || ( id.equals(\"\") ) ) {"));
		assertTrue(output.contains("String responseDetailsResponseText = \"'id' is a mandatory field\";"));
		assertTrue(output.contains("responseDetails.addError(new Response(CFConstants.T24_EB_MANDATORY_FIELD_MISSING_CODE, CFConstants.RESPONSE_TYPE_FATAL_ERROR, responseDetailsResponseText, uUId));"));
		assertTrue(output.contains("public Integer operationTwo(ResponseDetails responseDetails) {"));
		assertTrue(output.contains("getRuntime().callJBC(\"T24MySpecialServiceImpl.operationTwo\","));
		assertTrue(output.contains("return Integer.parseInt( resultString );"));
	}
}
