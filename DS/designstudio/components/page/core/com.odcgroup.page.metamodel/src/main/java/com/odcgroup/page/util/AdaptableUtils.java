package com.odcgroup.page.util;

import org.eclipse.core.runtime.IAdaptable;

/**
 * Utility methods for IAdaptable objects.
 * 
 * @author Gary Hayes
 */
public class AdaptableUtils {

	/**
	 * Adapts the object to the specified Class. If the object
	 * is already an 'instanceof' the specified class it is directly returned
	 * otherwise the IAdaptable mechanism is used.
	 * 
	 * @param object The Object to adapt
	 * @param clazz The Class to adapt teh Object to
	 * @return Object The adpated Objcet or null if the object cannot be adapted
	 */
	public static Object getAdapter(Object object, Class clazz) {
		if (object == null) {
			return null;
		}
		
		if (clazz.isInstance(object)) {
			return object;
		}
		
		if (! (object instanceof IAdaptable)) {
			return null;
		}
		
		IAdaptable a = (IAdaptable) object;
		return a.getAdapter(clazz);
	}
}