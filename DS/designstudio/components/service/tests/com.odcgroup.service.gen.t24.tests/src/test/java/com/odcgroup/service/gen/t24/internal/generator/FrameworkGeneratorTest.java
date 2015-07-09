package com.odcgroup.service.gen.t24.internal.generator;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public class FrameworkGeneratorTest {
	@Test
	public void testGenerate() throws LoadTemplateException {
		Writer outputWriter = new StringWriter();

		ServiceDescriptor serviceDescriptor = null;
		
		FrameworkGenerator generator = new FrameworkGenerator(new TemplateOutsideJarLoader());
		generator.generate(serviceDescriptor, outputWriter, "");
		
		String output = outputWriter.toString();
		
		//System.out.println(output);

		// Resource templates are wholly static, i.e. no dynamic insertions to be checked:
		assertTrue(output.contains("* Insert describing the complex type: ResponseDetails"));
//		assertTrue(output.contains("public Response(Integer code, ResponseType type, String text, String info) {"));
//		assertTrue(output.contains("public class ResponseDetails {"));

	}
}