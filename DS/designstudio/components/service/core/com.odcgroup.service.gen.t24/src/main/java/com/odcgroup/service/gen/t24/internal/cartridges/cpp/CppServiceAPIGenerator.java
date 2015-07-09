/*
 * this class is to generate artifact related to C++ API 
 * 
 * 1)C++ Service Interface 
 * 2)C++ service Impl .h and .cpp, which is the wrapper JBC Impl
 * 3)T24 Service library .def file
 * 
 */
package com.odcgroup.service.gen.t24.internal.cartridges.cpp;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLServiceCppDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class CppServiceAPIGenerator extends ServiceGenerator{	
	//velocity template for C++ service API
	public static final String CPP_SERVICE_API_TEMPLATE = 
		"CPP_ServiceAPI.vm";
	
	//velocity template for c++ service impl .h file
	public static final String CPP_SERVICE_IMPL_HEADER_TEMPLATE =
		"CPP_ServiceImplHeader.vm";
	
	//velocity template for c++ service impl .cpp file
	public static final String CPP_SERVICE_IMPL_CPP_TEMPLATE =
		"CPP_ServiceImplCpp.vm";	
	
	private VelocityTemplateLoader m_loader = null;
	private Template template = null;
	
	
	public CppServiceAPIGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;		
	}
	
	
	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path)
			throws LoadTemplateException {
		//construct UMLService model
		UMLServiceCppDescriptor umlService = new UMLServiceCppDescriptor(serviceDescriptor);
		
		//generate service API .h file
		writer = createWriter(path, umlService.getServiceAPIHeaderFileName());
		genServiceAPIFile(umlService, writer);
		
		//generate service impl .h file
		writer = createWriter(path, umlService.getServiceImplHeaderFileName());
		genServiceImplHeaderFile(umlService, writer);		
		
		//generate service impl .cpp file
		writer = createWriter(path, umlService.getServiceImplCppFileName());
		genServiceImplCppFile(umlService, writer);		
	}	
	
	/*
	 * generate c++ service API interface
	 */
	public void genServiceAPIFile(UMLServiceCppDescriptor umlService, Writer writer) 
		throws LoadTemplateException {
		template = m_loader.loadTemplate(CPP_SERVICE_API_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getServiceAPIHeaderFileName() 
												+ ": " 
												+ CPP_SERVICE_API_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate c++ service impl .h file	 
	 */
	public void genServiceImplHeaderFile(UMLServiceCppDescriptor umlService, Writer writer) 
		throws LoadTemplateException {
		template = m_loader.loadTemplate(CPP_SERVICE_IMPL_HEADER_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getServiceImplHeaderFileName() 
												+ ": " 
												+ CPP_SERVICE_IMPL_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * generate c++ service impl .cpp file	 
	 */
	public void genServiceImplCppFile(UMLServiceCppDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(CPP_SERVICE_IMPL_CPP_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getServiceImplCppFileName() 
												+ ": " 
												+ CPP_SERVICE_IMPL_CPP_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}	
}
