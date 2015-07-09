package com.odcgroup.aaa.ui.internal.page.validation;

import org.eclipse.core.runtime.IStatus;

import com.odcgroup.mdf.metamodel.MdfModelElement;

public interface IUDEValidationRule {
	
	/**
	 * @param originalModel should be used to browse the structure
	 * @param workingCopyForAAAAnnotations should be used to read current AAA annotations
	 * @return OK status or relevant error message
	 */
	IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations);

}
