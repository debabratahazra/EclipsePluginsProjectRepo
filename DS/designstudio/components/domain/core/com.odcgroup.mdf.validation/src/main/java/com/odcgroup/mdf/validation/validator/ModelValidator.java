package com.odcgroup.mdf.validation.validator;

import com.odcgroup.mdf.model.util.ModelVisitor;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public interface ModelValidator extends ModelVisitor {
	public static final String EXTENSION_POINT = "com.odcgroup.mdf.validation.modelvalidator";
	
	public void setValidationListener(ValidationListener listener);
}