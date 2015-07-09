package com.odcgroup.aaa.ui.internal.page.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.aaa.core.UDEHelper;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.MdfValidationCore;
import com.odcgroup.mdf.validation.validator.ModelValidator;
import com.odcgroup.mdf.validation.validator.ValidationListener;

public class UDEModelValidatorAdapter implements ModelValidator {

    private ValidationListener listener = null;
    
    private UDEModelValidator validator;
    
	public UDEModelValidatorAdapter() {
		validator = new UDEModelValidatorFactory().createUDEModelValidator();
	}

	@Override
	public void setValidationListener(ValidationListener listener) {
        this.listener = listener;
	}

	@Override
	public boolean accept(MdfModelElement model) {
        return listener.onValidation(model, validate(model));
	}

	public IStatus validate(MdfModelElement originalModel) {
		if (UDEHelper.getInstance().isUDEModel(originalModel)) {
			IStatus status = validator.validate(originalModel);
			// adapt to the UDE model validator constraint (can't display well multi lines error messages)
			return MdfValidationCore.newStatus(
					status.getMessage().replace("\n", " \\ "), 
					status.getSeverity());
		} else {
			return Status.OK_STATUS;
		}
	}
	
}
