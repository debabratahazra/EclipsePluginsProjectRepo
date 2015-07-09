package com.odcgroup.t24.enquiry.util;

/**
 * This class holds information about a vocabulary term
 */
public class EMTerm {
	EMTermType type;
	String value;

	public EMTerm(EMTermType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public EMTermType getType() {
		return type;
	}

	public String getName() {
		return type.getValue();
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * Two EMTerm objects are equal if they have the same type and value
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EMTerm other = (EMTerm) obj;
		if (type != other.type) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return type.getValue() + "=" + value;
	}

}
