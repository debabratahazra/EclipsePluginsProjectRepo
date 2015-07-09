package com.odcgroup.t24.validation.ui.action;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.statushandlers.StatusManager;

import com.odcgroup.t24.validation.ui.T24ValidationUICore;
import com.odcgroup.workbench.dsl.ui.validation.ModelsValidationHelperUI;

public class T24ValidateAction extends Action implements IObjectActionDelegate {

	private static final String ACTION_ID 		= "VALIDATE_MODELS";  		//$NON-NLS-1$
	private static final String ACTION_LABEL 	= "Validate Models"; 	//$NON-NLS-1$
	private static final String ACTION_IMAGE 	= "icons/valid.png";		//$NON-NLS-1$
	
	private IStructuredSelection selection = null;
	
	private void handleException(Exception ex) {
		int severity = (ex instanceof CoreException) 
				? (((CoreException)ex).getStatus().getSeverity())
				: Status.ERROR;
		IStatus status = new Status(
				severity, 
				T24ValidationUICore.PLUGIN_ID, 
				"Validation Error", //$NON-NLS-1$
				ex);
		StatusManager.getManager().handle(status, StatusManager.LOG|StatusManager.SHOW);
	}
	
	public T24ValidateAction() {
		setId(ACTION_ID);
		setText(ACTION_LABEL);
		setImageDescriptor(T24ValidationUICore.imageDescriptorFromPlugin(T24ValidationUICore.PLUGIN_ID, ACTION_IMAGE));
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	@Override
	public void run() {
		run(this);
	}

	@Override
	public void run(IAction action) {
		try {
			ModelsValidationHelperUI.startValidationJob(
					ModelsValidationHelperUI.collectSelectedResources(selection));
		} catch (Exception ex) {
			handleException(ex);
		}		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

	/**
	 * @param selection
	 */
	public void selectionChanged(final ISelection selection) {
		selectionChanged(this, selection);
	}

}
