package com.odcgroup.service.gen.t24.internal.categories.java.webservice;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;


public class JavaWSServiceXMLGeneratorTest {
	@Test
	public void testRefactoredGenerate() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", null, null);

		JavaWSServiceXMLGenerator generator = new JavaWSServiceXMLGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("<service name=\"MySpecialWebService\" scope=\"application\" targetNamespace=\"http://MySpecialServiceWS\">"));
		assertTrue(output.contains("<schema schemaNamespace=\"http://MySpecialServiceWS\"/>"));
		assertTrue(output.contains("<description>MySpecial Web Service</description>"));
		assertTrue(output.contains("com.temenos.webservices.myspecial.MySpecialServiceWS"));
		assertTrue(output.contains("<service class=\"com.temenos.services.myspecial.MySpecialServiceSpringInit\" name=\"MySpecialServiceSpringInit\">"));
		assertTrue(output.contains("<parameter name=\"ServiceClass\">com.temenos.services.myspecial.MySpecialServiceSpringInit</parameter>"));
		assertTrue(output.contains("<description>MySpecial WS-Security Enabled Web Service</description>"));
		assertTrue(output.contains("com.temenos.webservices.myspecial.MySpecialServiceSecWS"));
		assertTrue(output.contains("<passwordCallbackClass>com.temenos.webservices.myspecial.MySpecialServiceSecWSSamplePWHandler</passwordCallbackClass>"));
	}
}
