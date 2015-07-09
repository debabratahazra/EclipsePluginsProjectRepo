package com.odcgroup.service.gen.t24.internal.cartridges.cpp;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLServiceCppDescriptor;
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
import com.odcgroup.service.model.component.AccessSpecifier;

public class CppServiceAPIGeneratorTest extends TestCase {
	private UMLServiceCppDescriptor umlService;
	private UMLServiceCppDescriptor umlServiceExternal;
	private CppServiceAPIGenerator generator;
	
	//construct uml service model
	protected void setUp() {
		generator = new CppServiceAPIGenerator(new TemplateOutsideJarLoader());
		List<AttributeDescriptor> attributes = 
			new ArrayList<AttributeDescriptor>();
		//member is single string
		attributes.add(
				new AttributeDescriptor("strAttr", "String", Cardinality.SINGLE));
		//member is collection of string
		attributes.add(
				new AttributeDescriptor("strListAttr", "String", Cardinality.MULTIPLE));
		//member is single int
		attributes.add(
				new AttributeDescriptor("intAttr", "Integer", Cardinality.SINGLE));
		//member is collection of int
		attributes.add(
				new AttributeDescriptor("intListAttr", "Integer", Cardinality.MULTIPLE));
		//member is single boolean
		attributes.add(
				new AttributeDescriptor("boolAttr", "Boolean", Cardinality.SINGLE));
		//member is collection of boolean
		attributes.add(
				new AttributeDescriptor("boolListAttr", "Boolean", Cardinality.MULTIPLE));
		
		
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		//parameter is single int
		params.add(new ParamDescriptor("intPara", "Integer", 
																	Cardinality.SINGLE, Direction.IN, 
																	Complexity.PRIMITIVE, false, null));
		//parameter is collection of int
		params.add(new ParamDescriptor("intListPara", "Integer", 
																	Cardinality.MULTIPLE, Direction.IN, 
																	Complexity.PRIMITIVE, false, null));
		//parameter is single boolean
		params.add(new ParamDescriptor("boolPara", "Boolean", 
																	Cardinality.SINGLE, Direction.IN, 
																	Complexity.PRIMITIVE, false, null));
		//parameter is collection of boolean
		params.add(new ParamDescriptor("boolListPara", "Boolean", 
																	Cardinality.MULTIPLE, Direction.IN, 
																	Complexity.PRIMITIVE, false, null));
		//parameter is single string
		params.add(new ParamDescriptor("stringPara", "String", 
																	Cardinality.SINGLE, Direction.IN, 
																	Complexity.PRIMITIVE, false, null));
		//parameter is collection of string
		params.add(new ParamDescriptor("stringListPara", "String", 
																	Cardinality.MULTIPLE, Direction.IN, 
																	Complexity.PRIMITIVE, false, null));
		//parameter is complex type
		params.add(new ParamDescriptor("complexPara", "MyComplexType", 
																	Cardinality.SINGLE, Direction.IN, 
																	Complexity.COMPLEX, false, null));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		//define one operation
		operations.add(new OperationDescriptor("MyService", "op1", params, null));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		//define one complex type
		classDefDescriptors.add(new ClassDefDescriptor("MyComplexType", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MyService", operations, classDefDescriptors);
		serviceDescriptor.setComponentName("MyService");
		umlService = new UMLServiceCppDescriptor(serviceDescriptor);

		//Setup Service Descriptor for External interface Generation

		List<OperationDescriptor> operations2 = new ArrayList<OperationDescriptor>();
		//define one operation
		operations2.add(new OperationDescriptor("MyService", "op1", params, null,AccessSpecifier.EXTERNAL));

		ServiceDescriptor serviceDescriptorExternal = new ServiceDescriptor("MyService", operations, classDefDescriptors);
		serviceDescriptorExternal.setComponentName("MyService");
		umlServiceExternal = new UMLServiceCppDescriptor(serviceDescriptorExternal);
	}
	
	protected void tearDown() {		
	}
	
	public void testGenServiceAPI() throws LoadTemplateException {
		//test code generator to service API	
		Writer outputWriter = new StringWriter();
		
		generator.genServiceAPIFile(umlService, outputWriter);																
	    	
	  String output = outputWriter.toString();
	  
	  assertTrue(
	  		output.contains("virtual ~MyServiceAPI(){}"));
	  assertTrue(
	  		output.contains("virtual std::string GetMetaData() = 0;"));
	  assertTrue(
	  		output.contains("DLLDECL MyServiceAPI* createMyService(void);"));
    assertTrue(
    		output.contains("DLLDECL void destroyMyService(MyServiceAPI* myService);"));
	}
	
	public void testGenServiceImplHeader() throws LoadTemplateException {
		//test code generator to service impl .h	
		Writer outputWriter = new StringWriter();
		
		generator.genServiceImplHeaderFile(umlService, outputWriter);																
	    	
	  String output = outputWriter.toString();	  
	  assertTrue(output.contains("virtual void Op1"));
	  assertTrue(output.contains("std::string GetMetaData();"));
	  //assertTrue(output.contains("jBASEDataAreas *dp;"));
	  assertTrue(output.contains("tafc::session::TAFCSession* session;"));
	}
	
	public void testGenServiceImplCpp() throws LoadTemplateException {
		//test code generator to service impl .cpp	
		Writer outputWriter = new StringWriter();
		
		generator.genServiceImplCppFile(umlService, outputWriter);																
	    	
	  String output = outputWriter.toString();	  
	  assertTrue(output.contains("T24MyServiceImpl.op1"));
	  //assertTrue(output.contains("dp = const_cast<jBASEDataAreas*> (session->getDp());"));
	  assertTrue(output.contains("delete session;"));
	}

	public void testGenServiceAPIExternal() throws LoadTemplateException {
		//test code generator to service API
		Writer outputWriter = new StringWriter();

		generator.genServiceAPIFile(umlServiceExternal, outputWriter);

	  String output = outputWriter.toString();

	  assertTrue(
			output.contains("virtual ~MyServiceAPI(){}"));
	  assertTrue(
			output.contains("virtual std::string GetMetaData() = 0;"));
	  assertTrue(
			output.contains("DLLDECL MyServiceAPI* createMyService(void);"));
    assertTrue(
		output.contains("DLLDECL void destroyMyService(MyServiceAPI* myService);"));
	}

	public void testGenServiceImplHeaderExternal() throws LoadTemplateException {
		//test code generator to service impl .h
		Writer outputWriter = new StringWriter();

		generator.genServiceImplHeaderFile(umlServiceExternal, outputWriter);

	  String output = outputWriter.toString();
	  assertTrue(output.contains("virtual void Op1"));
	  assertTrue(output.contains("std::string GetMetaData();"));
	  //assertTrue(output.contains("jBASEDataAreas *dp;"));
	  assertTrue(output.contains("tafc::session::TAFCSession* session;"));
	}

	public void testGenServiceImplCppExternal() throws LoadTemplateException {
		//test code generator to service impl .cpp
		Writer outputWriter = new StringWriter();

		generator.genServiceImplCppFile(umlServiceExternal, outputWriter);

	  String output = outputWriter.toString();
	  assertTrue(output.contains("T24MyServiceImpl.op1"));
	  //assertTrue(output.contains("dp = const_cast<jBASEDataAreas*> (session->getDp());"));
	  assertTrue(output.contains("delete session;"));
	}
}
