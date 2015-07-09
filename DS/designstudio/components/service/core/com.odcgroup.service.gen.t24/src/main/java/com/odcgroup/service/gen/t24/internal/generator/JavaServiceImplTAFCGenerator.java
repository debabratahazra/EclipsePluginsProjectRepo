package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.utils.TypeUtils;

/**
 * Generates an impl class for the service described by the passed in {@link ServiceDescriptor} that 
 * invokes the TAFC adaptor subroutine.  This is used where the service is exposed for consumption by an
 * external Java system.   
 * @author sjunejo
 */
public class JavaServiceImplTAFCGenerator extends ServiceGenerator {

	private static final String TEMPLATE = "JavaServiceImplTAFC.vm";
	
	private Template template;
	
	public JavaServiceImplTAFCGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}
	
	public void generate(ServiceDescriptor service, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", service);
			ctx.put("serviceImpl", service.getName() + "ProviderImplTAFC");
			ctx.put("jbcServiceImpl", service.getName() + "Impl");
			ctx.put("typeUtils", new TypeUtils());
			ctx.put("soaPackage", "com.temenos.soa.services");
			
			if (writer == null) {
				String dirName = path + "/" + service.getPackageDirectory() + "/tafc";
				String apiFileName = service.getName() + "ProviderImplTAFC.java";
		
				writer = createWriter(dirName, apiFileName);
			}
			System.out.println("Merging Template - JavaServiceImplTAFCGenerator: " + TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
