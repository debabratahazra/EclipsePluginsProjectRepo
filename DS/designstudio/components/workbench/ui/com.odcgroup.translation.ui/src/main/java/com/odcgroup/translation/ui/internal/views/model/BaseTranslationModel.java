package com.odcgroup.translation.ui.internal.views.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public abstract class BaseTranslationModel implements ITranslationModel {

	/** Delegate used to implement property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);

	/**
	 * Report a property change to registered listeners.
	 * 
	 * @param property
	 *            the name of the property that changed
	 * @param oldValue
	 *            the old value of this property
	 * @param newValue
	 *            the new value of this property
	 */
	protected void firePropertyChange(String property, Object oldValue, Object newValue) {
		if (pcsDelegate.hasListeners(property)) {
			pcsDelegate.firePropertyChange(property, oldValue, newValue);
		}
	}	
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		pcsDelegate.addPropertyChangeListener(listener);
	}

	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		pcsDelegate.addPropertyChangeListener(property, listener);
	}

	@Override
	public void release() {
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener != null) {
			pcsDelegate.removePropertyChangeListener(listener);
		}
	}

	@Override
	public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
		if (listener != null) {
			pcsDelegate.removePropertyChangeListener(property, listener);
		}
		
	}

}
