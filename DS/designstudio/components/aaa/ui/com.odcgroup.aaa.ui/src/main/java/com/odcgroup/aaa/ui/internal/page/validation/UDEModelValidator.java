package com.odcgroup.aaa.ui.internal.page.validation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.MdfValidationCore;


public class UDEModelValidator {

    private List<IUDEValidationRule> validations = new LinkedList<IUDEValidationRule>();
    
	UDEModelValidator() {
	}

	public IStatus validate(MdfModelElement originalModel) {
		return validate(originalModel, originalModel);
	}
	
	public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		List<String> errorMessages = new ArrayList<String>();
		List<String> warningMessages = new ArrayList<String>();
		for (IUDEValidationRule validation: validations) {
			IStatus status = validation.validate(originalModel, workingCopyForAAAAnnotations);
			if (status.getSeverity() == IStatus.WARNING) {
				warningMessages.add(status.getMessage()); // Ensure warning doesn't override error
			} else if (status.getSeverity() == IStatus.ERROR) {
				errorMessages.add(status.getMessage());
			}
		}
		if (errorMessages.size() > 0) {
			return MdfValidationCore.newStatus(
					StringUtils.join(errorMessages, "\n"), 
					IStatus.ERROR);
		} else if (warningMessages.size() > 0) {
			return MdfValidationCore.newStatus(
					StringUtils.join(warningMessages, "\n"), 
					IStatus.WARNING);
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	/**
	 * @return the sql name 
	 */
	public void addValidation(IUDEValidationRule validation) {
		validations.add(validation);
	}

}
