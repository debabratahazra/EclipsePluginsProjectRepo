package com.odcgroup.service.gen.t24.internal.categories.java.webservice;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;
import com.odcgroup.service.gen.t24.internal.generator.ServiceGenerator;
import com.odcgroup.service.gen.t24.internal.generator.VelocityTemplateLoader;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

/**
 * Generate a services.xml for axis2 web sevrice. This class will directly generate an XML file
 * instead of using velocity template because we can have multiple services within a single model
 * and each needs to be represented/loaded within this file to work.  
 * @author srushworth
 *
 */
public class JavaWSServiceXMLGenerator extends ServiceGenerator {

	public JavaWSServiceXMLGenerator(VelocityTemplateLoader loader) throws LoadTemplateException {
		// Do nothing here as we will write file using this Java class
	}
	
	public void generate(ServiceDescriptor service, Writer writer, String path) {
		try {
			String dirName = path + "/META-INF";
			String apiFileName = "services.xml";
			
			Document serviceDoc = generateServicesXML(service);
			Document updatedDoc = null;
			boolean fileExists = checkIfFileAlreadyExists(dirName, apiFileName);
			if (fileExists) {
				// This means we have to append above document to the existing file contents
				updatedDoc = appendServiceDoc(serviceDoc, dirName, apiFileName);
			} else {
				updatedDoc = serviceDoc;
			}
			
			if (writer == null) {
				writer = createWriter(dirName, apiFileName);
			}
			if (updatedDoc != null) {
				String indentXML = StringUtils.transformXMLToString(updatedDoc);
				if (! indentXML.equals("")) {
					writer.write(indentXML);
				}
			} else {
				writer.write("Failed to generated XML Configurations. Please rectify before proceeding.");
			}
			System.out.println("Writing Template From - JavaWSServiceXMLGenerator");
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/* Check if file Exists */
	private boolean checkIfFileAlreadyExists(String dirName, String fileName) {
		File file = new File(dirName, fileName);
		return file.exists();
	}
	
	/** This method will generate the configurations contents for services.xml file
	 * 
	 * @param	service, ServiceDescriptor object contains component service information
	 * @return	Document, prepared XML document will be returned
	 * 
	 */
	private Document generateServicesXML(ServiceDescriptor service) {
		// Initialise common variables for this service
		String serviceName = service.getName();
		String webServiceName = serviceName + "WS";
		String secWebServiceName = serviceName + "SecWS";
		
		try {
            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            ////////////////////////
            //Creating the XML tree

            //create the root element and add it to the document
            Element root = doc.createElement("serviceGroup");
            
            /*********************** Service Spring Init configurations ***************/
            // SpringInit service node
            Element spServiceElem = doc.createElement("service");
            spServiceElem.setAttribute("name"		, serviceName + "SpringInit");
            spServiceElem.setAttribute("class"	, service.getPackageName() + "." + serviceName + "SpringInit");
            
            // SpringInit description node
            Element spDescElem = doc.createElement("description");
            spDescElem.setTextContent("This service will initializes Spring for " + serviceName);
            spServiceElem.appendChild(spDescElem);
            
            // SpringInit parameter 1 node
            Element spParameterElem1 = doc.createElement("parameter");
            spParameterElem1.setAttribute("name", "ServiceClass");
            spParameterElem1.setTextContent(service.getPackageName() + "." + serviceName + "SpringInit");
            spServiceElem.appendChild(spParameterElem1);
            
            // SpringInit parameter 2 node
            Element spParameterElem2 = doc.createElement("parameter");
            spParameterElem2.setAttribute("name", "ServiceTCCL");
            spParameterElem2.setTextContent("composite");
            spServiceElem.appendChild(spParameterElem2);
            
            // SpringInit parameter 2 node
            Element spParameterElem3 = doc.createElement("parameter");
            spParameterElem3.setAttribute("name", "load-on-startup");
            spParameterElem3.setTextContent("true");
            spServiceElem.appendChild(spParameterElem3);
            
            // Append the SpringInit service node
            root.appendChild(spServiceElem);
            
            /********************************** Service Node *************************/
            // service node
            Element serviceElem = doc.createElement("service");
            serviceElem.setAttribute("name"				, service.getNameOnlyInUpperCamelCase() + "WebService");
            serviceElem.setAttribute("scope"			, "application");
            serviceElem.setAttribute("targetNamespace"	, "http://" + webServiceName );
            
            // schema Node
            Element schemaElem = doc.createElement("schema");
            schemaElem.setAttribute("schemaNamespace"	, "http://" + webServiceName);
            serviceElem.appendChild(schemaElem);
            
            // description node
            Element descNode = doc.createElement("description");
            descNode.setTextContent(service.getNameOnlyInUpperCamelCase() + " Web Service");
            serviceElem.appendChild(descNode);
            
            // messageReceivers node
            Element messRecsElem = doc.createElement("messageReceivers");
            
            Element messRecElem1 = doc.createElement("messageReceiver");
            messRecElem1.setAttribute("mep"		, "http://www.w3.org/2004/08/wsdl/in-only");
            messRecElem1.setAttribute("class"	, "org.apache.axis2.rpc.receivers.RPCInOnlyMessageReceiver");
            messRecsElem.appendChild(messRecElem1);
            
            Element messRecElem2 = doc.createElement("messageReceiver");
            messRecElem2.setAttribute("mep"		, "http://www.w3.org/2004/08/wsdl/in-out");
            messRecElem2.setAttribute("class"	, "org.apache.axis2.rpc.receivers.RPCMessageReceiver");
            messRecsElem.appendChild(messRecElem2);
            
            serviceElem.appendChild(messRecsElem);
            
            // parameter node
            Element parameterElem = doc.createElement("parameter");
            parameterElem.setAttribute("name", "ServiceClass");
            parameterElem.setTextContent(service.getWsPackageName() + "." + webServiceName);
            serviceElem.appendChild(parameterElem);
            
            // Append the service node
            root.appendChild(serviceElem);
            
            /********************************** Secure Service Node *************************/
            
            // By default we need this configurations to be disabled so creating a comment node
            StringBuilder secServiceNodeSB = new StringBuilder();
            
            secServiceNodeSB.append("Uncomment following configuration to enable secure web service. NOTE: Please refer to the user guide for additional packages required\n");
            secServiceNodeSB.append("\t<service name=\"").append(service.getNameOnlyInUpperCamelCase()).
            					append("SecWebService\" scope=\"soapsession\" targetNamespace=\"http://").
            					append(secWebServiceName).append("\">\n");
            
            secServiceNodeSB.append("\t\t<schema schemaNamespace=\"http://").append(secWebServiceName).append("\"/>\n");
            
            secServiceNodeSB.append("\t\t<description>").append(service.getNameOnlyInUpperCamelCase()).
            					append(" WS-Security Enabled Web Service").append("</description>\n");
            
            secServiceNodeSB.append("\t\t<messageReceivers>\n").
            					append("\t\t\t<messageReceiver class=\"org.apache.axis2.rpc.receivers.RPCInOnlyMessageReceiver\" mep=\"http://www.w3.org/2004/08/wsdl/in-only\"/>\n").
            					append("\t\t\t<messageReceiver class=\"org.apache.axis2.rpc.receivers.RPCMessageReceiver\" mep=\"http://www.w3.org/2004/08/wsdl/in-out\"/>\n").
            				append("\t\t</messageReceivers>\n");
            
            secServiceNodeSB.append("\t\t<parameter name=\"ServiceClass\">").append(service.getWsPackageName()).
            					append(".").append(secWebServiceName).
            			append("</parameter>\n");
            
            secServiceNodeSB.append("\t\t<module ref=\"rampart\"/>\n");
            
            secServiceNodeSB.append("\t\t<parameter name=\"InflowSecurity\">\n").
            					append("\t\t\t<action>\n").
            						append("\t\t\t\t<items>UsernameToken</items>\n").
            						append("\t\t\t\t<passwordCallbackClass>").append(service.getWsPackageName()).append(".").append(secWebServiceName).append("SamplePWHandler").append("</passwordCallbackClass>\n").
            					append("\t\t\t</action>\n").
            				append("\t\t</parameter>\n");
            
            secServiceNodeSB.append("\t</service>");
            
            Comment secServiceElemAsComment = doc.createComment(secServiceNodeSB.toString());
            root.appendChild(secServiceElemAsComment);
                      
            // Append the root to the document and return
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
	private Document appendServiceDoc(Document serviceDoc, String dirName, String fileName) {
		Document doc = null;
		try {
			File file = new File(dirName, fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			Element existingRoot = doc.getDocumentElement();
			
			// Get serviceGroup Node
			Node newServiceGroupNode = serviceDoc.getFirstChild();
			NodeList newServiceNodeList = newServiceGroupNode.getChildNodes();
			for (int i = 0; i < newServiceNodeList.getLength(); i++) {
			    Node newNode = newServiceNodeList.item(i);
			    Node importedNewNode = doc.importNode(newNode, true); 
			    existingRoot.appendChild(importedNewNode);
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
		return serviceDoc;
	}
}
