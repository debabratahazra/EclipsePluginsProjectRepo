package com.odcgroup.page.model.util;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Utility methods that help parsing the domain model for Search 
 *
 * @author pkk
 *
 */
public class SearchDomainObjectUtil {
	
	/**
	 * @param datasetname
	 * @param ofsProject
	 * @return list
	 */
	public static List<DatasetAttribute> getDomainAttributes(String datasetname, IOfsProject ofsProject) {
		return getDomainAttributes(datasetname, null, ofsProject);
	}
	
	/**
	 * @param datasetname 
	 * @param mappings 
	 * @param ofsProject 
	 * @return list
	 */
	public static List<DatasetAttribute> getDomainAttributes(String datasetname, String[] mappings, IOfsProject ofsProject) {
		List<DatasetAttribute> attributes = new ArrayList<DatasetAttribute>();
		if (datasetname != null && ofsProject != null) {
			MdfName datasetMdfName = MdfNameFactory.createMdfName(datasetname);
			if(datasetMdfName!=null) {
				MdfDataset dataset = DomainRepository.getInstance(ofsProject).getDataset(datasetMdfName);
				if (dataset != null) {
					MdfClass baseClass = dataset.getBaseClass();
					for (Object obj : dataset.getProperties()) {
						if (obj instanceof MdfDatasetMappedProperty) {
							MdfDatasetMappedProperty property = (MdfDatasetMappedProperty) obj;
							DatasetAttribute dAttribute = getBaseClassProperty(baseClass, property.getName(), property.getPath(), mappings);
							if (dAttribute != null) {
								attributes.add(dAttribute);
							}
						} else if (obj instanceof MdfDatasetDerivedProperty) {
							MdfDatasetDerivedProperty calc = (MdfDatasetDerivedProperty) obj;
							DatasetAttribute dAttribute = new DatasetAttribute(calc.getType(), calc.getName(), calc.getName());
							attributes.add(dAttribute);
						}
					}
				}
			}
		}
		return attributes;
	}
	
	/**
	 * returns the base-class property (attributes, referenced attributes)
	 * 
	 * @param baseClass
	 * @param name 
	 * @param path
	 * @param mappings 
	 * @return Object
	 */
	private static DatasetAttribute getBaseClassProperty(MdfClass baseClass, String name, String path, String[] mappings) {
		int index = path.indexOf(".");
		MdfProperty property = null;
		if (index == -1) {
			property = baseClass.getProperty(path);
		} else {
			String associationName = path.substring(0, index);
			property = baseClass.getProperty(associationName);
			if (property instanceof MdfAssociation) {
				MdfAssociation assoc = (MdfAssociation) property;
				MdfClass type = (MdfClass) assoc.getType();
				return getBaseClassProperty(type, name, path.substring(index+1), mappings);
			} 
		}	
		
		DatasetAttribute attrib = null;
		if (property instanceof MdfAttribute) {
			MdfAttribute attribute = (MdfAttribute) property;
			attrib =  new DatasetAttribute(attribute.getType(), attribute.getName(), name);
			if (mappings != null) {
				for (String string : mappings) {
					if (name.equals(string)) {
						attrib.setMapped(true);
					}
				}
			}	
		}
		return attrib; 
	}

}
