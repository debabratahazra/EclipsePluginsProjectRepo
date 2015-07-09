package com.odcgroup.t24.enquiry.importer.internal;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class ModifyXml<T extends IModelDetail> implements IImportingStep<T> {

	private IImportModelReport report;
	private static List<String> drillDownTypes = Arrays.asList(new String[]{"screen","application","enquiry","fromField","cos","tab","quitSee","view"
			,"blank","pwProcess","download","run","util","javascript","shouldBeChanged"});

	
	/**
	 * @param report
	 * @param resourceSet
	 */
	public ModifyXml(IImportModelReport report) {
		this.report = report;
	}

	@Override
	public boolean execute(T model, IProgressMonitor monitor) {
		try {
			String xml = modifyXMLAttribute(model.getXmlString());
			model.setXmlString(xml);
			return true;
		} catch (Exception e) {
			report.error(model, e);
			return false;
		}
	}

	/**
	 * Modify the xml content if any error in
	 * INTEGER_OBJECT field.
	 * @param xml
	 * @param tagName
	 * @param itemName
	 * @return
	 * @throws Exception
	 */
	private String modifyXMLAttribute(String xml) throws Exception {
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
			
			// modify fields element's length attribute
			String tagName = "fields";
			String itemName = "length";
			NodeList nodesList = doc.getElementsByTagName(tagName);
			for (int j = 0; j < nodesList.getLength(); j++) {
				Node nodes = nodesList.item(j);
				NamedNodeMap nodeAttribute = nodes.getAttributes();
				Node nodeItem = nodeAttribute.getNamedItem(itemName);
				if (nodeItem == null) {
					continue;
				}
				try {
					Integer.parseInt(nodeItem.getNodeValue());
				} catch (NumberFormatException e) {
					nodeAttribute.removeNamedItem(nodeItem.getNodeName());
				}
			}
			
			// update Drill Down attribute with name (DS-7557)
			EnquiryUtil.fixUpDrillDownNamesInXML(doc);
			
			// update the customSelection element
			String element = "customSelection";
			String operatorChild = "operator";
			String selectionChild = "selection";
			nodesList = doc.getElementsByTagName(element);
			for (int j = 0; j < nodesList.getLength(); j++) {
				Node node = nodesList.item(j);				
				List<String> operators = new ArrayList<String>();
				List<Node> selections = new ArrayList<Node>();
				List<Node> operatorNodes = new ArrayList<Node>();
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node child = children.item(i);
					if (operatorChild.equals(child.getNodeName())) {
						operators.add(child.getTextContent());
						operatorNodes.add(child);
					} 
					else if (selectionChild.equals(child.getNodeName())) {
						selections.add(child);
					}
				}
				for(Node operatorNode: operatorNodes){
					node.removeChild(operatorNode);
				}
				for (int i = 0; i < selections.size(); i++) {
					Node child =selections.get(i);
					Attr attr = doc.createAttribute(operatorChild);
					if(i<operators.size()){
					   String op = operators.get(i);
					   attr.setValue(op);
					}else {
						attr.setValue("None");
					}
					child.getAttributes().setNamedItem(attr);				
				}
			}
			
			//update drilldown element
			element = "drillDowns";

			nodesList = doc.getElementsByTagName(element);
			for(int ddCount = 0;ddCount < nodesList.getLength();ddCount++){
				Node drillDownNode = nodesList.item(ddCount);
				NamedNodeMap nodeMap = drillDownNode.getAttributes();
				Node ddType = null;
				for(int i=0;i<nodeMap.getLength();i++){
					Node node = nodeMap.item(i);
					node.getNodeName();
					if(drillDownTypes.contains(node.getNodeName())){
						ddType =node;
						break;
					}
				}
				if(ddType !=null){
					Node typeNode =doc.createElement("type");
					Attr propertyAttr = doc.createAttribute("property");
					Attr valueAttr = doc.createAttribute("value");
					Attr xsiTypeAttr = doc.createAttribute("xsi:type");
					String suffix = (!ddType.getNodeName().equals("blank") && !ddType.getNodeName().equals("util"))?":" : "";
					propertyAttr.setValue(ddType.getNodeName()+suffix);
					valueAttr.setNodeValue(ddType.getNodeValue());
					String type;
					if(ddType.getNodeName().equals("cos")){
						type = "CompositeScreenType";
					}
					else if(ddType.getNodeName().equals("quitSee")){
						type = "QuitSEEType";
					}
					else if(ddType.getNodeName().equals("pwProcess")){
						type = "PWProcessType";
					}
					else if(ddType.getNodeName().equals("javascript")){
						type = "JavaScriptType";
					}
					else if(ddType.getNodeName().equals("tab")){
						type = "TabbedScreenType";
					}
					else{
						type = ddType.getNodeName().substring(0, 1).toUpperCase() + ddType.getNodeName().substring(1)+"Type";
					}
					xsiTypeAttr.setValue("enquiry:"+type);
					typeNode.getAttributes().setNamedItem(propertyAttr);
					typeNode.getAttributes().setNamedItem(valueAttr);
					typeNode.getAttributes().setNamedItem(xsiTypeAttr);
					drillDownNode.appendChild(typeNode);
					drillDownNode.getAttributes().removeNamedItem(ddType.getNodeName());
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new StringWriter());
			transformer.transform(source, result);

			return ((StringWriter) result.getWriter()).toString();
		} catch (Exception e) {
			throw e;
		}
	}
}
