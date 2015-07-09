package com.odcgroup.service.gen.t24.internal.data;

import java.util.Arrays;
import java.util.List;

import com.odcgroup.service.gen.t24.internal.utils.HashCodeHelper;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class AttributeDescriptor extends ElementDescriptor{	
	private String typeName;
	private Cardinality cardinality;
	private Complexity complexity;
	private static List<String> supportedPrimitives = Arrays.asList("String", "Integer", "Boolean");
	
	public AttributeDescriptor()
	{
		super("");
	}
	
	public AttributeDescriptor(String name, String typeName, Cardinality cardinality) {
		super(name);
		
		if (typeName == null) {
			throw new IllegalArgumentException("must have valid typeName");
		}
		this.typeName = typeName;		
		this.cardinality = cardinality;
		
		if (isPrimitiveType(this.typeName)) {
			complexity = Complexity.PRIMITIVE;
		} else {
			complexity = Complexity.COMPLEX;
		}
			
	}	

	private boolean isPrimitiveType(String type) {
		if (supportedPrimitives.contains(type)) {
			return true;
		}
		else {
			return false;
		}
	}

	//Capitalised name is used in Velocity templates to construct final static index positions for class definitions
	public String getCapitalisedName() {
    	return getName().toUpperCase();
	}

	//CamelCase name is used in Velocity templates to construct Setters
	public String getCamelCaseName() {
		return StringUtils.upperInitialCharacter(getName());
	}
	
	//Uppercase field name with '.' between words
	public String getT24FieldName() {
		String camelName = getCamelCaseName();
		StringBuilder t24Name = new StringBuilder();
		
		for(int i = 0, n = camelName.length() ; i < n ; i++) {
			char c = camelName.charAt(i);
			
			if ( Character.isLowerCase(c) )
			{
				t24Name.append( Character.toUpperCase(c) );
			}
			else
			{
				if ( t24Name.length() != 0 )
				{
					t24Name.append( "." );
				}
				
				t24Name.append( c );
			}
		}
		
    	return t24Name.toString();
	}
	
	public String getTypeName() {
		return typeName;
	}

	public Cardinality getCardinality() {
		return cardinality;
	}
	
	public Complexity getComplexity() {
		return this.complexity;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
	}

	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	@Override
	public int hashCode() {
		return getName().hashCode() + getTypeName().hashCode();
	}

	public int getSerialVerionUID() {
		return this.hashCode() + 
		HashCodeHelper.getHashCode(getCardinality()) +
		HashCodeHelper.getHashCode(getComplexity());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AttributeDescriptor)) {
			return false;
		}
		
		AttributeDescriptor other = (AttributeDescriptor) obj;
		return getName().equals(other.getName()) && (cardinality == other.cardinality) && (typeName.equals(other.typeName) );	
	}

	@Override
	public String toString() {
		return String.format("AttributeDescriptor: name=%s, typeName=%s, cardinality=%s, complexity=%s", getName(), typeName, cardinality, complexity);		
	}

}
