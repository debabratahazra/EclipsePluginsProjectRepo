package com.odcgroup.page.ui.properties.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;
import com.odcgroup.page.ui.util.DomainObjectUtils;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Utility methods that help parsing the domain model for ITable 
 *
 * @author pkk
 *
 */
public class TableDomainObjectUtil {
	
	/**
	 * list of dataset properties referenced from base-class attributes
	 * 
	 * @param table
	 * @return list
	 */
	public static List<MdfDatasetProperty> getDomainAttributes(IWidgetAdapter adapter) {
		MdfDataset dataset = DomainObjectUtils.getDataset(adapter.getWidget());		
		return getDomainAttributes(dataset);
	}
	
	/**
	 * @param element
	 * @return list
	 */
	public static List<MdfDatasetProperty> getDomainAttributes(MdfModelElement element) {
		List<MdfDatasetProperty> attributes = new ArrayList<MdfDatasetProperty>();
		if (element instanceof MdfDataset) {
			MdfDataset dataset = (MdfDataset) element;
			MdfClass baseClass = dataset.getBaseClass();
			for (Object obj : dataset.getProperties()) {
				if (obj instanceof MdfDatasetMappedProperty) {
					MdfDatasetMappedProperty property = (MdfDatasetMappedProperty) obj;
					Object object = getBaseClassProperty(baseClass, property.getPath());
					if (object instanceof MdfAttribute) {
						attributes.add(property);
					}
				}
			}
		}
		return attributes;
	}	
	
	/**
	 * @param ofsProject
	 * @param domainEntityName
	 * @return list
	 */
	public static List<MdfDatasetProperty> getDomainAttributes(IOfsProject ofsProject, String domainEntityName) {
		if (domainEntityName != null && ofsProject != null) {
			DomainRepository repository = DomainRepository.getInstance(ofsProject);
			MdfModelElement entity = repository.getEntity(MdfNameFactory.createMdfName(domainEntityName));
			return getDomainAttributes(entity);			
		}
		return Collections.emptyList();
	}
	
	/**
	 * returns the base-class property (attributes, referenced attributes)
	 * 
	 * @param baseClass
	 * @param path
	 * @return Object
	 */
	private static Object getBaseClassProperty(MdfClass baseClass, String path) {
		int index = path.indexOf(".");
		if (index == -1) {
			return baseClass.getProperty(path);
		} else {
			String associationName = path.substring(0, index);
			MdfProperty property = baseClass.getProperty(associationName);
			if (property instanceof MdfAssociation) {
				MdfAssociation assoc = (MdfAssociation) property;
				MdfClass type = (MdfClass) assoc.getType();
				return getBaseClassProperty(type, path.substring(index+1));
			}
		}
		return null;
	}
	
	/**
	 * @param adapter
	 * @return
	 */
	public static List<String> getDomainAttributeNames(IWidgetAdapter adapter) {
		List<MdfDatasetProperty> attributes = getDomainAttributes(adapter);
		List<String> names = new ArrayList<String>();
		for (MdfDatasetProperty mdfProperty : attributes) {
			names.add(mdfProperty.getName());
		}
		return names;		
	}
	
	/**
	 * @param attributes 
	 * @return list
	 */
	public static List<String> getDomainAttributeNames(List<MdfDatasetProperty> attributes) {
		List<String> names = new ArrayList<String>();
		for (MdfDatasetProperty mdfProperty : attributes) {
			names.add(mdfProperty.getName());
		}
		return names;
	}
	
}
