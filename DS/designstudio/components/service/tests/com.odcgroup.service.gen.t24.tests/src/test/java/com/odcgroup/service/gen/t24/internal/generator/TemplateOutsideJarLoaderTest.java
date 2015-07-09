package com.odcgroup.service.gen.t24.internal.generator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Test;

import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.Complexity;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public class TemplateOutsideJarLoaderTest {
	
	@Test 
	public void testLoadTemplate() throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException, LoadTemplateException {
		
		VelocityTemplateLoader underTest = new TemplateOutsideJarLoader();
		Template template = underTest.loadTemplate(BasicApiGenerator.TEMPLATE);
		assertNotNull(template);
		VelocityContext ctx = new VelocityContext();
		ParamDescriptor param = new ParamDescriptor("paramOne", "String", Cardinality.SINGLE, Direction.IN, Complexity.PRIMITIVE, true, null);
		OperationDescriptor operation = new OperationDescriptor("FooService", "barOp", Arrays.asList(param), null);
		ctx.put("service", "FooService");
		ctx.put("operation", operation);
		StringWriter writer = new StringWriter();
		template.merge(ctx, writer);
		String output = writer.toString();
		//System.out.println(output);
		assertTrue(output.contains("SUBROUTINE FooService.barOp("));
	}
	
	@Test(expected = Exception.class)
	public void testLoadTemplateFails() throws LoadTemplateException {
		VelocityTemplateLoader underTest = new TemplateOutsideJarLoader();
		Template template = underTest.loadTemplate("nonExistent.txt");
		assertNull(template);
	}
}
