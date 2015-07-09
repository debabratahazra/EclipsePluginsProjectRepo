package com.odcgroup.workbench.tap.validation.tests;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.odcgroup.workbench.tap.validation.AbstractDSModelConstraint;

public class TestModelConstraint_DS4745 extends AbstractDSModelConstraint {

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		// this should result in a validation error, not in the deactivation of the constraint
		throw new NullPointerException();
	}

}
