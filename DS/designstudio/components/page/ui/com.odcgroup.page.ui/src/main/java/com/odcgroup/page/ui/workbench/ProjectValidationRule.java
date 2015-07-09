package com.odcgroup.page.ui.workbench;

import org.eclipse.core.runtime.IStatus;

import com.odcgroup.workbench.core.IOfsProject;

/**
 * The ProjectValidationRule is used to validate a single project.
 * It does this by testing the model against the attached
 * ValidationRule's.
 * 
 * @author Gary Hayes
 */
public interface ProjectValidationRule {
	
	/**
	 * Validates the project.
	 * 
	 * @param ofsProject The IProject to validate
	 * 
	 * @return a status of the validation (if needed a MultiStatus)
	 */
	public IStatus validate(IOfsProject ofsProject);
}
