/*
 * this generator is to generate artifact related .NET API,
 * 1) .NET interface
 * 2) .NET wrapper for C++ API
 */
package com.odcgroup.service.gen.t24.internal.categories.dotnet.api;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLServiceDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class DotNetServiceAPIGenerator extends ServiceGenerator{	
	//velocity template for .NET service API
	public static final String DOTNET_SERVICE_API_TEMPLATE = 
		"DOTNET_ServiceAPI.vm";
	
	//velocity template for .NET service impl .h file
	public static final String DOTNET_SERVICE_IMPL_HEADER_TEMPLATE =
		"DOTNET_ServiceImplHeader.vm";
	
	//velocity template for .NET service impl .cpp file
	public static final String DOTNET_SERVICE_IMPL_CPP_TEMPLATE =
		"DOTNET_ServiceImplCpp.vm";
	
	// velocity template for .NET WCF Service .cs file
	public static final String DOTNET_WCF_SERVICE_CS_TEMPLATE = 
		"DOTNET_WCFServiceCS.vm";
	
	// velocity template for .NET WCF Service Impl .cs.svc file
	public static final String DOTNET_WCF_SERVICE_IMPL_SVCCS_TEMPLATE = 
		"DOTNET_WCFServiceCSSVCImpl.vm";
	
	// velocity template for Connection Manager .cs file
	public static final String DOTNET_WCF_SERVICE_CONN_MAN_CS_TEMPLATE = 
		"DOTNET_WCFServiceConnManCS.vm";
	
	// velocity template for .NET WCF Service Impl .cs.svc file
	public static final String DOTNET_WCF_SERVICE_SVC_MARKUP_XML_TEMPLATE = 
		"DOTNET_WCFServiceSVCMarkupXML.vm";
	
	// velocity template for .NET WCF Service web.config file
	public static final String DOTNET_WCF_SERVICE_WEB_CONFIG_XML_TEMPLATE = 
		"DOTNET_WCFServiceWebConfigXML.vm";
	
	// velocity template for .NET WCF Service app.native.config file
	public static final String DOTNET_WCF_SERVICE_NATIVE_CONFIG_XML_TEMPLATE = 
		"DOTNET_WCFServiceNativeConfigXML.vm";
	
	// velocity template for .NET WCF Service app.native.config file
	public static final String DOTNET_WCF_SERVICE_REMOTE_CONFIG_XML_TEMPLATE = 
		"DOTNET_WCFServiceRemoteConfigXML.vm";
	
	// velocity template for .NET WCF Service Global.asax file
	public static final String DOTNET_WCF_SERVICE_GLOBAL_ASAX_TEMPLATE =
		"DOTNET_WCFServiceGlobalAsax.vm";
	
	private VelocityTemplateLoader m_loader = null;
	private Template template = null;
	
	
	public DotNetServiceAPIGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;		
	}	
	
	
	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path)
			throws LoadTemplateException {
		//construct UMLService model
		UMLServiceDotNetDescriptor umlService = new UMLServiceDotNetDescriptor(serviceDescriptor);
				
		//generate .NET service API .h file
		writer = createWriter(path, 
							  					umlService.getDotNetServiceAPIHeaderFileName());
		genDotNetServiceAPIFile(umlService, writer);
		
		//generate .NET service impl .h file
		writer = createWriter(path, 
													umlService.getDotNetServiceImplHeaderFileName());
		genDotNetServiceImplHeaderFile(umlService, writer);		
		
		//generate .NET service impl .cpp file
		writer = createWriter(path, 
													umlService.getDotNetServiceImplCppFileName());
		genDotNetServiceImplCppFile(umlService, writer);
		
	}	
	
	/*
	 * generate .NET service API interface
	 */
	public void genDotNetServiceAPIFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException {
		template = m_loader.loadTemplate(DOTNET_SERVICE_API_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getDotNetServiceAPIHeaderFileName() 
												+ ":" 
												+ DOTNET_SERVICE_API_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .NET service impl .h file	 
	 */
	public void genDotNetServiceImplHeaderFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException {
		template = m_loader.loadTemplate(DOTNET_SERVICE_IMPL_HEADER_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getDotNetServiceImplHeaderFileName() 
												+ ": " 
												+ DOTNET_SERVICE_IMPL_HEADER_TEMPLATE);
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
	public void genDotNetServiceImplCppFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(DOTNET_SERVICE_IMPL_CPP_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getDotNetServiceImplCppFileName() 
												+ ": " 
												+ DOTNET_SERVICE_IMPL_CPP_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .NET WCF service Interface .cs file	 
	 */
	public void genDotNetWCFServiceCSFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(DOTNET_WCF_SERVICE_CS_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getDotNetWCFServiceCSFileName() 
												+ ": " 
												+ DOTNET_WCF_SERVICE_CS_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .NET WCF service Impl .svc.cs file	 
	 */
	public void genDotNetWCFServiceImplCSSVCFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(DOTNET_WCF_SERVICE_IMPL_SVCCS_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());	
			
			System.out.println("Merging Template - " 
												+ umlService.getDotNetWCFServiceImplSVCCSFileName() 
												+ ": " 
												+ DOTNET_WCF_SERVICE_IMPL_SVCCS_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .NET WCF service Conn Man .cs file	 
	 */
	public void genDotNetWCFServiceConnManCSFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(DOTNET_WCF_SERVICE_CONN_MAN_CS_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			ctx.put("service", umlService);
			
			System.out.println("Merging Template - JConnectionManager" 
												+ ": " 
												+ DOTNET_WCF_SERVICE_CONN_MAN_CS_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .NET WCF service Impl .svc file	 
	 */
	public void genDotNetWCFServiceSVCMarkupXMLFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(DOTNET_WCF_SERVICE_SVC_MARKUP_XML_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - " 
												+ umlService.getDotNetWCFServiceSVCMarkupXMLFileName() 
												+ ": " 
												+ DOTNET_WCF_SERVICE_SVC_MARKUP_XML_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}	
	
	/*
	 * generate .NET WCF app.native.config file	 
	 */
	public void genDotNetWCFServiceAppNativeConfigXMLFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(DOTNET_WCF_SERVICE_NATIVE_CONFIG_XML_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			
	
			System.out.println("Merging Template - app.native.config" 
													+ ": " 
													+ DOTNET_WCF_SERVICE_NATIVE_CONFIG_XML_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .NET WCF app.native.config file	 
	 */
	public void genDotNetWCFServiceAppRemoteConfigXMLFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(DOTNET_WCF_SERVICE_REMOTE_CONFIG_XML_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - app.remote.config"
												+ ": "  
												+ DOTNET_WCF_SERVICE_REMOTE_CONFIG_XML_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .NET WCF service Global.asax file	 
	 */
	public void genDotNetWCFServiceGlobalAsaxFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader.loadTemplate(DOTNET_WCF_SERVICE_GLOBAL_ASAX_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());			

			System.out.println("Merging Template - global.asax" 
												+ ": " 
												+ DOTNET_WCF_SERVICE_GLOBAL_ASAX_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
