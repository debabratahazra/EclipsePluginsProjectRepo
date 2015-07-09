package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Place holder Column
 * Migration to set the default value of  column-sortable property to false
 * and make the property read-only
 *
 * @author pkk
 *
 */
public class PlaceholderColumnMigration extends AbstractPageModelMigration {
	
	private static final String WIDGET_TABLETREE = "TableTree";
	private static final String WIDGET_TABLECOLUMN = "TableColumn";
	
	private static final String ATTRIBUTE_COLUMNTYPE = "column-type";
	private static final String ATTRIBUTE_COLUMNSORT = "column-sortable";
	private static final String PLACEHOLDER_COLUMN = "placeholder";
	
	private static final String PROPERTY_VALUE_ATTR = "value";
	private static final String PROPERTY_READONLY_ATTR = "readonly";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		List<Node> tableColumns = new ArrayList<Node>();				
		collectAllTableColumnNodes(modelNode, tableColumns);
		migrateTableColumnItems(tableColumns);		
	}
	

	
	/**
	 * @param tableColumns
	 */
	private void migrateTableColumnItems(List<Node> tableColumns) {
		for (Node tableColumn : tableColumns) {
			NodeList childNodes = tableColumn.getChildNodes();
			Node node = null;
			for(int ii = 0; ii < childNodes.getLength();ii++) {
				node = childNodes.item(ii);
				if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
					Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeAttr != null && typeAttr.getNodeValue().equals(ATTRIBUTE_COLUMNSORT)) {
						Node valueAttr = node.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
						if (valueAttr.getNodeValue().equals("true")) {
							valueAttr.setNodeValue("false");
						}
						Node readonly = node.getAttributes().getNamedItem(PROPERTY_READONLY_ATTR);
						if (readonly == null) {
							Attr attrib = node.getOwnerDocument().createAttribute(PROPERTY_READONLY_ATTR);
							attrib.setNodeValue("true");
							node.getAttributes().setNamedItem(attrib);
						} else {
							readonly.setNodeValue("true");
						}
					}
				}
			}
			
		}
	}	
	
	
	/**
	 * @param widget
	 * @param tableColumns
	 * @return
	 */
	private List<Node> collectAllTableColumnNodes(Node widget, List<Node> tableColumns) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (isContainerWidgetType(typeAttr)) {
						collectAllTableColumnNodes(node, tableColumns);
					} else if (typeAttr.getNodeValue().equals(WIDGET_TABLETREE)) {
						collectAllTableColumnNodes(node, tableColumns);
					} else if (typeAttr.getNodeValue().equals(WIDGET_TABLECOLUMN)) {
						if (isPlaceHolder(node) && isMigrationRequired(node)) {
							tableColumns.add(node);
						}
					}
				}
			}
		}
		return tableColumns;		
	}
	
	/**
	 * @param tableColumn
	 * @return
	 */
	private boolean isMigrationRequired(Node tableColumn) {
		NodeList childNodes = tableColumn.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null && typeAttr.getNodeValue().equals(ATTRIBUTE_COLUMNSORT)) {
					Node valueAttr = node.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
					if (valueAttr.getNodeValue().equals("true")) {
						return true;
					}
				}
			}
		}
		return false;		
	}
	
	/**
	 * @param tableColumn
	 * @return
	 */
	private boolean isPlaceHolder(Node tableColumn) {
		NodeList childNodes = tableColumn.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null && typeAttr.getNodeValue().equals(ATTRIBUTE_COLUMNTYPE)) {
					Node valueAttr = node.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
					if (valueAttr.getNodeValue().equals(PLACEHOLDER_COLUMN)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
