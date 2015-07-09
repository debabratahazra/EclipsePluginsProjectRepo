package com.odcgroup.page.model.util;

import com.odcgroup.mdf.metamodel.MdfEntity;

/**
 * 
 *
 */
public class DatasetAttribute {
	
	/** */
	private MdfEntity type;
	/** */
	private String displayName;
	/** */
	private String name;
	/** */
	private boolean mapped = false;
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param mapped the mapped to set
	 */
	public void setMapped(boolean mapped) {
		this.mapped = mapped;
	}

	/**
	 * @return the mapped
	 */
	public boolean isMapped() {
		return mapped;
	}
	
	/**
	 * @return the type of the dataset attribute
	 */
	public MdfEntity getType() {
		return type;
	}

	/**
	 * @param type
	 * @param name 
	 * @param displayName 
	 */
	public DatasetAttribute(MdfEntity type, String name, String displayName) {
		this.type = type;
		this.name = name;
		this.displayName = displayName;
	}
}
