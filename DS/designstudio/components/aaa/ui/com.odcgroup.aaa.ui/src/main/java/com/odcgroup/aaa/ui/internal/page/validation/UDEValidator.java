package com.odcgroup.aaa.ui.internal.page.validation;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.MdfValidationCore;

public class UDEValidator implements IUDEValidationRule {

    private List<IUDEValidationRule> validations = new LinkedList<IUDEValidationRule>();
    
	public IStatus validate(MdfModelElement originalModel) {
		return validate(originalModel, originalModel);
	}

	public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		List<IStatus> warnings = new LinkedList<IStatus>();
		for (IUDEValidationRule validation: validations) {
			IStatus status = validation.validate(originalModel, workingCopyForAAAAnnotations);
			if (status.getSeverity() == IStatus.WARNING) {
				warnings.add(status); // Ensure warning doesn't override error
			} else if (status.getSeverity() == IStatus.ERROR) {
				return status;
			}
		}
		if (!warnings.isEmpty()) {
			return warnings.get(0);
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
