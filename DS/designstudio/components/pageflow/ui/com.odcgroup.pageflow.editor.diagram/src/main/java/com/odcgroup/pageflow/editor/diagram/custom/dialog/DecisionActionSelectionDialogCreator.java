package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class DecisionActionSelectionDialogCreator implements IPropertySelectionDialogCreator {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator#createDialog(org.eclipse.swt.widgets.Shell, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IPropertySelectionDialog createDialog(Shell shell, EObject element) {
		return new DecisionActionSelectionDialog(shell, element);
	}

}
