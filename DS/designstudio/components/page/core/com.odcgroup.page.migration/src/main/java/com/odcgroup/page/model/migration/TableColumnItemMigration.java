package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * migration of tableColumnItems with no item-column property set
 * required by DS-2620
 *
 * @author pkk
 *
 */
public class TableColumnItemMigration extends AbstractPageModelMigration {
	
	private static final String WIDGET_TABLETREE = "TableTree";
	private static final String WIDGET_TABLECOLUMN = "TableColumn";
	private static final String WIDGET_TABLECOLUMNITEM = "TableColumnItem";
	
	private static final String ATTRIBUTE_COLUMNTYPE = "column-type";
	private static final String ATTRIBUTE_COLUMNNAME = "column-name";
	private static final String PLACEHOLDER_COLUMN = "placeholder";
	
	private static final String PROPERTY_ITEMCOLUMN_ATTR = "item-column";
	private static final String PROPERTY_DOMAIN_ATTR = "domainAttribute";
	private static final String PROPERTY_VALUE_ATTR = "value";

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
			String columnName =  getPropertyAttributeValue(tableColumn, PROPERTY_DOMAIN_ATTR);
			boolean computedColumn = StringUtils.isEmpty(columnName);
			if (computedColumn) {
				columnName = getPropertyAttributeValue(tableColumn, ATTRIBUTE_COLUMNNAME);
			}

			NodeList childNodes = tableColumn.getChildNodes();
			Node node = null;
			for(int ii = 0; ii < childNodes.getLength();ii++) {
				node = childNodes.item(ii);
				if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
					Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeAttr != null) {
						if (typeAttr.getNodeValue().equals(WIDGET_TABLECOLUMNITEM)) {
							NodeList itemNodes = node.getChildNodes();
							Node itemProp = null;
							boolean found = false;
							for (int jj = 0; jj < itemNodes.getLength(); jj++) {
								itemProp = itemNodes.item(jj);
								if(PROPERTIES_ELEMENT.equals(itemProp.getNodeName())) {
									Node itemTypeAttr = itemProp.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
									if (PROPERTY_ITEMCOLUMN_ATTR.equals(itemTypeAttr.getNodeValue())) {
										Node value = itemProp.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
										if(StringUtils.isEmpty(value.getNodeValue())) {
											value.setNodeValue(columnName);
										}
										found = true;
									}									
								}
							}
							if (!found) {
								Element element = node.getOwnerDocument().createElement(PROPERTIES_ELEMENT);
								element.setAttribute("libraryName", "xgui");
								element.setAttribute(ATTRIBUTE_TYPENAME, PROPERTY_ITEMCOLUMN_ATTR);
								element.setAttribute(PROPERTY_VALUE_ATTR, columnName);
								node.appendChild(element);							
							}
						}
					}
				}
			}		
		}
	}
	
	/**
	 * @param tableColumn
	 * @return
	 */
	private String getPropertyAttributeValue(Node tableColumn, String propertyType) {
		NodeList childNodes = tableColumn.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (propertyType.equals(typeAttr.getNodeValue())) {
					return node.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR).getNodeValue();
				}
			}
		}
		return null;
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
						if (!isPlaceHolder(node) && containsItemsForMigration(node)) {
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
	private boolean containsItemsForMigration(Node tableColumn) {
		NodeList childNodes = tableColumn.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (typeAttr.getNodeValue().equals(WIDGET_TABLECOLUMNITEM)) {
						NodeList itemNodes = node.getChildNodes();
						Node itemProp = null;
						boolean found  = false;
						for (int jj = 0; jj < itemNodes.getLength(); jj++) {
							itemProp = itemNodes.item(jj);
							if(PROPERTIES_ELEMENT.equals(itemProp.getNodeName())) {
								Node itemTypeAttr = itemProp.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
								if (itemTypeAttr.getNodeValue().equals(PROPERTY_ITEMCOLUMN_ATTR)) {
									found = true;
									Node value = itemProp.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
									return StringUtils.isEmpty(value.getNodeValue());
								}
							}
						}
						if (!found) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
