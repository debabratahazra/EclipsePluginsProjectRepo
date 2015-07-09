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
public class JavaProviderServiceGenerator extends ServiceGenerator {

	private static final String TEMPLATE = "JavaProviderInterface.vm";
	
	private Template template;
	
	public JavaProviderServiceGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}
	public void generate(ServiceDescriptor service, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			// Generate New style (web services) interface using new data packages
			ctx.put("service", service);
			ctx.put("genericDataPackage", "com.temenos.soa.services.data");
			ctx.put("serviceDataPackage", service.getDataPackageName());
			ctx.put("provider", "Provider");
			
			if (writer == null) {
				String dirName = path + "/" + service.getPackageDirectory();
				String apiFileName = service.getName() + "ProviderAPI.java";
		
				writer = createWriter(dirName, apiFileName);
			}
			System.out.println("Merging Template - JavaServiceGenerator: (Provider) " + TEMPLATE);
			template.merge(ctx, writer);
			
			System.out.println("Done (Provider)");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
