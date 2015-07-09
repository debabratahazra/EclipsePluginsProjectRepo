package com.odcgroup.aaa.ui.internal.model.impl;

import com.odcgroup.aaa.connector.domainmodel.TypeEntity;
import com.odcgroup.aaa.ui.internal.model.AAAFormatType;

/**
 * @author atr
 * @since DS 1.40.0
 */
class AAAFormatTypeAdapter implements AAAFormatType {

	private String displayName;

	/** Type entity represented by this combobox possible value, null for all */
	private TypeEntity typeEntity;

	/**
	 * @param id
	 * @param displayName
	 */
	public AAAFormatTypeAdapter(String displayName, TypeEntity typeEntity) {
//		if (displayName == null) {
//			throw new IllegalArgumentException("The display name cannot be null");
//		}
		this.displayName = displayName != null ? displayName : "";
		this.typeEntity = typeEntity;
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAFormatType#getDisplayName()
	 */
	public final String getDisplayName() {
		return this.displayName;
	}

	/**
	 * @return the typeEntity
	 */
	public TypeEntity getTypeEntity() {
		return typeEntity;
	}

}
