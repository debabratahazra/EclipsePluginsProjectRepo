package com.odcgroup.page.domain.ui.outline;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import com.odcgroup.page.model.Property;

/**
 * @author atr
 */
class DomainPropertyChangeListener {
	
	/**
	 * 
	 */
	private Property property;
	
	/**
	 * 
	 */
	private Adapter notifier = new Adapter() {
		
		private Notifier target;

		@Override
		public Notifier getTarget() {
	        return target;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return false;
		}

		@Override
		public void notifyChanged(Notification notification) {
			Object notifier = notification.getNotifier();
	        if (notifier instanceof Property) {
	        	notifyChange();
	        }
		}

		@Override
		public void setTarget(Notifier newTarget) {
			this.target = newTarget;
		}
		
	};
	
	/**
	 * 
	 */
	protected void notifyChange() {
	}
	
	/**
	 * 
	 */
	public void dispose() {
		if (property != null) {
			property.eAdapters().remove(notifier);
		}
	}
	
	/**
	 * @param property
	 */
	public DomainPropertyChangeListener(Property property) {
		this.property = property;
		if (property != null) {
			property.eAdapters().add(notifier);
		}
	}

}
