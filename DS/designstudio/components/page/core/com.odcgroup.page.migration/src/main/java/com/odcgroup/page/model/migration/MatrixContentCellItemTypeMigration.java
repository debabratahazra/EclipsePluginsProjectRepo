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
 * DS-3809
 *
 * @author pkk
 *
 */
public class MatrixContentCellItemTypeMigration extends AbstractPageModelMigration {
	
	private static final String MATRIX_WIDGET = "Matrix";
	private static final String MATRIXCONTENTCELL_WIDGET = "MatrixContentCell";
	private static final String MATRIXCONTENTCELLITEM_WIDGET = "MatrixContentCellItem";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {	
		Document document = modelNode.getOwnerDocument();
		List<Node> matrixCellItems = new ArrayList<Node>();
		collectAllMatrixContentCellItemsNodes(modelNode, matrixCellItems);
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
					if (type != null && "type".equals(type.getNodeValue())) {
						String val = child.getAttributes().getNamedItem("value").getNodeValue();

						Element property =  document.createElement(PROPERTIES_ELEMENT);
						property.setAttribute(ATTRIBUTE_TYPENAME, PropertyTypeConstants.FORMAT);
						property.setAttribute("value", val);
						String uuid = EcoreUtil.generateUUID();
						property.setAttribute("xmi:id", uuid);
						item.appendChild(property);
						
						item.removeChild(child);
					}
				}
			}
		}
	}

	/**
	 * @param widget
	 * @param items
	 * @return
	 */
	private List<Node> collectAllMatrixContentCellItemsNodes(Node widget, List<Node> items) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String nodeVal = typeAttr.getNodeValue();
					if (isContainerWidgetType(typeAttr)) {
						collectAllMatrixContentCellItemsNodes(node, items);
					}  else if (nodeVal.equals(MATRIX_WIDGET)) {
						collectAllMatrixContentCellItemsNodes(node, items);						
					} else if (nodeVal.equals(MATRIXCONTENTCELL_WIDGET) ) {
						collectAllMatrixContentCellItemsNodes(node, items);	
					}  else if (nodeVal.equals(MATRIXCONTENTCELLITEM_WIDGET)) {
						items.add(node);						
					}
				}
			}
		}
		return items;	
	}

}
