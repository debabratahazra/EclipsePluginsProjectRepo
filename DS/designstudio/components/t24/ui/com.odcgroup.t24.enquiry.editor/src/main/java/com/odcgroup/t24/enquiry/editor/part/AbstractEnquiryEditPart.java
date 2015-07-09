package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public abstract class AbstractEnquiryEditPart extends AbstractGraphicalEditPart implements Adapter {

	private Notifier notifier;


	@Override
	public Notifier getTarget() {
		return notifier;
	}

	@Override
	public void setTarget(Notifier notifier) {
		this.notifier = notifier;

	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type.equals(getModel().getClass());
	}

	@Override
	protected void registerModel() {
		super.registerModel();
		EObject model = (EObject) getModel();
		if (model != null) {
			model.eAdapters().add(this);
		}
	}

	@Override
	public void activate() {
		if (isActive())
			return;
		((EObject)getModel()).eAdapters().add(this);
		super.activate();
	}

	@Override
	public void notifyChanged(Notification notification) {
		refresh();		
	}
}
