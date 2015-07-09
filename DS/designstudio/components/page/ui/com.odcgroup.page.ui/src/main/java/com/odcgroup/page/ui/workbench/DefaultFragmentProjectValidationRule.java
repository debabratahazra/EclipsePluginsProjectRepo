package com.odcgroup.page.ui.workbench;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Validates that, for a specific Entity or Dataset there is only one fragment
 * which is the default fragment (for each cardinality).
 * 
 * @author Gary Hayes
 */
public class DefaultFragmentProjectValidationRule implements ProjectValidationRule {

	/**
	 * Used to separate the domain entity QName from the cardinality in the key.
	 */
	private static final String KEY_SEPARATOR = "_";

	/** The OFS project to validate. */
	private IOfsProject ofsProject;

	/**
	 * Validates the OFS project.
	 * 
	 * @param ofsProject
	 *            The IOfsProject to validate
	 * @return the validation result
	 */
	public IStatus validate(IOfsProject ofsProject) {
		try {
			this.ofsProject = ofsProject;
			return validateDefaults();
		} catch (CoreException ce) {
			throw new PageException("Unable to validate the project", ce);
		}
	}

	/**
	 * Validates that no more than one resource exists for a particular Domain
	 * Entity / Dataset - Cardinality.
	 * 
	 * @return the validation result
	 * @throws CoreException
	 *             Thrown if an error occurs
	 */
	private IStatus validateDefaults() throws CoreException {
		MultiStatus returnStatus = new MultiStatus(OfsCore.PLUGIN_ID, IStatus.OK, "Project validation results", null);

		DefaultFragmentValidator dfv = new DefaultFragmentValidator(ofsProject);
		for (Map.Entry<String, List<IOfsModelResource>> entry : dfv.getNonUniqueFragments()) {
			String key = entry.getKey();
			String qName = key.substring(0, key.lastIndexOf(KEY_SEPARATOR));
			List<IOfsModelResource> list = entry.getValue();

			// Errors - Create
			for (IOfsModelResource r : list) {
				IStatus status = new Status(IStatus.ERROR, PageUIPlugin.PLUGIN_ID,
						"Cannot have two fragments with the same Cardinality defined as the Default Fragment. Domain Entity = "
								+ qName + ". Fragment = " + r.getURI().path());
				returnStatus.add(status);
			}
		}
		return returnStatus;
	}

}