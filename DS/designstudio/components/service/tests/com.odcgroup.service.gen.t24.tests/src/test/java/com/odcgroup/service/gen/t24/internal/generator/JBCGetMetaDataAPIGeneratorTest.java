package com.odcgroup.service.gen.t24.internal.generator;

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
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.model.component.T24MethodStereotype;

public class JBCGetMetaDataAPIGeneratorTest extends TestCase {
	private ServiceDescriptor serviceDescriptor;
	private JBCGetMetaDataAPIGenerator generator;
	
	//construct uml service model
	protected void setUp() {
		generator = new JBCGetMetaDataAPIGenerator(new TemplateOutsideJarLoader());
		List<AttributeDescriptor> attributes = 
			new ArrayList<AttributeDescriptor>();
		//member is single string
		attributes.add(
				new AttributeDescriptor("strAttr", "String", Cardinality.SINGLE));
		//member is collection of string
		attributes.add(
				new AttributeDescriptor("strListAttr", "String", Cardinality.MULTIPLE));		
		
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		//parameter is single int
		params.add(new ParamDescriptor("intPara", "Integer", 
																	Cardinality.SINGLE, Direction.IN, 
																	Complexity.PRIMITIVE, false, null));
		//parameter is collection of int
		params.add(new ParamDescriptor("intListPara", "Integer", 
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

		serviceDescriptor = new ServiceDescriptor("MyService", operations, classDefDescriptors);
		
		System.setProperty("COMP_MAJOR_VERSION", "MAIN");
		System.setProperty("COMP_MINOR_VERSION", "latest-dev");
	}	
	
	protected void tearDown() {		
	}
	
	//test code generator to generate business object .h file
	public void testJBCGetMetaDataAPI() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();		

	    generator.genSubroutine(serviceDescriptor, outputWriter);
	    
	    String output = outputWriter.toString();

		//test whether each member has been declared correclty
		assertTrue(output.contains("<service xmlns=\"http://www.temenos.com/SOA/Meta\""));
		assertTrue(output.contains("name=\"MyService\""));
		assertTrue(output.contains("majorVersion=\"MAIN\" minorVersion=\"latest-dev\""));
		
		assertTrue(output.contains("<operation name=\"op1\""));
		assertTrue(output.contains("integrationFlowSupportable=\"false\""));

		
		assertTrue(output.contains("<parameter name=\"intPara\""));
		assertTrue(output.contains("typeName=\"Integer\""));
		assertTrue(output.contains("isPrimitive=\"true\""));
		assertTrue(output.contains("isCollection=\"false\""));
		assertTrue(output.contains("isMandatory=\"false\""));
		assertTrue(output.contains("direction=\"IN\""));
		
		
		assertTrue(output.contains("<parameter name=\"intListPara\""));		
		assertTrue(output.contains("isCollection=\"true\""));
		
		assertTrue(output.contains("<parameter name=\"complexPara\""));
		assertTrue(output.contains("typeName=\"MyComplexType\""));
		assertTrue(output.contains("isPrimitive=\"false\""));
		
		assertTrue(output.contains("<customizedType name=\"MyComplexType\">"));
		
		assertTrue(output.contains("<member name=\"strAttr\""));
		assertTrue(output.contains("typeName=\"String\""));
		
		assertTrue(output.contains("<member name=\"strListAttr\""));
		
	}
	
	/******************* With Stereo Types ***********************/
	//construct uml service model
	protected void setUpWithStereoTypes() {
		generator = new JBCGetMetaDataAPIGenerator(new TemplateOutsideJarLoader());
		
		List<ParamDescriptor> params = new ArrayList<ParamDescriptor>();
		//parameter is single int
		params.add(new ParamDescriptor("intPara", "Integer", 
																	Cardinality.SINGLE, Direction.IN, 
																	Complexity.PRIMITIVE, false, null));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		//define operation
		OperationDescriptor op1 = new OperationDescriptor("MyService", "op1", params, null);
		op1.addStereotype(T24MethodStereotype.INTEGRATION_FLOW_SUPPORTABLE);
		op1.addStereotype(T24MethodStereotype.UNSAFE);
		operations.add(op1);
	
		serviceDescriptor = new ServiceDescriptor("MyService", operations, null);
		
		System.setProperty("COMP_MAJOR_VERSION", "RXX");
		System.setProperty("COMP_MINOR_VERSION", "X.X.0X");
	}	
	
	protected void tearDownWithStereoTypes() {		
	}
	
	//test code generator
	public void testJBCGetMetaDataAPIWithStereoTypes() throws LoadTemplateException {
		setUpWithStereoTypes();
		Writer outputWriter = new StringWriter();		

	    generator.genSubroutine(serviceDescriptor, outputWriter);
	    
	    String output = outputWriter.toString();
	    //System.out.println(output);
		//test whether each stereotype is applied correctly
	    assertTrue(output.contains("majorVersion=\"RXX\" minorVersion=\"X.X.0X\""));
		assertTrue(output.contains("<operation name=\"op1\""));
		assertTrue(output.contains("integrationFlowSupportable=\"true\""));
		assertTrue(output.contains("idempotent=\"false\""));
		assertTrue(output.contains("unsafe=\"true\""));
	}
	
}
