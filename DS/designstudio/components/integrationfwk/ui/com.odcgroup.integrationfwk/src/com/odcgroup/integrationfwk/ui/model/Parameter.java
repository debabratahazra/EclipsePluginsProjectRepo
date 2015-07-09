package com.odcgroup.integrationfwk.ui.model;

public class Parameter {

	private String name;
	private String typeName;
	private String isPrimitive;
	private String isCollection;
	private String isMandatory;
	private String direction;

	public String getDirection() {
		return direction;
	}

	public String getIsCollection() {
		return isCollection;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public String getIsPrimitive() {
		return isPrimitive;
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public void setIsPrimitive(String isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
