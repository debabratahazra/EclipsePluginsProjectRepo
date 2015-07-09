package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialogCreator;

public class TransitionMappingDefinitionDialogCreator implements
		IPropertyDefinitionDialogCreator {
	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialogCreator#createDialog
	 * (org.eclipse.swt.widgets.Shell, org.eclipse.emf.ecore.EObject, boolean)
	 */
	@Override
	public IPropertyDefinitionDialog createDialog(Shell shell, EObject element,
			EObject parent, boolean update) {
		return new TransitionMappingDefDialog(shell, element, parent, update);
	}

}
