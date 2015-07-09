package com.odcgroup.page.model.migration;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * DS-3887
 * 
 * @author pkk
 *
 */
public class ModuleNamePropertyMigration extends AbstractDOMModelMigration {
	
	protected static final String MODEL_ELEMENT = "com.odcgroup.page.model:Model";	
	protected static final String WIDGET_ELEMENT = "widget";
	protected static final String WIDGET_MODULE = "Module";
	protected static final String ATTRIBUTE_TYPENAME = "typeName";	
	protected static final String PROPERTIES_ELEMENT = "properties";

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		Element rootElement = document.getDocumentElement();
		// check the widget associated with root is module/fragment
		Node module = fetchModuleWidget(rootElement);
		if (module != null) {
			NodeList properties = module.getChildNodes();
			for (int jj = 0; jj < properties.getLength(); jj++) {
				Node property = properties.item(jj);
				if (PROPERTIES_ELEMENT.equals(property.getNodeName())) {
					Node typeName = property.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeName != null && "name".equals(typeName.getNodeValue())) {
						module.removeChild(property);
					}
				}
			}
		}
		
	}
	
	/**
	 * fetch the module widget from the model root, 
	 * return 'null' if other
	 * 
	 * @param rootElement
	 * @return
	 */
	private Node fetchModuleWidget(Element rootElement) {
		if (MODEL_ELEMENT.equals(rootElement.getNodeName())) {
			Node widgetNode = fetchWidgetNode(rootElement.getChildNodes());		
			if (widgetNode != null) {
				NamedNodeMap attributes = widgetNode.getAttributes();
				Node typeAttribute = attributes.getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttribute != null 
						&& ( WIDGET_MODULE.equals(typeAttribute.getNodeValue()))) {
					return widgetNode;
				}
			}
		}
		return null;
	}	
	
	/**
	 * @param childNodes
	 * @return
	 */
	private Node fetchWidgetNode(NodeList childNodes) {
		Node childNode = null;
		for (int ii = 0; ii < childNodes.getLength(); ii++) {
			childNode = childNodes.item(ii);
			if(WIDGET_ELEMENT.equals(childNode.getNodeName())) {
				return childNode;
			}
		}
		return null;
	}



}
