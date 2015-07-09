package com.odcgroup.service.gen.t24.internal.categories.java.webservice;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;

/**
 * Generates a Java interface from a {@link ServiceDescriptor}. 
 * @author srushworth
 *
 */
public class JavaComponentSpringContextGenerator extends ServiceGenerator {

	private static final String TEMPLATE = "JavaComponentSpringContext.vm";
	
	private Template template;
	
	public JavaComponentSpringContextGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}
	
	public void generate(ServiceDescriptor service, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			String serviceName = service.getName();
			ctx.put("service", service);
			ctx.put("serviceName", serviceName);
			
			if (writer == null) {
				String dirName = path + "/" + service.getPackageDirectory();
				String apiFileName = service.getName() + "SpringContext.java";
		
				writer = createWriter(dirName, apiFileName);
			}
			System.out.println("Merging Template - JavaComponentSpringContextGenerator: " + TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
