package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.ProblemManagement;
import com.odcgroup.pageflow.model.TransitionAction;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class ProcessSelectionDialogCreator implements IPropertySelectionDialogCreator {

	private static final String DSPROCESS_INTERFACE = "\"com.odcgroup.wui.pageflow.DSProcess\"";
	private static final String VALIDATION_PROCESS_INTERFACE = "\"com.odcgroup.uif.validation.ValidationProcess\"";

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator#createDialog(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public IPropertySelectionDialog createDialog(Shell shell, EObject element) {
		TransitionAction action = (TransitionAction) element;
		boolean filterByValidation = false;
		if (action.getProblemManagement().getValue()==ProblemManagement.VALIDATION) {
			filterByValidation = true;
		}
		ProcessSelectionDialog dialog = ProcessSelectionDialog.createDialog(shell, filterByValidation);
		dialog.setTitle(Messages.TransitionActionBrowseDialogTitle); 
		if(filterByValidation){
			
			dialog.setMessage(Messages.TransitionActionBrowseDialogMessage + " "+VALIDATION_PROCESS_INTERFACE);
		}
		else{
			dialog.setMessage(Messages.TransitionActionBrowseDialogMessage+ " "+DSPROCESS_INTERFACE); 
		}
		return dialog;
	}

}
