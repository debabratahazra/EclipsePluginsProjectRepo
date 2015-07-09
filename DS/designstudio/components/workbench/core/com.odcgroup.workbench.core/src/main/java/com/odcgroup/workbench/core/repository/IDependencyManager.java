package com.odcgroup.workbench.core.repository;

import java.net.URI;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * This interface defines the methods that need to be provided by a dependency mechanism for
 * Design Studio.
 * A dependency manager is responsible for resolving declared dependencies between model containers
 * and to uniquely identify them.
 *
 * @author Kai Kreuzer
 *
 */
public interface IDependencyManager {

	/**
	 * This method returns the set of all (resolved) model container dependencies in a transitive fashion,
	 * i.e. the set also contains the dependencies of all dependencies. 
	 * 
	 * @param container the model container to get the dependencies for  
	 * @return a set of required model containers
	 */
	public Set<IOfsModelContainer> getDependencies(IOfsProject ofsProject);

	/**
	 * Retrieves the names of all sub-directories of a given path inside a model container
	 * 
	 * @param container the container to check for sub-directories
	 * @param path the path of the parent directory to search in
	 * @return the set of names of the sub-directories
	 */
	public Set<String> getSubDirectories(IOfsModelContainer container, IPath path);

	/**
	 * This method provides the identifier for a project.
	 * If the project is not managed by the dependency mechanism, the return value will be null.
	 * 
	 * @param project The project to get the identifier for
	 * @return a valid container identifier or null, if project is not managed
	 */
	public IContainerIdentifier getIdentifier(IProject project);

	/**
	 * Retrieves all file URIs in a given path inside a model container
	 * 
	 * @param container the container to check for files
	 * @param path the path of the parent directory to search in
	 * @return the set of file URIs in the given path
	 */
	public Set<URI> getFileURIs(IOfsModelContainer container, IPath path);
	
	/**
	 * This method determines whether a project has unresolved dependencies. This method
	 * should not be long-running.
	 * 
	 * @param project the project to check for unresolved dependencies
	 * @return true, if there are unresolved dependencies, false otherwise
	 */
	public boolean hasUnresolvedDependencies(IProject project);
	
	/**
	 * If there are unresolved dependencies, this method should be able to return an
	 * array of identifiers for these dependencies.
	 * 
	 * @param project the project to check for unresolved dependencies
	 * @return an array of container identifiers of all unresolved dependencies
	 */
	public IContainerIdentifier[] getUnresolvedDependencies(IProject project);
	
	/**
	 * Disables the auto artifact resolution mode of the artifact manager. 
	 * This is especially helpful for unit tests and headless mode.
	 */
	public void disableAutoResolution();

	/**
	 * Enables the auto artifact resolution mode of the artifact manager.
	 */
	public void enableAutoResolution();

	/**
	 * Resolves dependencies of all projects in the workspace. Makes only sense if
	 * auto-resolution is turned off.
	 */
	public void resolveDependencies();

	/**
	 * Resolves dependencies of a given project in the workspace. Makes only sense if
	 * auto-resolution is turned off.
	 */
	public void resolveDependencies(IProject project);

	/**
	 * Resolves dependencies of given projects in the workspace. Makes only sense if
	 * auto-resolution is turned off.
	 */
	public void resolveDependencies(IProject[] projects);

	/**
	 * Adds a dependency change listener
	 * 
	 * @param listener the listener to add
	 */
	public void addDependencyChangeListener(IDependencyChangeListener listener);

	/**
	 * removes a dependency change listener
	 * 
	 * @param listener the listener to remove
	 */
	public void removeDependencyChangeListener(IDependencyChangeListener listener);

	/**
	 * only returns as soon as no background resolution job is running anymore
	 */
	public void waitForResolutionJob();
	
	/**
	 * Reload the settings xml used by maven
	 */
	public void resetMavenSettings();

}
