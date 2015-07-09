package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.enquiry.util.EMUtils;

/**
 * This class is a 'flat' representation of the application fields, generated out of an MdfClass object, which can contain nested MdfClass objects.
 * It's much easier to deal with an Application object than to deal with an MdfDomain object.
 * @author agoulding
 *
 */
public class Application {

	private static Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
    private String t24Name = "";
	private String module = null;
	private String component = null;
	private List<AppField> fields = new ArrayList<AppField>();
	
	/**
	 * Sets up the application object from an MdfClass object.
	 * @param mdfDomain
	 * @param classname
	 */
	public Application(MdfClass mdfClass) {
		if (mdfClass != null) {
		    this.t24Name = T24Aspect.getT24Name((MdfModelElementImpl)mdfClass);
			addFieldsFromClass(mdfClass, "", "");
			MdfDomain mdfDomain = mdfClass.getParentDomain();
			if (mdfDomain != null) {
				module = EMUtils.getMdfDomainAnnotation(mdfDomain, EMUtils.T24_ANNOTATION_MODULE);
				component = mdfDomain.getName();
			}
		}
	}
	
	/**
	 * Useful for creating an application without an MdfClass, eg for unit tests
	 * @param t24Name
	 * @param module
	 * @param component
	 * @param appFields
	 */
	public Application(String t24Name, String module, String component, List<AppField> appFields) {
		this.t24Name = t24Name;
		this.module = module;
		this.component = component;
		this.fields = appFields;
	}
	
	public String getT24Name() {
	    return this.t24Name;
	}
	
	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
	}

	public List<AppField> getFields() {
		return this.fields;
	}
	
	public AppField getFieldBySysNumber(double sysNumber) {
		for (AppField appField : fields) {
			if (appField.getSysNumber() == sysNumber) {
				return appField;
			}
		}
		return null;
	}
	
	public AppField getFieldByT24Name(String t24name) {
		if (t24name != null && t24name.equals("@ID")) {
			return getIdField();
		}
		for (AppField appField : fields) {
			if (appField.getT24name().equals(t24name)) {
				return appField;
			}
		}
		return null;
	}
	
	public AppField getIdField() {
		return getFieldBySysNumber(0.0);
	}

	@SuppressWarnings("unchecked")
	private void addFieldsFromClass(MdfClass mdfClass, String mvGroup, String svGroup) {
		List<MdfProperty> mdfProperties = mdfClass.getProperties();
		for (MdfProperty mdfProperty : mdfProperties) {
			if (mdfProperty instanceof MdfAttribute) {  // It's a field
				addField(mdfProperty, mvGroup, svGroup);
			} else if (mdfProperty instanceof MdfAssociation) {
				MdfAssociationImpl mdfAssociation = (MdfAssociationImpl) mdfProperty;
				if (mdfAssociation.getContainment() == MdfConstants.CONTAINMENT_BYVALUE && mdfAssociation.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {  // It's an mv or sv Group
					MdfEntity type = getTypeAndWarnIfProxy(mdfAssociation);
					if (type instanceof MdfClass) {
						if (mvGroup.isEmpty()) {
							addFieldsFromClass((MdfClass)type, mdfAssociation.getName(), svGroup);
						} else if (svGroup.isEmpty()) {
							addFieldsFromClass((MdfClass)type, mvGroup, mdfAssociation.getName());
						} else {
							assert(false); // We can't go lower than sv
						}
					}
				} else { // It's a field
					addField(mdfProperty, mvGroup, svGroup);
				}
			}
		}
	}

	private void addField(MdfProperty mdfProperty, String mvGroup, String svGroup) {
		AppField appField = new AppField();
		appField.setName(mdfProperty.getName());
		appField.setT24name(T24Aspect.getT24Name(mdfProperty));
		appField.setJoinedTo(T24Aspect.getT24Name(getTypeAndWarnIfProxy(mdfProperty)));
		appField.setSysNumber(T24Aspect.getSysNumber(mdfProperty));
		appField.setPrimaryKey(mdfProperty.isPrimaryKey());
		appField.setMvGroup(mvGroup);
		appField.setSvGroup(svGroup);
		appField.setValueType(new IRISDomainMapper().getValueType(mdfProperty));
		appField.setLanguageField(T24Aspect.isMultiLanguage(mdfProperty));
		appField.setBusinessType(T24Aspect.getBusinessType(mdfProperty));
		fields.add(appField);
	}
	
	
	/* package local */
	static MdfEntity getTypeAndWarnIfProxy(MdfProperty mdfProperty) {
		MdfEntityImpl type = (MdfEntityImpl) mdfProperty.getType();
		if (type.eIsProxy()) {
			LOGGER.error("getType() on MdfProperty returned an unresolved EMF Proxy.. unless there truly is a broken reference in the DSL, this probably indicates some issue in the way your test was written");
			return type; // NOT null (callers don't handle it gracefully)
		}
		return type;
	}
	
	public boolean isPrimaryKey(String t24name) {
		if (t24name != null && t24name.equals("@ID")) { // @ID is a special name for the primary key field, so this is always true
			return true;
		}
		AppField appField = getFieldByT24Name(t24name);
		return (appField == null) ? false : appField.isPrimaryKey();
	}
	
	public String getValueType(String t24name) {
		AppField appField = getFieldByT24Name(t24name);
		return (appField == null) ? "" : appField.getValueType();
	}
	
	public String getT24Name(double sysNumber) {
		AppField appField = getFieldBySysNumber(sysNumber);
		return (appField == null) ? "" : appField.getT24name();
	}
	
	public String getMvGroupName(String t24name) {
		String mvGroupName = "";
		AppField appField = getFieldByT24Name(t24name);
		if (appField != null) {
			mvGroupName = appField.getMvGroup();
			if (!mvGroupName.isEmpty()) {
				mvGroupName = EMUtils.camelCaseName(mvGroupName) + "MvGroup";
			}
		}
		return mvGroupName;
	}
	
	public String getSvGroupName(String t24name) {
		String svGroupName = "";
		AppField appField = getFieldByT24Name(t24name);
		if (appField != null) {
			svGroupName = appField.getSvGroup();
			if (!svGroupName.isEmpty()) {
				svGroupName = EMUtils.camelCaseName(svGroupName) + "SvGroup";
			}
		}
		return svGroupName;
	}
	
	public String getMvGroup(String t24name) {
		String mvGroup = "";
		AppField appField = getFieldByT24Name(t24name);
		if (appField != null) {
			mvGroup = appField.getMvGroup();
		}
		return mvGroup;
	}
	
	public String getSvGroup(String t24name) {
		String svGroup = "";
		AppField appField = getFieldByT24Name(t24name);
		if (appField != null) {
			svGroup = appField.getSvGroup();
		}
		return svGroup;
	}
	
	public boolean isLanguageField(String t24Name) {
		AppField appField = getFieldByT24Name(t24Name);
		return appField != null ? appField.isLanguageField() : false;
	}
	
	public String getBusinessType(String t24Name) {
		AppField appField = getFieldByT24Name(t24Name);
		return appField != null ? appField.getBusinessType() : "";
	}
	
	@Override
	public String toString() {
		return fields.toString();
	}
}
