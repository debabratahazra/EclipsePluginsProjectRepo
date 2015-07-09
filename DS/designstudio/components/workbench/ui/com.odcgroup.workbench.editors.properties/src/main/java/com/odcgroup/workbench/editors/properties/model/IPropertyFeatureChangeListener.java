package com.odcgroup.workbench.editors.properties.model;

import java.util.EventListener;

/**
 *
 * @author pkk
 *
 */
public interface IPropertyFeatureChangeListener extends EventListener {
	
	/**
	 * @param event
	 */
	public void propertyChanged(IPropertyFeatureChangeEvent event);

}
