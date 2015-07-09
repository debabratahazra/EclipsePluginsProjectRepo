package com.odcgroup.page.model.migration;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * For each Widget bound to an dataset attribute whose primitive type is
 * BusinessType#Percent a migration is required only if the following conditions
 * are met 
 * 
 * <pre>
 * 
 * condition met iff = (1 AND ((2 & 3) OR (4 & 5)):

 * 1. attributes "domainAttribute", "format" and "type" exists and are not empty
 *    attribute "domainEntity" of the root widget must be set to a dataset
 *    attribute "domainAttribute" must be set to an attribute of the dataset
 * 
 * 2. the value of the attribute "format" is "percent" or starts with "percent."
 * 3. the value of the attribute "type" is "percent"
 * 4. the value of the attribute "format" is "percent" or start with "percent"
 * 5. the value of the attribute "type"  is "percentTA"
 * 
 * </pre>
 * 
 * After the migration 
 * 
 * the value of the attribute "type" must be "percent"
 * the value of the attribute "format" must be "percentTA" / "percent" or start with "percentTA." / "percent."
 * 
 * @author atr
 * 
 */
public class BusinessTypePercentToPercentTAMigration extends AbstractPageModelMigration {

	private static final String PROPERTY_VALUE_ATTR = "value";

	private static final String DOMAIN_ENTITY = "domainEntity";
	private static final String DOMAIN_ATTRIBUTE = "domainAttribute";
	private static final String FORMAT = "format";
	private static final String TYPE = "type";
	
	protected boolean isContainer(Node typeAttr) {
		String name = typeAttr.getNodeValue();
		return name.equals(WIDGET_BOX)
			|| name.equals("Grid")
			|| name.equals("GridCell")
			|| name.equals("TabbedPane")
			|| name.equals("Tab")
			|| name.equals("TableTree")
			|| name.equals("TableColumn")
			|| name.equals("Conditional")
			|| name.equals("ConditionalBody"); 
	}		
	
	private void migrateWidget(Node node, MdfDataset dataset) {

		Node attributeNode = null;
		MdfDatasetProperty domainAttribute = null;

		Node formatNode = null;
		String formatValue = null;

		Node typeNode = null;
		String typeValue = null;
		
		// first. collect meaningful attributes
		NodeList childNodes = node.getChildNodes();
		Node child = null;
		for (int ii = 0; ii < childNodes.getLength(); ii++) {
			child = childNodes.item(ii);
			if (PROPERTIES_ELEMENT.equals(child.getNodeName())) {
				Node typeAttr = child.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {

					// retrieve domainAttribute
					if (typeAttr.getNodeValue().equals(DOMAIN_ATTRIBUTE)) {
						Node valueAttr = child.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
						String value = valueAttr.getNodeValue();
						if (StringUtils.isNotEmpty(value)) {
							domainAttribute = dataset.getProperty(value.trim());
							if (domainAttribute != null) {
								MdfEntity mdfType = domainAttribute.getType();
								if (mdfType instanceof MdfBusinessType 
										&& ((MdfBusinessType)mdfType).getName().equals("Percent")) {
									attributeNode = child;
								} else {
									domainAttribute = null;
								}
							}
						}

					// retrieve format
					} else if (typeAttr.getNodeValue().equals(FORMAT)) {
						Node valueAttr = child.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
						String value = valueAttr.getNodeValue();
						if (StringUtils.isNotEmpty(value)) {
							formatValue = value.trim();
							formatNode = valueAttr;
						}

					// retrieve type
					} else if (typeAttr.getNodeValue().equals(TYPE)) {
						Node valueAttr = child.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
						String value = valueAttr.getNodeValue();
						if (StringUtils.isNotEmpty(value)) {
							typeValue = value.trim();
							typeNode = valueAttr;
						}
					}
				}
				
			}
		}
		
		Node attr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
		if (attr != null && attr.getNodeValue().equals("TableColumn")) {
			// special case for TableColumn widget
			if (formatNode != null) {
				if (!formatValue.startsWith("percentTA") && formatValue.startsWith("percent")) {
					String newValue = formatValue.replace("percent","percentTA");
					formatNode.setNodeValue(newValue);
				}
			}
		}
		
		else {
			if (attributeNode != null && formatNode != null && typeNode != null) {
				if (typeValue.equals("percent")) {
					if (!formatValue.startsWith("percentTA") && formatValue.startsWith("percent")) {
						String newValue = formatValue.replace("percent","percentTA");
						formatNode.setNodeValue(newValue);
					}
				} else if (typeValue.equals("percentTA")) {
					typeNode.setNodeValue("percent");
					if (!formatValue.startsWith("percentTA") && formatValue.startsWith("percent")) {
						String newValue = formatValue.replace("percent","percentTA");
						formatNode.setNodeValue(newValue);
					}
				}
			}
		}
		
	}
	
	private void performWidgetsMigration(Node widget, MdfDataset dataset) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for (int ii = 0; ii < childNodes.getLength(); ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (isContainer(typeAttr)) {
						if (typeAttr.getNodeValue().equals("TableColumn")) {
							migrateWidget(node, dataset);
						}
						performWidgetsMigration(node, dataset);
					} else {
						migrateWidget(node, dataset);
					}
				}
			}
		}
	}
	
	private MdfDataset getDataset(IOfsProject ofsProject, Node modelNode) {
		MdfDataset dataset = null;
		NodeList childNodes = modelNode.getChildNodes();
		Node node = null;
		for (int ix = 0; ix < childNodes.getLength(); ix++) {
			node = childNodes.item(ix);
			if (PROPERTIES_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (typeAttr.getNodeValue().equals(DOMAIN_ENTITY)) {
						Node valueAttr = node.getAttributes().getNamedItem(PROPERTY_VALUE_ATTR);
						if (valueAttr != null) {
							String value = valueAttr.getNodeValue();
							MdfName qName = MdfNameFactory.createMdfName(value); 
							DomainRepository repository = DomainRepository.getInstance(ofsProject);
							dataset = repository.getDataset(qName);
							break;
						}
					}
				}
			}
		}
		return dataset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.odcgroup.page.migration.internal.AbstractPageModelMigration#
	 * performMigration(com.odcgroup.workbench.core.IOfsProject,
	 * org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		MdfDataset dataset = getDataset(ofsProject, modelNode);
		if (dataset != null) {
			performWidgetsMigration(modelNode, dataset);
		}
	}

	public BusinessTypePercentToPercentTAMigration() {
	}

}
