package com.odcgroup.workbench.core.init;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

/** 
 * This interface has to be implemented by initializer classes that provide checks and behavior
 * for model projects. 
 * It allows changes on a newly created model project to initialize it as needed
 * and allows updating existing projects, in case their configuration is not up to date to the
 * latest release. 
 * 
 * @author Kai Kreuzer
 *
 */
public interface ProjectInitializer {

	/**
	 * This method is called directly after the creation of a new OFS project.
	 * @param project The OFS project to initialize
	 * @param force <code>TRUE</code> if command should performed even if resources already exist; 
	 * <code>FALSE</code> if the user should be asked if he wants to continue if resources already exist
	 * (must not be used in a non-UI task!)
	 */
	public void initialize(IProject project, boolean force, IProgressMonitor monitor) throws CoreException;
	
	/**
	 * This method is called on existing OFS projects in case of product updates or configuration
	 * changes. It should check, if the configuration is up-to-date and perform the necessary
	 * changes.
	 * @param project The OFS project to update
	 */
	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException;

	/**
	 * This method checks if the project configuration is up to date. If this is not the case,
	 * it provides a list of <code>IStatus</code> objects that list the existing problems
	 * @param project
	 * @param delta
	 * @return
	 */
	public IStatus checkConfiguration(IProject project);

	/**
	 * This method checks if the project configuration is up to date. If this is not the case,
	 * it provides a list of <code>IStatus</code> objects that list the existing problems
	 * @param project
	 * @param delta changes done in the workspace.
	 * @return
	 */
	public IStatus checkConfiguration(IProject project, IResourceDelta delta);
	
	/**
	 * This method checks the prerequisites for a successful initialization. If the prerequisites are
	 * not satisfied (i.e. some resources exist already) an according message should be presented to the
	 * user. The user might then be asked to decide whether he wants to continue with the initialization
	 * or not.
	 * This method must not be called from a non-UI task!
	 * 
	 * @param project The OFS project that should be checked
	 * @return
	 */
	public boolean canBeInitialized(IProject project);

	/**
	 * Sets the marker id for problem markers contributed by this initializer
	 *
	 * @param markerId the id of the marker to use
	 */
	public void setMarkerId(String markerId);
	
	/**
	 * Returns the marker if used for problem markers contributed by this initializer
	 *
	 * @return the marker id
	 */
	public String getMarkerId();
	
	/**
	 * Allows the initializer to define on which additional projects resource changes should trigger
	 * a new configuration check of the model project, e.g. the code gen initializer uses this to
	 * watch the gen project for changes.
	 * 
	 * @param project the model project for which the additional projects should be watched
	 * @return an array of projects to watch
	 */
	public IProject[] getProjectsToWatch(IProject project);

}
