package com.odcgroup.service.gen.t24.internal.categories.java.webservice;

import java.io.File;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;

/**
 * Generates a Java interface from a {@link ServiceDescriptor}. 
 * @author sjunejo
 *
 */
public class JavaWSApplicationContextXMLGenerator extends ServiceGenerator {

	private static final String TAFC_TEMPLATE = "JavaWSApplicationContextXML.vm";
	private static final String TAFJ_TEMPLATE = "JavaWSApplicationContextXMLTAFJ.vm";
	
	private VelocityTemplateLoader m_loader = null;
	private Template template;
	
	public JavaWSApplicationContextXMLGenerator(VelocityTemplateLoader loader) 
			throws LoadTemplateException {
		m_loader = loader;
	}
		
	public void generate(ServiceDescriptor service, Writer writer, String path) 
			throws LoadTemplateException {
		//generate JavaWSApplicationContext TAFC		
		writer = createWriter(path, service.getLowerCamelCaseName() + "Context.xml");
		generateJavaWSApplicationContextTAFC(service, writer);
		
		//generate JavaWSApplicationContext TAFJ		
		writer = createWriter(path + File.separator + "tafj", 
								service.getLowerCamelCaseName() + "Context.xml");
		generateJavaWSApplicationContextTAFJ(service, writer);
	}
	
	// Generate JavaWSApplicationConext for TAFC
	public void generateJavaWSApplicationContextTAFC(ServiceDescriptor service, Writer writer) 
			throws LoadTemplateException {
		template = m_loader.loadTemplate(TAFC_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();
		try {
			String serviceName = service.getName();
			ctx.put("service", service);
			ctx.put("serviceName", serviceName);
			ctx.put("webServiceName", serviceName + "WS");
			ctx.put("soaPackageName", "com.temenos.soa.services");
			
			System.out.println("Merging Template - JavaWSApplicationContextXMLGenerator: " +
													TAFC_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	// Generate JavaWSApplicationConext for TAFJ
	public void generateJavaWSApplicationContextTAFJ(ServiceDescriptor service, Writer writer) 
			throws LoadTemplateException {
		template = m_loader.loadTemplate(TAFJ_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();
		try {
			String serviceName = service.getName();
			ctx.put("service", service);
			ctx.put("serviceName", serviceName);
			ctx.put("webServiceName", serviceName + "WS");
			ctx.put("soaPackageName", "com.temenos.soa.services");
			
			System.out.println("Merging Template - JavaWSApplicationContextXMLGenerator: " +
													TAFJ_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
