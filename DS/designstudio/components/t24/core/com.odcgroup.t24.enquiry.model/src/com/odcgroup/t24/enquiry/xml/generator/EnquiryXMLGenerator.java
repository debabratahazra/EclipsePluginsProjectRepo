package com.odcgroup.t24.enquiry.xml.generator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryPackageImpl;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.xml.AbstractXmlCodeGenerator;

public class EnquiryXMLGenerator extends AbstractXmlCodeGenerator {

	private Resource xmlResource;

	@Override
	protected void transform(EObject root, Resource resource) throws Exception {
		// TODO Auto-generated method stub
		((Enquiry)root).getFields();
		removeFeatureGenerateIFP(root, resource);
		removedFieldsInDrilldown(root, resource);
		removeIntroSpectionMessages(root,resource);
		removedEnquiryMode(root,resource);
		removeServerMode(root, resource);
		super.transform(root, resource);
	}


	private void removeServerMode(EObject enquiryObject, Resource resource) {
		if(enquiryObject instanceof Enquiry){
		    Enquiry enquiry = (Enquiry)enquiryObject;
		    EStructuralFeature eFeature = enquiryObject.eClass().getEStructuralFeature(EnquiryPackageImpl.ENQUIRY__SERVER_MODE);
		    enquiry.eUnset(eFeature);
		}
	}

	/**
	 * @param root
	 * @param resource
	 */
	private void removedEnquiryMode(EObject enquiryObject, Resource resource) {
		if(enquiryObject instanceof Enquiry){
		    Enquiry enquiry = (Enquiry)enquiryObject;
		    EStructuralFeature eFeature = enquiryObject.eClass().getEStructuralFeature(EnquiryPackageImpl.ENQUIRY__ENQUIRY_MODE);
		    enquiry.eUnset(eFeature);
		}
	}

	/**
	 * @param root
	 * @param resource
	 */
	private void removeIntroSpectionMessages(EObject enquiryObject, Resource resource) {
		if(enquiryObject instanceof Enquiry){
		    Enquiry enquiry = (Enquiry)enquiryObject;
		    if(!enquiry.getIntrospectionMessages().isEmpty()){
		    	enquiry.getIntrospectionMessages().clear();
		    }
		}
	}

	/**
	 * @param root
	 * @param resource
	 */
	private void removedFieldsInDrilldown(EObject enquiryObject, Resource resource) {
		if(enquiryObject instanceof Enquiry){
		    Enquiry enquiry = (Enquiry)enquiryObject;
		    for (DrillDown drillDown : enquiry.getDrillDowns()) {
		    	//DS-7267
		    	 drillDown.setDrill_name(null);
			}
		}
	}

	private void removeFeatureGenerateIFP(EObject enquiryObject, Resource resource) {
		if(enquiryObject instanceof Enquiry){
		    Enquiry enquiry = (Enquiry)enquiryObject;
		    EStructuralFeature eFeature = enquiryObject.eClass().getEStructuralFeature(EnquiryPackageImpl.ENQUIRY__GENERATE_IFP);
		    enquiry.eUnset(eFeature);
		}
	}

	@Override
	protected String getModelsExtension() {
		return "enquiry";
	}

	@Override
	public Resource generateXMLResourceWithoutYetSavingIt(Resource resource, URI xmlURI, ModelLoader loader) throws Exception {
		setPostGenTransformRequired(true);
		xmlResource = super.generateXMLResourceWithoutYetSavingIt(resource, xmlURI, loader);
		return xmlResource;
	}

	private void processXml(Document doc, Map<String, Object> selectionNodes) {
		for (String selectedNode : selectionNodes.keySet()) {
			String selectionNodeName = selectedNode;
			Object operatorNodeName = selectionNodes.get(selectedNode);

			// modify the selection nodes
			NodeList nodesList = doc.getElementsByTagName(selectionNodeName);
			for (int j = 0; j < nodesList.getLength(); j++) {
				Node node = nodesList.item(j);
				NamedNodeMap nodeMap = node.getAttributes();
				if (operatorNodeName instanceof String) {
					setValueNodeItem(doc, selectionNodeName, node, nodeMap, (String) operatorNodeName);
				} else if (operatorNodeName instanceof String[]) {
					for (String operatorNodeItm : (String[]) operatorNodeName) {
						setValueNodeItem(doc, selectionNodeName, node, nodeMap, operatorNodeItm);
					}
				}
			}
		}
	}


	/**
	 * This method modifies the drilldown nodes to suit them for deployment.
	 *
	 * @param doc
	 */
	private void modifyDrillDown(Document doc) {
		// modify the drilldown nodes
		Map<String, String> propertyMap = new HashMap<String, String>();
		propertyMap.put("from-field", "fromField");
		propertyMap.put("composite-screen", "compositeScreen");
		propertyMap.put("tabbed-screen", "tabbedScreen");
		propertyMap.put("quit-SEE", "quitSee");
		propertyMap.put("pw-process", "pwProcess");
		propertyMap.put("pw-activity", "pwActivity");

		NodeList nodesList = doc.getElementsByTagName("drillDowns");
		for (int j = 0; j < nodesList.getLength(); j++) {
			Node drillDown = nodesList.item(j);

			NodeList childNodes = drillDown.getChildNodes();
			Node typeNode = null;
			for (int count = 0; count < childNodes.getLength(); count++) {
				Node child = childNodes.item(count);
				if (child.getNodeName().equals("type")) {
					typeNode = child;
					break;
				}

			}
			if (typeNode != null) {
				Attr nodeTypeAttr;
				String property = typeNode.getAttributes().getNamedItem("property").getNodeValue();
				if (property.contains(":")) {
					property = property.replace(":", "");
				}
				if (propertyMap.keySet().contains(property)) {
					nodeTypeAttr = doc.createAttribute(propertyMap.get(property));
				} else {
					nodeTypeAttr = doc.createAttribute(property);
				}
				Node valueAttr = typeNode.getAttributes().getNamedItem("value");
				if (valueAttr != null) {
					nodeTypeAttr.setValue(valueAttr.getNodeValue());
				}
				drillDown.getAttributes().setNamedItem(nodeTypeAttr);
				drillDown.removeChild(typeNode);
			}
		}
	}

	@Override
	protected void transform(Document document) {
		Map<String, Object> selectionNodes = new HashMap<String, Object>();
		selectionNodes.put("fixedSelections", "operand");
		selectionNodes.put("operation", "number");
		processXml(document, selectionNodes);
		modifyDrillDown(document);
	}

	/**
	 * @param doc
	 * @param selectionNodeName
	 * @param node
	 * @param nodeMap
	 * @param operatorNodeItm
	 */
	private void setValueNodeItem(Document doc, String selectionNodeName, Node node, NamedNodeMap nodeMap,
			String operatorNodeItm) {
		Node operatorNode = nodeMap.getNamedItem(operatorNodeItm);
		if (operatorNode == null) {
			String operatorText;
			if (selectionNodeName.equals("operation")) {
				String nodeValue = nodeMap.getNamedItem("xsi:type").getNodeValue();
				if (nodeValue.contains("FieldNumberOperation")) {
					operatorText = "0";
				} else {
					return;
				}
			} else {
				operatorText = "None";
			}
			Node operatorElment = doc.createAttribute(operatorNodeItm);
			operatorElment.setTextContent(operatorText);
			node.getAttributes().setNamedItem(operatorElment);
		}
	}
	
}