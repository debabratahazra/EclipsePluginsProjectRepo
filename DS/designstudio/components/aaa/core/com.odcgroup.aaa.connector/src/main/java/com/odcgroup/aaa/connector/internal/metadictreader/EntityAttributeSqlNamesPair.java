package com.odcgroup.aaa.connector.internal.metadictreader;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;

public class EntityAttributeSqlNamesPair {
	final String entitySQLName;
	final String attrSQLName;
	
	public EntityAttributeSqlNamesPair(String entitySQLName, String attributeSQLName) {
		this.entitySQLName = entitySQLName;
		if (entitySQLName == null) 
            throw new IllegalArgumentException("entitySQLName == null");
		this.attrSQLName = attributeSQLName;
		if (attributeSQLName == null)
            throw new IllegalArgumentException("attributeSQLName == null");
	}

	public EntityAttributeSqlNamesPair(DictAttribute dictAttribute) {
		this(dictAttribute.getDictEntity().getSQLName(), dictAttribute.getSQLName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attrSQLName == null) ? 0 : attrSQLName.hashCode());
		result = prime * result
				+ ((entitySQLName == null) ? 0 : entitySQLName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityAttributeSqlNamesPair other = (EntityAttributeSqlNamesPair) obj;
		if (attrSQLName == null) {
			if (other.attrSQLName != null)
				return false;
		} else if (!attrSQLName.equals(other.attrSQLName))
			return false;
		if (entitySQLName == null) {
			if (other.entitySQLName != null)
				return false;
		} else if (!entitySQLName.equals(other.entitySQLName))
			return false;
		return true;
	}
	
}
