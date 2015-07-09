package com.odcgroup.page.resource.exception;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;

import com.odcgroup.page.resource.PageDSLResource;

/**
 * A parent class for exceptions thrown during loading of {@link PageDSLResource}
 *
 * @author amc
 *
 */
public abstract class PageDSLResourceLoadException extends IOException {

	private static final long serialVersionUID = -3827706590473762551L;

	public PageDSLResourceLoadException(URI uri, String message) {
		super("Could not load resource "+uri+": "+message);
	}
}
