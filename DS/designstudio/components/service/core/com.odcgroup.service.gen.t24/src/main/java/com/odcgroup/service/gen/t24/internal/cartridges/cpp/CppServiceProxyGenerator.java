/*  
 * this class is to generate artifact related to c++ proxy adaptor 
 *  
 * 1)C++ Proxy interface that is contract between JBC API and 3rd party service 
 * 2)JBC proxy adaptor that is the wrapper of C++ proxy adaptor
 * 3)C++ proxy adaptor .h and .cpp file  
 * 4)c++ service proxy adaptor library .def file
 * 5)DLLDECL .h file
 * 
 */ 
package com.odcgroup.service.gen.t24.internal.cartridges.cpp;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLOperationCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLServiceCppDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class CppServiceProxyGenerator extends ServiceGenerator{	
		
	//velocity template for c++ service proxy interface
	public static final String CPP_SERVICE_PROXY_INTERFACE_TEMPLATE = 
		"CPP_ServiceProxyInterface.vm";		
	
	//velocity template for c++ service proxy adaptor .h file
	public static final String CPP_SERVICE_PROXY_ADAPTOR_HEADER_TEMPLATE =
		"CPP_ServiceProxyAdaptorHeader.vm";
		
	//velocity template for c++ service proxy adaptor .cpp file
	public static final String CPP_SERVICE_PROXY_ADAPTOR_CPP_TEMPLATE =
		"CPP_ServiceProxyAdaptorCpp.vm";
		
	//velocity template for JBC subroutine to wrap c++ proxy adaptor
	public static final String CPP_PROXY_ADAPTOR_JBC_INTERFACE_TEMPLATE = 
		"CPP_ServiceProxyAdaptorJBCInterface.vm";
		
		
	private VelocityTemplateLoader m_loader = null;
	private Template template = null;
		
	public CppServiceProxyGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;		
	}	
	
	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path)
			throws LoadTemplateException {
		//construct UMLService model
		UMLServiceCppDescriptor umlService = new UMLServiceCppDescriptor(serviceDescriptor);
				
		//generate c++ service interface
		writer = createWriter(path, umlService.getServiceProxyHeaderFileName());
		genServiceProxyInterface(umlService, writer);
		
		//generate c++ proxy adaptor .h file
		writer = createWriter(path, umlService.getServiceProxyAdaptorHeaderFileName());
		genServiceProxyAdaptorHeader(umlService, writer);
		
		//generate c++ proxy adaptor .cpp file
		writer = createWriter(path, umlService.getServiceProxyAdaptorCppFileName());
		genServiceProxyAdaptorCpp(umlService, writer);
		
		for(UMLOperationCppDescriptor umlOperation : umlService.getGenOperations()) {
			//generate one JBC subroutine for each C function in proxy adaptor 
			writer = createWriter(path, umlOperation.getJBCProxyAdaptorSubroutineFileName());
			genProxyAdaptorJBCInterface(umlOperation, writer);
		}		
	}	
	
	/*
	 * generate C++ service proxy interface
	 */
	public void genServiceProxyInterface(UMLServiceCppDescriptor umlService, Writer writer) 
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(CPP_SERVICE_PROXY_INTERFACE_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getServiceProxyHeaderFileName() 
												+ ": " 
												+ CPP_SERVICE_PROXY_INTERFACE_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .H file of service proxy adaptor 
	 */
	public void genServiceProxyAdaptorHeader(UMLServiceCppDescriptor umlService,
			                                  Writer writer) 
		throws LoadTemplateException {
		
		template = 
			m_loader.loadTemplate(CPP_SERVICE_PROXY_ADAPTOR_HEADER_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());						
			

			System.out.println("Merging Template - " 
												+ umlService.getServiceProxyAdaptorHeaderFileName()
												+ " : " 
												+ CPP_SERVICE_PROXY_ADAPTOR_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .cpp file of service proxy adaptor 
	 */
	public void genServiceProxyAdaptorCpp(UMLServiceCppDescriptor umlService,
			                                Writer writer) 
		throws LoadTemplateException {
		
		template = 
			m_loader.loadTemplate(CPP_SERVICE_PROXY_ADAPTOR_CPP_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());	

			System.out.println("Merging Template - " 
												+ umlService.getServiceProxyAdaptorCppFileName() 
												+ " : " 
												+ CPP_SERVICE_PROXY_ADAPTOR_CPP_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate JBC source file to wrapp the given operation
	 */
	public void genProxyAdaptorJBCInterface(UMLOperationCppDescriptor umlOperation,
			                       Writer writer) throws LoadTemplateException {
		
		template = m_loader.loadTemplate(CPP_PROXY_ADAPTOR_JBC_INTERFACE_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("operation", umlOperation);
			ctx.put("formatter", Formatter.getInstance());
			
			System.out.println("Merging Template - " 
												+ umlOperation.getJBCProxyAdaptorSubroutineFileName()
												+ " : " 
												+ CPP_PROXY_ADAPTOR_JBC_INTERFACE_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}		
}
