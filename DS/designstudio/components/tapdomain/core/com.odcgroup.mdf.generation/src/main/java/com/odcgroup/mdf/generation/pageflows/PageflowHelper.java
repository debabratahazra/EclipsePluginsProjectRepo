package com.odcgroup.mdf.generation.pageflows;

import com.odcgroup.mdf.metamodel.MdfEntity;

public class PageflowHelper {

	public final static String OBJECT_DEFAULT_SCOPE_KEY = "objectScopeKey";
	public final static String OBJECT_DEFAULT_SCOPE_KEY_VALUE = "pageflowBean";
	public final static String REF_DEFAULT_SCOPE_KEY = "refScopeKey";
	public final static String REF_DEFAULT_SCOPE_KEY_VALUE = "pageflowRef";
	public final static String PAGEFLOW_ACTION_PACKAGE = "pageflow.actions";
	public final static String PAGEFLOW_ACTION_SUFFIX = "Action";
	public final static String CREATE_PAGEFLOW_ACTION_PREFIX = "Create";
	public final static String SAVE_PAGEFLOW_ACTION_PREFIX = "Save";
	public final static String REMOVE_PAGEFLOW_ACTION_PREFIX = "Remove";
	public final static String FIND_BY_ID_PAGEFLOW_ACTION_PREFIX = "FindById";

	// For oAW extensions, cannot access constants directly...
	public static String getDefaultObjectScopeKey() {
		return OBJECT_DEFAULT_SCOPE_KEY;
	}

	// For oAW extensions, cannot access constants directly...
	public static String getDefaultObjectScopeKeyValue() {
		return OBJECT_DEFAULT_SCOPE_KEY_VALUE;
	}

	// For oAW extensions, cannot access constants directly...
	public static String getDefaultRefScopeKey() {
		return REF_DEFAULT_SCOPE_KEY;
	}

	// For oAW extensions, cannot access constants directly...
	public static String getDefaultRefScopeKeyValue() {
		return REF_DEFAULT_SCOPE_KEY_VALUE;
	}
	// For oAW extensions, cannot access constants directly...
	public static String getPageflowActionPackage() {
		return PAGEFLOW_ACTION_PACKAGE;
	}

	// For oAW extensions, cannot access constants directly...
	public static String getPageflowActionSuffix() {
		return PAGEFLOW_ACTION_SUFFIX;
	}

	public static String getPageflowActionName(MdfEntity entity) {
		return entity.getName() + getPageflowActionSuffix();
	}
	
	public static String getCreatePageflowActionName(MdfEntity entity) {
		return CREATE_PAGEFLOW_ACTION_PREFIX + getPageflowActionName(entity);
	}

	public static String getSavePageflowActionName(MdfEntity entity) {
		return SAVE_PAGEFLOW_ACTION_PREFIX + getPageflowActionName(entity);
	}
	
	public static String getRemovePageflowActionName(MdfEntity entity) {
		return REMOVE_PAGEFLOW_ACTION_PREFIX + getPageflowActionName(entity);
	}
	
	public static String getFindByIdPageflowActionName(MdfEntity entity) {
		return FIND_BY_ID_PAGEFLOW_ACTION_PREFIX + getPageflowActionName(entity);
	}
}
