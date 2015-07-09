package com.odcgroup.service.gen.t24.internal.data;

import java.util.List;

import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class ClassDefDescriptor extends ElementDescriptor{		

	private String typeName;

	private List<AttributeDescriptor> attributes;
	private boolean ifContainsCollection;
	
	public ClassDefDescriptor()
	{
		super("");
	}
	
	public ClassDefDescriptor(String name, String typeName, List<AttributeDescriptor> attributes) {
		super(name);
		
		if (typeName == null) {
			throw new IllegalArgumentException("must have valid type and typeName");
		}
		this.typeName = typeName;		
		this.attributes = attributes;
		setIfContainsCollection(attributes);
	}
	

	public String getLowerCamelCaseName() {
		return StringUtils.lowerInitialCharacter(getName());
	}

	public String getUpperCamelCaseName() {
		return StringUtils.upperInitialCharacter(getName());	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setAttributes(List<AttributeDescriptor> attributes) {
		this.attributes = attributes;
	}

	/** 
	 * @return list of attributes for this Class Definition
	 */
	public List<AttributeDescriptor> getAttributes() {
		return attributes;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof ClassDefDescriptor) {
			ClassDefDescriptor desc = (ClassDefDescriptor)obj;
			return getName().equals(desc.getName()) && (typeName.equals(desc.typeName) );
		} else {
			return false;
		}
	}

	/**
	 * This method will generate data class serialVersionUID for Serializable
	 * @return Adding all hashcode for each attribute and return as class serialVersionUID
	 */
	public long getSerialVersionUID() {
		long serialUID = 0L;
		for (AttributeDescriptor attr : getAttributes()) {
			serialUID += attr.getSerialVerionUID();
		}
		return serialUID;
	}
	
	@Override
	public String toString() {
		return String.format("ClassDefDescriptor: name=%s, typeName=%s, attributes=%s", getName(), typeName, attributes);		
	}

	// Check if class contains any Collection - Useful during code generation
	private void setIfContainsCollection(List<AttributeDescriptor> attributes) {
		if (attributes != null && attributes.size() > 0) {
			for (AttributeDescriptor attribute : attributes) {
				if (attribute.getCardinality().equals(Cardinality.MULTIPLE)){
					ifContainsCollection = true;	// Set to true and break
					break;
				}
			}
		} else {
			ifContainsCollection = false;
		}
	}
	
	public boolean ifContainsCollection () {
		return ifContainsCollection;
	}
}
