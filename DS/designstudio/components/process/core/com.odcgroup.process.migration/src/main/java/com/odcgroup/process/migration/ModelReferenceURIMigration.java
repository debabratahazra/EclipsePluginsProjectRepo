package com.odcgroup.process.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Migration of model references in workflow models to make them compatible with
 * the new changes to the resource URI format
 * 
 * @author pkk
 * 
 */
public class ModelReferenceURIMigration extends AbstractDOMModelMigration {

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate
	 *      (com.odcgroup.workbench.core.IOfsProject,
	 *      org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		if (modelURI.fileExtension().equals("workflow")) {

			Element rootElement = document.getDocumentElement();
			NodeList list = rootElement.getElementsByTagName("tasks");
			if (list != null) {
				List<Node> pageflows = new ArrayList<Node>();
				for (int i = 0; i < list.getLength(); i++) {
					Node task = list.item(i);
					Node userTask = task.getAttributes().getNamedItem("xmi:type");
					if (userTask != null && userTask.getNodeValue().equals("process:UserTask")) {
						Node pageflow = getChildNode(task, "pageflow");
						if (pageflow != null) {
							pageflows.add(pageflow);
						}
					}
				}
				if (!pageflows.isEmpty()) {
					migratePageflows(pageflows);
				}
			}
		}

	}

	/**
	 * @param views
	 */
	private void migratePageflows(List<Node> pageflows) {
		for (Node node : pageflows) {
			Node urlNode = node.getAttributes().getNamedItem("URI");
			if (urlNode != null) {
				migrateReferenceURINode(urlNode);
			}
		}
	}

	/**
	 * @param node
	 */
	private void migrateReferenceURINode(Node node) {
		if (node != null) {
			String uri = node.getNodeValue();
			if (uri.startsWith("resource:")) {
				String tmp = uri.substring("resource:".length());
				char[] chars = tmp.toCharArray();
				int k = 0;
				while (chars[k] == '/') {
					k++;
				}
				for (int j = 3 - k; j > 0; j--) {
					tmp = "/" + tmp;
				}
				node.setNodeValue("resource:" + tmp);
			}
		}
	}

	/**
	 * @param viewstate
	 * @return
	 */
	private Node getChildNode(Node state, String viewType) {
		NodeList children = state.getChildNodes();
		Node child = null;
		for (int i = 0; i < children.getLength(); i++) {
			child = children.item(i);
			if (child.getNodeName().equals(viewType)) {
				return child;
			}
		}
		return null;
	}

}
