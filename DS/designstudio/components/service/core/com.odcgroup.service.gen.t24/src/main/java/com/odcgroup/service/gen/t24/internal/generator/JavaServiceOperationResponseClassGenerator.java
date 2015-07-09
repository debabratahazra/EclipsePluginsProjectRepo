package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * Generates a Java interface from a {@link ServiceDescriptor}. 
 * @author dadkinson
 *
 */
public class JavaServiceOperationResponseClassGenerator extends ServiceGenerator {

	private static final String TEMPLATE = "JavaServiceOperationResponseClass.vm";
	
	private Template template;
	
	public JavaServiceOperationResponseClassGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}
	
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path) {
		for (OperationDescriptor operationDescriptor : serviceDescriptor.getGenOperations()) {
			generateJavaWebServiceResponseClass(serviceDescriptor, operationDescriptor, writer, path);
		}
	}
	
	public void generateJavaWebServiceResponseClass(ServiceDescriptor service, OperationDescriptor operationDescriptor, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", service);
			ctx.put("operation", operationDescriptor);
			ctx.put("className", operationDescriptor.getUpperName() + "Response");
			ctx.put("outParamList", operationDescriptor.getOutParameters());
		
			if (writer == null) {
				String dirName = path + "/" + service.getDataResponsePackageDirectory();
				String classFileName = operationDescriptor.getUpperName() + "Response.java";
		
				writer = createWriter(dirName, classFileName);
			}
			System.out.println("Merging Template - JavaServiceOperationResponseClassGenerator: " + TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
