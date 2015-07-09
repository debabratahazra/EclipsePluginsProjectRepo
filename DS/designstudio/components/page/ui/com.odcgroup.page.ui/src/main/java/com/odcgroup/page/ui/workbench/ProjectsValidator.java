package com.odcgroup.page.ui.workbench;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Validates all OFS projects which are open in Eclipse.
 * Only one thread is allowed to perform a validation. Other
 * thread return immediately.
 * 
 * @author Gary Hayes
 */
public class ProjectsValidator {

	/**
	 * Creates a new ProjectsValidator.
	 */
	private ProjectsValidator() {
	}	
	
	/**
	 * Validates a given OFS project
	 * 
	 * @param ofsProject the project to validate 
	 * @return the validation result
	 */
	public static IStatus validate(IOfsProject ofsProject) {
		MultiStatus returnStatus = new MultiStatus(OfsCore.PLUGIN_ID, IStatus.OK, "Project validation results", null);

		if (!ofsProject.getProject().isOpen()) {
			// Nothing to validate
			return returnStatus;
		}

		List<ProjectValidationRule> projectValidationRules = new ArrayList<ProjectValidationRule>();
		projectValidationRules.add(new DefaultFragmentProjectValidationRule());

		// Finally perform global (inter-resource) validation
		for (ProjectValidationRule pvr : projectValidationRules) {
			IStatus status = pvr.validate(ofsProject);
			if(status instanceof MultiStatus) {
				returnStatus.addAll(status);
			} else {
				returnStatus.add(status);
			}
		}
		return returnStatus;
	}

}
