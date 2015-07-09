package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Migration to remove sticky property from Matrix widgets
 * 
 * @author pkk
 *
 */
public class MatrixStickyFlagMigration extends AbstractPageModelMigration {
	
	private static final String MATRIX_WIDGET = "Matrix";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		List<Node> matrices = new ArrayList<Node>();
		collectAllMatrixNodes(modelNode, matrices);
		migrateMatrixProperties(matrices);
	}

	
	/**
	 * @param matrices
	 */
	private void migrateMatrixProperties(List<Node> matrices) {
		for (Node cell : matrices) {
			NodeList childNodes = cell.getChildNodes();
			Node node = null;
			for(int ii = 0; ii < childNodes.getLength();ii++) {
				node = childNodes.item(ii);
				if(PROPERTIES_ELEMENT.equals(node.getNodeName())) {
					Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeAttr != null && typeAttr.getNodeValue().equals("sticky")) {
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
	private List<Node> collectAllMatrixNodes(Node widget, List<Node> matrices) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String nodeVal = typeAttr.getNodeValue();
					if (isContainerWidgetType(typeAttr)) {
						collectAllMatrixNodes(node, matrices);
					}  else if (nodeVal.equals(MATRIX_WIDGET)) {
						matrices.add(node);
					} 
				}
			}
		}
		return matrices;	
	}

}
