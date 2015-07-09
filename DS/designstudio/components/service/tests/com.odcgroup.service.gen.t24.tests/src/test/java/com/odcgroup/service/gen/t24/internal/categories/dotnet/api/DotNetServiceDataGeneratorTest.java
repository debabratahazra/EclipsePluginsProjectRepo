package com.odcgroup.service.gen.t24.internal.categories.dotnet.api;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.odcgroup.service.gen.t24.internal.categories.dotnet.data.DotNetServiceDataGenerator;
import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Complexity;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLServiceDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;

public class DotNetServiceDataGeneratorTest extends TestCase {
	private UMLServiceDotNetDescriptor umlService;
	private DotNetServiceDataGenerator generator;
	
	//construct uml service model
	protected void setUp() {
		generator = new DotNetServiceDataGenerator(new TemplateOutsideJarLoader());
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
		umlService = new UMLServiceDotNetDescriptor(serviceDescriptor);		
	}
	
	protected void tearDown() {		
	}
	
	//test code generator to generate business object .h file
	public void testGenManagedBusinessObjectHeader() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		UMLClassDotNetDescriptor[] umlClasses = umlService.getUMLClasses();

		UMLClassDotNetDescriptor umlClass = umlClasses[0];

    generator.genManagedBusinessObjectHeader(umlService.getNamespace(), 
    																				umlClass,
    																				outputWriter);
    
    String output = outputWriter.toString();
		//test whether each member has been declared correclty
		assertTrue(output.contains("String^ m_managedStrAttr"));
		assertTrue(output.contains("List<String^>^ m_managedStrListAttr"));
		assertTrue(output.contains("int m_managedIntAttr"));
		assertTrue(output.contains("List<int>^ m_managedIntListAttr"));
		assertTrue(output.contains("bool m_managedBoolAttr"));
		assertTrue(output.contains("List<bool>^ m_managedBoolListAttr"));
		
		//test whether the property is declared correctly		
		assertTrue(output.contains("property String^ StrAttr"));
		assertTrue(output.contains("String^ get()"));
		assertTrue(output.contains("void set(String^ managedStrAttr"));
		
		assertTrue(output.contains("property List<String^>^ StrListAttr"));
		assertTrue(output.contains("List<String^>^ get()"));
		assertTrue(output.contains("void set(List<String^>^ managedStrListAttr"));
		
		assertTrue(output.contains("property int IntAttr"));
		assertTrue(output.contains("int get()"));
		assertTrue(output.contains("void set(int managedIntAttr"));
		
		assertTrue(output.contains("property List<int>^ IntListAttr"));
		assertTrue(output.contains("List<int>^ get()"));
		assertTrue(output.contains("void set(List<int>^ managedIntListAttr"));
		
		assertTrue(output.contains("property bool BoolAttr"));
		assertTrue(output.contains("bool get()"));
		assertTrue(output.contains("void set(bool managedBoolAttr"));
		
