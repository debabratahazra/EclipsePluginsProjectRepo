package com.odcgroup.iris.generator;

/**
 * Class representing an application field, with information about the field.
 *
 * @author agoulding
 *
 */
public class AppField {

	private String t24name = "";	// Should match the name in T24 standard selection definition
	private String name = "";		// Differs from t24name by '.' changed to '_' in name, but there could be other differences
	private String joinedTo = "";
	private String propertyGroup = "";
	private double sysNumber = -1.0;
	private String mvGroup = "";
	private String svGroup = "";
	private boolean primaryKey = false;
	private String valueType = "";
	private boolean isLanguageField = false;
	private String businessType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getT24name() {
		return t24name;
	}
	public void setT24name(String t24name) {
		this.t24name = t24name;
	}
	public String getJoinedTo() {
		return joinedTo;
	}
	public void setJoinedTo(String joinedTo) {
		this.joinedTo = joinedTo;
	}
	public double getSysNumber() {
		return sysNumber;
	}
	public void setSysNumber(Double sysNumber) {
		this.sysNumber = sysNumber != null ? sysNumber : -1.0;
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
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType != null ? valueType : "";
	}
	public void setLanguageField(boolean isLanguageField) {
		this.isLanguageField = isLanguageField;
	}
	public boolean isLanguageField() {
		return isLanguageField;
	}	
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(sysNumber).append(" ").append(name);
		if (!t24name.isEmpty()) {
			sb.append(", t24name=").append(t24name);
		}
		if (!joinedTo.isEmpty()) {
			sb.append(", joinedTo=").append(joinedTo);
		}
		if (!propertyGroup.isEmpty()) {
			sb.append(", propertyGroup=").append(propertyGroup);
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
		if (!valueType.isEmpty()) {
			sb.append(", valueType=").append(valueType);
		}
		if (isLanguageField) {
			sb.append(", languageField=true");
		}
		if (businessType != null) {
			sb.append(", businessType=").append(businessType);
		}
		sb.append("}");
		return sb.toString();
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
