package com.odcgroup.service.gen.t24.internal.cartridges.cpp;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLOperationCppDescriptor;
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

public class CppServiceProxyGeneratorTest extends TestCase {
	private UMLServiceCppDescriptor umlService;
	private CppServiceProxyGenerator generator;
	
	//construct uml service model
	protected void setUp() {
		generator = new CppServiceProxyGenerator(new TemplateOutsideJarLoader());
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
	}
	
	protected void tearDown() {		
	}  
	
	//test code generator to generate JBC interface for c++ proxy adaptor
	public void testGenProxyAdaptorJBCInterface() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
    	
		UMLOperationCppDescriptor[] umlOperations = umlService.getOperations();
		UMLOperationCppDescriptor umlOperation = umlOperations[0];
        
		generator.genProxyAdaptorJBCInterface(umlOperation, outputWriter);
    	
		String output = outputWriter.toString();		
		
		assertTrue(output.contains("CALLC myServiceProxyAdaptorOp1(intPara, intListPara, boolPara, boolListPara, stringPara, stringListPara, complexPara, responseDetail)"));		
	}
  
	//test code generator to generate proxy adaptor .h file
	public void testGenServiceProxyAdaptorHeader() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();    	
        
		generator.genServiceProxyAdaptorHeader(umlService, outputWriter);
    	
		String output = outputWriter.toString();    	
		
		//test if C interface of proxy adaptor is declared correctly
		assertTrue(output.contains("myServiceProxyAdaptorOp1"));
		assertTrue(output.contains("VAR* resultVar"));
		assertTrue(output.contains("VAR* intParaVar"));
		assertTrue(output.contains("VAR* intListParaVar"));
		assertTrue(output.contains("VAR* boolParaVar"));
		assertTrue(output.contains("VAR* boolListParaVar"));
		assertTrue(output.contains("VAR* stringParaVar"));
		assertTrue(output.contains("VAR* stringListParaVar"));
		assertTrue(output.contains("VAR* complexParaVar"));
		assertTrue(output.contains("VAR* responseDetailVar"));
	}
  
	//test code generator to generate proxy adaptor .cpp file
  public void testGenServiceProxyAdaptorCpp() throws LoadTemplateException {
  	Writer outputWriter = new StringWriter();    	
        
  	generator.genServiceProxyAdaptorCpp(umlService, outputWriter);
    	
  	String output = outputWriter.toString();    	
		
  	//test if declaration of single and collection c++ parameter are correct
		assertTrue(output.contains("int intPara = 0"));
		assertTrue(output.contains("std::vector<int> intListPara"));
		
		//test if the conversion from JBC to C++ for single parameter is correct
		assertTrue(output.contains("ConvertVarToPrimitive(session, intParaVar, intPara)"));
		
		//test if the conversion from JBC to C++ for collection parameter is correct
		assertTrue(output.contains("VARObject intListParaItemObj(session)"));
		assertTrue(output.contains("ExtractVarFromVar(session, intListParaItemObj.Get(), index + 1, intListParaVar)"));
		assertTrue(output.contains("ConvertVarToPrimitive(session, intListParaItemObj.Get(), intListParaItem)"));
		assertTrue(output.contains("intListPara.push_back(intListParaItem)"));
		
		//test if the conversion from JBC to C++ for complex type is correct
		assertTrue(output.contains("MyComplexTypeHandler::FromVAR(session, complexParaVar, complexPara)"));
		
		//test if look up the method to create proxy instance is correct 
		assertTrue(output.contains("JediObjectFindFunction(session->getNonConstDp(), \"createMyServiceProxy\", 0)"));
		
		//test if invoking proxy method is correct
		assertTrue(output.contains("myServiceProxyInstance->Op1"));
		
		//test if catch all the exceptions
		assertTrue(output.contains("catch(...)"));	
  }
  
  //test code generator to generate proxy interface
  public void testGenServiceProxyInterface() throws LoadTemplateException {
  	Writer outputWriter = new StringWriter();    	
        
  	generator.genServiceProxyInterface(umlService, outputWriter);
    	
  	String output = outputWriter.toString();    	
		
  	//test if declaration for UML operaiton is correct
		assertTrue(output.contains("virtual void Op1"));
		assertTrue(output.contains("int intPara"));
		assertTrue(output.contains("const std::vector<int >& intListPara"));
		assertTrue(output.contains("bool boolPara"));
		assertTrue(output.contains("const std::vector<bool >& boolListPara"));
		assertTrue(output.contains("const std::string& stringPara"));
		assertTrue(output.contains("const std::vector<std::string >& stringListPara"));
		assertTrue(output.contains("const MyComplexType& complexPara"));
	  assertTrue(output.contains("temenos::soa::common::SOAResponseDetail& responseDetail"));
	  assertTrue(output.contains("= 0"));
		
  } 
  
}
