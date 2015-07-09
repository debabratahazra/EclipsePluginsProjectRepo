package com.odcgroup.workbench.security;

/**
 * This enumeration defines possible permissions to be used with the IAuthorizationManager.
 *
 * @author Kai Kreuzer
 *
 */
public class DSPermission {

	// generic model access permissions
	public static final DSPermission OPEN_MODEL = new DSPermission("open", "Permission to open a model resource in an editor");
	public static final DSPermission EDIT_MODEL = new DSPermission("edit", "Permission to edit a model resource in an editor");
	
	
	protected String name;
	protected String desc;

	protected DSPermission(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
