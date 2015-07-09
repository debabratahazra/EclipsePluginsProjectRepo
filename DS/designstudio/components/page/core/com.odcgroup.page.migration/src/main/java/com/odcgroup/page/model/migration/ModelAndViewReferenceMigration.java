package com.odcgroup.page.model.migration;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * @author atr
 */
public class ModelAndViewReferenceMigration extends AbstractDOMModelMigration {

	private final String ATTRIBUTE_LIBNAME = "libraryName";	
	private final String ATTRIBUTE_TYPENAME = "typeName";	
	private final String ATTRIBUTE_VALUE = "value";
	private final String ATTRIBUTE_XMIID = "xmi:id";
	
	private final String WIDGET_ELEMENT = "widget";
	private final String CONTENTS_ELEMENT = "contents";
	private final String PROPERTIES_ELEMENT = "properties";
	
	
	private String dataset = "";
	private String reference = "";
	
	
	private Node createProperty(Document document, String propertyName, String propertyValue) {
		
		// create a property node
		Element property = document.createElement(PROPERTIES_ELEMENT);
		
		// set xmi:id
		{
		Attr xmiID= document.createAttribute(ATTRIBUTE_XMIID);
		xmiID.setNodeValue(EcoreUtil.generateUUID());
		property.setAttributeNode(xmiID);
		}
		
		// set typeName
		{
		Attr typeName= document.createAttribute(ATTRIBUTE_TYPENAME);
		typeName.setNodeValue(propertyName);
		property.setAttributeNode(typeName);
		}

		// set value
		{
		Attr value= document.createAttribute(ATTRIBUTE_VALUE);
		value.setNodeValue(propertyValue);
		property.setAttributeNode(value);
		}

		// set library
		{
		Attr lib = document.createAttribute(ATTRIBUTE_LIBNAME);
		lib.setNodeValue("xgui");
		property.setAttributeNode(lib);
		}
		
		return property;

	}

	
	/**
	 * @param wType the widget type
	 * @param widgetNode the widget node
	 */
	private void checkModelAndViewReferences(WidgetType wType, Node widgetNode) {
		
		String typeName = wType.getName();
		Document document = widgetNode.getOwnerDocument();
		
		NodeList childNodes = widgetNode.getChildNodes();
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			Node node = childNodes.item(ii);
			if (PROPERTIES_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String typeAttrName = typeAttr.getNodeValue();
					if (typeAttrName == null) {
						typeAttrName = "";
					}
					
					if ( "domainentity".equals(typeAttrName.toLowerCase())) {
						// lire la valeur de l'id, et générer une valeur s'il ne peut pas être vide.
						Node idAttr = node.getAttributes().getNamedItem(ATTRIBUTE_VALUE);
						if (idAttr != null) {
							dataset = idAttr.getNodeValue();
							if (StringUtils.isNotBlank(dataset)) {
								reference = "DSTableModel." + dataset.replaceAll(":",".");
							}
						}
						
					} else if ( "view-ref".equals(typeAttrName.toLowerCase())) {

						if (WidgetTypeConstants.TABLE_TREE.equals(typeName)) {
							// replace 'view-ref' by 'table-view-reference'
							String newValue = "";
							Node attr = node.getAttributes().getNamedItem(ATTRIBUTE_VALUE);
							if (attr != null) {
								String oldValue = attr.getNodeValue();
								if (oldValue != null && !oldValue.startsWith(reference+".")) {
									newValue = oldValue;
								}
							}
							Node newNode = createProperty(document,"table-view-reference",newValue);
							widgetNode.replaceChild(newNode, node);
						}
						
					} else if ( "table-model-reference".equals(typeAttrName.toLowerCase())) {

						if (WidgetTypeConstants.MATRIX.equals(typeName)) {
							
							// replace 'table-model-reference' by 'matrix-model-reference'
							String newValue = "";
							Node attr = node.getAttributes().getNamedItem(ATTRIBUTE_VALUE);
							if (attr != null) {
								String oldValue = attr.getNodeValue();
								if (oldValue != null && !oldValue.equals(reference)) {
									newValue = oldValue;
								}
							}
							Node newNode = createProperty(document,"matrix-model-reference", newValue);
							widgetNode.replaceChild(newNode, node);
						
						} else if (WidgetTypeConstants.TABLE_TREE.equals(typeName)) {
							
							// normalize the table-model-ref's value
							String newValue = "";
							Node attr = node.getAttributes().getNamedItem(ATTRIBUTE_VALUE);
							if (attr != null) {
								String oldValue = attr.getNodeValue();
								if (oldValue != null) {
									if (!oldValue.equals("domainEntity") && !oldValue.equals(reference)) {
										newValue = oldValue;
									}
								}
							}
							attr.setNodeValue(newValue);
						}
					}
				}
			}
		}
		
	}	
	
	
	private void migrateModelAndViewReferences(Node modelNode) {
		
		if (modelNode == null) {
			return;
		}
		
		NodeList childNodes = modelNode.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName()) || WIDGET_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					String typeName = typeAttr.getNodeValue();
					Node libAttr = node.getAttributes().getNamedItem(ATTRIBUTE_LIBNAME);
					String libName = (libAttr != null) ? libAttr.getNodeValue() : "xgui";
					WidgetType wType = MetaModelRegistry.getMetaModel().findWidgetType(libName, typeName);
					if (wType != null) {
						checkModelAndViewReferences(wType, node);
					}
				}
			}

		}
		
        // migrate children
        NodeList list = modelNode.getChildNodes();
        for(int i=0; i<list.getLength(); i++) {
        	migrateModelAndViewReferences(list.item(i));
		}		
		
		
		
	}	
	
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		migrateModelAndViewReferences(document);	
	}

}
