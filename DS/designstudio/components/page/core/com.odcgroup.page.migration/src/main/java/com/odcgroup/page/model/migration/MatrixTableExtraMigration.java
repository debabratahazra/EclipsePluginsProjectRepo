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
 *
 * @author pkk
 *
 */
public class MatrixTableExtraMigration extends AbstractPageModelMigration {
	
	private static final String MATRIX_WIDGET = "Matrix";
	private static final String TABLE_EXTRA = "TableExtra";

	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		Document document = modelNode.getOwnerDocument();
		List<Node> matrices = new ArrayList<Node>();
		collectAllMatrixNodes(modelNode, matrices);
		migrateMatrixTableExtra(matrices, document);
	}

	
	/**
	 * @param matrices
	 * @param document
	 */
	private void migrateMatrixTableExtra(List<Node> matrices, Document document) {
		List<Node> extras = collectTableExtraNodes(matrices);
		for (Node extra : extras) {
			extra.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME).setNodeValue("MatrixExtra");

			Element property =  document.createElement(PROPERTIES_ELEMENT);
			property.setAttribute(ATTRIBUTE_TYPENAME, "aggregationType");
			property.setAttribute("value", "sum");
			String uuid = EcoreUtil.generateUUID();
			property.setAttribute("xmi:id", uuid);
			extra.appendChild(property);
			
		}
	}
	
	/**
	 * @param widget
	 * @param extras
	 * @return
	 */
	private List<Node> collectTableExtraNodes(List<Node> matrices) {
		List<Node> extras = new ArrayList<Node>();
		for (Node node : matrices) {
			NodeList list = node.getChildNodes();
			Node child = null;
			for (int i = 0; i < list.getLength(); i++) {
				child = list.item(i);
				if (CONTENTS_ELEMENT.equals(child.getNodeName())) {
					Node typeAttr = child.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeAttr != null) {
						String nodeVal = typeAttr.getNodeValue();
						 if (nodeVal.equals(TABLE_EXTRA)) {
								extras.add(child);
						} 
					}
				}	
				
			}		
		}
		return extras;	
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
