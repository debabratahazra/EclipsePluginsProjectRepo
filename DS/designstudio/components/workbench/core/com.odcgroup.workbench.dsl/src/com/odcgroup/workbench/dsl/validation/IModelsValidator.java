package com.odcgroup.workbench.dsl.validation;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IModelsValidator {

	/**
	 * @param models
	 * @param monitor
	 */
	void validate(Set<String> models, IProgressMonitor monitor);
}
