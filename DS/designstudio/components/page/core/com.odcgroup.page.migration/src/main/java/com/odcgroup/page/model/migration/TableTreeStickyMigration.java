package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * DS-3736
 * Migration required to remove sticky property from TableTree widgets
 *
 * @author pkk
 *
 */
public class TableTreeStickyMigration extends AbstractPageModelMigration {
	
	private static final String WIDGET_TABLETREE = "TableTree";
	private static final String PROPERTY_STICKY = "sticky";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		List<Node> tables = new ArrayList<Node>();				
		collectAllTableNodes(modelNode, tables);
		migrateTableStickyProperty(tables);		
	}
	

	
	/**
	 * @param tables
	 */
	private void migrateTableStickyProperty(List<Node> tables) {
		for (Node table : tables) {
			NodeList properties = table.getChildNodes();
			for (int jj = 0; jj < properties.getLength(); jj++) {
				Node property = properties.item(jj);
				if (PROPERTIES_ELEMENT.equals(property.getNodeName())) {
					Node typeName = property.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeName != null && PROPERTY_STICKY.equals(typeName.getNodeValue())) {
						table.removeChild(property);
					}
				}
			}
		}
	}
	
	/**
	 * @param widget
	 * @param tables
	 * @return
	 */
	private List<Node> collectAllTableNodes(Node widget, List<Node> tables) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (isContainerWidgetType(typeAttr)) {
						collectAllTableNodes(node, tables);
					} else if (typeAttr.getNodeValue().equals(WIDGET_TABLETREE)) {
						tables.add(node);
					} 
				}
			}
		}
		return tables;		
	}
	
}
