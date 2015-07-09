package com.odcgroup.mdf.validation.validator;

import org.eclipse.core.runtime.IStatus;

import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public interface ValidationListener {
	public boolean onValidation(MdfModelElement model, IStatus status);
}
