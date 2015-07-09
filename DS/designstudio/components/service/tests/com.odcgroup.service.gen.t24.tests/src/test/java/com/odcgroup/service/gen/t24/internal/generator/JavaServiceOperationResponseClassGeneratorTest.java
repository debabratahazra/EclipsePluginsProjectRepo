package com.odcgroup.service.gen.t24.internal.generator;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.Complexity;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public class JavaServiceOperationResponseClassGeneratorTest {
	@Test
	public void testGenerateIntegerReturn() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("integerOut", "Integer", Cardinality.SINGLE, Direction.OUT, Complexity.PRIMITIVE, false, null));
		paramsPerOperation.add(params);
				
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", params, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));
		
		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, null);

		JavaServiceOperationResponseClassGenerator generator = new JavaServiceOperationResponseClassGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data.response;"));
		assertTrue(output.contains("public class OperationOneResponse"));
		assertTrue(output.contains("Integer integerOut;"));
		assertTrue(output.contains("ResponseDetails responseDetails;"));
		assertTrue(output.contains("public Integer getIntegerOut() {"));
		assertTrue(output.contains("return integerOut;"));
		assertTrue(output.contains("public void setIntegerOut(Integer integerOut) {"));
		assertTrue(output.contains("this.integerOut = integerOut;"));
	}
	
	@Test
	public void testGenerateStringReturn() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("stringOut", "String", Cardinality.SINGLE, Direction.OUT, Complexity.PRIMITIVE, false, null));
		paramsPerOperation.add(params);
				
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", params, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));
		
		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, null);

		JavaServiceOperationResponseClassGenerator generator = new JavaServiceOperationResponseClassGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data.response;"));
		assertTrue(output.contains("public class OperationOneResponse"));
		assertTrue(output.contains("String stringOut;"));
		assertTrue(output.contains("ResponseDetails responseDetails;"));
		assertTrue(output.contains("public String getStringOut() {"));
		assertTrue(output.contains("return stringOut;"));
		assertTrue(output.contains("public void setStringOut(String stringOut) {"));
		assertTrue(output.contains("this.stringOut = stringOut;"));
	}

	@Test
	public void testGenerateComplexReturn() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("complextOut", "CouldBeLots", Cardinality.SINGLE, Direction.OUT, Complexity.COMPLEX, false, null));
		paramsPerOperation.add(params);
				
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", params, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));
		
		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, null);

		JavaServiceOperationResponseClassGenerator generator = new JavaServiceOperationResponseClassGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data.response;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.CouldBeLots;"));
		assertTrue(output.contains("public class OperationOneResponse"));
		assertTrue(output.contains("CouldBeLots complextOut;"));
		assertTrue(output.contains("ResponseDetails responseDetails;"));
		assertTrue(output.contains("public CouldBeLots getComplextOut() {"));
		assertTrue(output.contains("return complextOut;"));
		assertTrue(output.contains("public void setComplextOut(CouldBeLots complextOut) {"));
		assertTrue(output.contains("this.complextOut = complextOut;"));
	}

	@Test
	public void testGenerateIntegerArrayReturn() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("integerArrayOut", "Integer", Cardinality.MULTIPLE, Direction.OUT, Complexity.PRIMITIVE, false, null));
		paramsPerOperation.add(params);
				
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", params, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));
		
		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, null);
	
		JavaServiceOperationResponseClassGenerator generator = new JavaServiceOperationResponseClassGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data.response;"));
		assertTrue(output.contains("public class OperationOneResponse"));
		assertTrue(output.contains("Integer[] integerArrayOut;"));
		assertTrue(output.contains("ResponseDetails responseDetails;"));
		assertTrue(output.contains("public Integer[] getIntegerArrayOut() {"));
		assertTrue(output.contains("return integerArrayOut;"));
		assertTrue(output.contains("public void setIntegerArrayOut(Integer[] integerArrayOut) {"));
		assertTrue(output.contains("this.integerArrayOut = integerArrayOut;"));
	}
	
	@Test
	public void testGenerateStringArrayReturn() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("stringArrayOut", "String", Cardinality.MULTIPLE, Direction.OUT, Complexity.PRIMITIVE, false, null));
		paramsPerOperation.add(params);
				
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", params, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));
		
		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, null);
	
		JavaServiceOperationResponseClassGenerator generator = new JavaServiceOperationResponseClassGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data.response;"));
		assertTrue(output.contains("public class OperationOneResponse"));
		assertTrue(output.contains("String[] stringArrayOut;"));
		assertTrue(output.contains("ResponseDetails responseDetails;"));
		assertTrue(output.contains("public String[] getStringArrayOut() {"));
		assertTrue(output.contains("return stringArrayOut;"));
		assertTrue(output.contains("public void setStringArrayOut(String[] stringArrayOut) {"));
		assertTrue(output.contains("this.stringArrayOut = stringArrayOut;"));
	}
	
	@Test
	public void testGenerateComplexArrayReturn() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		List<List<ParamDescriptor>> paramsPerOperation = new ArrayList<List<ParamDescriptor>>();
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		params.add(new ParamDescriptor("id", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null));
		params.add(new ParamDescriptor("complextArrayOut", "CouldBeLots", Cardinality.MULTIPLE, Direction.OUT, Complexity.COMPLEX, false, null));
		paramsPerOperation.add(params);
				
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		operations.add(new OperationDescriptor("MySpecialService", "operationOne", params, new ParamDescriptor("", "String", Cardinality.SINGLE, Direction.RETURN, Complexity.PRIMITIVE, false, null)));
		
		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", operations, null);
	
		JavaServiceOperationResponseClassGenerator generator = new JavaServiceOperationResponseClassGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("package com.temenos.services.myspecial.data.response;"));
		assertTrue(output.contains("import com.temenos.services.myspecial.data.CouldBeLots;"));
		assertTrue(output.contains("public class OperationOneResponse"));
		assertTrue(output.contains("CouldBeLots[] complextArrayOut;"));
		assertTrue(output.contains("ResponseDetails responseDetails;"));
		assertTrue(output.contains("public CouldBeLots[] getComplextArrayOut() {"));
		assertTrue(output.contains("return complextArrayOut;"));
		assertTrue(output.contains("public void setComplextArrayOut(CouldBeLots[] complextArrayOut) {"));
		assertTrue(output.contains("this.complextArrayOut = complextArrayOut;"));
	}
}
