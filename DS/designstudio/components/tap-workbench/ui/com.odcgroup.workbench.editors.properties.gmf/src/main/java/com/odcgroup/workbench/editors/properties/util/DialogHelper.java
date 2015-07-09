package com.odcgroup.workbench.editors.properties.util;

import java.lang.reflect.Method;

/**
 * helper class, used by the popupdialogs
 *
 */
public class DialogHelper {
	
	protected String label;
	protected Object value;
	protected Object widget;
	protected Method setMethod;
	protected Method getMethod;

	/**
	 * @param method
	 */
	protected DialogHelper(Method setMethod) {
		this.setMethod = setMethod;
		label = setMethod.getName().substring(3);//$NON-NLS-1$
	}

	public void setGetMethod(Method getMethod) {
		this.getMethod = getMethod;
	}

}
