package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.page.model.util.QTPCompliantIDGenerator;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * DS-2502
 * add the QTP compliant ID to the modules/fragments widgets
 * (module, tabletree, tablecolumn, buttons etc)
 * 
 * @author pkk
 *
 */
public class QTPComplianceIDMigration extends AbstractPageModelMigration {
	
	private static final String WIDGET_TABLETREE = "TableTree";
	private static final String WIDGET_TABLECOLUMN = "TableColumn";
	private static final String WIDGET_BUTTON = "Button";
	
	private static final String COLUMN_TYPE ="column-type";
	
	private static final String COMP_COLUMN = "computed";
	private static final String DATA_COLUMN = "domain";
	private static final String VALUE_ATTR = "value";
	private List<Node> tableTrees = null;
	private List<Node> buttons = null;

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration
	 * (com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		
		this.tableTrees = new ArrayList<Node>();
		this.buttons = new ArrayList<Node>();
		
		// set ID incase of a module
		NamedNodeMap attributes = modelNode.getAttributes();
		Node typeAttribute = attributes.getNamedItem(ATTRIBUTE_TYPENAME);
		if (typeAttribute != null 
				&& ( WIDGET_MODULE.equals(typeAttribute.getNodeValue()) )) {
			String name = modelURI.lastSegment();
			int pos = name.indexOf('.');
			if (pos >= 0) {
				name = name.substring(0, pos);
			}
			setID(modelNode, name+"_");
		}
		
		// process all tabletrees
		collectWidgetsForMigration(modelNode);
		for (Node tableTree : tableTrees) {
			setID(tableTree, null);
			NodeList childNodes = tableTree.getChildNodes();
			migrateTableColumns(childNodes);
		}
		
		// process all buttons
		for (Node button : buttons) {
			setID(button, "btn_");
		}
	}
	
	
	/**
	 * @param nodes
	 */
	private void migrateTableColumns(NodeList nodes) {
		Node node = null;
		for (int ii = 0; ii < nodes.getLength();ii++) {
			node = nodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null && typeAttr.getNodeValue().equals(WIDGET_TABLECOLUMN)) {
					NodeList properties = node.getChildNodes();
					for (int jj = 0; jj < properties.getLength(); jj++) {
						Node property = properties.item(jj);
						if (PROPERTIES_ELEMENT.equals(property.getNodeName())) {
							Node typeName = property.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
							if (typeName != null && COLUMN_TYPE.equals(typeName.getNodeValue())) {
								Node valueNode = property.getAttributes().getNamedItem(VALUE_ATTR);
								if (valueNode.getNodeValue().equals(COMP_COLUMN)) {
									setID(node, null);
								} else if (valueNode.getNodeValue().equals(DATA_COLUMN)) {
									String dataValue = getDataAttribute(properties);
									if (dataValue != null)
										setID(node, dataValue, true);
								}
							}
						}						
					}
				}
			}
		}
	}
	
	/**
	 * @param properties
	 * @return
	 */
	private String getDataAttribute(NodeList properties) {
		for (int jj = 0; jj < properties.getLength(); jj++) {
			Node property = properties.item(jj);
			if (PROPERTIES_ELEMENT.equals(property.getNodeName())) {
				NamedNodeMap attributes = property.getAttributes();
				Node typeName = attributes.getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeName != null && "domainAttribute".equals(typeName.getNodeValue()) ) {				
					Node value = attributes.getNamedItem(VALUE_ATTR);
					if (value != null) {
						return value.getNodeValue();
					}
				}	
			}
		}
		return null;
	}
	
	/**
	 * @param node
	 * @param prefix
	 */
	private void setID(Node node, String prefix) {
		this.setID(node, prefix, false);
	}
	
	/**
	 * check whether id property exists for the given node
	 * @param node
	 * @return
	 */
	private void setID(Node node, String prefix, boolean nonGen) {
		NodeList childNodes = node.getChildNodes();
		Node child = null;
		for(int ii = 0;ii<childNodes.getLength();ii++) {
			child = childNodes.item(ii);
			if (PROPERTIES_ELEMENT.equals(child.getNodeName())) {
				Node typeAttr = child.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (TYPE_ID.equals(typeAttr.getNodeValue())) {
						Node idVal = child.getAttributes().getNamedItem(VALUE_ATTR);
						String idValue = null;
						if (nonGen) {
							idValue = prefix;
						} else {
							idValue = QTPCompliantIDGenerator.generateID();
							if (prefix != null) {								
								idValue = prefix+idValue;
							}
						}
						idVal.setNodeValue(idValue);
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
					if (isContainerWidgetType(typeAttr)) {
						collectWidgetsForMigration(node);
					} else if (typeAttr.getNodeValue().equals(WIDGET_TABLETREE)) {
						tableTrees.add(node);
					} else if (typeAttr.getNodeValue().equals(WIDGET_BUTTON)) {
						buttons.add(node);
					}
				}
			}
		}	
	}

	

}
