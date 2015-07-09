/*
 * this class is to generate the following C++ proxy adaptor 
 * resource code for each service component defined in UML:
 * 1)C++ Service Interface 
 * 2)C++ service proxy adaptor
 * 3)C++ business object .h and .cpp files
 * 4)C++ business object handler .h and .cpp files
 * 5)JBC subroutine to wrap each C function in C++ service proxy adaptor  
 */
package com.odcgroup.service.gen.t24.internal.cartridges.java;


import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;

public class UMLServiceJavaGenerator extends ServiceGenerator{
	
	//velocity template for Java Service API
	public static final String JAVA_SERVICE_API_TEMPLATE = 
		"Java_ServiceAPI.vm";		
	
	//velocity template for Java Service Impl
	public static final String JAVA_SERVICE_IMPL_TEMPLATE =
		"Java_ServiceImpl.vm";
		
	private VelocityTemplateLoader m_loader = null;
	private Template template = null;
	
	
	public UMLServiceJavaGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;		
	}	
	
	public void generate(ServiceDescriptor serviceDescriptor, 
											Writer writer, String path)
			throws LoadTemplateException {
		//construct UMLService model
		UMLServiceJavaDescriptor umlService = new UMLServiceJavaDescriptor(serviceDescriptor);
		
		
		//generate Java Service API		
		writer = createWriter(path + File.separator 
														+ packageToDir(umlService.getPackageName()),
													umlService.getServiceAPIName() + ".java");
		genServiceAPI(umlService, writer);
		
		//generate Java Service Impl
		writer = createWriter(path + File.separator 
														+ packageToDir(umlService.getPackageName()),
													umlService.getServiceImplName() + ".java");			
		genServiceImpl(umlService, writer);		
		
		
	}	
	
	/*
	 * generate Java Service API
	 */
	public void genServiceAPI(UMLServiceJavaDescriptor umlService, Writer writer) 
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(JAVA_SERVICE_API_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {			
			ctx.put("service", umlService);						

			System.out.println("Merging Template - " 
												+ umlService.getServiceAPIName() 
												+ ": " 
												+ JAVA_SERVICE_API_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate Java Service implemenation 
	 */
	public void genServiceImpl(UMLServiceJavaDescriptor umlService,
			                       Writer writer) 
		throws LoadTemplateException {
		
		template = 
			m_loader.loadTemplate(JAVA_SERVICE_IMPL_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			
			System.out.println("Merging Template - " 
												+ umlService.getServiceImplName()
												+ " : " 
												+ JAVA_SERVICE_IMPL_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private String packageToDir(String packageName) {
		if(packageName == null)
			return "";
		else
			return packageName.replace(".", File.separator);
	}
}
