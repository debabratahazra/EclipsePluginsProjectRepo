package com.odcgroup.t24.common.importer;

import org.eclipse.core.resources.IContainer;

/**
 * @author atripod
 */
public interface IContainerSelector extends ISelector {

	/**
	 * Sets the container where the model must be imported
	 * @param container 
	 */
	void setContainer(IContainer container);
	
	/**
	 * @return the current selected container, or null.
	 */
	IContainer getContainer();
	
	/**
	 * @return true if a container has been selected.
	 */
	boolean isContainerSet();
	
	/**
	 * return the greatest accepted level for a container (0 based)
	 * -1 means all levels
	 */
	int level(); 
	
	/**
	 * return the lowest accepted level for a container (0 based)
	 */
	int minSelectedLevel();
	
	/**
	 * The model type can used to filter the selection dialog. 
	 * Ex: only model folders of the given type are displayed.
	 * @return the model type
	 */
	String getModelType();

	/**
	 * Change the name of the model.
	 * @param text
	 */
	void setModelName(String text);
	
}
