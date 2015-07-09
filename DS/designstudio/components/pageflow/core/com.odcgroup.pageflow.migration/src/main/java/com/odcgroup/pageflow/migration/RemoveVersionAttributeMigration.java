package com.odcgroup.pageflow.migration;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Performs the migration of the pageflow model by removing the obsolete
 * "version" attribute on the root "Pageflow" element.<p>
 *
 * Entries similar to this
 * <pre>
 * &lt;pageflow:Pageflow version="..." ...&gt;
 *    ...
 * &lt;/pageflow:Pageflow&gt;
 * </pre>
 * are replaced by 
 * <pre>
 * &lt;pageflow:Pageflow ...&gt;
 *    ...
 * &lt;/pageflow:Pageflow&gt;

 * </pre>
 * @author atr
 */
public class RemoveVersionAttributeMigration extends AbstractDOMModelMigration {
	
	/** */
	private static final String PAGEFLOW_ELEMENT = "pageflow:Pageflow";
	/** */
	private static final String VERSION_ATTRIBUTE = "version";
	
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
        if (node.getNodeName().equals(PAGEFLOW_ELEMENT) && node.getAttributes() != null) {
	        Node versionNode = node.getAttributes().getNamedItem(VERSION_ATTRIBUTE);
	        if (versionNode != null) {
	        	node.getAttributes().removeNamedItem(VERSION_ATTRIBUTE);
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
