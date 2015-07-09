package com.odcgroup.workbench.editors.properties;

import org.eclipse.core.resources.IMarker;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public interface IValidationStatusProvider {
	
	public IMarker[] getValidationStatus(Object element);

}
