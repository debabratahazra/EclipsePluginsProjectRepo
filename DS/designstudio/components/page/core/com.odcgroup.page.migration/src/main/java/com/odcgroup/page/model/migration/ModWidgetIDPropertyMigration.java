package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.page.model.util.QTPCompliantIDGenerator;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * DS-3510
 *
 * @author pkk
 *
 */
public class ModWidgetIDPropertyMigration extends AbstractPageModelMigration {
	
	private static List<String> REQ_WIDGETS = new ArrayList<String>(); 
	
	static {
		REQ_WIDGETS.add(WidgetTypeConstants.BOX);
		REQ_WIDGETS.add(WidgetTypeConstants.GRID);
		REQ_WIDGETS.add(WidgetTypeConstants.AUTO_COMPLETE);
		REQ_WIDGETS.add(WidgetTypeConstants.CALDATE_FIELD);
		REQ_WIDGETS.add(WidgetTypeConstants.FILE_CHOOSER);
		REQ_WIDGETS.add(WidgetTypeConstants.PASSWORD_FIELD);
		REQ_WIDGETS.add(WidgetTypeConstants.SEARCH_FIELD);
		REQ_WIDGETS.add(WidgetTypeConstants.TEXTAREA);
		REQ_WIDGETS.add(WidgetTypeConstants.TEXTFIELD);
		REQ_WIDGETS.add(WidgetTypeConstants.COMBOBOX);
		REQ_WIDGETS.add(WidgetTypeConstants.LIST);
		REQ_WIDGETS.add(WidgetTypeConstants.HIDDEN_FIELD);
		REQ_WIDGETS.add(WidgetTypeConstants.ICON);
		REQ_WIDGETS.add(WidgetTypeConstants.LABEL);
	}
	
	/* */
	private static String PROPERTY_ID = "id";	
	/* */
	private static String VALUE_ATTR = "value";
	
	public ModWidgetIDPropertyMigration() {
		allowPages = true;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		Document document = modelNode.getOwnerDocument();
		List<Node> widgets = new ArrayList<Node>();
		collectWidgetsForMigration(modelNode, widgets);		
		performIDMigration(document, widgets);
	}
	
	/**
	 * @param widgets
	 */
	private void performIDMigration(Document document, List<Node> widgets) {
		for (Node node : widgets) {
			NodeList properties = node.getChildNodes();
			Node id = fetchIDPropertyNode(properties);
			if (id != null) {
				Node valueNode = id.getAttributes().getNamedItem(VALUE_ATTR);
				String value = valueNode.getNodeValue();
				if (StringUtils.isEmpty(value)) {
					valueNode.setNodeValue(QTPCompliantIDGenerator.generateID());
				}
			} else {
				Element property =  document.createElement(PROPERTIES_ELEMENT);
				property.setAttribute(ATTRIBUTE_TYPENAME, PROPERTY_ID);
				property.setAttribute("value", QTPCompliantIDGenerator.generateID());
				String uuid = EcoreUtil.generateUUID();
				property.setAttribute("xmi:id", uuid);
				node.appendChild(property);			
			}				
		}
	}
	
	/**
	 * @param properties
	 * @return
	 */
	private Node fetchIDPropertyNode(NodeList properties) {
		for (int jj = 0; jj < properties.getLength(); jj++) {
			Node property = properties.item(jj);
			if (PROPERTIES_ELEMENT.equals(property.getNodeName())) {
				Node typeName = property.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeName != null && PROPERTY_ID.equals(typeName.getNodeValue())) {
					return property;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param modelNode
	 * @param widgets
	 */
	private void collectWidgetsForMigration(Node modelNode, List<Node> widgets) {
		NodeList childNodes = modelNode.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String value = typeAttr.getNodeValue();
					if(REQ_WIDGETS.contains(value)) {
						widgets.add(node);					
					}
					if (isContainerWidgetType(typeAttr)) {
						collectWidgetsForMigration(node, widgets);
					} 
					
					
				}
			}
		}		
	}

}
