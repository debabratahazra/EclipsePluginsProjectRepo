package com.odcgroup.page.model.translation;

import java.lang.ref.WeakReference;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import com.odcgroup.translation.core.translation.InheritableTranslation;

public class DomainPropertyListener implements Adapter {
	
	private WeakReference<InheritableTranslation> translationReference;
	
	/** */
	private Notifier target = null;
	
	public DomainPropertyListener(InheritableTranslation translation) {
		this.translationReference = new WeakReference<InheritableTranslation>(translation);
	}

	@Override
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}

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
		InheritableTranslation translation = translationReference.get();
		if(translation == null) {
			getTarget().eAdapters().remove(this);
		}
		else {
			translation.invalidateDelegate();
		}
	}
}