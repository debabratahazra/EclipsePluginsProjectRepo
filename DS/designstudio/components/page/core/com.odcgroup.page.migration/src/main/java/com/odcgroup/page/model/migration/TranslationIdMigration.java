package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Adds the new "tid" property and set its default value to "0".
 * @author atr
 */
public class TranslationIdMigration extends AbstractDOMModelMigration {

	private final String ATTRIBUTE_LIBNAME = "libraryName";	
	private final String ATTRIBUTE_EVENTNAME = "eventName";	
	private final String ATTRIBUTE_TYPENAME = "typeName";	
	private final String ATTRIBUTE_VALUE = "value";
	private final String ATTRIBUTE_XMIID = "xmi:id";

	private final String WIDGET_ELEMENT = "widget";
	private final String CONTENTS_ELEMENT = "contents";
	private final String EVENTS_ELEMENT = "events";
	private final String PROPERTIES_ELEMENT = "properties";
	
	private Set<String> i18nProperties = new HashSet<String>();
	
	/**
	 * Checks the properties and remove all that are not defined in the widget type
	 * @param wType the widget type
	 * @param widgetNode the widget node
	 */
	private void checkProperties(WidgetType wType, String widgetName, Node widgetNode) {
		
		List<Node> nodeToRemove = new ArrayList<Node>();
		NodeList childNodes = widgetNode.getChildNodes();
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			Node node = childNodes.item(ii);
			if (PROPERTIES_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String typeName = typeAttr.getNodeValue();
					if (typeName == null) {
						typeName = "";
					}
					// ignore properties that could contains message key
					if ( ! i18nProperties.contains(typeName.toLowerCase())) {
						if (null == wType.findPropertyType(typeName, true)) {
							nodeToRemove.add(node);
							//System.out.println("Widget ["+widgetName + "] remove attribute [" + typeName+"]");
						}
					}
				}
			}
		}
		
		for (Node node : nodeToRemove) {
			widgetNode.removeChild(node);
		}
		
	}
	
	private boolean nodeHasTID(Node node) {
		NodeList childNodes = node.getChildNodes();
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			Node child = childNodes.item(ii);
			if (PROPERTIES_ELEMENT.equals(child.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String typeName = typeAttr.getNodeValue();
					if ("tid".equalsIgnoreCase(typeName)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param nodes
	 */
	private void migrateWidgetsAndEvents(Document document, List<Node> nodes) {
	
		for (Node node : nodes) {
			
			if (! nodeHasTID(node)) {
			
				// add new tid property
				Element model = document.createElement(PROPERTIES_ELEMENT);
				
				// xmi:id
				{
				Attr xmiID= document.createAttribute(ATTRIBUTE_XMIID);
				xmiID.setNodeValue(EcoreUtil.generateUUID());
				model.setAttributeNode(xmiID);
				}
				
				// value
				{
				Attr value= document.createAttribute(ATTRIBUTE_VALUE);
				value.setNodeValue("0");
				model.setAttributeNode(value);
				}
	
				// value
				{
				Attr typeName= document.createAttribute(ATTRIBUTE_TYPENAME);
				typeName.setNodeValue("tid");
				model.setAttributeNode(typeName);
				}
	
				// library
				{
				Attr lib = document.createAttribute(ATTRIBUTE_LIBNAME);
				lib.setNodeValue("xgui");
				model.setAttributeNode(lib);
				}
				
				node.appendChild(model);
			}

		}
	}
	
	
	/**
	 * @param modelNode
	 * @param nodes
	 */
	private void collectWidgetsAndEvents(Node modelNode, List<Node> nodes) {
		
		if (modelNode == null) {
			return;
		}
		
		NodeList childNodes = modelNode.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName()) || WIDGET_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String typeName = typeAttr.getNodeValue();
					Node libAttr = node.getAttributes().getNamedItem(ATTRIBUTE_LIBNAME);
					String libName = libAttr != null ? libAttr.getNodeValue() : "xgui"; // default
					WidgetType wType = MetaModelRegistry.getMetaModel().findWidgetType(libName, typeName);
					if (wType != null) {
						if (wType.translationSupported()) {
							nodes.add(node);
						}
						checkProperties(wType, typeName, node);
					}
				}
			}
			if (EVENTS_ELEMENT.equals(node.getNodeName())) {
				Node eventAttr = node.getAttributes().getNamedItem(ATTRIBUTE_EVENTNAME);
				if (eventAttr != null) {
					String eventName = eventAttr.getNodeValue();
					EventType eType = MetaModelRegistry.findEventType(eventName);
					for (PropertyType pt : eType.getPropertyTypes()) {
						if ("tid".equals(pt.getName())) {
							nodes.add(node);
							break;
						}
					}
				}
			}
		}
		
        // migrate children
        NodeList list = modelNode.getChildNodes();
        for(int i=0; i<list.getLength(); i++) {
        	collectWidgetsAndEvents(list.item(i), nodes);
		}		
		
	}

	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		List<Node> nodes = new ArrayList<Node>();		
		collectWidgetsAndEvents(document, nodes);
		migrateWidgetsAndEvents(document, nodes);
	}
	
	/**
	 * 
	 */
	public TranslationIdMigration() {
		i18nProperties.add("caption");
		i18nProperties.add("text");
		i18nProperties.add("tooltip");
		i18nProperties.add("name");
		i18nProperties.add("confirm");
	}




}