		assertTrue(output.contains("property List<bool>^ BoolListAttr"));
		assertTrue(output.contains("List<bool>^ get()"));
		assertTrue(output.contains("void set(List<bool>^ managedBoolListAttr"));		
	}
	
	//test code generator to generate business object .cpp file
	public void testGenManagedBusinessObjectCpp() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
    	
		UMLClassDotNetDescriptor[] umlClasses = umlService.getUMLClasses();
		UMLClassDotNetDescriptor umlClass = umlClasses[0];
        
		generator.genManagedBusinessObjectCpp(umlService.getNamespace(), 
																					umlClass, 
																					outputWriter);
    	
		String output = outputWriter.toString();    	
    	
		//test if getting and setting single member is correct	
		assertTrue(output.contains("return m_managedStrAttr"));
		assertTrue(output.contains("return m_managedStrListAttr"));
		assertTrue(output.contains("return m_managedIntAttr"));
		assertTrue(output.contains("return m_managedIntListAttr"));
		assertTrue(output.contains("return m_managedBoolAttr"));
		assertTrue(output.contains("return m_managedBoolListAttr"));	
		
		assertTrue(output.contains("m_managedStrAttr = managedStrAttr"));
		assertTrue(output.contains("m_managedStrListAttr = managedStrListAttr"));
		assertTrue(output.contains("m_managedIntAttr = managedIntAttr"));
		assertTrue(output.contains("m_managedIntListAttr = managedIntListAttr"));
		assertTrue(output.contains("m_managedBoolAttr = managedBoolAttr"));
		assertTrue(output.contains("m_managedBoolListAttr = managedBoolListAttr"));
		
	}
	
	//test code generator to generate business helper class .h file
	public void testGenBusinessObjectMarshalHeader() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
    	
		UMLClassDotNetDescriptor[] umlClasses = umlService.getUMLClasses();
		UMLClassDotNetDescriptor umlClass = umlClasses[0];
        
		generator.genManagedBusinessObjectMarshalHeader(umlService.getNamespace(), 
																								umlClass, 
																								outputWriter);
    	
		String output = outputWriter.toString();
		
		assertTrue(output.contains("#include <vector>"));
		assertTrue(output.contains("#include <msclr/gcroot.h>"));
		assertTrue(output.contains("using System::Collections::Generic::List"));
		assertTrue(output.contains("using msclr::gcroot"));
		
		assertTrue(output.contains("class MyComplexType;"));
		assertTrue(output.contains("ref class ManagedMyComplexType"));
		
		assertTrue(output.contains("const gcroot<ManagedMyComplexType^> managedMyComplexTypeHandle,"));
		assertTrue(output.contains("MyComplexType& unmanagedMyComplexType);"));
		
		assertTrue(output.contains("const gcroot<List<ManagedMyComplexType^>^> managedMyComplexTypeListHandle,"));
		assertTrue(output.contains("std::vector<MyComplexType>& unmanagedMyComplexTypeList);"));
		
		assertTrue(output.contains("const MyComplexType& unmanagedMyComplexType);"));
		assertTrue(output.contains("const std::vector<MyComplexType>& unmanagedMyComplexTypeList);"));
	}
	
	//test code generator to generate business helper class .cpp file
	public void testGenBusinessObjectMarshalCpp() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
    	
		UMLClassDotNetDescriptor[] umlClasses = umlService.getUMLClasses();
		UMLClassDotNetDescriptor umlClass = umlClasses[0];
        
		generator.genManagedBusinessObjectMarshalCpp(umlService.getNamespace(), 
																								umlClass, 
																								outputWriter);
    	
		String output = outputWriter.toString(); 
		
		assertTrue(output.contains("std::string strAttr"));
		assertTrue(output.contains("temenos::soa::common::PrimitiveMarshal::Marshal(managedMyComplexType->StrAttr, strAttr);"));
		assertTrue(output.contains("unmanagedMyComplexType.SetStrAttr(strAttr);"));
		
		assertTrue(output.contains("std::string itemOfStrListAttr;"));
		assertTrue(output.contains("temenos::soa::common::PrimitiveMarshal::Marshal(itemOfManagedStrListAttr,itemOfStrListAttr);"));
		assertTrue(output.contains("unmanagedMyComplexType.AppendItemIntoStrListAttr(itemOfStrListAttr);"));
		
		assertTrue(output.contains("int intAttr = managedMyComplexType->IntAttr;"));
		assertTrue(output.contains("unmanagedMyComplexType.SetIntAttr(intAttr);"));
				
		assertTrue(output.contains("int itemOfIntListAttr = itemOfManagedIntListAttr;"));
		assertTrue(output.contains("unmanagedMyComplexType.AppendItemIntoIntListAttr(itemOfIntListAttr);"));

		assertTrue(output.contains("bool boolAttr = managedMyComplexType->BoolAttr;"));
		assertTrue(output.contains("unmanagedMyComplexType.SetBoolAttr(boolAttr);"));
				
		assertTrue(output.contains("bool itemOfBoolListAttr = itemOfManagedBoolListAttr;"));
		assertTrue(output.contains("unmanagedMyComplexType.AppendItemIntoBoolListAttr(itemOfBoolListAttr);"));
		
		assertTrue(output.contains("std::string strAttr = unmanagedMyComplexType.GetStrAttr();"));
		assertTrue(output.contains("temenos::soa::common::PrimitiveMarshal::Unmarshal(strAttr);"));
				
		assertTrue(output.contains("std::string itemOfStrListAttr = unmanagedMyComplexType.GetItemOfStrListAttr(index);"));
		assertTrue(output.contains("String^ itemOfManagedStrListAttr = temenos::soa::common::PrimitiveMarshal::Unmarshal(itemOfStrListAttr);"));
		assertTrue(output.contains("managedMyComplexType->StrListAttr->Add(itemOfManagedStrListAttr);"));
		
		assertTrue(output.contains("managedMyComplexType->IntAttr = unmanagedMyComplexType.GetIntAttr();"));
		
		assertTrue(output.contains("int itemOfIntListAttr = unmanagedMyComplexType.GetItemOfIntListAttr(index);"));
		assertTrue(output.contains("managedMyComplexType->IntListAttr->Add(itemOfIntListAttr);"));
		
		assertTrue(output.contains("managedMyComplexType->BoolAttr = unmanagedMyComplexType.GetBoolAttr();"));
		
		assertTrue(output.contains("bool itemOfBoolListAttr = unmanagedMyComplexType.GetItemOfBoolListAttr(index);"));
		assertTrue(output.contains("managedMyComplexType->BoolListAttr->Add(itemOfBoolListAttr);"));
	}
	//test code generator to generate DLL export header file
	public void testGenManagedDLLDeclHeaderFile() throws LoadTemplateException {
  	Writer outputWriter = new StringWriter();    	
        
  	generator.genManagedDLLDeclHeaderFile(umlService, outputWriter);
    	
  	String output = outputWriter.toString();
		assertTrue(output.contains("#ifdef WIN32"));
		assertTrue(output.contains("#define DLLEXPORT __declspec(dllexport)"));
		assertTrue(output.contains("#define DLLIMPORT __declspec(dllimport)"));
		assertTrue(output.contains("#ifdef MYSERVICEDATAWRAPPER_LIB"));
		assertTrue(output.contains("#define DLLDECL DLLEXPORT"));
		assertTrue(output.contains("#define DLLDECL DLLIMPORT"));
		assertTrue(output.contains("#define DLLDECL DLLEXPORT"));	
  }
	
	//test code generator to generate business object converter .cs file
	public void testGenBusinessObjectConverter() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
    	
		UMLClassDotNetDescriptor[] umlClasses = umlService.getUMLClasses();
		UMLClassDotNetDescriptor umlClass = umlClasses[0];
        
		generator.genManagedDataConverterCSharp(umlService.getNamespace(), umlClass, outputWriter);
    	
		String output = outputWriter.toString();
		//System.out.println(output);
		
		assertTrue(output.contains("namespace MyServiceNS"));
		assertTrue(output.contains("ManagedMyComplexTypeConverter"));
		assertTrue(output.contains("jdUtils.getStringList(jd, STRLISTATTR_INDEX, myComplexType.StrListAttr)"));
		assertTrue(output.contains("jdUtils.getBooleanList(jd, BOOLLISTATTR_INDEX, myComplexType.BoolListAttr)"));
		assertTrue(output.contains("jdUtils.getIntegerList(jd, INTLISTATTR_INDEX, myComplexType.IntListAttr)"));
	}
}
