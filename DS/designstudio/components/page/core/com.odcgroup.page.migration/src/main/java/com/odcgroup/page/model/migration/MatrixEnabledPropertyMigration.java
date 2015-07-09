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
 * DS-3770
 * migration to remove enabled & enabledIsBasedOn properties from axis widgets
 * and create the same for matrixcontentcellitems
 *
 * @author pkk
 *
 */
public class MatrixEnabledPropertyMigration extends AbstractPageModelMigration {
	
	private static final String MATRIX_WIDGET = "Matrix";
	private static final String MATRIXAXIS_WIDGET = "MatrixAxis";
	private static final String MATRIXCONTENTCELL_WIDGET = "MatrixContentCell";
	private static final String MATRIXCONTENTCELLITEM_WIDGET = "MatrixContentCellItem";
	private static final String PROPTYPE_ENABLEDBASED = "enabledIsBasedOn";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {	
		Document document = modelNode.getOwnerDocument();
		List<Node> matrixAxes = new ArrayList<Node>();
		List<Node> matrixCellItems = new ArrayList<Node>();
		collectAllMatrixAxisNodes(modelNode, matrixAxes);
		collectAllMatrixCellItemsNodes(modelNode, matrixCellItems);
		migrateMatrixAxisProperties(matrixAxes);
		migrateMatrixCellItemProperties(matrixCellItems, document);
	}
	
	/**
	 * remove enabled & enabedIsBasedOn properties from axis nodes
	 * @param matrixCells
	 */
	private void migrateMatrixAxisProperties(List<Node> matrixAxes) {
		for (Node cell : matrixAxes) {
			NodeList childNodes = cell.getChildNodes();
			Node node = null;
			for(int ii = 0; ii < childNodes.getLength();ii++) {
				node = childNodes.item(ii);
				if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
					Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeAttr != null) { 
						if(typeAttr.getNodeValue().equals(PropertyTypeConstants.ENABLED)
								|| typeAttr.getNodeValue().equals(PROPTYPE_ENABLEDBASED)) {
							cell.removeChild(node);
						}
					} 	
				}
			}			
		}
	}
	
	/**
	 * @param items
	 */
	private void migrateMatrixCellItemProperties(List<Node> items, Document document) {		
		for (Node item : items) {
			Element property =  document.createElement(PROPERTIES_ELEMENT);
			property.setAttribute(ATTRIBUTE_TYPENAME, PropertyTypeConstants.ENABLED);
			property.setAttribute("value", "true");
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
	
	/**
	 * @param widget
	 * @param matrixAxes
	 * @return
	 */
	private List<Node> collectAllMatrixAxisNodes(Node widget, List<Node> matrixAxes) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String nodeVal = typeAttr.getNodeValue();
					if (isContainerWidgetType(typeAttr)) {
						collectAllMatrixAxisNodes(node, matrixAxes);
					}  else if (nodeVal.equals(MATRIX_WIDGET)) {
						collectAllMatrixAxisNodes(node, matrixAxes);						
					} else if (nodeVal.equals(MATRIXAXIS_WIDGET) ) {
						matrixAxes.add(node);
					} 
				}
			}
		}
		return matrixAxes;	
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
					} else if (nodeVal.equals(MATRIXCONTENTCELL_WIDGET) ) {
						collectAllMatrixCellItemsNodes(node, items);	
					}  else if (nodeVal.equals(MATRIXCONTENTCELLITEM_WIDGET)) {
						items.add(node);						
					}
				}
			}
		}
		return items;	
	}

}
