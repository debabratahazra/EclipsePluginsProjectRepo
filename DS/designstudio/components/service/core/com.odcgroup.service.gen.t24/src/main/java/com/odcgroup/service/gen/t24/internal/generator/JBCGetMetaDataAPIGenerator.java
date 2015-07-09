
package com.odcgroup.service.gen.t24.internal.generator;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public class JBCGetMetaDataAPIGenerator extends ServiceGenerator{
	
	//velocity template to generate JBC subroutine to Query meta data
	public static final String JBC_GET_META_DATA_API_TEMPLATE = 
		"JBC_GetMetaDataAPI.vm";	
	
	private VelocityTemplateLoader m_loader = null;
	private Template template = null;	
	
	public JBCGetMetaDataAPIGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;		
	}	
	
	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path)
			throws LoadTemplateException {		
		
		writer = createWriter(path, serviceDescriptor.getName() + ".getMetaData.b");
		genSubroutine(serviceDescriptor, writer);
		
	}	
	
	/*
	 * generate JBC subroutine to query the meta data of this service component
	 */
	public void genSubroutine(ServiceDescriptor serviceDescriptor, Writer writer) 
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(JBC_GET_META_DATA_API_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", serviceDescriptor);
			ctx.put("majorVersion", System.getProperty("COMP_MAJOR_VERSION") == null ? "DEV" : System.getProperty("COMP_MAJOR_VERSION"));
			ctx.put("minorVersion", System.getProperty("COMP_MINOR_VERSION") == null ? "latest-dev" : System.getProperty("COMP_MINOR_VERSION"));

			System.out.println("Merging Template - " 
												+ serviceDescriptor.getName() + ".getMetaData" 
												+ ": " 
												+ JBC_GET_META_DATA_API_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}	
	
}
