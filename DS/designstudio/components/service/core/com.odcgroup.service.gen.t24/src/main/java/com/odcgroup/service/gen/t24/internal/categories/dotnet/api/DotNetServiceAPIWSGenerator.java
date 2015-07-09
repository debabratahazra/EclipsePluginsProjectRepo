/*
 * this generator is to generate artifact related .NET API,
 * 1) .NET interface
 * 2) .NET wrapper for C++ API
 */
package com.odcgroup.service.gen.t24.internal.categories.dotnet.api;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLServiceDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class DotNetServiceAPIWSGenerator extends ServiceGenerator {
	// velocity template for .NET service API
	public static final String DOTNET_SERVICE_API_TEMPLATE = "DOTNET_ServiceAPI.vm";

	// velocity template for .NET service impl .h file
	public static final String DOTNET_SERVICE_IMPL_HEADER_TEMPLATE = "DOTNET_ServiceImplHeader.vm";

	// velocity template for .NET service impl .cpp file
	public static final String DOTNET_SERVICE_IMPL_CPP_TEMPLATE = "DOTNET_ServiceImplCpp.vm";

	// velocity template for .NET WCF Service .cs file
	public static final String DOTNET_WCF_SERVICE_CS_TEMPLATE = "DOTNET_WCFServiceCS.vm";

	// velocity template for .NET WCF Service Impl .cs.svc file
	public static final String DOTNET_WCF_SERVICE_IMPL_SVCCS_TEMPLATE = "DOTNET_WCFServiceCSSVCImpl.vm";

	// velocity template for .NET WCF Service Behavior .cs file
	public static final String DOTNET_WCF_SERVICE_BEHVR_CS_TEMPLATE = "DOTNET_WCFServiceBehavior.vm";
	
	// velocity template for Connection Manager .cs file
	public static final String DOTNET_WCF_SERVICE_CONN_MAN_CS_TEMPLATE = "DOTNET_WCFServiceConnManCS.vm";

	// velocity template for .NET WCF Service Impl .cs.svc file
	public static final String DOTNET_WCF_SERVICE_SVC_MARKUP_XML_TEMPLATE = "DOTNET_WCFServiceSVCMarkupXML.vm";

	// velocity template for .NET WCF Service web.config file
	public static final String DOTNET_WCF_SERVICE_WEB_CONFIG_XML_TEMPLATE = "DOTNET_WCFServiceWebConfigXML.vm";

	// velocity template for .NET WCF Service app.native.config file
	public static final String DOTNET_WCF_SERVICE_NATIVE_CONFIG_XML_TEMPLATE = "DOTNET_WCFServiceNativeConfigXML.vm";

	// velocity template for .NET WCF Service app.native.config file
	public static final String DOTNET_WCF_SERVICE_REMOTE_CONFIG_XML_TEMPLATE = "DOTNET_WCFServiceRemoteConfigXML.vm";

	// velocity template for .NET WCF Service Global.asax file
	public static final String DOTNET_WCF_SERVICE_GLOBAL_ASAX_TEMPLATE = "DOTNET_WCFServiceGlobalAsax.vm";

	private VelocityTemplateLoader m_loader = null;
	private Template template = null;

	public DotNetServiceAPIWSGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;
	}

	@Override
	public void generate(ServiceDescriptor serviceDescriptor, Writer writer,
			String path) throws LoadTemplateException {
		// construct UMLService model
		UMLServiceDotNetDescriptor umlService = new UMLServiceDotNetDescriptor(
				serviceDescriptor);

		// generate .NET WCF service .cs file
		writer = createWriter(path, umlService.getDotNetWCFServiceCSFileName());
		genDotNetWCFServiceCSFile(umlService, writer);

		// generate .NET WCF service Impl .svc.cs file
		writer = createWriter(path, umlService.getDotNetWCFServiceImplSVCCSFileName());
		genDotNetWCFServiceImplCSSVCFile(umlService, writer);

		//generate .NET WCF service Behavior .cs file
		writer = createWriter(path, umlService.getDotNetWCFServiceBehaviorCSFileName());
		genDotNetWCFServiceBehaviorCSFile(umlService, writer);
		
		// generate .NET WCF service Connection Manager .cs file
		writer = createWriter(path, "JConnectionManager.cs");
		genDotNetWCFServiceConnManCSFile(umlService, writer);

		// generate .NET WCF Service Markup XML (.svc) file
		writer = createWriter(path, umlService.getDotNetWCFServiceSVCMarkupXMLFileName());
		genDotNetWCFServiceSVCMarkupXMLFile(umlService, writer);

		// generate .NET WCF Service Web Config (.config) file - As there can be
		// multiple interfaces
		// in a single model we have to generate services part within Java class
		// generator and set
		// as property
		genDotNetWCFServiceWebConfigXMLFile(umlService, null, path);

		// generate .NET WCF Service app.native.config file
		writer = createWriter(path, "app.native.config");
		genDotNetWCFServiceAppNativeConfigXMLFile(umlService, writer);

		// generate .NET WCF Service app.remote.config file
		writer = createWriter(path, "app.remote.config");
		genDotNetWCFServiceAppRemoteConfigXMLFile(umlService, writer);

		// generate .NET WCF Service Global.asax (.asax) file
		writer = createWriter(path, "global.asax");
		genDotNetWCFServiceGlobalAsaxFile(umlService, writer);

	}

	/*
	 * generate .NET service API interface
	 */
	public void genDotNetServiceAPIFile(UMLServiceDotNetDescriptor umlService, Writer writer) throws LoadTemplateException {
		template = m_loader.loadTemplate(DOTNET_SERVICE_API_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - "
					+ umlService.getDotNetServiceAPIHeaderFileName() + ":"
					+ DOTNET_SERVICE_API_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
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
					+ umlService.getDotNetServiceImplHeaderFileName() + ": "
					+ DOTNET_SERVICE_IMPL_HEADER_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate c++ service impl .cpp file
	 */
	public void genDotNetServiceImplCppFile(UMLServiceDotNetDescriptor umlService, Writer writer)
			throws LoadTemplateException {
		template = m_loader.loadTemplate(DOTNET_SERVICE_IMPL_CPP_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - "
					+ umlService.getDotNetServiceImplCppFileName() + ": "
					+ DOTNET_SERVICE_IMPL_CPP_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate .NET WCF service Interface .cs file
	 */
	public void genDotNetWCFServiceCSFile(UMLServiceDotNetDescriptor umlService, Writer writer)
			throws LoadTemplateException {
		template = m_loader.loadTemplate(DOTNET_WCF_SERVICE_CS_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - "
					+ umlService.getDotNetWCFServiceCSFileName() + ": "
					+ DOTNET_WCF_SERVICE_CS_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate .NET WCF service Impl .svc.cs file
	 */
	public void genDotNetWCFServiceImplCSSVCFile(UMLServiceDotNetDescriptor umlService, Writer writer)
			throws LoadTemplateException {
		template = m_loader
				.loadTemplate(DOTNET_WCF_SERVICE_IMPL_SVCCS_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - "
					+ umlService.getDotNetWCFServiceImplSVCCSFileName() + ": "
					+ DOTNET_WCF_SERVICE_IMPL_SVCCS_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate .NET WCF service behavior .cs file	 
	 */
	public void genDotNetWCFServiceBehaviorCSFile(UMLServiceDotNetDescriptor umlService, Writer writer) 
		throws LoadTemplateException	{
		template = m_loader
				.loadTemplate(DOTNET_WCF_SERVICE_BEHVR_CS_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {
			
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());	
			
			System.out.println("Merging Template - " 
												+ umlService.getDotNetWCFServiceBehaviorCSFileName()  + ": "
												+ DOTNET_WCF_SERVICE_BEHVR_CS_TEMPLATE);
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
			throws LoadTemplateException {
		template = m_loader
				.loadTemplate(DOTNET_WCF_SERVICE_CONN_MAN_CS_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", umlService);

			System.out.println("Merging Template - JConnectionManager" + ": "
					+ DOTNET_WCF_SERVICE_CONN_MAN_CS_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate .NET WCF service Impl .svc file
	 */
	public void genDotNetWCFServiceSVCMarkupXMLFile(UMLServiceDotNetDescriptor umlService, Writer writer)
			throws LoadTemplateException {
		template = m_loader
				.loadTemplate(DOTNET_WCF_SERVICE_SVC_MARKUP_XML_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - "
					+ umlService.getDotNetWCFServiceSVCMarkupXMLFileName()
					+ ": " + DOTNET_WCF_SERVICE_SVC_MARKUP_XML_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate .NET WCF service Web config xml file
	 */
	public void genDotNetWCFServiceWebConfigXMLFile(UMLServiceDotNetDescriptor umlService, Writer writer, String path)
			throws LoadTemplateException {
		template = m_loader
				.loadTemplate(DOTNET_WCF_SERVICE_WEB_CONFIG_XML_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			String dirName = path;
			String fileName = "Web.config";

			Document serviceDoc = generateServicesXML(umlService);
			Document updatedDoc = null;
			if (checkIfFileAlreadyExists(dirName, fileName)) {
				// This means we have to append above document to the existing
				// file contents
				updatedDoc = updateServiceDoc(serviceDoc, dirName, fileName);
			} else {
				updatedDoc = serviceDoc;
			}
			if (writer == null) {
				writer = createWriter(path, "Web.config");
			}
			if (updatedDoc != null) {
				String indentXML = StringUtils.transformXMLToString(updatedDoc);
				if (!indentXML.equals("")) {
					indentXML = indentXML
							.replace(
									"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
									"");
					ctx.put("servicesNode", indentXML);
				}
			} else {
				ctx.put("servicesNode",
						"Failed to generated XML Configurations. Please rectify before proceeding.");
			}

			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - Web.config" + ": "
					+ DOTNET_WCF_SERVICE_WEB_CONFIG_XML_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate .NET WCF app.native.config file
	 */
	public void genDotNetWCFServiceAppNativeConfigXMLFile(
			UMLServiceDotNetDescriptor umlService, Writer writer)
			throws LoadTemplateException {
		template = m_loader
				.loadTemplate(DOTNET_WCF_SERVICE_NATIVE_CONFIG_XML_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - app.native.config" + ": "
					+ DOTNET_WCF_SERVICE_NATIVE_CONFIG_XML_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate .NET WCF app.native.config file
	 */
	public void genDotNetWCFServiceAppRemoteConfigXMLFile(
			UMLServiceDotNetDescriptor umlService, Writer writer)
			throws LoadTemplateException {
		template = m_loader
				.loadTemplate(DOTNET_WCF_SERVICE_REMOTE_CONFIG_XML_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {
			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - app.remote.config" + ": "
					+ DOTNET_WCF_SERVICE_REMOTE_CONFIG_XML_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate .NET WCF service Global.asax file
	 */
	public void genDotNetWCFServiceGlobalAsaxFile(
			UMLServiceDotNetDescriptor umlService, Writer writer)
			throws LoadTemplateException {
		template = m_loader
				.loadTemplate(DOTNET_WCF_SERVICE_GLOBAL_ASAX_TEMPLATE);

		VelocityContext ctx = new VelocityContext();
		try {

			ctx.put("service", umlService);
			ctx.put("formatter", Formatter.getInstance());

			System.out.println("Merging Template - global.asax" + ": "
					+ DOTNET_WCF_SERVICE_GLOBAL_ASAX_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*********************** Manually Generating ServicesXML for Web.config *******************/
	/* Check if file Exists */
	private boolean checkIfFileAlreadyExists(String dirName, String fileName) {
		File file = new File(dirName, fileName);
		return file.exists();
	}

	/**
	 * This method will generate the configurations contents for services.xml
	 * file
	 * 
	 * @param service
	 *            , ServiceDescriptor object contains component service
	 *            information
	 * @return Document, prepared XML document will be returned
	 * 
	 */
	private Document generateServicesXML(UMLServiceDotNetDescriptor service) {
		try {
			// ///////////////////////////
			// Creating an empty XML Document

			// We need a Document
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			// //////////////////////
			// Creating the XML tree

			// create the root element and add it to the document
			Element root = doc.createElement("services");

			/********************************** Service Node *************************/
			// service node
			Element serviceElem = doc.createElement("service");
			serviceElem.setAttribute(
					"name",
					"temenos.soa." + service.getNamespace() + "."
							+ service.getServiceName() + "WS");

			// endpoint Node 1
			Element endpointElem1 = doc.createElement("endpoint");
			endpointElem1.setAttribute("address", "");
			endpointElem1.setAttribute("binding", "basicHttpBinding");
			endpointElem1.setAttribute(
					"contract",
					"temenos.soa." + service.getNamespace() + ".I"
							+ service.getServiceName() + "WS");
			serviceElem.appendChild(endpointElem1);

			// endpoint Node 2
			Element endpointElem2 = doc.createElement("endpoint");
			endpointElem2.setAttribute("address", "mex");
			endpointElem2.setAttribute("binding", "mexHttpBinding");
			endpointElem2.setAttribute("contract", "IMetadataExchange");
			serviceElem.appendChild(endpointElem2);

			// Append the service node
			root.appendChild(serviceElem);

			// Append the root to the document and return
			doc.appendChild(root);

			return doc;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * This method will read and load the existing services.xml file and append
	 * the provided document in the existing XML
	 * 
	 * @param serviceDoc
	 *            Document XML generated earlier
	 * @param dirName
	 *            Directory where file exists
	 * @param fileName
	 *            Name of the file to load the previous xml from
	 * @return Modified XML document to be written on file
	 */
	private Document updateServiceDoc(Document serviceDoc, String dirName,
			String fileName) {
		try {
			Element newRoot = serviceDoc.getDocumentElement();

			// Read the existing file and load as Document
			File file = new File(dirName, fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			NodeList existingServiceNodeList = doc
					.getElementsByTagName("service");
			for (int i = 0; i < existingServiceNodeList.getLength(); i++) {
				Node existingServiceNode = existingServiceNodeList.item(i);
				Node importExistingNode = serviceDoc.importNode(
						existingServiceNode, true);
				newRoot.appendChild(importExistingNode);
			}
		} catch (ParserConfigurationException pe) {
			System.out
					.println("Failed to parse existing services XML. ParserConfigException : "
							+ pe.getMessage());
		} catch (SAXException sae) {
			System.out
					.println("Failed to parse existing services XML. SAXException : "
							+ sae.getMessage());
		} catch (DOMException dome) {
			System.out
					.println("Failed to parse existing services XML. DOMException : "
							+ dome.getMessage());
		} catch (IOException ioe) {
			System.out.println("Failed to locate services.xml . IOException : "
					+ ioe.getMessage());
		} catch (Exception e) {
			System.out.println("Failed to parse services.xml . Exception : "
					+ e.getMessage());
		}
		// Return the original one back
		return serviceDoc;
	}
}
