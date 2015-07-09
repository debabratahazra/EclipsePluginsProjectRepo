package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * Generates BASIC implementation code from a {@link ServiceDescriptor}. 
 * @author jannadani
 *
 */
public class BasicImplementationGenerator extends ServiceGenerator {

	private static final String TEMPLATE = "BasicImplementation.vm";

	private Template template;
	
	public BasicImplementationGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}

	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path) {
		for (OperationDescriptor operationDescriptor : serviceDescriptor.getGenOperations()) {
			generateOperation(serviceDescriptor, operationDescriptor, writer, path);
		}
	}
	
	private void generateOperation(ServiceDescriptor service, OperationDescriptor operation, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", service);
			ctx.put("classes", service.getClassDefDescriptors());
			ctx.put("operation", operation);
			String apiFileName = "T24" + service.getName() + "Impl." + operation.getLowerName() + ".b";
			if (writer == null) {		
				writer = createWriter(path, apiFileName);
			}
			System.out.println("Merging Template - " + apiFileName + " template: " + TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
}
