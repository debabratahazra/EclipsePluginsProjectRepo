package com.odcgroup.service.gen.t24.internal.categories.java.webservice;

import static org.junit.Assert.*;

import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;


public class JavaWSApplicationContextXMLGeneratorTest {
	
	ServiceDescriptor service = new ServiceDescriptor("MySpecialService", null, null);
	
	@Test
	public void testJavaWSApplicationContextTAFC() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		JavaWSApplicationContextXMLGenerator generator = new JavaWSApplicationContextXMLGenerator(new TemplateOutsideJarLoader());
		generator.generateJavaWSApplicationContextTAFC(service, outputWriter);
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("<bean id=\"MySpecialServiceProvider\" class=\"com.temenos.services.myspecial.tafc.MySpecialServiceProviderImplTAFC\" />"));
	}
	
	@Test
	public void testJavaWSApplicationContextTAFJ() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		JavaWSApplicationContextXMLGenerator generator = new JavaWSApplicationContextXMLGenerator(new TemplateOutsideJarLoader());
		generator.generateJavaWSApplicationContextTAFJ(service, outputWriter);
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("<bean id=\"MySpecialServiceProvider\" class=\"com.temenos.services.myspecial.tafj.MySpecialServiceProviderImplTAFJ\" />"));
		assertTrue(output.contains("<bean id=\"MySpecialServiceImpl\" class=\"com.temenos.services.myspecial.MySpecialServiceImpl\" singleton=\"false\">"));
		assertTrue(output.contains("<constructor-arg type=\"com.temenos.soa.services.RuntimeProperties\" ref=\"RuntimeProperties\" />"));
		assertTrue(output.contains("<bean id=\"MySpecialServiceAPIPool\" class=\"com.temenos.services.myspecial.tafj.MySpecialServicePoolExt\">"));
		assertTrue(output.contains("<property name=\"targetBeanName\" value=\"MySpecialServiceImpl\" />"));
		assertTrue(output.contains("<bean id=\"RuntimeProperties\" class=\"com.temenos.soa.services.RuntimeProperties\">"));
	}
}