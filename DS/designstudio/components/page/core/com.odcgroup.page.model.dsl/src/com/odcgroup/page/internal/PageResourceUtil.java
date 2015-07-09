package com.odcgroup.page.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class PageResourceUtil {

	/** 
	 * Traverses up the container hierarchy to find the
	 * resource for an EObject (as sometimes eResource() is null on the
	 * object, but not on its parent)
	 * 
	 * @param context the EObject to retrieve the resource for
	 * @return the resource which contains the EObject
	 */
	static public Resource getResource(EObject context) {
		if(context.eResource()!=null) {
			return context.eResource();
		} else {
			if(context.eContainer()!=null) {
				return getResource(context.eContainer());
			} else {
				return null;
			}
		}
	}


}
