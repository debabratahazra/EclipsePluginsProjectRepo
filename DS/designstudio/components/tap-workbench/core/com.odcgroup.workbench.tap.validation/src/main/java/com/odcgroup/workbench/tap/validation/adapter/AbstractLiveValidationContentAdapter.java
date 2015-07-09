package com.odcgroup.workbench.tap.validation.adapter;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

/**
 * This abstract class can implemented in order to use live constraint validation.
 * 
 * It needs to be registered on a ResourceSet, a Resource or an EObject by calling
 * <p>
 * <code>resourceSet.eAdapters().add(liveValidationContentAdapter);</code>
 * <p>
 * The method <code>handleFailureStatus()</code> can then react on validation problems
 * as required.
 *
 * @author Kai Kreuzer
 *
 */
abstract public class AbstractLiveValidationContentAdapter extends EContentAdapter {
	
	private ILiveValidator validator = null;

	public AbstractLiveValidationContentAdapter() {}

	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);
		
		if (validator == null) {
			validator = 
				(ILiveValidator)ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		}
				
		IStatus status = validator.validate(notification);
		
		if (!status.isOK()) {
			handleFailureStatus(status);
		}
	}
	
	abstract protected void handleFailureStatus(IStatus status);
}
