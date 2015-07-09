package com.odcgroup.page.migration.internal;

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
 * Warning, if you extends this class, you migration rule will be restricted 
 * to only Module, Fragment or Layer 
 */
public abstract class AbstractPageModelMigration extends AbstractDOMModelMigration {

	protected static final String MODEL_ELEMENT = "com.odcgroup.page.model:Model";	
	
	protected static final String ATTRIBUTE_LIBNAME = "libraryName";	
	protected static final String ATTRIBUTE_EVENTNAME = "eventName";	
	protected static final String ATTRIBUTE_TYPENAME = "typeName";	
	protected static final String ATTRIBUTE_VALUE = "value";
	
	protected static final String WIDGET_ELEMENT = "widget";
	protected static final String WIDGET_PAGE = "Page";
	protected static final String WIDGET_MODULE = "Module";
	protected static final String WIDGET_FRAGMENT = "Fragment";
	protected static final String WIDGET_LAYER = "Layer"; // special fragment
	protected static final String WIDGET_BOX = "Box";	
	protected static final String WIDGET_TABBEDPANE = "TabbedPane";	
	protected static final String WIDGET_TAB = "Tab";	
	protected static final String WIDGET_CONDITION = "Conditional";
	protected static final String WIDGET_CONDITION_BODY = "ConditionalBody";
	protected static final String WIDGET_GRID = "Grid";
	protected static final String WIDGET_GRIDCELL = "GridCell";
	protected static final String WIDGET_TABLE = "TableTree";
	protected static final String WIDGET_TABLECOL = "TableColumn";
	protected static final String WIDGET_MATRIX = "Matrix";
	protected static final String WIDGET_MATRIX_CONTCELL = "MatrixContentCell";

	protected static final String EVENTS_ELEMENT = "events";
	protected static final String CONTENTS_ELEMENT = "contents";
	protected static final String PROPERTIES_ELEMENT = "properties";	

	protected static final String TYPE_ID = "id";
	
	protected boolean allowPages = false;

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI,
			Document document) throws MigrationException {
		Element rootElement = document.getDocumentElement();
		// check the widget associated with root is module/fragment
		Node widgetNode = fetchModuleOrFragmentWidget(rootElement);
		if (widgetNode == null) {
			throw new MigrationException("Migration does not support '"+modelURI+"'");
		}		
		try {
			performMigration(ofsProject, modelURI, widgetNode);
		} catch(RuntimeException e) {
			e.printStackTrace();
			throw new MigrationException("Exception Migrating '"+modelURI.toString()+"'", e);
		}
	}
	
	/**
	 * @param ofsProject
	 * @param modelURI
	 * @param modelNode
	 */
	protected abstract void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode);
	
	/**
	 * fetch the module/fragment/layer widget from the model root, 
	 * return 'null' if other
	 * 
	 * @param rootElement
	 * @return
	 */
	private Node fetchModuleOrFragmentWidget(Element rootElement) {
		if (MODEL_ELEMENT.equals(rootElement.getNodeName())) {
			Node widgetNode = fetchWidgetNode(rootElement.getChildNodes());		
			if (widgetNode != null) {
				NamedNodeMap attributes = widgetNode.getAttributes();
				Node typeAttribute = attributes.getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttribute != null 
						&& ( WIDGET_MODULE.equals(typeAttribute.getNodeValue()) 
						|| WIDGET_FRAGMENT.equals(typeAttribute.getNodeValue())
						|| WIDGET_LAYER.equals(typeAttribute.getNodeValue())
						|| checkIfPagesAllowed(typeAttribute.getNodeValue()) )) {
					return widgetNode;
				}
			}
		}
		return null;
	}	
	
	/**
	 * @return
	 */
	private boolean checkIfPagesAllowed(String type) {
		if(allowPages && WIDGET_PAGE.equals(type)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param typeAttr
	 * @return
	 */
	protected boolean isContainerWidgetType(Node typeAttr) {
		if (typeAttr != null) {
			if (typeAttr.getNodeValue().equals(WIDGET_BOX) 
					|| typeAttr.getNodeValue().equals(WIDGET_GRID) 
					||typeAttr.getNodeValue().equals(WIDGET_GRIDCELL) 
					|| typeAttr.getNodeValue().equals(WIDGET_CONDITION) 
					||typeAttr.getNodeValue().equals(WIDGET_CONDITION_BODY) 
					|| typeAttr.getNodeValue().equals(WIDGET_TABLE) 
					||typeAttr.getNodeValue().equals(WIDGET_TABLECOL) 
					|| typeAttr.getNodeValue().equals(WIDGET_MATRIX) 
					||typeAttr.getNodeValue().equals(WIDGET_MATRIX_CONTCELL) 
					||typeAttr.getNodeValue().equals(WIDGET_TABBEDPANE) 
					||typeAttr.getNodeValue().equals(WIDGET_TAB)) {
				return true;
			}
		}
		return false;
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
