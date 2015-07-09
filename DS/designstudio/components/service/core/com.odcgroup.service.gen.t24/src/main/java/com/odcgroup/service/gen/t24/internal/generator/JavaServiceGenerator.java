package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * Generates a Java interface from a {@link ServiceDescriptor}. 
 * @author jannadani
 *
 */
public class JavaServiceGenerator extends ServiceGenerator {

	private static final String TEMPLATE = "Java_Service.vm";
	
	private Template template;
	
	public JavaServiceGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}
	public void generate(ServiceDescriptor service, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", service);
			ctx.put("genericDataPackage", "com.temenos.soa.services.data");
			ctx.put("serviceDataPackage", service.getPackageName());
			
			if (writer == null) {
				String dirName = 
					path 
					+ "/" + service.getPackageDirectory();
				String apiFileName = service.getName() + ".java";
		
				writer = createWriter(dirName, apiFileName);
			}
			System.out.println("Merging Template - Java_ServiceGenerator: " + TEMPLATE);
			template.merge(ctx, writer);
			
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
