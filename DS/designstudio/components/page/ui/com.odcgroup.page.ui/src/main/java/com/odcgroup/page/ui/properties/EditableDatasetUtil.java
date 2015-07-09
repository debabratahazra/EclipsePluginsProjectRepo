package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class EditableDatasetUtil {
	
	/**
	 * @param property
	 * @param entityName
	 * @return
	 */
	public static boolean entityHasSQLNameAnnotation(Property property, String entityName) {
		IOfsProject project = OfsResourceHelper.getOfsProject(property.eResource());
		DomainRepository repository = DomainRepository.getInstance(project);
		MdfName qname = MdfNameFactory.createMdfName(entityName);
		MdfDataset ds = repository.getDataset(qname);
		if (ds != null) {
			MdfClass bc = ds.getBaseClass();
			if (bc != null) {
				String sqlName = AAAAspect.getTripleAEntitySQLName(bc);
				if (!StringUtils.isEmpty(sqlName)) {
					return true;
				}
			}
		}
		return false;
	}

}
