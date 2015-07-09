package com.odcgroup.page.model.migration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Performs the migration of the icon property to the image property
 *
 * @author atr
 */
public class ImagePropertyMigration extends AbstractDOMModelMigration {

	/** */
	private static String DISABLED_ICON_ATTRIBUTE = "disabledIcon";
	/** */
	private static String ICON_ATTRIBUTE = "icon";
	/** */
	private static String TYPE_NAME_ATTRIBUTE = "typeName";
	/** */
	private static String VALUE_ATTRIBUTE = "value";
	
	/** 
	 * Key: Image Data Value
	 * Value: Image file name
	 */ 
	private Map<String, String> imageMap = new HashMap<String, String>();
	
	/**
	 * Walk recursively through the DOM tree and migrate specific node
	 * @param document the DOM tree
	 * @param node the node to be inspected
	 */
	private void migrate(Document document, Node node) {
		
		if (node == null) {
			return;
		}
		
        // look for "icon property" and migrate
        if (node.getAttributes() != null) {
	        Node typeName = node.getAttributes().getNamedItem(TYPE_NAME_ATTRIBUTE);
	        if (typeName != null) {
	        	String name = typeName.getNodeValue();
	        	if (ICON_ATTRIBUTE.equals(name) || DISABLED_ICON_ATTRIBUTE.equals(name)) {
        			Node value = node.getAttributes().getNamedItem(VALUE_ATTRIBUTE);
        			String imgName = value.getNodeValue();
        			String newValue = imageMap.get(imgName);
        			value.setNodeValue(newValue != null ? newValue : "");
        			// return no need to migrate children
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
	public ImagePropertyMigration() {
		imageMap.put("fastReverse", "5421-g.gif");
		imageMap.put("reverse", "5422-g.gif");
		imageMap.put("forward", "5423-g.gif");
		imageMap.put("fastForward", "5424-g.gif");
		imageMap.put("magnifyingGlass", "8710.gif");
		imageMap.put("excel", "excel.gif");
		imageMap.put("pdf", "8393_pdf.gif");
		imageMap.put("print", "8393.gif");
	}

}
