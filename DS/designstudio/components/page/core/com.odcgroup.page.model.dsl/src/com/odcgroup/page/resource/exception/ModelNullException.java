package com.odcgroup.page.resource.exception;

import org.eclipse.emf.common.util.URI;

/**
 * Thrown if a resource's model is not present
 *
 * @author amc
 *
 */
public class ModelNullException extends PageDSLResourceLoadException {

	private static final long serialVersionUID = 203215175787658203L;

	public ModelNullException(URI uri) {
		super(uri, "Model not present (null)");
	}
	
}
