package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * DS-3791
 *
 * @author pkk
 *
 */
public class MatrixExtraColumnTypeMigration  extends AbstractPageModelMigration {
	
	private static final String MATRIX_WIDGET = "Matrix";
	private static final String MATRIXEXTRACOLUMN_WIDGET = "MatrixExtraColumn";
	private static final String MATRIXEXTRACOLUMNITEM_WIDGET = "MatrixExtraColumnItem";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		Document document = modelNode.getOwnerDocument();
		List<Node> matrixExtraColumnItems = new ArrayList<Node>();		
		collectAllMatrixExtraColumnItemNodes(modelNode, matrixExtraColumnItems);
		migrateMatrixExtraColumnItemProperties(matrixExtraColumnItems, document);
	}
	
	/**
	 * @param matrixCells
	 * @param document
	 */
	private void migrateMatrixExtraColumnItemProperties(List<Node> matrixCells, Document document) {
		for (Node cell : matrixCells) {
			NodeList childNodes = cell.getChildNodes();
			Node node = null;
			for(int ii = 0; ii < childNodes.getLength();ii++) {
				node = childNodes.item(ii);
				if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
					Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeAttr != null && typeAttr.getNodeValue().equals("matrixCellItemType")) {
						node.getAttributes().getNamedItem("value").setNodeValue("domain");
						Node readOnly = node.getAttributes().getNamedItem("readonly");
						if (readOnly != null) {
							readOnly.setNodeValue("true");
						} else {
							Attr attr = document.createAttribute("readonly");
							attr.setNodeValue("true");
							node.getAttributes().setNamedItem(attr);
						}
					} 
				}
			}	
		}
	}

	/**
	 * collect all cell items
	 * 
	 * @param widget
	 * @param items
	 * @return
	 */
	private List<Node> collectAllMatrixExtraColumnItemNodes(Node widget, List<Node> items) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String nodeVal = typeAttr.getNodeValue();
					if (isContainerWidgetType(typeAttr)) {
						collectAllMatrixExtraColumnItemNodes(node, items);
					} else if (nodeVal.equals(MATRIX_WIDGET)) {
						collectAllMatrixExtraColumnItemNodes(node, items);						
					} else if (nodeVal.equals(MATRIXEXTRACOLUMN_WIDGET) ) {
						collectAllMatrixExtraColumnItemNodes(node, items);
					} else if (nodeVal.equals(MATRIXEXTRACOLUMNITEM_WIDGET)) {
						items.add(node);
					}
				}
			}
		}
		return items;		
	}

}
