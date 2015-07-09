package com.odcgroup.page.model.migration;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Performs the migration of the includeSrc property
 *
 * @author atr
 */
public class IncludeSrcPropertyMigration extends AbstractDOMModelMigration {
	
	/** */
	private static String HREF_ATTRIBUTE = "href";
	/** */
	private static String INCLUDE_SOURCE_ATTRIBUTE = "includeSrc";
	/** */
	private static String TYPE_NAME_ATTRIBUTE = "typeName";
	/** */
	private static String VALUE_ATTRIBUTE = "value";
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
	        		if (list == null || list.getLength() == 0) {
	        			// create the child "model" and set its attribute
	        			Element model = document.createElement(MODEL_ELEMENT);
	        			Node value = node.getAttributes().getNamedItem(VALUE_ATTRIBUTE);
	        			String modelRef = value.getNodeValue();
	        			value.setNodeValue("");
	        			URI uri = ModelURIConverter.createModelURI(modelRef);
	        			Attr href = document.createAttribute(HREF_ATTRIBUTE);
	        			href.setNodeValue(uri.toString());
	        			model.setAttributeNode(href);
	        			node.appendChild(model);
	        			// return no need to migrate children
	        			return;
	        		}
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
	public IncludeSrcPropertyMigration() {
	}

}
