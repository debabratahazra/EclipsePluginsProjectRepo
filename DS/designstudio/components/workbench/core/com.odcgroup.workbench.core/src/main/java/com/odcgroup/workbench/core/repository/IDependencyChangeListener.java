package com.odcgroup.workbench.core.repository;

import org.eclipse.core.resources.IProject;

/**
 * A listener which is notified of changes in dependencies
 * 
 * @author Kai Kreuzer
 *
 */
public interface IDependencyChangeListener {
	
	/** This method is called by the dependency manager whenever dependencies of a project have
	 * changed, so that the listener can react to it.
	 * 
	 * @param project the project where the dependencies have changed
	 */
	public void dependenciesChanged(IProject project);
	
}
