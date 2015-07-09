package com.odcgroup.aaa.ui.internal.page.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public class UDEModelValidatorTest {

	private static final String ERROR_MESSAGE = "error message";
	private static final Status ERROR_STATUS = new Status(IStatus.ERROR, "dummy", ERROR_MESSAGE);

	private static final String WARNING_MESSAGE = "warning message";
	private static final Status WARNING_STATUS = new Status(IStatus.WARNING, "dummy", WARNING_MESSAGE);

	@Test
	public void testValidate_noValidationRules() {
		// Given
		MdfModelElement model = MdfFactory.eINSTANCE.createMdfDomain();
		UDEModelValidator validator = new UDEModelValidator();
		
		// When
		IStatus status = validator.validate(model);
		
		// Then
		Assert.assertTrue("Should return ok status", status.isOK());
	}

	@Test
	public void testValidate_detectError() {
		// Given
		MdfModelElement model = MdfFactory.eINSTANCE.createMdfDomain();
		UDEModelValidator validator = new UDEModelValidator();
		validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel,
					MdfModelElement workingCopyForAAAAnnotations) {
				return ERROR_STATUS;
			}
		});
		
		// When
		IStatus status = validator.validate(model);
		
		// Then
		Assert.assertFalse("Shouldn't return ok status", status.isOK());
		Assert.assertEquals("Should be an error", IStatus.ERROR, status.getSeverity());
		Assert.assertEquals("Wrong error message", ERROR_MESSAGE, status.getMessage());
	}

	@Test
	public void testValidate_detectWarning() {
		// Given
		MdfModelElement model = MdfFactory.eINSTANCE.createMdfDomain();
		UDEModelValidator validator = new UDEModelValidator();
		validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel,
					MdfModelElement workingCopyForAAAAnnotations) {
				return WARNING_STATUS;
			}
		});
		
		// When
		IStatus status = validator.validate(model);
		
		// Then
		Assert.assertFalse("Shouldn't return ok status", status.isOK());
		Assert.assertEquals("Should be a warning", IStatus.WARNING, status.getSeverity());
		Assert.assertEquals("Wrong warning message", WARNING_MESSAGE, status.getMessage());
	}
	
	@Test
	public void testValidate_detectErrorIfWarning() {
		// Given
		MdfModelElement model = MdfFactory.eINSTANCE.createMdfDomain();
		UDEModelValidator validator = new UDEModelValidator();
		validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel,
					MdfModelElement workingCopyForAAAAnnotations) {
				return WARNING_STATUS;
			}
		});
		validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel,
					MdfModelElement workingCopyForAAAAnnotations) {
				return ERROR_STATUS;
			}
		});
		
		// When
		IStatus status = validator.validate(model);
		
		// Then
		Assert.assertFalse("Shouldn't return ok status", status.isOK());
		Assert.assertEquals("Should be an error", IStatus.ERROR, status.getSeverity());
		Assert.assertEquals("Wrong error message", ERROR_MESSAGE, status.getMessage());
	}
	
	
}
