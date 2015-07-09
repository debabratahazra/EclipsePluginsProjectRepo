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
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public class JavaDataClassGeneratorTest {
	@Test
	public void testDataClassGenerate() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<OperationDescriptor> operations = null;		
		
		//Set up some attributes which we can use for the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("onlyOne", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("oneNumber", "Integer", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("oneBoolean", "Boolean", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("couldBeLots", "String", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("couldBeLotsMore", "Integer", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("couldBeLotsBooleans", "Boolean", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("class1", "ComplexClass", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("class2", "ComplexClass", Cardinality.SINGLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("ComplexClass", "Class", attributes));

		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors);

		JavaDataClassGenerator generator = new JavaDataClassGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data;"));
		assertTrue(output.contains("public class ComplexClass"));
		assertTrue(output.contains("private String onlyOne;"));
		assertTrue(output.contains("private Integer oneNumber;"));
		assertTrue(output.contains("private Boolean oneBoolean;"));
		assertTrue(output.contains("private List<String> couldBeLots;"));
		assertTrue(output.contains("private List<Integer> couldBeLotsMore;"));
		assertTrue(output.contains("private List<Boolean> couldBeLotsBooleans;"));
		assertTrue(output.contains("private ComplexClass class1;"));
		assertTrue(output.contains("private ComplexClass class2;"));
		assertTrue(output.contains("public ComplexClass(String onlyOne, Integer oneNumber, Boolean oneBoolean, String[] couldBeLots, Integer[] couldBeLotsMore, Boolean[] couldBeLotsBooleans, ComplexClass class1, ComplexClass class2) {"));
		assertTrue(output.contains("this.onlyOne = onlyOne;"));
		assertTrue(output.contains("this.oneNumber = oneNumber;"));
		assertTrue(output.contains("this.oneBoolean = oneBoolean;"));
		assertTrue(output.contains("Collections.addAll(this.couldBeLots, couldBeLots);"));
		assertTrue(output.contains("Collections.addAll(this.couldBeLotsMore, couldBeLotsMore);"));
		assertTrue(output.contains("Collections.addAll(this.couldBeLotsBooleans, couldBeLotsBooleans);"));
		assertTrue(output.contains("this.class1 = class1;"));
		assertTrue(output.contains("this.class2 = class2;"));
		assertTrue(output.contains("public ComplexClass()"));
		assertTrue(output.contains("this.onlyOne = \"\""));
		assertTrue(output.contains("this.oneNumber = 0"));
		assertTrue(output.contains("this.oneBoolean = false"));
		assertTrue(output.contains("this.couldBeLots = new ArrayList<String>();"));
		assertTrue(output.contains("this.couldBeLotsMore = new ArrayList<Integer>();"));
		assertTrue(output.contains("this.couldBeLotsBooleans = new ArrayList<Boolean>();"));
		assertTrue(output.contains("this.class1 = new ComplexClass();"));
		assertTrue(output.contains("this.class2 = new ComplexClass();"));
		assertTrue(output.contains("public String getOnlyOne(){"));
		assertTrue(output.contains("return onlyOne;"));
		assertTrue(output.contains("public Boolean getOneBoolean(){"));
		assertTrue(output.contains("return oneBoolean;"));
		assertTrue(output.contains("public String[] getCouldBeLots(){"));
		assertTrue(output.contains("return couldBeLots.toArray(new String[]{});"));
		assertTrue(output.contains("public Integer[] getCouldBeLotsMore(){"));
		assertTrue(output.contains("return couldBeLotsMore.toArray(new Integer[]{});"));
		assertTrue(output.contains("public Boolean[] getCouldBeLotsBooleans(){"));
		assertTrue(output.contains("return couldBeLotsBooleans.toArray(new Boolean[]{});"));
		assertTrue(output.contains("public ComplexClass getClass1(){"));
		assertTrue(output.contains("return class1;"));
		assertTrue(output.contains("public ComplexClass getClass2(){"));
		assertTrue(output.contains("return class2;"));
		assertTrue(output.contains("public void setOnlyOne(String newOnlyOne){"));
		assertTrue(output.contains("onlyOne = newOnlyOne;"));
		assertTrue(output.contains("public void setOneBoolean(Boolean newOneBoolean){"));
		assertTrue(output.contains("oneBoolean = newOneBoolean;"));
		assertTrue(output.contains("public void setCouldBeLots(String[] newCouldBeLots){"));
		assertTrue(output.contains("Collections.addAll(couldBeLots, newCouldBeLots);"));
		assertTrue(output.contains("public void setCouldBeLotsMore(Integer[] newCouldBeLotsMore){"));
		assertTrue(output.contains("Collections.addAll(couldBeLotsMore, newCouldBeLotsMore);"));
		assertTrue(output.contains("public void setCouldBeLotsBooleans(Boolean[] newCouldBeLotsBooleans){"));
		assertTrue(output.contains("Collections.addAll(couldBeLotsBooleans, newCouldBeLotsBooleans);"));
		assertTrue(output.contains("public void setClass1(ComplexClass newClass1){"));
		assertTrue(output.contains("class1 = newClass1;"));
		assertTrue(output.contains("public void setClass2(ComplexClass newClass2){"));
		assertTrue(output.contains("class2 = newClass2;"));
	}

	@Test
	public void testDataClassGenerateExternal() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();

		List<OperationDescriptor> operations = null;

		//Set up some attributes which we can use for the class-definition-attributes.
		List<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
		attributes.add(new AttributeDescriptor("onlyOne", "String", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("oneNumber", "Integer", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("oneBoolean", "Boolean", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("couldBeLots", "String", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("couldBeLotsMore", "Integer", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("couldBeLotsBooleans", "Boolean", Cardinality.MULTIPLE));
		attributes.add(new AttributeDescriptor("class1", "ComplexClass", Cardinality.SINGLE));
		attributes.add(new AttributeDescriptor("class2", "ComplexClass", Cardinality.SINGLE));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		classDefDescriptors.add(new ClassDefDescriptor("ComplexClass", "Class", attributes));

		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, classDefDescriptors,"1.30.7");

		JavaDataClassGenerator generator = new JavaDataClassGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");

		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data;"));
		assertTrue(output.contains("public class ComplexClass"));
		assertTrue(output.contains("private String onlyOne;"));
		assertTrue(output.contains("private Integer oneNumber;"));
		assertTrue(output.contains("private Boolean oneBoolean;"));
		assertTrue(output.contains("private List<String> couldBeLots;"));
		assertTrue(output.contains("private List<Integer> couldBeLotsMore;"));
		assertTrue(output.contains("private List<Boolean> couldBeLotsBooleans;"));
		assertTrue(output.contains("private ComplexClass class1;"));
		assertTrue(output.contains("private ComplexClass class2;"));
		assertTrue(output.contains("public ComplexClass(String onlyOne, Integer oneNumber, Boolean oneBoolean, String[] couldBeLots, Integer[] couldBeLotsMore, Boolean[] couldBeLotsBooleans, ComplexClass class1, ComplexClass class2) {"));
		assertTrue(output.contains("this.onlyOne = onlyOne;"));
		assertTrue(output.contains("this.oneNumber = oneNumber;"));
		assertTrue(output.contains("this.oneBoolean = oneBoolean;"));
		assertTrue(output.contains("Collections.addAll(this.couldBeLots, couldBeLots);"));
		assertTrue(output.contains("Collections.addAll(this.couldBeLotsMore, couldBeLotsMore);"));
		assertTrue(output.contains("Collections.addAll(this.couldBeLotsBooleans, couldBeLotsBooleans);"));
		assertTrue(output.contains("this.class1 = class1;"));
		assertTrue(output.contains("this.class2 = class2;"));
		assertTrue(output.contains("public ComplexClass()"));
		assertTrue(output.contains("this.onlyOne = \"\""));
		assertTrue(output.contains("this.oneNumber = 0"));
		assertTrue(output.contains("this.oneBoolean = false"));
		assertTrue(output.contains("this.couldBeLots = new ArrayList<String>();"));
		assertTrue(output.contains("this.couldBeLotsMore = new ArrayList<Integer>();"));
		assertTrue(output.contains("this.couldBeLotsBooleans = new ArrayList<Boolean>();"));
		assertTrue(output.contains("this.class1 = new ComplexClass();"));
		assertTrue(output.contains("this.class2 = new ComplexClass();"));
		assertTrue(output.contains("public String getOnlyOne(){"));
		assertTrue(output.contains("return onlyOne;"));
		assertTrue(output.contains("public Boolean getOneBoolean(){"));
		assertTrue(output.contains("return oneBoolean;"));
		assertTrue(output.contains("public String[] getCouldBeLots(){"));
		assertTrue(output.contains("return couldBeLots.toArray(new String[]{});"));
		assertTrue(output.contains("public Integer[] getCouldBeLotsMore(){"));
		assertTrue(output.contains("return couldBeLotsMore.toArray(new Integer[]{});"));
		assertTrue(output.contains("public Boolean[] getCouldBeLotsBooleans(){"));
		assertTrue(output.contains("return couldBeLotsBooleans.toArray(new Boolean[]{});"));
		assertTrue(output.contains("public ComplexClass getClass1(){"));
		assertTrue(output.contains("return class1;"));
		assertTrue(output.contains("public ComplexClass getClass2(){"));
		assertTrue(output.contains("return class2;"));
		assertTrue(output.contains("public void setOnlyOne(String newOnlyOne){"));
		assertTrue(output.contains("onlyOne = newOnlyOne;"));
		assertTrue(output.contains("public void setOneBoolean(Boolean newOneBoolean){"));
		assertTrue(output.contains("oneBoolean = newOneBoolean;"));
		assertTrue(output.contains("public void setCouldBeLots(String[] newCouldBeLots){"));
		assertTrue(output.contains("Collections.addAll(couldBeLots, newCouldBeLots);"));
		assertTrue(output.contains("public void setCouldBeLotsMore(Integer[] newCouldBeLotsMore){"));
		assertTrue(output.contains("Collections.addAll(couldBeLotsMore, newCouldBeLotsMore);"));
		assertTrue(output.contains("public void setCouldBeLotsBooleans(Boolean[] newCouldBeLotsBooleans){"));
		assertTrue(output.contains("Collections.addAll(couldBeLotsBooleans, newCouldBeLotsBooleans);"));
		assertTrue(output.contains("public void setClass1(ComplexClass newClass1){"));
		assertTrue(output.contains("class1 = newClass1;"));
		assertTrue(output.contains("public void setClass2(ComplexClass newClass2){"));
		assertTrue(output.contains("class2 = newClass2;"));
	}
}
