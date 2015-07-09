package com.odcgroup.service.gen.t24.internal.categories.dotnet.api;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.Complexity;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLServiceDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;

public class DotNetServiceAPIGeneratorTest extends TestCase {
	private UMLServiceDotNetDescriptor umlService;
	private DotNetServiceAPIGenerator generator;
	
	//construct uml service model
	protected void setUp() {
		generator = new DotNetServiceAPIGenerator(new TemplateOutsideJarLoader());
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
																	Cardinality.SINGLE, Direction.INOUT, 
																	Complexity.COMPLEX, false, null));
		//parameter is collection of complex type
		params.add(new ParamDescriptor("complexListPara", "MyComplexType", 
																	Cardinality.MULTIPLE, Direction.OUT, 
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
	
	//test code generator to generate WCF Interface (.cs) file
	public void testGenWCFServiceInterface() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		generator.genDotNetWCFServiceCSFile(umlService, outputWriter);
    
		String output = outputWriter.toString();
		//System.out.println(output);

		// Check the namespace and interface name
		assertTrue(output.contains("namespace MyServiceNS {"));
		assertTrue(output.contains("[ServiceContract]"));
		assertTrue(output.contains("public interface IMyServiceWS"));
		
		//test whether each member has been declared correclty
		assertTrue(output.contains("[OperationContract]"));
		assertTrue(output.contains("void op1(ManagedUserContext managedUserContext, int managedIntPara, List<int> managedIntListPara, bool managedBoolPara, List<bool> managedBoolListPara, String managedStringPara, List<String> managedStringListPara, ref ManagedMyComplexType managedComplexPara, out List<ManagedMyComplexType> managedComplexListPara, out ManagedSOAResponseDetail managedResponseDetail)"));
	}
	
	//test code generator to generate WCF Service Impl (.cs.svc) file
	public void testGenWCFServiceImpl() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		generator.genDotNetWCFServiceImplCSSVCFile(umlService, outputWriter);
    
		String output = outputWriter.toString();
		//System.out.println(output);

		// Check the namespace and interface name
		assertTrue(output.contains("namespace MyServiceNS {"));
		assertTrue(output.contains("public class MyServiceWS : IMyServiceWS"));
		
		//test whether each member has been declared correclty
		assertTrue(output.contains("myServiceAPI = new ManagedMyServiceImpl(contextCallback);"));
		assertTrue(output.contains("public void op1(ManagedUserContext managedUserContext, int managedIntPara, List<int> managedIntListPara, bool managedBoolPara, List<bool> managedBoolListPara, String managedStringPara, List<String> managedStringListPara, ref ManagedMyComplexType managedComplexPara, out List<ManagedMyComplexType> managedComplexListPara, out ManagedSOAResponseDetail managedResponseDetail)"));
		assertTrue(output.contains("managedComplexListPara = new List<ManagedMyComplexType>();"));
		assertTrue(output.contains("myServiceAPI.Op1(managedIntPara, managedIntListPara, managedBoolPara, managedBoolListPara, managedStringPara, managedStringListPara, ref managedComplexPara, ref managedComplexListPara, ref managedResponseDetail);"));
		
		// Remote Checks
		assertTrue(output.contains("T24MyServiceImpl.op1"));
		assertTrue(output.contains("jsParams.add(new JDynArray(managedIntPara.ToString()))"));
		assertTrue(output.contains("jdUtils.setValue(managedIntListParajDyn, managedIntListPara)"));
		assertTrue(output.contains("managedComplexListParaConverterObj.fromJDyn(res, managedComplexListPara)"));
	}
	
	//test code generator to generate WCF Markup (.svc) file
	public void testGenWCFServiceMarkupXML() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		generator.genDotNetWCFServiceSVCMarkupXMLFile(umlService, outputWriter);
    
		String output = outputWriter.toString();
		//System.out.println(output);

		// Check the generated output
		assertTrue(output.contains("Service=\"temenos.soa.MyServiceNS.MyServiceWS\""));
		assertTrue(output.contains("CodeBehind=\"MyServiceWS.svc.cs\""));
	}
	
//	//test code generator to generate WCF web.config file
//	public void testGenWCFServiceWebConfigXML() throws LoadTemplateException {
//		Writer outputWriter = new StringWriter();
//		generator.genDotNetWCFServiceWebConfigXMLFile(umlService, outputWriter);
//    
//		String output = outputWriter.toString();
//		//System.out.println(output);
//
//		// Check the generated output
//		assertTrue(output.contains("<service name=\"temenos.soa.MyServiceNS.MyServiceWS\">"));
//		assertTrue(output.contains("contract=\"temenos.soa.MyServiceNS.IMyServiceWS\""));
//	}
}
