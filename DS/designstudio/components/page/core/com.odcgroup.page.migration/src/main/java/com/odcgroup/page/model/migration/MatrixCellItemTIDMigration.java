package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * DS-3787 - Tooltip 
 *
 * @author pkk
 *
 */
public class MatrixCellItemTIDMigration extends AbstractPageModelMigration {
	
	private static final String MATRIX_WIDGET = "Matrix";
	private static final String MATRIXCELL_WIDGET = "MatrixCell";
	private static final String MATRIXCONTENTCELL_WIDGET = "MatrixContentCell";
	private static final String MATRIXCONTENTCELLITEM_WIDGET = "MatrixContentCellItem";
	private static final String MATRIXCELLITEM_WIDGET = "MatrixCellItem";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		Document document = modelNode.getOwnerDocument();
		List<Node> matrixCellItems = new ArrayList<Node>();		
		collectAllMatrixCellItemNodes(modelNode, matrixCellItems);
		migrateMatrixCellItemProperties(matrixCellItems, document);
	}
	
	/**
	 * @param matrixCells
	 * @param document
	 */
	private void migrateMatrixCellItemProperties(List<Node> matrixCells, Document document) {
		for (Node cell : matrixCells) {
			Element property =  document.createElement(PROPERTIES_ELEMENT);
			property.setAttribute(ATTRIBUTE_TYPENAME, "tid");
			property.setAttribute("value", "0");
			String uuid = EcoreUtil.generateUUID();
			property.setAttribute("xmi:id", uuid);
			cell.appendChild(property);			
		}
	}

	/**
	 * collect all cell items
	 * 
	 * @param widget
	 * @param items
	 * @return
	 */
	private List<Node> collectAllMatrixCellItemNodes(Node widget, List<Node> items) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String nodeVal = typeAttr.getNodeValue();
					if (isContainerWidgetType(typeAttr)) {
						collectAllMatrixCellItemNodes(node, items);
					} else if (nodeVal.equals(MATRIX_WIDGET)) {
						collectAllMatrixCellItemNodes(node, items);						
					} else if (nodeVal.equals(MATRIXCELL_WIDGET)
							|| nodeVal.equals(MATRIXCONTENTCELL_WIDGET) ) {
						collectAllMatrixCellItemNodes(node, items);
					} else if (nodeVal.equals(MATRIXCELLITEM_WIDGET) || nodeVal.equals(MATRIXCONTENTCELLITEM_WIDGET)) {
						items.add(node);
					}
				}
			}
		}
		return items;		
	}

}
