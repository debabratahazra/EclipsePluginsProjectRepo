package com.odcgroup.page.model.migration;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Performs the migration of the includeSrc/model URI property.<p>
 *
 * entry similar to this
 * <pre>
 * &lt;properties xmi:id="..." value="" typeName="includeSrc" libraryName="..."&gt;
 *    &lt;model href="resource:/a.b.c#xyz"&gt; 
 * &lt;/properties&gt;
 * 
 * are replaced by 
 * 
 * &lt;properties xmi:id="..." value="" typeName="includeSrc" libraryName="..."&gt;
 *    &lt;model href="resource:///a.b.c#xyz"&gt; 
 * &lt;/properties&gt;

 * </pre>
 * @author atr
 */
public class IncludeSrcModelURIMigration extends AbstractDOMModelMigration {
	
	/** */
	private static String HREF_ATTRIBUTE = "href";
	/** */
	private static String INCLUDE_SOURCE_ATTRIBUTE = "includeSrc";
	/** */
	private static String TYPE_NAME_ATTRIBUTE = "typeName";
	/** */
	private static String MODEL_ELEMENT = "model";

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
        if (node.getAttributes() != null) {
	        Node typeName = node.getAttributes().getNamedItem(TYPE_NAME_ATTRIBUTE);
	        if (typeName != null) {
	        	String name = typeName.getNodeValue();
	        	if (INCLUDE_SOURCE_ATTRIBUTE.equals(name)) {
	        		NodeList list = node.getChildNodes();
	        		if (list != null) {
	        	        for(int i=0; i<list.getLength(); i++) {
		        			Node childNode = list.item(i);
		        			if (childNode.getNodeName().equals(MODEL_ELEMENT) && childNode.getAttributes() != null) {
		        				Node hrefNode = childNode.getAttributes().getNamedItem(HREF_ATTRIBUTE);
		        				if (hrefNode != null) {
		        					String uri = hrefNode.getNodeValue();
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
			        					hrefNode.setNodeValue("resource:"+tmp);
		        					}
		        				}
		        			}
		        			
	        	        }
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
	public IncludeSrcModelURIMigration() {
	}

}
