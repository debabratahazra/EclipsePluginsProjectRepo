package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Migration to remove id, documentation properties from MatrixCell widgets
 * and id property from MatrixAxis
 *
 * @author pkk
 *
 */
public class MatrixWidgetPropertyMigration extends AbstractPageModelMigration {
	
	private static final String MATRIX_WIDGET = "Matrix";
	private static final String MATRIXAXIS_WIDGET = "MatrixAxis";
	private static final String MATRIXCELL_WIDGET = "MatrixCell";
	private static final String MATRIXCONTENTCELL_WIDGET = "MatrixContentCell";
	private static final String MATRIXEXTRA_WIDGET = "MatrixExtraColumn";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		List<Node> matrixCells = new ArrayList<Node>();		
		List<Node> matrixAxes = new ArrayList<Node>();
		collectAllMatrixCellNodes(modelNode, matrixCells);
		collectAllMatrixAxisNodes(modelNode, matrixAxes);
		migrateMatrixCellProperties(matrixCells, true);
		migrateMatrixCellProperties(matrixAxes, false);
	}
	
	/**
	 * @param matrixCells
	 */
	private void migrateMatrixCellProperties(List<Node> matrixCells, boolean documentation) {
		for (Node cell : matrixCells) {
			NodeList childNodes = cell.getChildNodes();
			Node node = null;
			for(int ii = 0; ii < childNodes.getLength();ii++) {
				node = childNodes.item(ii);
				if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
					Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeAttr != null && typeAttr.getNodeValue().equals(PropertyTypeConstants.ID)) {
						cell.removeChild(node);
					} 					
					if (typeAttr != null 
							&& typeAttr.getNodeValue().equals(PropertyTypeConstants.DOCUMENTATION) 
							&& documentation) {
						cell.removeChild(node);						
					}
				}
			}
			
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
	 * @param tableColumns
	 * @return
	 */
	private List<Node> collectAllMatrixCellNodes(Node widget, List<Node> matrixCells) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String nodeVal = typeAttr.getNodeValue();
					if (isContainerWidgetType(typeAttr)) {
						collectAllMatrixCellNodes(node, matrixCells);
					} else if (nodeVal.equals(MATRIX_WIDGET)) {
						collectAllMatrixCellNodes(node, matrixCells);						
					} else if (nodeVal.equals(MATRIXCELL_WIDGET)
							|| nodeVal.equals(MATRIXCONTENTCELL_WIDGET)
							|| nodeVal.equals(MATRIXEXTRA_WIDGET) ) {
						matrixCells.add(node);
					} 
				}
			}
		}
		return matrixCells;		
	}

}
