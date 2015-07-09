package com.odcgroup.service.gen.t24.internal.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class JavaServiceEJBGeneratorTest {

	private ServiceDescriptor service;
	private JavaServiceEJBGenerator generator;
	
	@Before
	public void setUp() {
		generator = new JavaServiceEJBGenerator(new TemplateOutsideJarLoader());
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
																	Cardinality.SINGLE, Direction.OUT, 
																	Complexity.COMPLEX, false, null));
		
		List<OperationDescriptor> operations = new ArrayList<OperationDescriptor>();
		//define one operation
		operations.add(new OperationDescriptor("MyService", "op1", params, null));

		List<ClassDefDescriptor> classDefDescriptors = new ArrayList<ClassDefDescriptor>();
		//define one complex type
		classDefDescriptors.add(new ClassDefDescriptor("MyComplexType", "Class", attributes));

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("MyService", operations, classDefDescriptors);
		serviceDescriptor.setComponentName("MyService");
		service = serviceDescriptor;		
	}
	
	@After
	public void tearDown() {
		service = null;
		generator = null;
	}
	
	@Test
	public void testGenServiceEJBLocalAPI() throws LoadTemplateException {
		//test code generator to service API	
		Writer outputWriter = new StringWriter();
		generator.genServiceEJBLocalAPI(service, outputWriter);																
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("import com.temenos.services.my.data.*;"));
		assertTrue(output.contains("import com.temenos.services.my.data.response.Op1Response;"));
		assertTrue(output.contains("@Local"));
		assertTrue(output.contains("MyServiceBeanLocal"));
	}
	
	@Test
	public void testGenServiceEJBRemoteAPI() throws LoadTemplateException {
		//test code generator to service API	
		Writer outputWriter = new StringWriter();
		generator.genServiceEJBRemoteAPI(service, outputWriter);																
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("import com.temenos.services.my.data.*;"));
		assertTrue(output.contains("import com.temenos.services.my.data.response.Op1Response;"));
		assertTrue(output.contains("@Remote"));
		assertTrue(output.contains("MyServiceBeanRemote"));
	}
	
	@Test
	public void testGenServiceEJBTAFJImpl() throws LoadTemplateException {
		//test code generator to service API	
		Writer outputWriter = new StringWriter();
		generator.genServiceEJBTAFJImpl(service, outputWriter);																
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("com.temenos.services.my.MyServiceAPI;"));
		assertTrue(output.contains("com.temenos.services.my.MyServiceImpl;"));
		assertTrue(output.contains("MyServiceBeanTAFJ implements MyServiceBeanLocal, MyServiceBeanRemote"));
		assertTrue(output.contains("MyServiceAPI"));
	}
	
	@Test
	public void testGenServiceEJBTAFCImpl() throws LoadTemplateException {
		//test code generator to service API	
		Writer outputWriter = new StringWriter();
		generator.genServiceEJBTAFCImpl(service, outputWriter);																
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("com.temenos.services.my.MyServiceProviderAPI;"));
		assertTrue(output.contains("com.temenos.services.my.tafc.MyServiceProviderImplTAFC;"));
		assertTrue(output.contains("MyServiceBeanTAFC implements MyServiceBeanLocal, MyServiceBeanRemote"));
		assertTrue(output.contains("MyServiceProviderAPI"));
	}
	
	@Test
	public void testGetServiceEJBJARXMLForTAFJ() throws LoadTemplateException {
		String output = StringUtils.transformXMLToString(generator.getServiceEJBJARXML(service, "TAFJ"));
		//System.out.println(output);
		assertTrue(output.contains("com.temenos.services.my.ejb.MyServiceBeanLocal"));
		assertTrue(output.contains("com.temenos.services.my.ejb.MyServiceBeanRemote"));
		assertTrue(output.contains("com.temenos.services.my.ejb.MyServiceBeanTAFJ"));
		assertTrue(output.contains("runtimeProperties"));
		assertTrue(output.contains("jdbc/t24DataSource"));
		assertTrue(output.contains("jdbc/t24LockingDataSource"));
	}
	
	@Test
	public void testGetServiceEJBJARXMLForTAFC() throws LoadTemplateException {
		String output = StringUtils.transformXMLToString(generator.getServiceEJBJARXML(service, "TAFC"));
		//System.out.println(output);
		assertTrue(output.contains("com.temenos.services.my.ejb.MyServiceBeanLocal"));
		assertTrue(output.contains("com.temenos.services.my.ejb.MyServiceBeanRemote"));
		assertTrue(output.contains("com.temenos.services.my.ejb.MyServiceBeanTAFC"));
		assertTrue(output.contains("jca/t24ConnectionFactory"));
	}
	
	@Test
	public void testGetServiceJbossXMLForTAFJ() throws LoadTemplateException {
		String output = StringUtils.transformXMLToString(generator.getServiceJbossXML(service, "TAFJ"));	
		//System.out.println(output);
		assertTrue(output.contains("MyServiceBeanTAFJ"));
		assertTrue(output.contains("jdbc/t24DataSource"));
		assertTrue(output.contains("jdbc/t24LockingDataSource"));
	}
	
	@Test
	public void testGetServiceJbossXMLForTAFC() throws LoadTemplateException {
		String output = StringUtils.transformXMLToString(generator.getServiceJbossXML(service, "TAFC"));
		//System.out.println(output);
		assertTrue(output.contains("MyServiceBeanTAFC"));
		assertTrue(output.contains("jca/t24ConnectionFactory"));
	}
	
	@Test
	public void testGetServiceIBMXMLForTAFJ() throws LoadTemplateException {
		String output = StringUtils.transformXMLToString(generator.getServiceIBMXML(service, "TAFJ"));
		//System.out.println(output);
		assertTrue(output.contains("MyServiceBeanTAFJ"));
		assertTrue(output.contains("jdbc/t24DataSource"));
	}
	
	@Test
	public void testGetServiceIBMXMLForTAFC() throws LoadTemplateException {
		String output = StringUtils.transformXMLToString(generator.getServiceIBMXML(service, "TAFC"));
		//System.out.println(output);
		assertTrue(output.contains("MyServiceBeanTAFC"));
		assertTrue(output.contains("jca/t24ConnectionFactory"));
	}
	
	@Test
	public void testGetServiceWebLogicXMLForTAFJ() throws LoadTemplateException {
		String output = StringUtils.transformXMLToString(generator.getServiceWebLogicXML(service, "TAFJ"));
		//System.out.println(output);
		assertTrue(output.contains("MyServiceBeanTAFJ"));
		assertTrue(output.contains("com.temenos.services.my.ejb.MyServiceBeanRemote"));
		assertTrue(output.contains("jdbc/t24DataSource"));
		assertTrue(output.contains("ejb/MyServiceBeanRemote"));
		assertTrue(output.contains("ejb/MyServiceBeanLocal"));
	}
	
	@Test
	public void testGetServiceWebLogicXMLForTAFC() throws LoadTemplateException {
		String output = StringUtils.transformXMLToString(generator.getServiceWebLogicXML(service, "TAFC"));	
		//System.out.println(output);
		assertTrue(output.contains("MyServiceBeanTAFC"));
		assertTrue(output.contains("com.temenos.services.my.ejb.MyServiceBeanRemote"));
		assertTrue(output.contains("jca/t24ConnectionFactory"));
		assertTrue(output.contains("ejb/MyServiceBeanRemote"));
		assertTrue(output.contains("ejb/MyServiceBeanLocal"));
	}
	
	@Test 
	public void testAppendXMLDoc () {
		
		/*************** EJB JAR XML TEST ***************************/
		// For TAFC - Integration and My Service should be in output 
		String output = StringUtils.transformXMLToString(
				generator.appendXMLDoc (generator.getServiceEJBJARXML(service, "TAFC"), "./src/test/resources/xmlDescriptors/tafc", "ejb-jar.xml"));
		//System.out.println(output);
		assertEquals(2, StringUtils.getNumberOccurances(output, 
								"<ejb-name>IntegrationServiceBeanTAFC</ejb-name>"));
		assertEquals(2, StringUtils.getNumberOccurances(output, 
								"<ejb-name>MyServiceBeanTAFC</ejb-name>"));
		
		// For TAFJ - Integration and My Service should be in output
		output = StringUtils.transformXMLToString(
				generator.appendXMLDoc (generator.getServiceEJBJARXML(service, "TAFJ"), "./src/test/resources/xmlDescriptors/tafj", "ejb-jar.xml"));
		//System.out.println(output);
		assertEquals(2, StringUtils.getNumberOccurances(output, 
								"<ejb-name>IntegrationServiceBeanTAFJ</ejb-name>"));
		assertEquals(2, StringUtils.getNumberOccurances(output, 
								"<ejb-name>MyServiceBeanTAFJ</ejb-name>"));

		/*************** IBM EJB JAR BND TEST ***************************/
		// For TAFC - Integration and My Service should be in output 
		output = StringUtils.transformXMLToString(
				generator.appendXMLDoc (generator.getServiceIBMXML(service, "TAFC"), "./src/test/resources/xmlDescriptors/tafc", "ibm-ejb-jar-bnd.xml"));
		//System.out.println(output);
		assertTrue(output.contains("name=\"IntegrationServiceBeanTAFC\""));
		assertTrue(output.contains("name=\"MyServiceBeanTAFC\""));
		
		// For TAFJ - Integration and My Service should be in output
		output = StringUtils.transformXMLToString(
				generator.appendXMLDoc (generator.getServiceIBMXML(service, "TAFJ"), "./src/test/resources/xmlDescriptors/tafj", "ibm-ejb-jar-bnd.xml"));
		//System.out.println(output);
		assertTrue(output.contains("name=\"IntegrationServiceBeanTAFJ\""));
		assertTrue(output.contains("name=\"MyServiceBeanTAFJ\""));
		
		/*************** WEBLOGIC EJB JAR TEST ***************************/
		// For TAFC - Integration and My Service should be in output 
		output = StringUtils.transformXMLToString(
				generator.appendXMLDoc (generator.getServiceWebLogicXML(service, "TAFC"), "./src/test/resources/xmlDescriptors/tafc", "weblogic-ejb-jar.xml"));
		//System.out.println(output);
		assertTrue(output.contains("<wls:ejb-name>IntegrationServiceBeanTAFC</wls:ejb-name>"));
		assertTrue(output.contains("<wls:ejb-name>MyServiceBeanTAFC</wls:ejb-name>"));
		
		// For TAFJ - Integration and My Service should be in output
		output = StringUtils.transformXMLToString(
				generator.appendXMLDoc (generator.getServiceWebLogicXML(service, "TAFJ"), "./src/test/resources/xmlDescriptors/tafj", "weblogic-ejb-jar.xml"));
		//System.out.println(output);
		assertTrue(output.contains("<wls:ejb-name>IntegrationServiceBeanTAFJ</wls:ejb-name>"));
		assertTrue(output.contains("<wls:ejb-name>MyServiceBeanTAFJ</wls:ejb-name>"));
		
		/*************** JBOSS EJB 3 TEST ***************************/
		// For TAFC - Integration and My Service should be in output 
		output = StringUtils.transformXMLToString(
				generator.appendXMLDoc (generator.getServiceJbossEJB3XML(service, "TAFC"), "./src/test/resources/xmlDescriptors/tafc", "jboss-ejb3.xml"));
		//System.out.println(output);
		assertEquals(2, StringUtils.getNumberOccurances(output, "<session>"));
		assertTrue(output.contains("<ejb-name>IntegrationServiceBeanTAFC</ejb-name>"));
		assertTrue(output.contains("<ejb-name>MyServiceBeanTAFC</ejb-name>"));
		assertEquals(2, StringUtils.getNumberOccurances(output, "<s:security>"));
		
		// For TAFJ - Integration and My Service should be in output
		output = StringUtils.transformXMLToString(
				generator.appendXMLDoc (generator.getServiceJbossEJB3XML(service, "TAFJ"), "./src/test/resources/xmlDescriptors/tafj", "jboss-ejb3.xml"));
		//System.out.println(output);
		assertEquals(2, StringUtils.getNumberOccurances(output, "<session>"));
		assertTrue(output.contains("<ejb-name>IntegrationServiceBeanTAFJ</ejb-name>"));
		assertTrue(output.contains("<ejb-name>MyServiceBeanTAFJ</ejb-name>"));
		assertEquals(2, StringUtils.getNumberOccurances(output, "<s:security>"));
	}
}
