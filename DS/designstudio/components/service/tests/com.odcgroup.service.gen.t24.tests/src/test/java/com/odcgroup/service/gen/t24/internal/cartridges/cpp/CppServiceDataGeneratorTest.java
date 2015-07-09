package com.odcgroup.service.gen.t24.internal.cartridges.cpp;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassCppDescriptor;
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

public class CppServiceDataGeneratorTest extends TestCase {
	private UMLServiceCppDescriptor umlService;
	private CppServiceDataGenerator generator;
	
	//construct uml service model
	protected void setUp() {
		generator = new CppServiceDataGenerator(new TemplateOutsideJarLoader());
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
	
	//test code generator to generate business object .h file
	public void testGenBusinessObjectHeader() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		UMLClassCppDescriptor[] umlClasses = umlService.getUMLClasses();

		UMLClassCppDescriptor umlClass = umlClasses[0];

    generator.genBusinessObjectHeader(umlService.getNamespace(), 
    																	umlClass,
    																	outputWriter);
    
    String output = outputWriter.toString();		
		
		//test whether each member has been declared correclty
		assertTrue(output.contains("std::string m_strAttr"));
		assertTrue(output.contains("std::vector<std::string> m_strListAttr"));
		assertTrue(output.contains("int m_intAttr"));
		assertTrue(output.contains("std::vector<int> m_intListAttr"));
		assertTrue(output.contains("bool m_boolAttr"));
		assertTrue(output.contains("std::vector<bool> m_boolListAttr"));
		
		//test whether the methods to operate on single member is declared correctly
		assertTrue(output.contains("std::string GetStrAttr(void) const"));
		assertTrue(output.contains("void SetStrAttr(const std::string& strAttr)"));
		
		//test whether the methods to operation on collection member is declared correctly
		assertTrue(output.contains("size_t SizeOfStrListAttr(void) const"));
		assertTrue(output.contains("std::string GetItemOfStrListAttr(size_t index) const"));
		assertTrue(output.contains("void AppendItemIntoStrListAttr(const std::string& strListAttrItem)"));
		assertTrue(output.contains("void AddItemIntoStrListAttrAt(const std::string& strListAttrItem, size_t index)"));
		assertTrue(output.contains("void RemoveItemFromStrListAttrAt(size_t index)"));
		assertTrue(output.contains("void ClearAllInStrListAttr()"));
	}
	
	//test code generator to generate business object .cpp file
	public void testGenBusinessObjectCpp() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
    	
		UMLClassCppDescriptor[] umlClasses = umlService.getUMLClasses();
		UMLClassCppDescriptor umlClass = umlClasses[0];
        
		generator.genBusinessObjectCpp(umlService.getNamespace(), 
																		umlClass, 
																		outputWriter);
    	
		String output = outputWriter.toString();    	
    	
		//test if getting and setting single member is correct
		assertTrue(output.contains("return m_strAttr"));
		assertTrue(output.contains("m_strAttr = strAttr"));
		
