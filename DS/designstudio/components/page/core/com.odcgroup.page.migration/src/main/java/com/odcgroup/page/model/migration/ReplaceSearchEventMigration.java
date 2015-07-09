package com.odcgroup.page.model.migration;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 *
 * @author pkk
 *
 */
public class ReplaceSearchEventMigration extends AbstractDOMModelMigration {

	/**
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		migrate(document, (Node) document);		
	}
	
	/**
	 * Walk recursively through the DOM tree and migrate specific node
	 * 
	 * @param document
	 *            the DOM tree
	 * @param node
	 *            the node to be inspected
	 */
	private void migrate(Document document, Node node) {

		if (node == null) {
			return;
		}
		// look for "event" nodes and try to migrate
		if (node.getNodeName().equals("events") && node.getAttributes() != null) {
			Node natureNode = node.getAttributes().getNamedItem("nature");
			if (natureNode != null) {
				String value = natureNode.getNodeValue();
				if ("SEARCH".equals(value)) {
					natureNode.setNodeValue("SIMPLIFIED");					
				}
			}
		} else {
			// migrate children
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				migrate(document, list.item(i));
			}
		}

	}


}
