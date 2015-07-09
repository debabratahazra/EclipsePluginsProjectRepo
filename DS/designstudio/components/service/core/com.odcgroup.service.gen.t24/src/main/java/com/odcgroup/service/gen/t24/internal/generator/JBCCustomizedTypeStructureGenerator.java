
package com.odcgroup.service.gen.t24.internal.generator;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public class JBCCustomizedTypeStructureGenerator extends ServiceGenerator {
	
	//velocity template to generate JBC subroutine to Query customized type Structure
	public static final String API_TEMPLATE = 
		"JBC_GetCustomizedTypeStructure.vm";	
	
	private VelocityTemplateLoader m_loader = null;
	private Template template = null;	
	
	public JBCCustomizedTypeStructureGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;		
	}	
	
	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path)
			throws LoadTemplateException {		
		
		writer = createWriter(path, serviceDescriptor.getName() + ".getCustomizedTypeStructure.b");
		genSubroutine(serviceDescriptor, writer);
		
	}	
	
	/*
	 * generate JBC subroutine to query the meta data of this service component
	 */
	public void genSubroutine(ServiceDescriptor serviceDescriptor, Writer writer) 
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(API_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", serviceDescriptor);
			System.out.println("Merging Template - " 
												+ serviceDescriptor.getName() + ".getCustomizedTypeStructure" 
												+ ": " 
												+ API_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}	
	
}
