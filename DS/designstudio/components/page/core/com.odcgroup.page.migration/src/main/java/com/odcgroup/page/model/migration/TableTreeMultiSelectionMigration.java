package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * For each TableTree widget that has a PlaceHolder column having a property
 * checkbox-header active (true) then all its contents is removed
 * 
 * @author atr
 * 
 */
public class TableTreeMultiSelectionMigration extends AbstractPageModelMigration {

	private static final String WIDGET_TABLETREE = "TableTree";
	private static final String WIDGET_TABLECOLUMN = "TableColumn";

	private static final String PLACEHOLDER_COLUMN = "placeholder";
	private static final String ATTRIBUTE_COLUMNTYPE = "column-type";
	private static final String ATTRIBUTE_CHECKBOX_HEADER = "column-checkbox-header";
	private static final String PROPERTY_VALUE_ATTR = "value";

	protected boolean isContainer(Node typeAttr) {
		String name = typeAttr.getNodeValue();
		return name.equals(WIDGET_BOX)
			|| name.equals("TabbedPane")
			|| name.equals("Tab")
			|| name.equals("Conditional")
			|| name.equals("ConditionalBody"); 
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
				if (typeAttr != null && typeAttr.getNodeValue().equals(ATTRIBUTE_CHECKBOX_HEADER)) {
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
	 * @param widget
	 * @param tableColumns
	 * @return a collection
	 */
	private List<Node> collectAllPlaceHolderColumnNodes(Node widget, List<Node> tableColumns) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (isContainer(typeAttr)) {
						collectAllPlaceHolderColumnNodes(node, tableColumns);
					} else if (typeAttr.getNodeValue().equals(WIDGET_TABLETREE)) {
						collectAllPlaceHolderColumnNodes(node, tableColumns);
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
	 * Remove all widgets included in the placeholder.
	 * 
	 * @param tableColumns a list of Placeholder columns
	 */
	private void migratePlaceHolderColumnItems(List<Node> tableColumns) {
		for (Node tableColumn : tableColumns) {
			NodeList childNodes = tableColumn.getChildNodes();
			Node node = null;
			for(int ii = 0; ii < childNodes.getLength();ii++) {
				node = childNodes.item(ii);
				if(CONTENTS_ELEMENT.equals(node.getNodeName())) {
					tableColumn.removeChild(node);
				}
			}
			
		}
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		List<Node> tableColumns = new ArrayList<Node>();				
		collectAllPlaceHolderColumnNodes(modelNode, tableColumns);
		migratePlaceHolderColumnItems(tableColumns);	
		// if (tableColumns.size()>0) System.out.println(modelURI);
	}
	
	public TableTreeMultiSelectionMigration() {
		
	}

}
