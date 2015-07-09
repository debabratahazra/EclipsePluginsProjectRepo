package com.odcgroup.mdf.editor.security;

import com.odcgroup.workbench.security.DSPermission;

/**
 * This class defines permissions on domain models
 *
 * @author Kai Kreuzer
 *
 */
public class MdfPermission extends DSPermission {

	public static final DSPermission CONTEXT_MENU_NEW = new MdfPermission("new", "Permission to use the 'new' context menu in the editor to create new model elements");
	public static final DSPermission PROPERTY_SHEET_EDIT = new MdfPermission("property_edit", "Permission to edit properties of model elements");
	
	protected MdfPermission(String name, String desc) {
		super(name, desc);
	}

}
