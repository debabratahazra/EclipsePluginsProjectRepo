package com.odcgroup.workbench.core;

/**
 * This class is used to uniquely identify an IOfsModelContainer instance.
 *
 * @author Kai Kreuzer
 *
 */
public interface IContainerIdentifier {
	
	/**
	 * @return the group id of the model container
	 */
	String getGroupId();

	/**
	 * returns the name of the model container
	 * @return the name of the model container
	 */
	String getName();
	
	/**
	 * returns the version of the model container
	 * @return the version of the model container
	 */
	String getVersion();

}
