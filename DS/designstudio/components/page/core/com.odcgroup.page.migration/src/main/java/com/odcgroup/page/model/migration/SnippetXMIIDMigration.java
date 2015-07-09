package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Migration to replace duplicate XMI ID of snippets
 * DS-3012
 * 
 * @author pkk
 *
 */
public class SnippetXMIIDMigration extends AbstractDOMModelMigration {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject, 
	 * org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		Map<String, List<Node>> filterSets = new HashMap<String, List<Node>>();
		collectFilterSetSnippets(document, (Node) document, filterSets);	
		Set<String> keys = filterSets.keySet();
		for (String key : keys) {
			List<Node> nodes = filterSets.get(key);
			if (nodes.size() > 1) {
				migrateFilterSetId(nodes);
			}
		}
	}
	
	/**
	 * @param nodes
	 */
	private void migrateFilterSetId(List<Node> nodes) {
		for (Node node : nodes) {
			if(nodes.indexOf(node) > 0) {
				String uuid = EcoreUtil.generateUUID();
				node.getAttributes().getNamedItem("xmi:id").setNodeValue(uuid);
			}
		}
	}	
	
	/**
	 * parse the document recursively and collect all the filterset occurances
	 * 
	 * @param document
	 * @param node
	 */
	private void collectFilterSetSnippets(Document document, Node node, Map<String, List<Node>> map) {
		
		if (node == null) {
			return;
		}
		
		if (node.getNodeName().equals("snippets") && node.getAttributes() != null) {
			Node typeName = node.getAttributes().getNamedItem("typeName");
			String type = typeName.getNodeValue();
			if ("FilterSet".equals(type)) {
				String xmiId = node.getAttributes().getNamedItem("xmi:id").getNodeValue();
				if (map.containsKey(xmiId)) {
					map.get(xmiId).add(node);
				} else {
					List<Node> list = new ArrayList<Node>();
					list.add(node);
					map.put(xmiId, list);
				}
			}
		} else {
			// look in children
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				collectFilterSetSnippets(document, list.item(i), map);
			}
		}

	}

}
