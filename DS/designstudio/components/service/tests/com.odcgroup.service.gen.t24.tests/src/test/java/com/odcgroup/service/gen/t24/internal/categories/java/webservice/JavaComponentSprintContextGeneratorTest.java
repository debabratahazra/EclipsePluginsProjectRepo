package com.odcgroup.service.gen.t24.internal.categories.java.webservice;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.TemplateOutsideJarLoader;


public class JavaComponentSprintContextGeneratorTest {
	@Test
	public void testRefactoredGenerate() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();
		
		ServiceDescriptor service = new ServiceDescriptor("MySpecialService", null, null);

		JavaComponentSpringContextGenerator generator = new JavaComponentSpringContextGenerator(new TemplateOutsideJarLoader());
		generator.generate(service, outputWriter, "");
		
		String output = outputWriter.toString();
		//System.out.println(output);
		assertTrue(output.contains("appContext = new ClassPathXmlApplicationContext(new String[] {\"mySpecialServiceContext.xml\"}, false);"));
	}
}
