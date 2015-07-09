package com.odcgroup.workbench.tap.validation.tests;

import org.eclipse.core.runtime.IStatus;
import org.junit.Assert;
import org.junit.Test;

public class ModelConstraintTest_DS4745  {

	@Test
	public void testCatchingException() {
		TestModelConstraint_DS4745 constraint = new TestModelConstraint_DS4745();
		IStatus status = constraint.validate(null);
		Assert.assertEquals(IStatus.ERROR, status.getSeverity());
	}
}
