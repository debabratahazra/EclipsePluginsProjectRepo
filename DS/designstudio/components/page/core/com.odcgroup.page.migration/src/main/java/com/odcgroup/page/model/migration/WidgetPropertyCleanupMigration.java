package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

public class WidgetPropertyCleanupMigration extends AbstractPageModelMigration {
	

	private static final String WIDGET_COMBO = "ComboBox";
	private static final String WIDGET_LIST = "List";
	
	private static final String PROPERTY_WIDTH = "width";
	private static final String VALUE_ATTR = "value";
	

	private List<Node> widgets = null;

	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI,
			Node modelNode) {
		widgets = new ArrayList<Node>();
		collectWidgetsForMigration(modelNode);
		if (!widgets.isEmpty())
			performPropertyCleanup();
	}
	
	/**
	 * 
	 */
	private void performPropertyCleanup() {
		for (Node node : widgets) {
			NodeList properties = node.getChildNodes();
			for (int jj = 0; jj < properties.getLength(); jj++) {
				Node property = properties.item(jj);
				if (PROPERTIES_ELEMENT.equals(property.getNodeName())) {
					Node typeName = property.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeName != null && PROPERTY_WIDTH.equals(typeName.getNodeValue())) {
						Node valueNode = property.getAttributes().getNamedItem(VALUE_ATTR);
						if (valueNode.getNodeValue().equals("0")) {
							valueNode.setNodeValue("");
						}
					}
				}						
			}
		}
	}
	
	/**
	 * @param modelNode
	 * @param tableTrees
	 */
	private void collectWidgetsForMigration(Node modelNode) {
		NodeList childNodes = modelNode.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (typeAttr.getNodeValue().equals(WIDGET_COMBO) 
							|| typeAttr.getNodeValue().equals(WIDGET_LIST)) {
						widgets.add(node);
					} else {
						collectWidgetsForMigration(node);
					}
				}
			}
		}	
	}

}
