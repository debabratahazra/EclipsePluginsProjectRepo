package com.odcgroup.workbench.core;


/**
 * This is the interface for a general model container. The typical model container is an IOfsProject,
 * but the dependency mechanism might also provide model containers that are jar or zip files being
 * provisioned from somewhere external of the workspace.
 *
 * The key features of such a model container are that they can return a name and have a technical
 * unique identifier.
 *
 * @author Kai Kreuzer
 *
 */
public interface IOfsModelContainer {
	
	/**
	 * Returns the (technical) identifier of the container 
	 * 
	 * @return the container identifier
	 */
	public IContainerIdentifier getIdentifier();	
	
	/**
	 * Returns the name of the container (typically the project name) 
	 * 
	 * @return the name of the container
	 */
	public String getName();

}
