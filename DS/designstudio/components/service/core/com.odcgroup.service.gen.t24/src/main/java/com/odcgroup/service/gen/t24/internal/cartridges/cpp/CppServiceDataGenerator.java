/*  
 * this class is to generate artifact related to complex type 
 * defined in UML model.  
 * 
 * 1)C++ data class .h and .cpp for each complex type 
 * 2)C++ data handler class .h and .cpp to transform  
 * 	 each complex type between JBC VAR and C++
 * 
 */

package com.odcgroup.service.gen.t24.internal.cartridges.cpp;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassCppDescriptor;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLServiceCppDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class CppServiceDataGenerator extends ServiceGenerator{	
	
	//velocity template to generate DLLDECL definition
	public static final String CPP_DLL_DECL_HEADER_TEMPLATE =
		"CPP_DLLDeclHeader.vm";
	
	//velocity template for c++ business object .h file
	public static final String CPP_BUSINESS_OBJECT_HEADER_TEMPLATE =
		"CPP_BusinessObjectHeader.vm";
	
	//velocity template for c++ business object .cpp file
	public static final String CPP_BUSINESS_OBJECT_CPP_TEMPLATE =
		"CPP_BusinessObjectCpp.vm";
	
	//velocity template for c++ business object handler .h file
	public static final String CPP_BUSINESS_OBJECT_HANDLER_HEADER_TEMPLATE =
		"CPP_BusinessObjectHandlerHeader.vm";
	
	//velocity template for c++ business object handler .cpp file
	public static final String CPP_BUSINESS_OBJECT_HANDLER_CPP_TEMPLATE =
		"CPP_BusinessObjectHandlerCpp.vm";	
	
	
	private VelocityTemplateLoader m_loader = null;
	private Template template = null;
	
	
	public CppServiceDataGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;		
	}
	
	
	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer,
			String path) throws LoadTemplateException {
		// construct UMLService model
		UMLServiceCppDescriptor umlService = new UMLServiceCppDescriptor(
				serviceDescriptor);

		for (UMLClassCppDescriptor umlClass : umlService.getUMLClasses()) {
			// generate business object .h file
			writer = createWriter(path, umlClass.getClassHeaderFileName());
			genBusinessObjectHeader(umlService.getNamespace(), umlClass, writer);

			// generate business object .cpp file
			writer = createWriter(path, umlClass.getClassCppFileName());
			genBusinessObjectCpp(umlService.getNamespace(), umlClass, writer);

			// generate business object handler .h file
			writer = createWriter(path,
					umlClass.getHandlerClassHeaderFileName());
			genBusinessObjectHandlerHeader(umlService.getNamespace(), umlClass,
					writer);

			// generate business object handler .cpp file
			writer = createWriter(path, umlClass.getHandlerClassCppFileName());
			genBusinessObjectHandlerCpp(umlService.getNamespace(), umlClass,
					writer);
		}

		// generate DLL decl header file
		writer = createWriter(path,
				umlService.getDLLDeclHeaderFileName());
		genDLLDeclHeaderFile(umlService, writer);
		
	}		
	
	/*
	 * generate .h file for the given complex type 
	 * defined in the given service
	 */
	public void genBusinessObjectHeader(String namespace, UMLClassCppDescriptor umlClass, Writer writer)
			throws LoadTemplateException {
		
		template = m_loader.loadTemplate(CPP_BUSINESS_OBJECT_HEADER_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("namespace", namespace);
			ctx.put("formatter", Formatter.getInstance());
			ctx.put("umlClass", umlClass);		

			System.out.println("Merging Template - "
													+ umlClass.getClassHeaderFileName()
													+ " : " 
													+ CPP_BUSINESS_OBJECT_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * generate .cpp file for the given complex type
	 * defined in the given service
	 */
	public void genBusinessObjectCpp(String namespace, UMLClassCppDescriptor umlClass,Writer writer) 
			throws LoadTemplateException {
		
		template = m_loader.loadTemplate(CPP_BUSINESS_OBJECT_CPP_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();
		
		try {			
			ctx.put("namespace", namespace);
			ctx.put("formatter", Formatter.getInstance());
			ctx.put("umlClass", umlClass);	

			System.out.println("Merging Template - "
													+ umlClass.getClassCppFileName()
													+ " : " 
													+ CPP_BUSINESS_OBJECT_CPP_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate .h file of the handler to manipulate
	 * the given complex type
	 */
	public void genBusinessObjectHandlerHeader(String namespace, UMLClassCppDescriptor umlClass, Writer writer) 
					throws LoadTemplateException {
		
		template = 
			m_loader.loadTemplate(
					CPP_BUSINESS_OBJECT_HANDLER_HEADER_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {			
			ctx.put("namespace", namespace);
			ctx.put("formatter", Formatter.getInstance());
			ctx.put("umlClass", umlClass);			

			System.out.println("Merging Template - "
													+ umlClass.getHandlerClassHeaderFileName()
													+ " : " 
													+ CPP_BUSINESS_OBJECT_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * generate .cpp file for the handler to manipulate
	 * the given complex type
	 */
	public void genBusinessObjectHandlerCpp(String namespace, UMLClassCppDescriptor umlClass, Writer writer) 
				throws LoadTemplateException {
		
		template = 
			m_loader.loadTemplate(
					CPP_BUSINESS_OBJECT_HANDLER_CPP_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();
		
		try {			
			ctx.put("namespace", namespace);
			ctx.put("formatter", Formatter.getInstance());
			ctx.put("umlClass", umlClass);			

			System.out.println("Merging Template - " 
													+ umlClass.getHandlerClassHeaderFileName()
													+ " : " 
													+ CPP_BUSINESS_OBJECT_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * generate dll decl header file
	 */
	public void genDLLDeclHeaderFile(UMLServiceCppDescriptor umlService, Writer writer) 
				throws LoadTemplateException {
		
		template = 
			m_loader.loadTemplate(CPP_DLL_DECL_HEADER_TEMPLATE);					
		
		VelocityContext ctx = new VelocityContext();
		
		try {			
			ctx.put("service", umlService);			

			System.out.println("Merging Template - " 
								+ umlService.getDLLDeclHeaderFileName()
								+ " : " 
								+ CPP_DLL_DECL_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}	
}
