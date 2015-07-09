package com.odcgroup.page.model.migration;

import java.util.StringTokenizer;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.page.mdf.DomainConstants;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Migrate the mandatory label property to the required property.
 * Doing that, the same logic that in the drag and drop operation will
 * be applied (if the referenced property is required, so will be the label)
 * @author yan
 */
public class PageRequiredMigration extends AbstractDOMModelMigration {

	private static String PROPERTIES_NODE = "properties";
	
	private static String TYPE_NAME_ATTRIBUTE = "typeName";
	
	private static String VALUE_ATTRIBUTE = "value";
	
	private static String REQUIRED_VALUE = "required";
	
	private static String MANDATORY_VALUE = "mandatory";
	
	private static String DOMAIN_ENTITY_VALUE = "domainEntity";
	
	private static String DOMAIN_ATTRIBUTE_VALUE = "domainAttribute";
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		MdfDataset dataset = findDataset(ofsProject, document);
		migrate(document, dataset);
	}

	/**
	 * Retrieve the dataset used by this module (recursive algorithm)
	 * @param document xml tree of the document to migrate
	 * @return the associated dataset
	 */
	private MdfDataset findDataset(IOfsProject ofsProject, Node node) {
		if (node == null) {
			return null;
		}

	    // <properties libraryName="xgui" typeName="domainEntity" value="NewDomain:NewDataset1" xmi:id="_Uk6as9QyEd6K--yGnNIqMw"/>
        if (node.getAttributes() != null) {
        	if (PROPERTIES_NODE.equals(node.getNodeName())) {
        		Node typeName = node.getAttributes().getNamedItem(TYPE_NAME_ATTRIBUTE);
    	        if (typeName != null) {
    	        	if (DOMAIN_ENTITY_VALUE.equals(typeName.getNodeValue())) {
    	        		Node value = node.getAttributes().getNamedItem(VALUE_ATTRIBUTE);
    	        		if (value != null) {
        	        		String domainEntity = value.getNodeValue();
        	        		DomainRepository repository = DomainRepository.getInstance(ofsProject);
    	        			StringTokenizer st = new StringTokenizer(domainEntity, ":"); 
    	        			if (st.countTokens() == 2) {
        	        			String domainName = st.nextToken();
        	        			String entityName = st.nextToken();
        	        			MdfDomain domain = repository.getDomain(domainName);
        	        			if (domain != null) {
            	        			MdfEntity entity = domain.getEntity(entityName);
            	        			if (entity instanceof MdfDataset) {
            	        				return (MdfDataset)entity;
            	        			}
        	        			}
    	        			}
    	        		}
    	        	}    	        		
    	        }
    	        
        	}
        }

        // migrate children
        NodeList list = node.getChildNodes();
        for(int i=0; i<list.getLength(); i++) {
        	MdfDataset dataset = findDataset(ofsProject, list.item(i));
        	if (dataset != null) {
        		return dataset;
        	}
		}

		return null;
	}

	/**
	 * Walk recursively through the DOM tree and migrate specific node
	 * @param document the DOM tree
	 * @param node the node to be inspected
	 */
	private void migrate(Node node, MdfDataset dataset) {
		if (node == null) {
			return;
		}
		
        // look for "mandatory" typeName and migrate to "required"
        if (node.getAttributes() != null) {
        	if (PROPERTIES_NODE.equals(node.getNodeName())) {
        		Node typeName = node.getAttributes().getNamedItem(TYPE_NAME_ATTRIBUTE);
    	        if (typeName != null) {
    	        	if (MANDATORY_VALUE.equals(typeName.getNodeValue())) {
    	        		typeName.setNodeValue(REQUIRED_VALUE);
    	        		// Re-apply the same logic that in the drag and drop in the UI
    	        		// (only if a dataset is associated to the page)
    	        		if (dataset != null) {
        	    	        Node value = node.getAttributes().getNamedItem(VALUE_ATTRIBUTE);
        	    	        if (value != null && value.getNodeName().length() != 0) {
        	    	        	// Retrieve the dataset property used by this label
        	    	        	String domainAttribute = findDomainAttribute(node.getParentNode());
        	    	        	MdfDatasetProperty datasetProperty = dataset.getProperty(domainAttribute);
        	    	        	if (datasetProperty instanceof MdfDatasetMappedProperty) {
        	    	        		MdfDatasetMappedProperty mappedProperty = (MdfDatasetMappedProperty)datasetProperty;
        	    	        		// Retreive the property referenced by the dataset property 
        	    	        		MdfProperty property = findProperty(mappedProperty);
        	    	        		if (property != null) {
        	    	    	        	value.setNodeValue(String.valueOf(property.isRequired()));
        	    	        		}
        	    	        	}
        	    	        }
    	        		}
    	        	}    	        		
    	        }
        	}
        }
        
        // migrate children
        NodeList list = node.getChildNodes();
        for(int i=0; i<list.getLength(); i++) {
        	migrate(list.item(i), dataset);
		}
	}

	/**
	 * Find the domain attribute as a direct child of the node. Return null if none.
	 * @param parentNode to inspect
	 * @return Find the domain attribute as a direct child of the node. Return null if none.
	 */
	private String findDomainAttribute(Node parentNode) {
		NodeList list = parentNode.getChildNodes();
        for(int i=0; i<list.getLength(); i++) {
        	Node node = list.item(i);
        	if (PROPERTIES_NODE.equals(node.getNodeName())) {
            	Node typeName = node.getAttributes().getNamedItem(TYPE_NAME_ATTRIBUTE);
            	if (typeName != null) {
                	if (DOMAIN_ATTRIBUTE_VALUE.equals(typeName.getNodeValue())) {
                		Node value = node.getAttributes().getNamedItem(VALUE_ATTRIBUTE);
                		return value.getNodeValue();
                	}
            	}
        	}
		}
        return null;
	}

	/**
	 * Find the referenced property from a dataset mapped property 
	 * @param dmp dataset mapped property
	 * @return the referenced property from a dataset mapped property. Null if not found.
	 */
	private MdfProperty findProperty(MdfDatasetMappedProperty dmp) {
		MdfClass clazz = dmp.getParentDataset().getBaseClass();
		String path = dmp.getPath();
		StringTokenizer st = new StringTokenizer(path, DomainConstants.DATASET_PROPERTY_PATH_SEPARATOR);
		MdfProperty property = null;
		while (st.hasMoreTokens()) {
			String subPath = st.nextToken();
			if (clazz == null) {
				return null; // Invalid path
			}
			property = clazz.getProperty(subPath);
			if (property instanceof MdfAssociation || 
					property instanceof MdfReverseAssociation) {
				clazz = (MdfClass)property.getType();
			} else {
				clazz = null;
			}
		}
		return property;
	}

}