		//test if geting and setting collection member is correct
		assertTrue(output.contains("return m_strListAttr.size()"));
		assertTrue(output.contains("return m_strListAttr.at(index)"));
		assertTrue(output.contains("m_strListAttr.insert(m_strListAttr.begin() + index, strListAttrItem)"));
		assertTrue(output.contains("m_strListAttr.erase(m_strListAttr.begin() + index)"));
		assertTrue(output.contains("m_strListAttr.clear()"));		
		
	}
  
	//test code generator to generate business object handler .h file  
	public void testGenBusinessObjectHandlerHeader() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
    	
    UMLClassCppDescriptor[] umlClasses = umlService.getUMLClasses();
    UMLClassCppDescriptor umlClass = umlClasses[0];
        
    generator.genBusinessObjectHandlerHeader(umlService.getNamespace(), 
    																				umlClass, 
    																				outputWriter);
    	
    String output = outputWriter.toString();    	
    
    //test if generate ToVAR and FromVAR method
    assertTrue(output.contains("ToVAR"));
    assertTrue(output.contains("FromVAR"));
    
    //test if JBC and C++ variable is declared correctly
    assertTrue(output.contains("VAR* myComplexTypeVar"));
    assertTrue(output.contains("const MyComplexType& myComplexType"));
    assertTrue(output.contains("MyComplexType& myComplexType"));
    
    //test if index helper variables are declared correctly
    assertTrue(output.contains("static const int m_strAttrIndex"));
    assertTrue(output.contains("static const int m_strListAttrIndex"));
    assertTrue(output.contains("static const int m_intAttrIndex"));
    assertTrue(output.contains("static const int m_intListAttrIndex"));
    assertTrue(output.contains("static const int m_boolAttrIndex"));
    assertTrue(output.contains("static const int m_boolListAttrIndex"));
	}
  
	
	//test code generator to generate business object handler .cpp file
	public void testGenBusinessObjectHandlerCpp() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
    	
		UMLClassCppDescriptor[] umlClasses = umlService.getUMLClasses();
		UMLClassCppDescriptor umlClass = umlClasses[0];
        
		generator.genBusinessObjectHandlerCpp(umlService.getNamespace(), 
																					umlClass, 
																					outputWriter);
    	
    String output = outputWriter.toString();
    
    //test if index of each member in JBC VAR is correct
		assertTrue(output.contains("m_strAttrIndex = 1"));
		assertTrue(output.contains("m_strListAttrIndex = 2"));
		assertTrue(output.contains("m_intAttrIndex = 3"));
		assertTrue(output.contains("m_intListAttrIndex = 4"));
		assertTrue(output.contains("m_boolAttrIndex = 5"));
		assertTrue(output.contains("m_boolListAttrIndex = 6"));
		
		//store single member into VAR
		assertTrue(output.contains("VARObject strAttrObj(session)"));
		assertTrue(output.contains("int m_intAttr = myComplexType.GetIntAttr()"));
    assertTrue(output.contains("ConvertPrimitiveToVar(session, m_strAttr, strAttrObj.Get())"));
    assertTrue(output.contains("InsertVarIntoVar(session, myComplexTypeVar, m_strAttrIndex, strAttrObj.Get())"));
        
    //store collection of member into VAR
    assertTrue(output.contains("VARObject strListAttrObj(session)"));
    assertTrue(output.contains("VARObject strListAttrItemObj(session)"));
    assertTrue(output.contains("myComplexType.GetItemOfStrListAttr(index)"));
    assertTrue(output.contains("ConvertPrimitiveToVar(session, strListAttrItem,strListAttrItemObj.Get())"));
    assertTrue(output.contains("InsertVarIntoVar(session, strListAttrObj.Get(), index + 1, strListAttrItemObj.Get())"));
    assertTrue(output.contains("LowerVar(session, strListAttrObj.Get())"));
    assertTrue(output.contains("InsertVarIntoVar(session, myComplexTypeVar, m_strListAttrIndex, strListAttrObj.Get())"));
        
    //extract attribute from VAR
    assertTrue(output.contains("VARObject strAttrObj(session)"));
    assertTrue(output.contains("ExtractVarFromVar(session, strAttrObj.Get(), m_strAttrIndex, myComplexTypeVar)"));
    assertTrue(output.contains("ConvertVarToPrimitive(session, strAttrObj.Get(), m_strAttr)"));
    assertTrue(output.contains("myComplexType.SetStrAttr(m_strAttr)"));
        
    //extract multiple value from VAR
    assertTrue(output.contains("VARObject strListAttrObj(session)"));
    assertTrue(output.contains("ExtractVarFromVar(session, strListAttrObj.Get(), m_strListAttrIndex, myComplexTypeVar"));
    assertTrue(output.contains("RaiseVar(session, strListAttrObj.Get())"));
    assertTrue(output.contains("VARObject strListAttrItemObj(session)"));
    assertTrue(output.contains("ExtractVarFromVar(session, strListAttrItemObj.Get(), index + 1, strListAttrObj.Get())"));
    assertTrue(output.contains("ConvertVarToPrimitive(session, strListAttrItemObj.Get(), strListAttrItem)"));
    assertTrue(output.contains("myComplexType.AppendItemIntoStrListAttr(strListAttrItem)"));
	}	
	
	//test code generator to generate DLL export header file
  public void testGenDLLDeclHeaderFile() throws LoadTemplateException {
  	Writer outputWriter = new StringWriter();    	
        
  	generator.genDLLDeclHeaderFile(umlService, outputWriter);
    	
  	String output = outputWriter.toString();
		assertTrue(output.contains("#ifdef WIN32"));
		assertTrue(output.contains("#define DLLEXPORT __declspec(dllexport)"));
		assertTrue(output.contains("#define DLLIMPORT __declspec(dllimport)"));
		assertTrue(output.contains("#ifdef MYSERVICEDATA_LIB"));
		assertTrue(output.contains("#define DLLDECL DLLEXPORT"));
		assertTrue(output.contains("#define DLLDECL DLLIMPORT"));
		assertTrue(output.contains("#define DLLDECL DLLEXPORT"));	
  }
}
