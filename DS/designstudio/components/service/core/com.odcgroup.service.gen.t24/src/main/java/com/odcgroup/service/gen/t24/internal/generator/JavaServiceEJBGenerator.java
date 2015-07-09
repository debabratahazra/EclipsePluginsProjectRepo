package com.odcgroup.service.gen.t24.internal.generator;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

/**
 * This class will generate all EJB related artifact for TAFC and TAFJ
 * @author sjunejo
 *
 */
public class JavaServiceEJBGenerator extends ServiceGenerator{
	
	//velocity template for Service EJB Interface
	public static final String JAVA_EJB_API_TEMPLATE = 
		"JavaEJBAPI.vm";		
	
	//velocity template for Java Service EJB Impl TAFJ 
	public static final String JAVA_EJB_IMPL_TEMPLATE =
		"JavaEJBImpl.vm";
	
	//velocity template for EJB Resources
	public static final String JAVA_EJB_RESOURCES_TEMPLATE = 
		"JavaEJBResources.vm";		
		
	private VelocityTemplateLoader m_loader = null;
	private Template template = null;
	
	
	public JavaServiceEJBGenerator(VelocityTemplateLoader loader) {
		m_loader = loader;		
	}	
	
	public void generate(ServiceDescriptor service, Writer writer, String path)
			throws LoadTemplateException {
		//construct UMLService model
		//UMLServiceJavaDescriptor service = new UMLServiceJavaDescriptor(serviceDescriptor);
		
		//generate Java EJB Service Local API		
		writer = createWriter(path + File.separator	+ service.getEJBPackageDir(),
													service.getServiceEJBAPIName("Local") + ".java");
		genServiceEJBLocalAPI(service, writer);
		
		//generate Java EJB Service Remote API		
		writer = createWriter(path + File.separator	+ service.getEJBPackageDir(),
													service.getServiceEJBAPIName("Remote") + ".java");
		genServiceEJBRemoteAPI(service, writer);
		
		//generate Java EJB Service Impl For TAFJ		
		writer = createWriter(path + File.separator	+ service.getEJBPackageDir(),
													service.getServiceEJBImplName("TAFJ") + ".java");
		genServiceEJBTAFJImpl(service, writer);
		
		//generate Java EJB Service Impl For TAFC		
		writer = createWriter(path + File.separator	+ service.getEJBPackageDir(),
													service.getServiceEJBImplName("TAFC") + ".java");
		genServiceEJBTAFCImpl(service, writer);
		
		/**** Generate Java EJB Resources ****/ 
		
		// 1. For TAFC
		genServiceEJBResourcesForTAFC(service, null, path);
		
		// 2. For TAFJ
		genServiceEJBResourcesForTAFJ(service, null, path);
	}	
	
