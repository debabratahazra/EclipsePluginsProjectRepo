package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * Generates BASIC Insert from a {@link ClassDefDescriptor}. 
 * @author dadkinson
 *
 */
public class BasicClassInsertGenerator extends ServiceGenerator{

	private static final String TEMPLATE = "BasicClassInsert.vm";

	private Template template;
	
	public BasicClassInsertGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}

	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path) {
		for (ClassDefDescriptor classDefDescriptor : serviceDescriptor.getClassDefDescriptors()) {
			generateClassInsert(serviceDescriptor.getName(), classDefDescriptor, writer, path);
		}
	}
	
	private void generateClassInsert(String service, ClassDefDescriptor classDefDescriptor, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", service);
			ctx.put("classDef", classDefDescriptor.getUpperCamelCaseName());
			ctx.put("paramName", classDefDescriptor.getLowerCamelCaseName());
			ctx.put("attributeList", classDefDescriptor.getAttributes());
			String classInsertFileName = "I_" + service + "_" + classDefDescriptor.getName();
			if (writer == null) {		
				writer = createWriter(path, classInsertFileName);
			}
			System.out.println("Merging Template - " + classInsertFileName + " template: " + TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
}
