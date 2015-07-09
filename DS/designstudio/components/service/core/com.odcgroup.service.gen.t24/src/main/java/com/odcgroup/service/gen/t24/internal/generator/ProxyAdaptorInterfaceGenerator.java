package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * Generates a TAFJ proxy adaptor interface class (intended for subroutine replacement)  
 * that will call out to a java proxy adaptor. 
 * @author mreilly
 */
public class ProxyAdaptorInterfaceGenerator extends ServiceGenerator {
	
	private static final String TEMPLATE = "ProxyAdaptorInterface.vm";

	private Template template;
	
	public ProxyAdaptorInterfaceGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		template = loader.loadTemplate(TEMPLATE);
	}


	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path) {
		for (OperationDescriptor operationDescriptor : serviceDescriptor.getGenOperations()) {
			generateOperation(serviceDescriptor, operationDescriptor, writer, path);
		}
	}
	
	private void generateOperation(ServiceDescriptor service, OperationDescriptor operation, Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		String serviceName = service.getName();
		String proxyAdaptorPackageName = service.getPackageName();
		String proxyAdaptorClass = serviceName + "ProxyAdaptor" + operation.getUpperName();
		String tafjClassName = operation.getTafjProxyInterfaceName();
		
		try {
			ctx.put("proxyAdaptorPackage", proxyAdaptorPackageName);
			ctx.put("proxyAdaptorClassName", proxyAdaptorClass);
			ctx.put("tafjClassName", tafjClassName);

			String dirName = path + "/" + service.getPackageDirectory();
			String apiFileName = tafjClassName + ".java";

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
