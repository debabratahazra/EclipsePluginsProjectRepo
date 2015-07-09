package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.utils.TypeUtils;

/**
 * Generates an impl class for the service described by the passed in {@link ServiceDescriptor} that 
 * invokes the TAFJ adaptor subroutine.  This is used where the service is exposed for consumption by an
 * external Java system.   
 * @author sjunejo
 */
public class JavaServiceImplTAFJGenerator extends ServiceGenerator {

	private static final String TEMPLATE = "JavaServiceImplTAFJ.vm";
	
	private Template template;
	
	public JavaServiceImplTAFJGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}
	
	public void generate(ServiceDescriptor service, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", service);
			ctx.put("serviceImpl", service.getName() + "ProviderImplTAFJ");
			ctx.put("servicePoolExt", service.getName() + "PoolExt");
			ctx.put("typeUtils", new TypeUtils());
			ctx.put("soaPackageName", "com.temenos.soa.services");
			
			if (writer == null) {
				String dirName = path + "/" + service.getPackageDirectory() + "/tafj";
				String apiFileName = service.getName() + "ProviderImplTAFJ.java";
		
				writer = createWriter(dirName, apiFileName);
			}
			System.out.println("Merging Template - JavaServiceImplTAFJGenerator: " + TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
