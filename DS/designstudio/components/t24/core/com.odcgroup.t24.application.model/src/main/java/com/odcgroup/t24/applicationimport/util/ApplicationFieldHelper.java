package com.odcgroup.t24.applicationimport.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * Helper class with methods fetching fields from applications
 *
 * @author phanikumark
 *
 */
public class ApplicationFieldHelper {

	
	public static List<MdfProperty> getAllProperties(MdfClass clazz) {
    	List<MdfProperty> propertyList = new ArrayList<MdfProperty>();
    	List<MdfClass> clazzList = new ArrayList<MdfClass>();
    	clazzList.add(clazz);
    	getProperties(clazz, propertyList, clazzList);
    	return propertyList;
    }
	
	@SuppressWarnings("unchecked")
	private static void getProperties(MdfClass clazz, List<MdfProperty> propertyList, List<MdfClass> clazzList) {
		if (clazz != null) {
			List<Object> propertiesList = clazz.getProperties(true);
			for (Object property : propertiesList) {
				if (property instanceof MdfAttribute) {
					propertyList.add((MdfProperty) property);
				}
				if (property instanceof MdfAssociation) {
					MdfAssociation association = (MdfAssociation) property;
					MdfClass mdfClazz = (MdfClass) association.getType();
					if (mdfClazz == null) {
						return;
					}
					if (!clazzList.contains(mdfClazz) && association.getContainment() == MdfContainment.BY_VALUE) {
						clazzList.add(mdfClazz);
						getProperties((MdfClass) association.getType(), propertyList, clazzList);
					} else if (association.getContainment() == MdfContainment.BY_REFERENCE) {
						propertyList.add((MdfProperty) property);
					}
				}
			}
		}
	}

	/**
	 * This method returns an Extended application for a given application else
	 * it will return back the same given application.
	 * 
	 * @param mdfClass
	 * @return
	 */
	public static MdfClass getExtendedApplicationIfExists(MdfClass mdfClass) {
		Resource res = ((EObject) mdfClass).eResource();
		if (res != null) {
			String refDomiainName = "X_" + mdfClass.getName();
			MdfDomain domain = DomainRepositoryUtil.getMdfDomain(res, QualifiedName.create(refDomiainName));
			if (domain != null) {
				for (Object obj : domain.getClasses()) {
					MdfClass klass = (MdfClass) obj;
					if (klass.getName().equals(refDomiainName)) {
						return klass;
					}
				}
			}
			
		}
		return mdfClass;
	}
}
