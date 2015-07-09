package com.odcgroup.integrationfwk.ui.model;

import javax.xml.bind.annotation.XmlElement;

public abstract class Field {

	/**
	 * boolean flag which tell us a field is already selected or not.
	 */
	private boolean isSelected;

	@Override
	public abstract boolean equals(Object obj);

	@XmlElement(name = "DisplayName")
	public abstract String getDisplayName();

	@XmlElement(name = "FieldName")
	public abstract String getFieldName();

	@XmlElement(name = "FieldType")
	public abstract String getFieldType();

	@Override
	public abstract int hashCode();

	/**
	 * method which give us the status of the field i.e whether the field is
	 * already selected or not.
	 * 
	 * @return true if selected, false otherwise
	 */
	public boolean isSelected() {
		return isSelected;
	}

	public abstract void setDisplayName(String displayName);

	public abstract void setFieldName(String fieldName);

	public abstract void setFieldType(String fieldType);

	/**
	 * set this instance of {@link Field} is already selected or not.
	 * 
	 * @param isSelected
	 *            - true if already selected, false otherwise.
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return " " + getFieldName() + " " + getFieldType() + " "
				+ " displayName";
	}

}
