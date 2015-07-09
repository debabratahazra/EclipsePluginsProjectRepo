package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * DS-3803, migration to have a column name for a computed column
 * 
 * @author pkk
 *
 */
public class MatrixComputedColumnMigration extends AbstractPageModelMigration {
	
	private static final String MATRIX_WIDGET = "Matrix";
	private static final String MATRIXCONTENTCELL_WIDGET = "MatrixContentCell";
	private static final String MATRIXCELL_WIDGET = "MatrixCell";
	private static final String MATRIXCELLITEM_WIDGET = "MatrixCellItem";
	private static final String MATRIXCONTENTCELLITEM_WIDGET = "MatrixContentCellItem";
	private static final String PROPTYPE_ENABLEDBASED = "enabledIsBasedOn";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {	
		Document document = modelNode.getOwnerDocument();
		List<Node> matrixCellItems = new ArrayList<Node>();
		collectAllMatrixCellItemsNodes(modelNode, matrixCellItems);
		migrateMatrixCellItemProperties(matrixCellItems, document);
	}
	
	/**
	 * @param items
	 */
	private void migrateMatrixCellItemProperties(List<Node> items, Document document) {		
		for (Node item : items) {
			NodeList childNodes = item.getChildNodes();
			Node child = null;
			for(int ii = 0; ii < childNodes.getLength();ii++) {
				child = childNodes.item(ii);
				if (PROPERTIES_ELEMENT.equals(child.getNodeName())) {
					Node type = child.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (type != null && "matrixCellItemType".equals(type.getNodeValue())) {
						String value = child.getAttributes().getNamedItem("value").getNodeValue();
						if ("computed".equals(value)) {
							Element property =  document.createElement(PROPERTIES_ELEMENT);
							property.setAttribute(ATTRIBUTE_TYPENAME, "column-name");
							String id = getCellItemID(childNodes);
							property.setAttribute("value", "comp_"+id);
							String uuid = EcoreUtil.generateUUID();
							property.setAttribute("xmi:id", uuid);
							item.appendChild(property);
				
							Element isenabledProp = document.createElement(PROPERTIES_ELEMENT);
							isenabledProp.setAttribute(ATTRIBUTE_TYPENAME, PROPTYPE_ENABLEDBASED);
							isenabledProp.setAttribute("value", "");
							uuid = EcoreUtil.generateUUID();
							isenabledProp.setAttribute("xmi:id", uuid);
							item.appendChild(isenabledProp);
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param childNodes
	 * @return
	 */
	private String getCellItemID(NodeList childNodes) {
		Node child = null;
		String id = "";
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			child = childNodes.item(ii);
			if (PROPERTIES_ELEMENT.equals(child.getNodeName())) {
				Node type = child.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (type != null && PropertyTypeConstants.ID.equals(type.getNodeValue())) {
					id = child.getAttributes().getNamedItem("value").getNodeValue();
					break;
				}
			}
		}
		return id;
	}
	

	/**
	 * @param widget
	 * @param items
	 * @return
	 */
	private List<Node> collectAllMatrixCellItemsNodes(Node widget, List<Node> items) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String nodeVal = typeAttr.getNodeValue();
					if (isContainerWidgetType(typeAttr)) {
						collectAllMatrixCellItemsNodes(node, items);
					}  else if (nodeVal.equals(MATRIX_WIDGET)) {
						collectAllMatrixCellItemsNodes(node, items);						
					} else if (nodeVal.equals(MATRIXCONTENTCELL_WIDGET) || nodeVal.equals(MATRIXCELL_WIDGET) ) {
						collectAllMatrixCellItemsNodes(node, items);	
					}  else if (nodeVal.equals(MATRIXCONTENTCELLITEM_WIDGET) || nodeVal.equals(MATRIXCELLITEM_WIDGET)) {
						items.add(node);						
					}
				}
			}
		}
		return items;	
	}

}
