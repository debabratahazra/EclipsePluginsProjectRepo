package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * Generates a TAFJ adaptor class (intended for subroutine replacement) that will call out 
 * to an implementation specific implementation of the service interface, i.e. this supports calling out to 
 * an external Java implementation of the service. The impl classname will be loaded up from a properties 
 * file in the same directory.
 * @author jannadani
 */
public class ProxyAdaptorGenerator extends ServiceGenerator {
	
	private static final String TEMPLATE = "ProxyAdaptor.vm";

	private Template template;
	
	public ProxyAdaptorGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}

	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path) {
		for (OperationDescriptor operationDescriptor : serviceDescriptor.getGenOperations()) {
			generateOperation(serviceDescriptor, operationDescriptor, writer, path);
		}
	}
	
	private void generateOperation(ServiceDescriptor service, OperationDescriptor operation, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		String serviceName = service.getName();
		String packageName = service.getPackageName();
		String replService = "Proxy" + serviceName;
		String serviceAdaptor = serviceName + "ProxyAdaptor";
		
		try {
			ctx.put("service", serviceName);
			ctx.put("package", packageName);
			ctx.put("serviceAdaptor", serviceAdaptor);
			ctx.put("classes", service.getClassDefDescriptors());
			ctx.put("proxyService", replService);
			ctx.put("operation", operation);

			String dirName = path + "/" + service.getPackageDirectory();
			String apiFileName = serviceAdaptor + operation.getUpperName() + ".java";
			if (writer == null) {
				writer = createWriter(dirName, apiFileName);
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
