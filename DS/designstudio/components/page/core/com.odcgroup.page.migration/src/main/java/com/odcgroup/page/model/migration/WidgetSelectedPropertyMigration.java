package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Radio Button & Checkbox Widget Selected Property Migration
 *
 * @author pkk
 *
 */
public class WidgetSelectedPropertyMigration extends AbstractPageModelMigration {
	
	private static final String RADIO_WIDGET_TYPE = "RadioButton";
	private static final String PROPERTY_VALUE_ATTR = "value";

	/**
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		List<Node> toggleWidgets = new ArrayList<Node>();		
		collectAllToggleWidgetNodes(modelNode, toggleWidgets);
		migrateToggleWidgets(toggleWidgets);
	}
	
	/**
	 * collect all the checkBox & RadioButton widgets in the module/fragment for migration
	 * 
	 * @param widget node
	 * @param toggleWidget nodes
	 */
	private void collectAllToggleWidgetNodes(Node widget, List<Node> toggleWidgets) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (isContainerWidgetType(typeAttr)) {
						collectAllToggleWidgetNodes(node, toggleWidgets);
					} else if (typeAttr.getNodeValue().equals(WidgetTypeConstants.CHECKBOX) 
							|| typeAttr.getNodeValue().equals(RADIO_WIDGET_TYPE)) {
						toggleWidgets.add(node);
					}
				}
			}
		}		
	}
	
	/**
	 * @param toggleWidgets
	 */
	private void migrateToggleWidgets(List<Node> toggleWidgets) {
		for (Node node : toggleWidgets) {
			Node selectedNode = getPropertyNode(node, "selected");
			if (selectedNode == null) {
				continue;
			}
			Node valueAttribute = selectedNode.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
			String value = valueAttribute.getNodeValue();
			if (StringUtils.isEmpty(value) || !value.equals("false")) {
				valueAttribute.setNodeValue("true");
			}
		}
	}
	
	/**
	 * @param tableColumn
	 * @return
	 */
	private Node getPropertyNode(Node widget, String propertyType) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (propertyType.equals(typeAttr.getNodeValue())) {
					return node;
				}
			}
		}
		return null;
	}

}
