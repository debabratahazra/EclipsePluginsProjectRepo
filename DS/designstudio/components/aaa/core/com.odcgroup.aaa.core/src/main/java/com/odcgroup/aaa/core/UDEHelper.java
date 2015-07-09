package com.odcgroup.aaa.core;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;

public class UDEHelper {

	private static final String RIGHT_BRACKET = "]";
	private static final String LEFT_BRACKET = "[";
	static UDEHelper UDE_HELPER = new UDEHelper();

	public static UDEHelper getInstance() {
		return UDE_HELPER;
	}
	
	public boolean isUDEModel(MdfModelElement model) {
		MdfDomain domain = getMdfDomain(model);
		if (domain != null && AAAAspect.isTripleAUDEntities(domain)) {
			return true;
		} else {
			return false;
		}
	}

	private MdfDomain getMdfDomain(MdfModelElement model) {
		if (model instanceof MdfDomain) {
			return (MdfDomain)model;
		} else if (model instanceof MdfReverseAssociation) {
			return ((MdfReverseAssociation)model).getAssociation().getParentClass().getParentDomain();
		} else if (model instanceof MdfEntity) {
			return ((MdfEntity)model).getParentDomain();
		} else if (model instanceof MdfProperty) {
			return ((MdfProperty)model).getParentClass().getParentDomain();
		} else if (model instanceof MdfDatasetProperty) {
			return ((MdfDatasetProperty)model).getParentDataset().getParentDomain();
		} else if (model instanceof MdfEnumValue) {
			return ((MdfEnumValue)model).getParentEnumeration().getParentDomain();
		}
		return null;
	}

	public MdfClass findMdfClass(MdfModelElement model, String path) {
		MdfName mdfName = MdfNameFactory.createMdfName(path);
		if (mdfName == null) {
			return null;
		}
		String domain = mdfName.getDomain();
		String className = StringUtils.substringBefore(mdfName.getLocalName(), "#");
		MdfName classMdfName = MdfNameFactory.createMdfName(domain + ":" + className);
		MdfEntity entity = MdfEcoreUtil.getMdfEntity(((EObject)model).eResource(), classMdfName);
		if (entity != null && entity instanceof MdfClass) {
			return (MdfClass)entity;
		} else {
			return null;
		}
	}
	
	public MdfEnumeration findMdfEnum(MdfModelElement model, String path) {
		MdfName mdfName = MdfNameFactory.createMdfName(path);
		if (mdfName == null) {
			return null;
		}
		MdfEntity entity = MdfEcoreUtil.getMdfEntity(((EObject)model).eResource(), mdfName);
		if (entity != null && entity instanceof MdfEnumeration) {
			return (MdfEnumeration)entity;
		} else {
			return null;
		}
	}

	public MdfAssociation findMdfAssociation(MdfModelElement model, String path) {
		return findMdfAssociationInClass(findMdfClass(model, path), path);
	}
	
	public MdfAssociation findMdfAssociationInClass(MdfClass mdfClass, String path) {
		MdfProperty property = findMdfPropertyInClass(mdfClass, path);
		if (property instanceof MdfAssociation) {
				return (MdfAssociation)property;
		}
		return null;
	}
	
	public MdfAttribute findMdfAttribute(MdfModelElement model, String path) {
		return findMdfAttributeInClass(findMdfClass(model, path), path);
	}
	
	public MdfAttribute findMdfAttributeInClass(MdfClass mdfClass, String path) {
		MdfProperty property = findMdfPropertyInClass(mdfClass, path);
		if (property instanceof MdfAttribute) {
				return (MdfAttribute)property;
		}
		return null;
	}

	public MdfProperty findMdfProperty(MdfModelElement model, String path) {
		return findMdfPropertyInClass(findMdfClass(model, path), path);
	}
	
	public MdfProperty findMdfPropertyInClass(MdfClass mdfClass, String path) {
		if (mdfClass != null) {
			String associationName = StringUtils.substringAfter(path, "#");
			MdfProperty property = mdfClass.getProperty(associationName);
			return (MdfProperty)property;
		}
		return null;
	}

	public String removeBrackets(String nameRef) {
		if (nameRef == null)
			return "";
		
		if (hasBrackets(nameRef)) {
			return nameRef.substring(1, nameRef.length()-1);
		}
		return "";
	}
	
	public String addBrackets(String name) {
		if (name == null)
			return "";
		if (hasBrackets(name)) {
			return name;
		} else {
			return LEFT_BRACKET + name + RIGHT_BRACKET;
		}
	}

	public boolean hasBrackets(String name) {
		if (name == null)
			return false;
		
		return name.startsWith(LEFT_BRACKET) && name.endsWith(RIGHT_BRACKET);
	}
	
	public String resolveEntitySqlNameReference(MdfModelElement model,
			String annotationValue) {
		if (hasBrackets(annotationValue)) {
			String path = removeBrackets(annotationValue);
			MdfClass mdfClass = findMdfClass(model, path);
			if (mdfClass != null) {
				return AAAAspect.getTripleAEntitySQLName(mdfClass);
			} else {
				return "";
			}
		} else {
			// If the [...] notation is not used, it means the value should be used instead.
			// This is the case of the standard entity which doesn't use the reference to a 
			// class, but copy the value.
			return annotationValue;
		}
	}

	public String resolveAttributeSqlNameReference(MdfModelElement model,
			String annotationValue) {
		if (hasBrackets(annotationValue)) {
			String path = removeBrackets(annotationValue);
			MdfProperty property = findMdfProperty(model, path);
			if (property != null) {
				return AAAAspect.getTripleAAttributeSQLName(property);
			} else {
				return "";
			}
		} else {
			// If the [...] notation is not used, it means the value should be used instead.
			// This is the case of the standard entity which doesn't use the reference to an 
			// attribute, but copy the value.
			return annotationValue;
		}
	}


}
