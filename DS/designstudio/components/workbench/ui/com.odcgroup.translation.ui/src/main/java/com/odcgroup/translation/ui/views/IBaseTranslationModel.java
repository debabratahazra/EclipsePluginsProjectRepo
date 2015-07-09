package com.odcgroup.translation.ui.views;

import java.beans.PropertyChangeListener;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public interface IBaseTranslationModel {
	
	/**
	 * Add a PropertyChangeListener to the model change for all
	 * properties. Has no effect if an identical listener is already registered.
	 * <p>
	 * Model listener are informed about state changes that affect the
	 * rendering of the viewer that display the translations
	 * </p>
	 * If <code>listener</code> is null, no exception is thrown and no action is
	 * taken.
	 * 
	 * @param listener
	 *            The ITranslationChangeListener to be added
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * @param property
	 * @param listener
	 */
	void addPropertyChangeListener(String property, PropertyChangeListener listener);

	/**
	 * Removes a listener to this model. 
	 * 
	 * If <code>listener</code> is null, or was never added, no exception is
	 * thrown and no action is taken.
	 * 
	 * @param listener
	 *            The PropertyChangeListener to be removed
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * @param property
	 * @param listener
	 */
	void removePropertyChangeListener(String property, PropertyChangeListener listener);

	/**
	 * The <code>ITranslationViewer<code> invokes this method to notify the model 
	 * that it can release all its internal resources.
	 */
	void release();

}
