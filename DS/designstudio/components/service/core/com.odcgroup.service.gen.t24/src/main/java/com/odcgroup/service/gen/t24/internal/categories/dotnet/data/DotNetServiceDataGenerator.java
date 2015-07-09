/*  
 * this class is to generate .NET artifact related to complex type 
 * defined in UML model.  
 * 
 * 1).NET data class for each complex type 
 * 2)helper utility to marshal and unmarshal data class between native C++ and .NET 
 *  
 */

package com.odcgroup.service.gen.t24.internal.categories.dotnet.data;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLServiceDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class DotNetServiceDataGenerator extends ServiceGenerator {

	// velocity template to generate DLLDECL definition
	public static final String DOTNET_DLL_DECL_HEADER_TEMPLATE = "DOTNET_DLLDeclHeader.vm";

	// velocity template for managed business object .h file
	public static final String DOTNET_BUSINESS_OBJECT_HEADER_TEMPLATE = "DOTNET_BusinessObjectHeader.vm";

	// velocity template for managed business object .cpp file
	public static final String DOTNET_BUSINESS_OBJECT_CPP_TEMPLATE = "DOTNET_BusinessObjectCpp.vm";

	// velocity template for managed business object helper .h file
	// which is to do marshal between managed and unmanaged business object
	public static final String DOTNET_BUSINESS_OBJECT_MARSHAL_HEADER_TEMPLATE = "DOTNET_BusinessObjectMarshalHeader.vm";

	// velocity template for managed business object helper .cpp file
	// which is to do marshal between managed and unmanaged business object
	public static final String DOTNET_BUSINESS_OBJECT_MARSHAL_CPP_TEMPLATE = "DOTNET_BusinessObjectMarshalCpp.vm";

	// velocity template for managed business object converter .cs file
	// which is to convert between managed data and jRemote JDynArray object
	public static final String DOTNET_BUSINESS_OBJECT_CONVERTER_CSHARP_TEMPLATE = "DOTNET_BusinessObjectCSharpConverter.vm";

	private VelocityTemplateLoader m_loader = null;
	private Template template = null;

	public DotNetServiceDataGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;
	}

	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer,
			String path) throws LoadTemplateException {
		// construct UMLService model
		UMLServiceDotNetDescriptor umlService = new UMLServiceDotNetDescriptor(
				serviceDescriptor);

		for (UMLClassDotNetDescriptor umlClass : umlService.getUMLClasses()) {
			// generate business object .h file
			writer = createWriter(path, umlClass.getDotNetClassHeaderFileName());
			genManagedBusinessObjectHeader(umlService.getNamespace(), umlClass,
					writer);

			// generate business object .cpp file
			writer = createWriter(path, umlClass.getDotNetClassCppFileName());
			genManagedBusinessObjectCpp(umlService.getNamespace(), umlClass,
					writer);

			// generate business object helper .h file
			writer = createWriter(path,
					umlClass.getMarshalClassHeaderFileName());
			genManagedBusinessObjectMarshalHeader(umlService.getNamespace(),
					umlClass, writer);

			// generate business object helper .cpp file
			writer = createWriter(path, umlClass.getMarshalClassCppFileName());
			genManagedBusinessObjectMarshalCpp(umlService.getNamespace(),
					umlClass, writer);
			writer = createWriter(path,
					umlClass.getDotNetConverterClassCsFileName());
			genManagedDataConverterCSharp(umlService.getNamespace(), umlClass,
					writer);

		}

		// generate DLL decl header file
		writer = createWriter(path, umlService.getDotNetDLLDeclHeaderName());
		genManagedDLLDeclHeaderFile(umlService, writer);

	}

	/*
	 * generate .h file for the given complex type defined in the given service
	 */
	public void genManagedBusinessObjectHeader(String namespace,
			UMLClassDotNetDescriptor umlClass, Writer writer)
			throws LoadTemplateException {

		template = m_loader
				.loadTemplate(DOTNET_BUSINESS_OBJECT_HEADER_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			ctx.put("namespace", namespace);
			ctx.put("formatter", Formatter.getInstance());
			ctx.put("umlClass", umlClass);

			System.out.println("Merging Template - "
					+ umlClass.getDotNetClassHeaderFileName() + " : "
					+ DOTNET_BUSINESS_OBJECT_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * generate .cpp file for the given complex type defined in the given
	 * service
	 */
	public void genManagedBusinessObjectCpp(String namespace,
			UMLClassDotNetDescriptor umlClass, Writer writer)
			throws LoadTemplateException {

		template = m_loader.loadTemplate(DOTNET_BUSINESS_OBJECT_CPP_TEMPLATE);

		VelocityContext ctx = new VelocityContext();

		try {
			ctx.put("namespace", namespace);
			ctx.put("formatter", Formatter.getInstance());
			ctx.put("umlClass", umlClass);

			System.out.println("Merging Template - "
					+ umlClass.getDotNetClassCppFileName() + " : "
					+ DOTNET_BUSINESS_OBJECT_CPP_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate marshal helper class .h file for the given complex type defined
	 * in the given service
	 */
	public void genManagedBusinessObjectMarshalHeader(String namespace,
			UMLClassDotNetDescriptor umlClass, Writer writer)
			throws LoadTemplateException {

		template = m_loader
				.loadTemplate(DOTNET_BUSINESS_OBJECT_MARSHAL_HEADER_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			ctx.put("namespace", namespace);
			ctx.put("formatter", Formatter.getInstance());
			ctx.put("umlClass", umlClass);

			System.out.println("Merging Template - "
					+ umlClass.getMarshalClassHeaderFileName() + " : "
					+ DOTNET_BUSINESS_OBJECT_MARSHAL_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * generate marshal helper class .cpp file for the given complex type
	 * defined in the given service
	 */
	public void genManagedBusinessObjectMarshalCpp(String namespace,
			UMLClassDotNetDescriptor umlClass, Writer writer)
			throws LoadTemplateException {

		template = m_loader
				.loadTemplate(DOTNET_BUSINESS_OBJECT_MARSHAL_CPP_TEMPLATE);

		VelocityContext ctx = new VelocityContext();

		try {
			ctx.put("namespace", namespace);
			ctx.put("formatter", Formatter.getInstance());
			ctx.put("umlClass", umlClass);

			System.out.println("Merging Template - "
					+ umlClass.getMarshalClassCppFileName() + " : "
					+ DOTNET_BUSINESS_OBJECT_MARSHAL_CPP_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate dll decl header file
	 */
	public void genManagedDLLDeclHeaderFile(
			UMLServiceDotNetDescriptor umlService, Writer writer)
			throws LoadTemplateException {

		template = m_loader.loadTemplate(DOTNET_DLL_DECL_HEADER_TEMPLATE);

		VelocityContext ctx = new VelocityContext();

		try {
			ctx.put("service", umlService);

			System.out.println("Merging Template - "
					+ umlService.getDotNetDLLDeclHeaderName() + " : "
					+ DOTNET_DLL_DECL_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * generate managed data converter file(s)
	 */
	public void genManagedDataConverterCSharp(String namespace,
			UMLClassDotNetDescriptor umlClass, Writer writer)
			throws LoadTemplateException {

		template = m_loader
				.loadTemplate(DOTNET_BUSINESS_OBJECT_CONVERTER_CSHARP_TEMPLATE);

		VelocityContext ctx = new VelocityContext();

		try {
			ctx.put("namespace", namespace);
			ctx.put("umlClass", umlClass);

			System.out.println("Merging Template - "
					+ umlClass.getDotNetConverterClassCsFileName() + " : "
					+ DOTNET_BUSINESS_OBJECT_CONVERTER_CSHARP_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
