package com.odcgroup.workbench.core.repository;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeListener;

import com.odcgroup.workbench.core.IOfsProject;

public interface IOfsProjectManager extends IResourceChangeListener {

	/**
	 * Returns the singleton ofs model project for a given project
	 * 
	 * @param project
	 *            the project for which the ofs model project is requested
	 * @return the singleton instance of the ofs model project
	 */
	public abstract IOfsProject getOfsProject(IProject project);

	/**
	 * Returns a set of all ofs projects currently in the workspace
	 * 
	 * @return the set of all ofs projects
	 */
	public Set<IOfsProject> getAllOfsProjects();
	
	/**
	 * Removes the cached IOfsProject instance from the internal cache,
	 * so that it is recreated on the next request.
	 * 
	 * @param project the project to remove from cache
	 */
	public void invalidateOfsProject(IProject project);
}