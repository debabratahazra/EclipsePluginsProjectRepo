package com.odcgroup.page.resource.exception;

import org.eclipse.emf.common.util.URI;

/**
 * Thrown if a PageDSLResource is loaded and no root widget is present
 *
 * @author amc
 *
 */
public class RootWidgetNullException extends PageDSLResourceLoadException {

	private static final long serialVersionUID = -9131994957243179862L;
	
	public RootWidgetNullException(URI uri) {
		super(uri, "Root widget not present (null)");
	}

}
