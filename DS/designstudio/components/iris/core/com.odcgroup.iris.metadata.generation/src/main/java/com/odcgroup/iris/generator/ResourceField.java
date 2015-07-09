package com.odcgroup.iris.generator;

import com.odcgroup.t24.enquiry.util.EMUtils;

/**
 * Class representing an application field, with information about the field.
 *
 * @author agoulding
 *
 */
public class ResourceField {

    private final String t24name;
    private final String t24NameCamelCase;
    private String selectionField = "";
    private String joinedTo = "";
    private String propertyGroup = ""; 
    private String mvGroup = "";
    private String svGroup = "";
    private boolean primaryKey = false;
    private boolean mandatory = false;
    private boolean selectionOnly = true;
    private String valueType = "";
    private boolean isLanguageField = false;
    private String businessType;

    public ResourceField(AppField appField) {
    	/*
    	 * Make at least these 2 final. Since there are used in iteration, this will definitely speed up the whole.
    	 */
    	this.t24name = appField.getT24name().replace(" ", "");
    	this.t24NameCamelCase = EMUtils.camelCaseName(this.t24name);
        setSelectionField(appField.getT24name());
        setJoinedTo(appField.getJoinedTo());
        setPropertyGroup(appField.getPropertyGroup());
        setMvGroup(appField.getMvGroup());
        setSvGroup(appField.getSvGroup());
        setPrimaryKey(appField.isPrimaryKey());
        setMandatory(false);
        setSelectionOnly(false);
        setValueType(appField.getValueType());
        isLanguageField = appField.isLanguageField();
        setBusinessType(appField.getBusinessType());
    }
    public ResourceField(String t24name, String selectionField, String joinedTo, String propertyGroup, String mvGroup, String svGroup, boolean primaryKey, boolean mandatory, String valueType, boolean selectionOnly, boolean isLanguageField, String businessType) {
    	this.t24name = t24name.replace(" ", "");;
    	this.t24NameCamelCase = EMUtils.camelCaseName(this.t24name);
        setSelectionField(selectionField);
        setJoinedTo(joinedTo);
        setPropertyGroup(propertyGroup);
        setMvGroup(mvGroup);
        setSvGroup(svGroup);
        setPrimaryKey(primaryKey);
        setMandatory(mandatory);
        setSelectionOnly(selectionOnly);
        setValueType(valueType);
        this.isLanguageField = isLanguageField; 
        setBusinessType(businessType);
    }

    // Getters and setters
    public final String getT24name() {
        return t24name;
    }
    
    public final String getIrisName() {
    	/*
    	 * Not doing the camelCasing here saved million of invocation to camelCaseName(...)
    	 */
        return t24NameCamelCase;
    }
    public String getSelectionField() {
        return selectionField;
    }
    public void setSelectionField(String selectionField) {
        this.selectionField = selectionField != null ? selectionField : "";
    }
    public String getJoinedTo() {
        return joinedTo;
    }
    public void setJoinedTo(String joinedTo) {
        this.joinedTo = joinedTo != null ? joinedTo : "";
    }
    public String getMvGroup() {
        return mvGroup;
    }
    public void setMvGroup(String mvGroup) {
        this.mvGroup = mvGroup;
    }
    public String getSvGroup() {
        return svGroup;
    }
    public void setSvGroup(String svGroup) {
        this.svGroup = svGroup;
    }
    public boolean isPrimaryKey() {
        return primaryKey;
    }
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
    public boolean isMandatory() {
        return mandatory;
    }
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
    public boolean isSelectionOnly() {
        return selectionOnly;
    }
    public void setSelectionOnly(boolean selectionOnly) {
        this.selectionOnly = selectionOnly;
    }
    public String getValueType() {
        return valueType;
    }
    public void setValueType(String valueType) {
        this.valueType = valueType != null ? valueType : "";
    }
    public String getBusinessType() {
    	return businessType;
    }
    public void setBusinessType(String businessType) {
    	this.businessType = businessType == null ? "" : businessType;
    }
    public boolean isLanguageField(){
        return isLanguageField;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{t24name=").append(t24name);
        if (!selectionField.isEmpty()) {
            sb.append(", selectionField=").append(selectionField);
        }
        if (!mvGroup.isEmpty()) {
            sb.append(", mvGroup=").append(mvGroup);
        }
        if (!svGroup.isEmpty()) {
            sb.append(", svGroup=").append(svGroup);
        }
        if (primaryKey) {
            sb.append(", primaryKey=").append(primaryKey);
        }
        if (mandatory) {
            sb.append(", mandatory=").append(mandatory);
        }
        if (selectionOnly) {
            sb.append(", selectionOnly=").append(selectionOnly);
        }
        if (!valueType.isEmpty()) {
            sb.append(", valueType=").append(valueType);
        }
        if (!businessType.isEmpty()) {
        	sb.append(", businessType=").append(businessType);
        }
        if (isLanguageField) {
            sb.append(", languageField=true");
        }
        if (!joinedTo.isEmpty()) {
            sb.append(", joinedTo=").append(joinedTo);
        }
        if (!propertyGroup.isEmpty()) {
            sb.append(", propertyGroup=").append(propertyGroup);
        }
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    /**
     * Two ResourceFields are considered equal if they have the same name
     */
    public boolean equals(Object o) {
        if (!(o instanceof ResourceField)) {
            return false;
        }
        ResourceField other = (ResourceField) o;
        if (this.getIrisName().equals(other.getIrisName())) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return t24name.hashCode();
    }
    /**
     * @return the propertyGroup
     */
    public String getPropertyGroup() {
        return propertyGroup;
    }
    /**
     * @param propertyGroup the propertyGroup to set
     */
    public void setPropertyGroup(String propertyGroup) {
        this.propertyGroup = propertyGroup != null ? propertyGroup : "";
    }   
}
