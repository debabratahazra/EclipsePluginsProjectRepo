package com.odcgroup.page.model.migration;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * DS-2882 - migration of property "bean-name" and "bean-define"
 *
 * @author atr
 */
public class BeanNameAndBeanDefineMigration extends AbstractPageModelMigration {
	
	private static final String DOMAIN_ENTITY = "domainEntity";
	private static final String BEAN_NAME = "bean-name";
	private static final String BEAN_DEFINE = "bean-define";

	/**
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		
		String dataset = null;
		Node beanNameNode = null;
		Node beanDefineNode = null;
		
		// first. collect meaningful attributes
		NodeList childNodes = modelNode.getChildNodes();
		for (int ii = 0; ii < childNodes.getLength(); ii++) {
			
			Node child = childNodes.item(ii);
			if (PROPERTIES_ELEMENT.equals(child.getNodeName())) {
				
				Node typeAttr = child.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {

					// retrieve the name of the dataset
					if (typeAttr.getNodeValue().equals(DOMAIN_ENTITY)) {
						Node valueAttr = child.getAttributes().getNamedItem(ATTRIBUTE_VALUE);
						String value = valueAttr.getNodeValue();
						if (StringUtils.isNotEmpty(value)) {
							dataset = value.trim();
						}

					// retrieve the value of the property bean-name
					} else if (typeAttr.getNodeValue().equals(BEAN_NAME)) {
						beanNameNode = child.getAttributes().getNamedItem(ATTRIBUTE_VALUE);

					// retrieve the value of the property bean-define
					} else if (typeAttr.getNodeValue().equals(BEAN_DEFINE)) {
						beanDefineNode = child.getAttributes().getNamedItem(ATTRIBUTE_VALUE);
					}
				}
				
			}
		} // for
		
		if (dataset != null) {
			MdfName qName = MdfNameFactory.createMdfName(dataset);
			String domain = qName.getDomain();
			String datasetname = qName.getLocalName();
			
			if ("CDMAPP".equals(domain)) {
				// CDM specific migration rule (DS-2955)
				if (beanNameNode != null) {
					beanNameNode.setNodeValue(datasetname.toLowerCase());
				}
				if (beanDefineNode != null) {
					beanDefineNode.setNodeValue(datasetname.toLowerCase());
				}
			} else {
				if (beanNameNode != null) {
					beanNameNode.setNodeValue(datasetname);
				}
				if (beanDefineNode != null) {
					beanDefineNode.setNodeValue("DSBean."+domain+"."+datasetname);
				}
			}
		}
		
	}

}
