package com.odcgroup.page.model.migration;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Performs the migration of all model by removing the obsolete
 * "version" attribute on the root "model" element.<p>
 *
 * Entries similar to this
 * <pre>
 * &lt;com.odcgroup.page.model:Model version="..." ...&gt;
 *    ...
 * &lt;/com.odcgroup.page.model:Model&gt;
 * </pre>
 * are replaced by 
 * <pre>
 * &lt;com.odcgroup.page.model:Model ...&gt;
 *    ...
 * &lt;/com.odcgroup.page.model:Model&gt;

 * </pre>
 * @author atr
 */
public class RemoveVersionAttributeMigration extends AbstractDOMModelMigration {
	
	/**
	 * Walk recursively through the DOM tree and migrate specific node
	 * @param document the DOM tree
	 * @param node the node to be inspected
	 */
	private void migrate(Document document, Node node) {
		
		if (node == null) {
			return;
		}
		
        // look for "include source" and migrate
        if (node.getNodeName().equals("com.odcgroup.page.model:Model") && node.getAttributes() != null) {
	        Node versionNode = node.getAttributes().getNamedItem("version");
	        if (versionNode != null) {
	        	node.getAttributes().removeNamedItem("version");
	        	// no need to migrate child nodes
	        	return;
	        }
        }
        
        // migrate children
        NodeList list = node.getChildNodes();
        for(int i=0; i<list.getLength(); i++) {
        	migrate(document, list.item(i));
		}
        
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
	    migrate(document,(Node)document);
	}
	
	/**
	 * 
	 */
	public RemoveVersionAttributeMigration() {
	}

}
