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
public class JavaWebServiceSecEnabledGenerator extends ServiceGenerator {

	private static final String TEMPLATE = "JavaWebServiceSecEnabled.vm";
	
	private Template template;
	
	public JavaWebServiceSecEnabledGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}
	
	public void generate(ServiceDescriptor service, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			String serviceName = service.getName();
			ctx.put("service", service);
			ctx.put("serviceName", serviceName);
			ctx.put("webServiceName", serviceName + "SecWS");
			ctx.put("soaPackageName", "com.temenos.soa.services");
			
			if (writer == null) {
				String dirName = path + "/" + service.getWsPackageDirectory();
				String apiFileName = serviceName + "SecWS.java";
		
				writer = createWriter(dirName, apiFileName);
			}
			System.out.println("Merging Template - JavaWebServiceSecEnabledGenerator: " + TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
