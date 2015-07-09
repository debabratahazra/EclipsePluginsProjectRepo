package com.odcgroup.service.gen.t24.internal.data;

import java.util.List;

import com.odcgroup.service.gen.t24.internal.utils.HashCodeHelper;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class ParamDescriptor extends ElementDescriptor {	
	private String typeName;
	private Direction direction;
	private Cardinality cardinality;
	private Complexity complexity; 
	private boolean mandatory;
	private List<AttributeDescriptor> attributes;
	
	public ParamDescriptor(String name, String typeName, Cardinality cardinality, Direction direction, Complexity complexity, boolean mandatory, List<AttributeDescriptor> attributes) {
		super(name);
		
		if (direction == null || typeName == null) {
			throw new IllegalArgumentException("must have valid type and typeName");
		}
		this.typeName = typeName;		
		this.cardinality = cardinality;
		this.direction = direction;
		this.complexity = complexity;
		this.mandatory = mandatory;
		this.attributes = attributes;
	}	
	
	public String getCapitalisedName() {
    	return getName().toUpperCase();
	}
	
	public String getUpperCamelCaseName() {
    	return StringUtils.upperInitialCharacter(getName());
	}	

	public String getLowerCamelCaseName() {
		return StringUtils.lowerInitialCharacter(getName());
	}
	
	public String getTypeName() {
		return typeName;
	}

	public Direction getDirection() {
		return direction;
	}

	public Cardinality getCardinality() {
		return cardinality;
	}

	public Complexity getComplexity() {
		return complexity;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
	
	/** 
	 * @return list of non-return parameters (i.e. IN, OUT and INOUT) for this operation
	 */
	public List<AttributeDescriptor> getAttributes() {
		return attributes;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode() + getTypeName().hashCode();
	}
	
	public int getSerialVersionUID() {
		return this.hashCode() + 
			HashCodeHelper.getHashCode(getCardinality()) +
			HashCodeHelper.getHashCode(getComplexity()) +  
			HashCodeHelper.getHashCode(getDirection());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ParamDescriptor)) {
			return false;
		}
		
		ParamDescriptor other = (ParamDescriptor) obj;
		return getName().equals(other.getName()) && (direction == other.direction) && (cardinality == other.cardinality) && (typeName.equals(other.typeName) && (complexity == other.complexity) );	
	}

	@Override
	public String toString() {
		return String.format("ParamDescriptor: name=%s, typeName=%s, cardinality=%s, type=%s, mandatory=%b, attributes=%s", getName(), typeName, cardinality, direction, mandatory, attributes);		
	}

}
