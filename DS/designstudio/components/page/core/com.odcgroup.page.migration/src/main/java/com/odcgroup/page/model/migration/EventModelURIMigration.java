package com.odcgroup.page.model.migration;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Performs the migration of model references in event widget.<p>
 *
 * Entries similar to this
 * <pre>
 * &lt;events ...&gt;
 *    &ltparameters name="call-URI" value="resource:/a.b.c#xyz" .../&gt; 
 * &lt;/events&gt;
 * 
 * are replaced by 
 * 
 * &lt;events ...&gt;
 *    &lt;paramters name="call-URI" value="resource:///a.b.c#xyz" .../&gt; 
 * &lt;/properties&gt;

 * </pre>
 * @author atr
 */
public class EventModelURIMigration extends AbstractDOMModelMigration {
	
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
        if (node.getNodeName().equals("parameters") && node.getAttributes() != null) {
	        Node nameNode = node.getAttributes().getNamedItem("name");
	        if (nameNode != null && nameNode.getNodeValue().equals("call-URI")) {
		        Node valueNode = node.getAttributes().getNamedItem("value");
		        if (valueNode != null) {
   					String uri = valueNode.getNodeValue();
   					if (uri.startsWith("resource:")) {
   						String tmp = uri.substring("resource:".length());
						char[] chars = tmp.toCharArray();
						int k = 0;
						while (chars[k] == '/') {
							k++;
						}
						for (int j = 3-k; j > 0; j--) {
							tmp = "/"+tmp;
						}
						valueNode.setNodeValue("resource:"+tmp);
					}
        			// no need to migrate child nodes
        			return;
	        	}
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
	public EventModelURIMigration() {
	}

}