	/*
	 * generate Java Service EJB Local API
	 */
	public void genServiceEJBLocalAPI(ServiceDescriptor service, Writer writer) 
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(JAVA_EJB_API_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {			
			ctx.put("service", service);
			ctx.put("interfaceName", "Local");
			ctx.put("interfaceClassName", service.getServiceEJBAPIName("Local"));
			ctx.put("soaPackageName", "com.temenos.soa.services");
			
			System.out.println("Merging Template - " 
												+ service.getServiceEJBAPIName("Local")
												+ ": " 
												+ JAVA_EJB_API_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate Java Service EJB Remote API
	 */
	public void genServiceEJBRemoteAPI(ServiceDescriptor service, Writer writer) 
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(JAVA_EJB_API_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {			
			ctx.put("service", service);
			ctx.put("interfaceName", "Remote");
			ctx.put("interfaceClassName", service.getServiceEJBAPIName("Remote"));
			ctx.put("soaPackageName", "com.temenos.soa.services");
			
			System.out.println("Merging Template - " 
												+ service.getServiceEJBAPIName("Remote")
												+ ": " 
												+ JAVA_EJB_API_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate Java Service EJB Impl TAFJ
	 */
	public void genServiceEJBTAFJImpl(ServiceDescriptor service, Writer writer) 
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(JAVA_EJB_IMPL_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {			
			ctx.put("service", service);
			ctx.put("codeGeneratingFor", "TAFJ");
			ctx.put("serviceClassName", service.getServiceEJBImplName("TAFJ"));
			ctx.put("serviceImplements", service.getServiceEJBAPIName("Local") 
										+ ", " 
										+ service.getServiceEJBAPIName("Remote"));
			ctx.put("importedServiceAPI", service.getName() + "API");
			ctx.put("importedServiceImpl", service.getName() + "Impl");
			// runtimeProps is instantiated in velocity Template
			ctx.put("importedServiceImplInstantiation", "new " + service.getName() + "Impl(runtimeProps)");
			ctx.put("ejbCreateLogMessage", " with session properties : \" + runtimeProps");
			ctx.put("ejbCleanCode", "serviceAPI.cleanup();\n		serviceAPI = null;");
			// Common import
			List<String> commonImports = new ArrayList<String>();
			String commonPackage = "com.temenos.soa.services.";
			commonImports.add(commonPackage + "RuntimeProperties");
			commonImports.add(commonPackage + "UserContextCallBack");
			commonImports.add(commonPackage + "T24UserContextCallBackImpl");
			commonImports.add(commonPackage + "data.ResponseDetails");
			commonImports.add(commonPackage + "data.UserDetails");
			commonImports.add(commonPackage + "data.T24UserDetails");
			commonImports.add(commonPackage + "data.SSOUserDetails");
			ctx.put("commonImports", commonImports);
			
			System.out.println("Merging Template - " 
												+ service.getServiceEJBImplName("TAFJ")
												+ ": " 
												+ JAVA_EJB_IMPL_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * generate Java Service EJB Impl TAFC
	 */
	public void genServiceEJBTAFCImpl(ServiceDescriptor service, Writer writer) 
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(JAVA_EJB_IMPL_TEMPLATE);
		
		VelocityContext ctx = new VelocityContext();		
		try {			
			ctx.put("service", service);
			ctx.put("codeGeneratingFor", "TAFC");
			ctx.put("serviceClassName", service.getServiceEJBImplName("TAFC"));
			ctx.put("serviceImplements", service.getServiceEJBAPIName("Local") 
										+ ", " 
										+ service.getServiceEJBAPIName("Remote"));
			ctx.put("importedServiceAPI", service.getName() + "ProviderAPI");
			ctx.put("importedServiceImpl", "tafc." + service.getName() + "ProviderImplTAFC");
			ctx.put("importedServiceImplInstantiation", "new " + service.getName() + "ProviderImplTAFC()");
			ctx.put("ejbCreateLogMessage", "...\"");
			ctx.put("ejbCleanCode", "serviceAPI = null;");
			
			// Common import
			List<String> commonImports = new ArrayList<String>();
			String commonPackage = "com.temenos.soa.services.";
			commonImports.add(commonPackage + "UserContextCallBack");
			commonImports.add(commonPackage + "T24UserContextCallBackImpl");
			commonImports.add(commonPackage + "data.ResponseDetails");
			commonImports.add(commonPackage + "data.UserDetails");
			commonImports.add(commonPackage + "data.T24UserDetails");
			commonImports.add(commonPackage + "data.SSOUserDetails");
			ctx.put("commonImports", commonImports);
			
			System.out.println("Merging Template - " 
												+ service.getServiceEJBImplName("TAFC")
												+ ": " 
												+ JAVA_EJB_IMPL_TEMPLATE);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method will generate the ejb resources
	 */
	public void genServiceEJBResourcesForTAFC(ServiceDescriptor service, Writer writer, String path)
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(JAVA_EJB_RESOURCES_TEMPLATE);
		String GENERATED_JAVA_EJB_RSR_DIR = path + File.separator + "META-INF";
		String dirName = GENERATED_JAVA_EJB_RSR_DIR + File.separator + "tafc";
		// a. ejb-jar.xml
		try {
			Document xmlDoc = getServiceEJBJARXML(service, "TAFC");
			Document updatedDoc = null;
			String descFileName = "ejb-jar.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// b. jboss.xml
		try {
			Document xmlDoc = getServiceJbossXML(service, "TAFC");
			Document updatedDoc = null;
			String descFileName = "jboss.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// c. ibm-ejb-jar-bnd
		try {
			Document xmlDoc = getServiceIBMXML(service, "TAFC");
			Document updatedDoc = null;
			String descFileName = "ibm-ejb-jar-bnd.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// d. weblogic-ejb-jar
		try {
			Document xmlDoc = getServiceWebLogicXML(service, "TAFC");
			Document updatedDoc = null;
			String descFileName = "weblogic-ejb-jar.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// e. jboss-ejb3.xml [jBoss 7+]
		try {
			Document xmlDoc = getServiceJbossEJB3XML(service, "TAFC");
			Document updatedDoc = null;
			String descFileName = "jboss-ejb3.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method will generate the ejb resources
	 */
	public void genServiceEJBResourcesForTAFJ(ServiceDescriptor service, Writer writer, String path)
		throws LoadTemplateException {
		
		template = m_loader.loadTemplate(JAVA_EJB_RESOURCES_TEMPLATE);
		String GENERATED_JAVA_EJB_RSR_DIR = path + File.separator + "META-INF";
		String dirName = GENERATED_JAVA_EJB_RSR_DIR + File.separator + "tafj";
		// a. ejb-jar.xml
		try {
			Document xmlDoc = getServiceEJBJARXML(service, "TAFJ");
			Document updatedDoc = null;
			String descFileName = "ejb-jar.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// b. jboss.xml
		try {
			Document xmlDoc = getServiceJbossXML(service, "TAFJ");
			Document updatedDoc = null;
			String descFileName = "jboss.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// c. ibm-ejb-jar-bnd
		try {
			Document xmlDoc = getServiceIBMXML(service, "TAFJ");
			Document updatedDoc = null;
			String descFileName = "ibm-ejb-jar-bnd.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// d. weblogic-ejb-jar
		try {
			Document xmlDoc = getServiceWebLogicXML(service, "TAFJ");
			Document updatedDoc = null;
			String descFileName = "weblogic-ejb-jar.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// e. jboss-ejb3.xml [jBoss 7+]
		try {
			Document xmlDoc = getServiceJbossEJB3XML(service, "TAFJ");
			Document updatedDoc = null;
			String descFileName = "jboss-ejb3.xml";
			boolean fileExists = checkIfFileAlreadyExists(dirName, descFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendXMLDoc(xmlDoc, dirName, descFileName);
			} else {
				updatedDoc = xmlDoc;
			}
			VelocityContext ctx = new VelocityContext();
			ctx.put("resourceXML", StringUtils.transformXMLToString(updatedDoc));
			System.out.println("Merging Template - " 
												+ dirName + File.separator + descFileName
												+ ": " 
												+ JAVA_EJB_RESOURCES_TEMPLATE);
			Writer localWriter;
			if (writer == null)
				localWriter = createWriter(dirName, descFileName);
			else 
				localWriter = writer;
			template.merge(ctx, localWriter);
			System.out.println("Done");
			localWriter.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Generate weblogic-ejb-jar content for TAFC and TAFJ  
	 */
	public Document getServiceWebLogicXML(ServiceDescriptor service, String framework) {
		// Initialise common variables for this service
		String serviceEJBName = service.getServiceEJBName();
		String serviceEJBPackageName = service.getEJBPackageName();
		String serviceRemoteAPIFullyQualifiedName = serviceEJBPackageName + "." + service.getServiceEJBAPIName("Remote");
		try {
            //We need a new Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            ////////////////////////
            //Creating the XML tree
            ////////////////////////
            
            //Create the root element with namespace
            Element root = doc.createElement("wls:weblogic-ejb-jar");
            root.setAttribute("xmlns:wls", "http://xmlns.oracle.com/weblogic/weblogic-ejb-jar");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd http://xmlns.oracle.com/weblogic/weblogic-ejb-jar http://xmlns.oracle.com/weblogic/weblogic-ejb-jar/1.1/weblogic-ejb-jar.xsd");
            
            // Session node
            Element entBeanNode = doc.createElement("wls:weblogic-enterprise-bean");
            
            // EJB Name
            Element ejbName = doc.createElement("wls:ejb-name");
            ejbName.setTextContent(serviceEJBName + framework);
            entBeanNode.appendChild(ejbName);
            
            // Stateless Session Desc
            Element stlsSessDescNode = doc.createElement("wls:stateless-session-descriptor");
            Element busIntJNDIMapNameNode = doc.createElement("wls:business-interface-jndi-name-map");
            Element busRemote = doc.createElement("wls:business-remote");
            busRemote.setTextContent(serviceRemoteAPIFullyQualifiedName);
            busIntJNDIMapNameNode.appendChild(busRemote);
            Element busJNDIName = doc.createElement("wls:jndi-name");
            busJNDIName.setTextContent("ejb/" + service.getServiceEJBAPIName("Remote"));
            busIntJNDIMapNameNode.appendChild(busJNDIName);
            stlsSessDescNode.appendChild(busIntJNDIMapNameNode);
            entBeanNode.appendChild(stlsSessDescNode);
            
            // Framework specific config
            if (framework.equals("TAFC")) {
            	Element resDescNode = doc.createElement("wls:resource-description");
                Element resRefName = doc.createElement("wls:res-ref-name");
                resRefName.setTextContent("jca/t24ConnectionFactory");
                resDescNode.appendChild(resRefName);
                Element resRefJNDIName = doc.createElement("wls:jndi-name");
                resRefJNDIName.setTextContent("jca/t24ConnectionFactory");
                resDescNode.appendChild(resRefJNDIName); 
                entBeanNode.appendChild(resDescNode);
            } else if (framework.equals("TAFJ")){
            	Element resDescNode = doc.createElement("wls:resource-description");
                Element resRefName = doc.createElement("wls:res-ref-name");
                resRefName.setTextContent("jdbc/t24DataSource");
                resDescNode.appendChild(resRefName);
                Element resRefJNDIName = doc.createElement("wls:jndi-name");
                resRefJNDIName.setTextContent("jdbc/t24DS");
                resDescNode.appendChild(resRefJNDIName); 
                entBeanNode.appendChild(resDescNode);
                
                Element resLockDescNode = doc.createElement("wls:resource-description");
                Element resLockRefName = doc.createElement("wls:res-ref-name");
                resLockRefName.setTextContent("jdbc/t24LockingDataSource");
                resLockDescNode.appendChild(resLockRefName);
                Element resRefLockJNDIName = doc.createElement("wls:jndi-name");
                resRefLockJNDIName.setTextContent("jdbc/t24LockingDS");
                resLockDescNode.appendChild(resRefLockJNDIName); 
                entBeanNode.appendChild(resLockDescNode);
            }
            
            // JNDI Maps
            Element jndiName = doc.createElement("wls:jndi-name");
            jndiName.setTextContent("ejb/" + service.getServiceEJBAPIName("Remote"));
            entBeanNode.appendChild(jndiName);
            Element localJNDIName = doc.createElement("wls:local-jndi-name");
            localJNDIName.setTextContent("ejb/" + service.getServiceEJBAPIName("Local"));
            entBeanNode.appendChild(localJNDIName);

            // Append entBeans node to root
            root.appendChild(entBeanNode);
            // Append the root to Document
            doc.appendChild(root);
            return doc;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
    }
	
	/*
	 * Generate ibm-ejb-jar-bnd content for TAFC and TAFJ  
	 */
	public Document getServiceIBMXML(ServiceDescriptor service, String framework) {
		// Initialise common variables for this service
		String serviceEJBName = service.getServiceEJBName();
		try {
            //We need a new Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            ////////////////////////
            //Creating the XML tree
            ////////////////////////
            
            //Create the root element with namespace
            Element root = doc.createElement("ejb-jar-bnd");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xmlns", "http://websphere.ibm.com/xml/ns/javaee");
            root.setAttribute("xsi:schemaLocation", "http://websphere.ibm.com/xml/ns/javaee http://websphere.ibm.com/xml/ns/javaee/ibm-ejb-jar-bnd_1_0.xsd");
            root.setAttribute("version", "1.0");
            
            // Session node
            Element sessNode = doc.createElement("session");
            sessNode.setAttribute("name", serviceEJBName + framework);
            
            if (framework.equals("TAFC")) {
        		// JCA Resource Ref Node for DS Lookup
        		Element resRefElem = doc.createElement("resource-ref");
        		resRefElem.setAttribute("name", "jca/t24ConnectionFactory");
        		resRefElem.setAttribute("binding-name", "jca/t24ConnectionFactory");
        		sessNode.appendChild(resRefElem);
            } else if(framework.equals("TAFJ")) {
        		// TAFJ DS Lookup Resource
            	Element resRefElem = doc.createElement("resource-ref");
            	resRefElem.setAttribute("name", "jdbc/t24DataSource");
        		resRefElem.setAttribute("binding-name", "jdbc/t24DataSource");
        		sessNode.appendChild(resRefElem);
            	// TAFJ Locking DS Lookup Resource
            	Element resRefLockElem = doc.createElement("resource-ref");
            	resRefLockElem.setAttribute("name", "jdbc/t24LockingDataSource");
            	resRefLockElem.setAttribute("binding-name", "jdbc/t24LockingDataSource");
            	sessNode.appendChild(resRefLockElem);
            } 
        	// Append session node to root
            root.appendChild(sessNode);
            // Append the root to Document
            doc.appendChild(root);
            return doc;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
    }
	
	/*
	 * Generate jboss content for TAFC and TAFJ  
	 */
	public Document getServiceJbossXML(ServiceDescriptor service, String framework) {
		// Initialise common variables for this service
		String serviceEJBName = service.getServiceEJBName();
		
		try {
            //We need a new Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            ////////////////////////
            //Creating the XML tree
            ////////////////////////
            
            //Create the root element
            Element root = doc.createElement("jboss");
            
            // Security domain
            Element secDomain = doc.createElement("security-domain");
            secDomain.setTextContent("java:/jaas/T24App");
            root.appendChild(secDomain);
            
            // Enterprise Beans Node
            Element entBeansNode = doc.createElement("enterprise-beans");
            
            // Session node
            Element sessNode = doc.createElement("session");
            
            // EJB Name node
            Element ejbNameElem = doc.createElement("ejb-name"); 
            ejbNameElem.setTextContent(serviceEJBName + framework);
            sessNode.appendChild(ejbNameElem);
            
            // Framework specific configurations
            if (framework.equals("TAFC")) {
            	// Simply the resource ref bindings
        		Element resRefElem = doc.createElement("resource-ref");
        		// Res Ref Name node
            	Element resNameElem = doc.createElement("res-ref-name");
            	resNameElem.setTextContent("jca/t24ConnectionFactory");
            	resRefElem.appendChild(resNameElem);
            	// Res Ref Type node
            	Element resTypeElem = doc.createElement("res-type");
            	resTypeElem.setTextContent("com.jbase.jremote.JConnectionFactory");
            	resRefElem.appendChild(resTypeElem);
            	// Res jndi-name node
            	Element resJNDIElem = doc.createElement("jndi-name");
            	resJNDIElem.setTextContent("java:jca/t24ConnectionFactory");
            	resRefElem.appendChild(resJNDIElem);
            	// Append the Resource Ref to session
            	sessNode.appendChild(resRefElem);
            } else if (framework.equals("TAFJ")) {
            	// Add the aop config
            	Element aopElem = doc.createElement("aop-domain-name");
            	aopElem.setTextContent("TAFJPooledEJB");
            	sessNode.appendChild(aopElem);
            	// Now add the DS bindings
            	Element resRefElem = doc.createElement("resource-ref");
        		// Res Ref Name node
            	Element resNameElem = doc.createElement("res-ref-name");
            	resNameElem.setTextContent("jdbc/t24DataSource");
            	resRefElem.appendChild(resNameElem);
            	// Res Ref Type node
            	Element resTypeElem = doc.createElement("res-type");
            	resTypeElem.setTextContent("javax.sql.DataSource");
            	resRefElem.appendChild(resTypeElem);
            	// Res jndi-name node
            	Element resJNDIElem = doc.createElement("jndi-name");
            	resJNDIElem.setTextContent("java:jdbc/t24DS");
            	resRefElem.appendChild(resJNDIElem);
            	// Append the DS resource to session
            	sessNode.appendChild(resRefElem);
            	// TAFJ Locking DS Lookup Resource
            	Element resRefLockElem = doc.createElement("resource-ref");
            	// Res Ref Name node
            	Element resNameLockElem = doc.createElement("res-ref-name");
            	resNameLockElem.setTextContent("jdbc/t24LockingDataSource");
            	resRefLockElem.appendChild(resNameLockElem);
            	// Res Ref Type node
            	Element resTypeLockElem = doc.createElement("res-type");
            	resTypeLockElem.setTextContent("javax.sql.DataSource");
            	resRefLockElem.appendChild(resTypeLockElem);
            	// Res jndi-name node
            	Element resJNDILockElem = doc.createElement("jndi-name");
            	resJNDILockElem.setTextContent("java:jdbc/t24LockingDS");
            	resRefLockElem.appendChild(resJNDILockElem);
            	// Append the DS resource to session
            	sessNode.appendChild(resRefLockElem);
            }
            // Append the session node
            entBeansNode.appendChild(sessNode);
            // Append enterprise-beans node to root
            root.appendChild(entBeansNode);
            // Append the root to Document
            doc.appendChild(root);
            return doc;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	/*
	 * Generate ejb-jar content for TAFC and TAFJ  
	 */
	public Document getServiceEJBJARXML(ServiceDescriptor service, String framework) {
		// Initialise common variables for this service
		String serviceName = service.getName();
		String serviceEJBName = service.getServiceEJBName();
		String serviceEJBPackageName = service.getEJBPackageName();
		String serviceLocalAPIFullyQualifiedName = serviceEJBPackageName + "." + service.getServiceEJBAPIName("Local"); 
		String serviceRemoteAPIFullyQualifiedName = serviceEJBPackageName + "." + service.getServiceEJBAPIName("Remote");
		
		try {
            //We need a new Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            ////////////////////////
            //Creating the XML tree
            ////////////////////////
            
            //Create the root element with namespace
            Element root = doc.createElement("ejb-jar");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xmlns", "http://java.sun.com/xml/ns/javaee");
            root.setAttribute("xmlns:ejb", "http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd");
            root.setAttribute("xsi:schemaLocation", "http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd");
            root.setAttribute("version", "3.0");
            
            // Enterprise Beans Node
            Element entBeansNode = doc.createElement("enterprise-beans");
            
            // Session node
            Element sessNode = doc.createElement("session");
            Comment beanComment = doc.createComment(serviceName + " Session bean");
            sessNode.appendChild(beanComment);
            // Description node
        	Element descElem = doc.createElement("description");
        	descElem.setTextContent(serviceEJBName + " " + framework);
        	sessNode.appendChild(descElem);
        	// Display Name
        	Element dispElem = doc.createElement("display-name");
        	dispElem.setTextContent(serviceEJBName + " " + framework);
        	sessNode.appendChild(dispElem);
        	// EJB Name
        	Element ejbNameElem = doc.createElement("ejb-name");
        	ejbNameElem.setTextContent(serviceEJBName + framework);
        	sessNode.appendChild(ejbNameElem);
        	// Business Local
        	Element busLocalElem = doc.createElement("business-local");
        	busLocalElem.setTextContent(serviceLocalAPIFullyQualifiedName);
        	sessNode.appendChild(busLocalElem);
        	// Business Remote
        	Element busRemoteElem = doc.createElement("business-remote");
        	busRemoteElem.setTextContent(serviceRemoteAPIFullyQualifiedName);
        	sessNode.appendChild(busRemoteElem);
        	// EJB Class Remote
        	Element ejbClassElem = doc.createElement("ejb-class");
        	ejbClassElem.setTextContent(serviceEJBPackageName + "." + service.getServiceEJBImplName(framework));
        	sessNode.appendChild(ejbClassElem);	
        	// Session Type
        	Element sessionTypeElem = doc.createElement("session-type");
        	sessionTypeElem.setTextContent("Stateless");
        	sessNode.appendChild(sessionTypeElem);
        	// Framework Specific configurations
        	if (framework.equals("TAFC")) {
        		// Transaction type
            	Element transTypeElem = doc.createElement("transaction-type");
            	transTypeElem.setTextContent("Bean");
            	sessNode.appendChild(transTypeElem);
        		// JCA Resource Ref Node for DS Lookup
        		Element resRefElem = doc.createElement("resource-ref");
        		resRefElem.setAttribute("id", "ResourceRef_" + serviceName + "t24ConnectionFactory");
            	// Res Desc node
            	Element resDescElem = doc.createElement("description");
            	resDescElem.setTextContent("Used to get connection to T24 JCA Resource Adaptor");
            	resRefElem.appendChild(resDescElem);
            	// Res Ref Name node
            	Element resNameElem = doc.createElement("res-ref-name");
            	resNameElem.setTextContent("jca/t24ConnectionFactory");
            	resRefElem.appendChild(resNameElem);
            	// Res Ref Type node
            	Element resTypeElem = doc.createElement("res-type");
            	resTypeElem.setTextContent("com.jbase.jremote.JConnectionFactory");
            	resRefElem.appendChild(resTypeElem);
            	// Res Auth node
            	Element resAuthElem = doc.createElement("res-auth");
            	resAuthElem.setTextContent("Container");
            	resRefElem.appendChild(resAuthElem);
            	// Append the Resource Ref to session
            	sessNode.appendChild(resRefElem);
            } else if(framework.equals("TAFJ")) {
            	// Transaction type
            	Element transTypeElem = doc.createElement("transaction-type");
            	transTypeElem.setTextContent("Container");
            	sessNode.appendChild(transTypeElem);
        		// Add the Env entries for Runtime properties and Resource Ref for DS Lookup
            	Comment envEntryComment = doc.createComment("For tafj session");
                sessNode.appendChild(envEntryComment);
                // Env Entry Node
                Element envEntryElem = doc.createElement("env-entry");
        		// Env Entry Desc node
            	Element envDescElem = doc.createElement("description");
            	envDescElem.setTextContent("Comma Separated Runtime Properties for TAFJ Session");
            	envEntryElem.appendChild(envDescElem);
            	// Env Entry Name node
            	Element envNameElem = doc.createElement("env-entry-name");
            	envNameElem.setTextContent("runtimeProperties");
            	envEntryElem.appendChild(envNameElem);
            	// Env Entry Type node
            	Element envTypeElem = doc.createElement("env-entry-type");
            	envTypeElem.setTextContent("java.lang.String");
            	envEntryElem.appendChild(envTypeElem);
            	// Env Entry Value node
            	Element envValueElem = doc.createElement("env-entry-value");
            	envValueElem.setTextContent("OFS_SOURCE=GCS");
            	envEntryElem.appendChild(envValueElem);
            	sessNode.appendChild(envEntryElem);
            	
            	// Env Entry superTransaction Node
                Element envSTEntryElem = doc.createElement("env-entry");
        		Element envSTDescElem = doc.createElement("description");
        		envSTDescElem.setTextContent("Enable/Disable TAFJ Super Transaction, Diable [false : For Container managed] / Enable [true : For Bean managed] transactions");
        		envSTEntryElem.appendChild(envSTDescElem);
            	
        		Element envSTNameElem = doc.createElement("env-entry-name");
        		envSTNameElem.setTextContent("superTransaction");
        		envSTEntryElem.appendChild(envSTNameElem);
        		
            	Element envSTTypeElem = doc.createElement("env-entry-type");
            	envSTTypeElem.setTextContent("java.lang.Boolean");
            	envSTEntryElem.appendChild(envSTTypeElem);
            	
            	Element envSTValueElem = doc.createElement("env-entry-value");
            	envSTValueElem.setTextContent("false");
            	envSTEntryElem.appendChild(envSTValueElem);
            	sessNode.appendChild(envSTEntryElem);
            	
            	// DS Lookup comment
            	Comment resRefComment = doc.createComment("For T24 purpose");
                sessNode.appendChild(resRefComment);
            	// TAFJ DS Lookup Resource
            	Element resRefElem = doc.createElement("resource-ref");
        		resRefElem.setAttribute("id", "ResourceRef_" + serviceName + "t24DataSource");
            	// Res Desc node
            	Element resDescElem = doc.createElement("description");
            	resDescElem.setTextContent("Used to get connections from T24 jdbc pool");
            	resRefElem.appendChild(resDescElem);
            	// Res Ref Name node
            	Element resNameElem = doc.createElement("res-ref-name");
            	resNameElem.setTextContent("jdbc/t24DataSource");
            	resRefElem.appendChild(resNameElem);
            	// Res Ref Type node
            	Element resTypeElem = doc.createElement("res-type");
            	resTypeElem.setTextContent("javax.sql.DataSource");
            	resRefElem.appendChild(resTypeElem);
            	// Res Auth node
            	Element resAuthElem = doc.createElement("res-auth");
            	resAuthElem.setTextContent("Container");
            	resRefElem.appendChild(resAuthElem);
            	// Append the DS resource to session
            	sessNode.appendChild(resRefElem);
            	
            	// TAFJ Locking DS Lookup Resource
            	Element resRefLockElem = doc.createElement("resource-ref");
            	resRefLockElem.setAttribute("id", "ResourceRef_" + serviceName + "t24LockingDataSource");
            	// Res Desc node
            	Element resDescLockElem = doc.createElement("description");
            	resDescLockElem.setTextContent("Used to get connections from T24 jdbc pool");
            	resRefLockElem.appendChild(resDescLockElem);
            	// Res Ref Name node
            	Element resNameLockElem = doc.createElement("res-ref-name");
            	resNameLockElem.setTextContent("jdbc/t24LockingDataSource");
            	resRefLockElem.appendChild(resNameLockElem);
            	// Res Ref Type node
            	Element resTypeLockElem = doc.createElement("res-type");
            	resTypeLockElem.setTextContent("javax.sql.DataSource");
            	resRefLockElem.appendChild(resTypeLockElem);
            	// Res Auth node
            	Element resAuthLockElem = doc.createElement("res-auth");
            	resAuthLockElem.setTextContent("Container");
            	resRefLockElem.appendChild(resAuthLockElem);
            	// Append the DS resource to session
            	sessNode.appendChild(resRefLockElem);
            } 
        	// Append the session node
            entBeansNode.appendChild(sessNode);
            // Append enterprise-beans node to root
            root.appendChild(entBeansNode);
            
        	// Lets add security configurations - Enabled by default
        	Element assDesc = doc.createElement("assembly-descriptor");
        		Element secRole = doc.createElement("security-role");
        			Element secRoleName = doc.createElement("role-name");
        			secRoleName.setTextContent("t24user");
        		secRole.appendChild(secRoleName);
        	assDesc.appendChild(secRole);
        		Element methodPer = doc.createElement("method-permission");
            		Element roleName = doc.createElement("role-name");
            		roleName.setTextContent("t24user");
            	methodPer.appendChild(roleName);
            		Element method = doc.createElement("method");
            			Element secEjbName = doc.createElement("ejb-name");
            			secEjbName.setTextContent(serviceEJBName + framework);
            		method.appendChild(secEjbName);
            			Element methodName = doc.createElement("method-name");
            			methodName.setTextContent("*");
            		method.appendChild(methodName);
            	methodPer.appendChild(method);
            assDesc.appendChild(methodPer);
            root.appendChild(assDesc);
            
            // Append the root to Document
            doc.appendChild(root);
            return doc;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	/* Check if file Exists */
	private boolean checkIfFileAlreadyExists(String dirName, String fileName) {
		File file = new File(dirName, fileName);
		return file.exists();
	}
	
	/*
	 * Generate jboss 7 content for TAFC and TAFJ  
	 */
	public Document getServiceJbossEJB3XML(ServiceDescriptor service, String framework) {
		// Initialise common variables for this service
		String serviceEJBName = service.getServiceEJBName();
		try {
            //We need a new Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            ////////////////////////
            //Creating the XML tree
            ////////////////////////
            
            //Create the root element
            Element root = doc.createElement("jboss:ejb-jar");
            root.setAttribute("xmlns:jboss", "http://www.jboss.com/xml/ns/javaee");
            root.setAttribute("xmlns", "http://java.sun.com/xml/ns/javaee");
            root.setAttribute("xmlns:s", "urn:security");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://www.jboss.com/xml/ns/javaee http://www.jboss.org/j2ee/schema/jboss-ejb3-2_0.xsd http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_1.xsd");
            root.setAttribute("version", "3.1");
            root.setAttribute("impl-version", "2.0");
            
            // Enterprise Beans Node
            Element entBeansNode = doc.createElement("enterprise-beans");
            
            // Session node
            Element sessNode = doc.createElement("session");
            
            // EJB Name node
            Element ejbNameElem = doc.createElement("ejb-name"); 
            ejbNameElem.setTextContent(serviceEJBName + framework);
            sessNode.appendChild(ejbNameElem);
            
            // Framework specific configurations
            if (framework.equals("TAFC")) {
            	// Simply the resource ref bindings
        		Element resRefElem = doc.createElement("resource-ref");
        		// Res Ref Name node
            	Element resNameElem = doc.createElement("res-ref-name");
            	resNameElem.setTextContent("jca/t24ConnectionFactory");
            	resRefElem.appendChild(resNameElem);
            	// Res Ref Type node
            	Element resTypeElem = doc.createElement("res-type");
            	resTypeElem.setTextContent("com.jbase.jremote.JConnectionFactory");
            	resRefElem.appendChild(resTypeElem);
            	// Res jndi-name node
            	Element resJNDIElem = doc.createElement("jndi-name");
            	resJNDIElem.setTextContent("java:/jca/t24ConnectionFactory");
            	resRefElem.appendChild(resJNDIElem);
            	// Append the Resource Ref to session
            	sessNode.appendChild(resRefElem);
            } else if (framework.equals("TAFJ")) {
            	// Now add the DS bindings
            	Element resRefElem = doc.createElement("resource-ref");
        		// Res Ref Name node
            	Element resNameElem = doc.createElement("res-ref-name");
            	resNameElem.setTextContent("jdbc/t24DataSource");
            	resRefElem.appendChild(resNameElem);
            	// Res Ref Type node
            	Element resTypeElem = doc.createElement("res-type");
            	resTypeElem.setTextContent("javax.sql.DataSource");
            	resRefElem.appendChild(resTypeElem);
            	// Res jndi-name node
            	Element resJNDIElem = doc.createElement("jndi-name");
            	resJNDIElem.setTextContent("java:/jdbc/t24DS");
            	resRefElem.appendChild(resJNDIElem);
            	// Append the DS resource to session
            	sessNode.appendChild(resRefElem);
            	// TAFJ Locking DS Lookup Resource
            	Element resRefLockElem = doc.createElement("resource-ref");
            	// Res Ref Name node
            	Element resNameLockElem = doc.createElement("res-ref-name");
            	resNameLockElem.setTextContent("jdbc/t24LockingDataSource");
            	resRefLockElem.appendChild(resNameLockElem);
            	// Res Ref Type node
            	Element resTypeLockElem = doc.createElement("res-type");
            	resTypeLockElem.setTextContent("javax.sql.DataSource");
            	resRefLockElem.appendChild(resTypeLockElem);
            	// Res jndi-name node
            	Element resJNDILockElem = doc.createElement("jndi-name");
            	resJNDILockElem.setTextContent("java:/jdbc/t24LockingDS");
            	resRefLockElem.appendChild(resJNDILockElem);
            	// Append the DS resource to session
            	sessNode.appendChild(resRefLockElem);
            }
            // Append the session node
            entBeansNode.appendChild(sessNode);
            // Append enterprise-beans node to root
            root.appendChild(entBeansNode);
            
            // Assembly Descriptor
            Element assmDesc = doc.createElement("assembly-descriptor");
            	// Security domain
            	Element secDomain = doc.createElement("s:security");
            	
            	// EJB Name
            	Element secEjbName = doc.createElement("ejb-name");
            	secEjbName.setTextContent(serviceEJBName + framework);
            	secDomain.appendChild(secEjbName);
            	
            	// Secutiry Domain Name
            	Element secDomainName = doc.createElement("s:security-domain");
            	secDomainName.setTextContent("T24");
            	secDomain.appendChild(secDomainName);
            	
            assmDesc.appendChild(secDomain);
            root.appendChild(assmDesc);
            
            // Append the root to Document
            doc.appendChild(root);
            return doc;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	/** 
	 * This method will read and load the existing services.xml file and append the provided document
	 * in the existing XML
	 * 
	 * @param serviceDoc Document XML generated earlier
	 * @param dirName Directory where file exists			
	 * @param fileName Name of the file to load the previous xml from
	 * @return Modified XML document to be written on file
	 */
	public Document appendXMLDoc(Document xmlDoc, String dirName, String fileName) {
		Document doc = null;
		try {
			File file = new File(dirName, fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			Element existingRoot = doc.getDocumentElement();
			
			List<Node> importTo = new ArrayList<Node>();
			List<Node> nodeToImport = new ArrayList<Node>();
			if (fileName.equals ("ejb-jar.xml") || fileName.equals("jboss.xml") || fileName.equals("jboss-ejb3.xml")) {
				importTo.add(existingRoot.getElementsByTagName("enterprise-beans").item(0));
				nodeToImport.add(xmlDoc.getDocumentElement().getElementsByTagName("session").item(0));
				if (fileName.equals ("ejb-jar.xml")) {
					// Additional security config to append
					importTo.add(existingRoot.getElementsByTagName("assembly-descriptor").item(0));
					nodeToImport.add(xmlDoc.getDocumentElement().getElementsByTagName("method-permission").item(0));
				} else if (fileName.equals ("jboss-ejb3.xml")) {
					// Additional security config to append
					importTo.add(existingRoot.getElementsByTagName("assembly-descriptor").item(0));
					nodeToImport.add(xmlDoc.getDocumentElement().getElementsByTagName("s:security").item(0));
				}
			} else if (fileName.equals ("ibm-ejb-jar-bnd.xml")) {
				importTo.add(existingRoot);
				nodeToImport.add(xmlDoc.getDocumentElement().getElementsByTagName("session").item(0));
			} else if (fileName.equals ("weblogic-ejb-jar.xml")){
				importTo.add(existingRoot);
				nodeToImport.add(xmlDoc.getDocumentElement().getElementsByTagName("wls:weblogic-enterprise-bean").item(0));
			}
			if (importTo.size() > 0 && nodeToImport.size() > 0) {
				for (int i = 0; i < importTo.size(); i++) {
					importTo.get(i).appendChild(doc.importNode(nodeToImport.get(i), true));
				}
			}
			return doc;
		} catch (ParserConfigurationException pe) {
			System.out.println("Failed to parse existing services XML. ParserConfigException : " + pe.getMessage());
		} catch (SAXException sae) {
			System.out.println("Failed to parse existing services XML. SAXException : " + sae.getMessage());
		} catch (DOMException dome) {
			System.out.println("Failed to parse existing services XML. DOMException : " + dome.getMessage());
		} catch (IOException ioe){
			System.out.println("Failed to locate services.xml . IOException : " + ioe.getMessage());
		} catch (Exception e) {
			System.out.println("Failed to parse services.xml . Exception : " + e.getMessage());
		}
		// Return the original one back
		return xmlDoc;
	}
}