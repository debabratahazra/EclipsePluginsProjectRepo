package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Migrate the models with the cancel filterset property set to false
 * and update the level to 0 incase of level set to true
 *
 * @author pkk
 *
 */
public class FiltersetCancelPropertyMigration extends AbstractDOMModelMigration {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		List<Node> filterSets = new ArrayList<Node>();
		collectFilterSetSnippets(document, (Node) document, filterSets);
		migrateFilterSetCancel(filterSets);
	}
	

	
	/**
	 * @param nodes
	 */
	private void migrateFilterSetCancel(List<Node> nodes) {
		for (Node node : nodes) {
			NodeList list = node.getChildNodes();
			Node childNode = null;
			for (int i = 0; i < list.getLength(); i++) {
				childNode = list.item(i);
				if (childNode.getNodeName().equals("properties")) {
					Node typeAttrib = childNode.getAttributes().getNamedItem("typeName");
					if (typeAttrib.getNodeValue().equals("cancel")) {
						Node valueAttrib = childNode.getAttributes().getNamedItem("value");
						if (valueAttrib != null) {
							 String value = valueAttrib.getNodeValue();
							 if (value.equals("false")) {
								 valueAttrib.setNodeValue("true");
							 } else {
								 migrateLevelPropertyNode(list);
							 }								
						}
					}
				}
			}
		}
	}	
	
	/**
	 * @param list
	 * @return
	 */
	private void migrateLevelPropertyNode(NodeList list) {
		Node childNode = null;
		for (int i = 0; i < list.getLength(); i++) {
			childNode = list.item(i);
			if (childNode.getNodeName().equals("properties")) {
				Node typeAttrib = childNode.getAttributes().getNamedItem("typeName");
				if (typeAttrib.getNodeValue().equals("level")) {
					Node valueAttrib = childNode.getAttributes().getNamedItem("value");
					if (valueAttrib != null) {
						 valueAttrib.setNodeValue("0");
					}
				}
			}
		}
	}
	
	/**
	 * parse the document recursively and collect all the filterset occurances
	 * 
	 * @param document
	 * @param node
	 */
	private void collectFilterSetSnippets(Document document, Node node, List<Node> snippets) {
		
		if (node == null) {
			return;
		}
		
		if (node.getNodeName().equals("snippets") && node.getAttributes() != null) {
			Node typeName = node.getAttributes().getNamedItem("typeName");
			String type = typeName.getNodeValue();
			if ("FilterSet".equals(type)) {
				snippets.add(node);
			}
		} else {
			// look in children
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				collectFilterSetSnippets(document, list.item(i), snippets);
			}
		}

	}

}
