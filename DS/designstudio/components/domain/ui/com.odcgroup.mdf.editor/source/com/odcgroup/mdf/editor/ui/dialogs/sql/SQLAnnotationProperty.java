package com.odcgroup.mdf.editor.ui.dialogs.sql;

import org.eclipse.jface.action.IAction;

public class SQLAnnotationProperty {

	private boolean multiple = false;
	private boolean nameOptional = false;
	private String propertyName = null;
	private boolean booleanValue = false;
	private boolean enumValue = false;
	private String[] possibleValues = null;
	
	private IAction action = null;

	public IAction getAction() {
		return action;
	}

	public void setAction(IAction action) {
		this.action = action;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public boolean isNameOptional() {
		return nameOptional;
	}

	public String getPropertyName() {
		return propertyName;
	}

	private SQLAnnotationProperty(String propertyName, boolean nameOptional) {
		this.propertyName = propertyName;
		this.nameOptional = nameOptional;
	}

	public static SQLAnnotationProperty MULTIPLE_PROPERTY_INSTANCE(
			String propertyNameLike) {
		SQLAnnotationProperty property = new SQLAnnotationProperty(
				propertyNameLike, true);
		property.multiple = true;
		return property;
	}
	
	public static SQLAnnotationProperty SINGLE_PROPERTY_INSANCE(String propertyName, boolean nameOptional) {
		SQLAnnotationProperty property = new SQLAnnotationProperty(
				propertyName, nameOptional);
		return property;		
	}
	
	public static SQLAnnotationProperty SINGLE_PROPERTY_BOOLEANVAL_INSTANCE(String propertyName, boolean nameOptional) {
		SQLAnnotationProperty property = new SQLAnnotationProperty(
				propertyName, nameOptional);
		property.booleanValue = true;
		return property;		
		
	}

	public static SQLAnnotationProperty SINGLE_PROPERTY_ENUMVAL_INSTANCE(String propertyName, boolean nameOptional, String[] possibleValues) {
		SQLAnnotationProperty property = new SQLAnnotationProperty(
				propertyName, nameOptional);
		property.enumValue = true;
		property.possibleValues = possibleValues;
		return property;		
	}

	public boolean isBooleanValue() {
		return booleanValue;
	}
	
	public boolean isEnumValue() {
		return enumValue;
	}

	/**
	 * @return
	 */
	public String[] getPossibleValues() {
		return possibleValues;
	}

}
