package com.odcgroup.aaa.ui.internal.page.bindings.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import com.odcgroup.aaa.core.util.NamingHelper;
import com.odcgroup.aaa.ui.internal.page.bindings.IUDEPropertiesBinding;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public abstract class AbstractUDEPropertiesBinding implements IUDEPropertiesBinding {

	private boolean initializing;
	
	@Override
	public void updateTextToModel(boolean modifyEvent) {
		doUpdateTextToModel(modifyEvent);
	}

	public abstract void doUpdateTextToModel(boolean modifyEvent);
	
	public void updateModelToText() {
		initializing = true;
		try {
			doUpdateModelToText();
		} finally {
			initializing = false;
		}
	}

	public abstract void doUpdateModelToText();

	protected String nullSafeTrimmedString(String value) {
		if (value == null) {
			return "";
		} else {
			return value.trim();
		}
	}
	
	protected String nullIfEmptyOrTrimmed(String value) {
		if (value == null || value.length() == 0) {
			return null;
		} else {
			return value.trim();
		}
	}

	/**
	 * @return the initializing
	 */
	public boolean isInitializing() {
		return initializing;
	}
	
	/**
	 * @param name
	 * @return
	 */
	protected String replaceHash(String name) {
		if (StringUtils.isEmpty(name)) {
			return name;
		}
		return name.replaceAll("#", ".");
	}
	
	/**
	 * @param name
	 * @return
	 */
	protected String replaceDot(String name) {
		if (StringUtils.isEmpty(name)) {
			return name;
		}
		return name.replaceAll("\\.", "#");		
	}
	
	/**
	 * @param model
	 * @return
	 */
	protected String getUDESqlName(MdfModelElement model) {
		String val = WordUtils.capitalize(NamingHelper.turnCamelCaseIntoSpaces(model.getName()));
		return val.replaceAll(" ", "_").toLowerCase();
	}
	
	/**
	 * @param model
	 * @return
	 */
	protected String getUDEName(MdfModelElement model) {
		String val = WordUtils.capitalize(NamingHelper.turnCamelCaseIntoSpaces(model.getName()));
		int index = val.lastIndexOf(" ");
		String name = "";
		if (val.substring(index+1, val.length()).length()<=1) {
			name = val.substring(0, index);
		} else {
			name = val;
		}
		return name;
	}
	

}
