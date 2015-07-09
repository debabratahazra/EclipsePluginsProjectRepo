package com.odcgroup.service.gen.t24.internal.generator;

import static org.junit.Assert.*;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public class JavaDataClassConverterGeneratorTest {

	@Test
	public void testDataClassConverterGenerateImpl() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		// Set up some attributes which we can use for the
		// converter class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("anAttribute", "String",	Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("anotherAttribute", "String", Cardinality.SINGLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("OnlyOne", "Class", attributes));

		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", null, classDefDescriptors);

		JavaDataClassConverterGenerator generator = new JavaDataClassConverterGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");

		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data.converter;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.OnlyOne;"));
		assertTrue(output.contains("public class OnlyOneConverter implements JVarObject, JVarClientObject, JDynObject {"));
		assertTrue(output.contains("public void fromJVar(OnlyOne onlyOne, jVar jv) throws InvalidNestLevelException {"));
		assertTrue(output.contains("public void fromJVarClient(OnlyOne onlyOne, jVarClient jv) throws InvalidNestLevelException {"));
		assertTrue(output.contains("onlyOne.setAnotherAttribute(utils.getString(jv, ANOTHERATTRIBUTE_INDEX));"));
		assertTrue(output.contains("onlyOne.setAnotherAttribute(JVarClientUtils.getInstance().getString(jv, ANOTHERATTRIBUTE_INDEX));"));
		assertTrue(output.contains("public jVar toJVar(OnlyOne onlyOne) throws InvalidNestLevelException {"));
		assertTrue(output.contains("public jVarClient toJVarClient(OnlyOne onlyOne) throws InvalidNestLevelException {"));
		assertTrue(output.contains("utils.setValue(jv, ANATTRIBUTE_INDEX, onlyOne.getAnAttribute());"));
		assertTrue(output.contains("JVarClientUtils.getInstance().setValue(jv, ANATTRIBUTE_INDEX, onlyOne.getAnAttribute());"));
		assertTrue(output.contains("onlyOne.setAnAttribute(JDynUtils.getInstance().getString(jd, ANATTRIBUTE_INDEX));"));
		assertTrue(output.contains("onlyOne.setAnotherAttribute(JDynUtils.getInstance().getString(jd, fieldNum, ANOTHERATTRIBUTE_INDEX));"));
		assertTrue(output.contains("onlyOne.setAnotherAttribute(JDynUtils.getInstance().getString(jd, fieldNum, valueNum, ANOTHERATTRIBUTE_INDEX));"));
		assertTrue(output.contains("JDynUtils.getInstance().getJDynObjectList(jd, onlyOneConverterList, new OnlyOneConverter());"));
		assertTrue(output.contains("JDynUtils.getInstance().setValue(jd, ANOTHERATTRIBUTE_INDEX, JDynUtils.getInstance().getStringValue(onlyOne.getAnotherAttribute()));"));
		assertTrue(output.contains("JDynUtils.getInstance().setValue(jd, fieldNum, ANOTHERATTRIBUTE_INDEX, JDynUtils.getInstance().getStringValue(onlyOne.getAnotherAttribute()));"));
		assertTrue(output.contains("JDynUtils.getInstance().setValue(jd, fieldNum, valueNum, ANOTHERATTRIBUTE_INDEX, JDynUtils.getInstance().getStringValue(onlyOne.getAnotherAttribute()));"));
		assertTrue(output.contains("for (int i=0; i < onlyOneArray.length; i++ ) {"));
		assertTrue(output.contains("toJDyn (onlyOneArray[i], jd, fieldNum, i+1);"));
		assertTrue(output.contains("illegalNestLevel (\"Attempt to nest to Objects beyond allowable range\");"));
		assertTrue(output.contains("throw new InvalidNestLevelException (errorMessage);"));
	}
}
