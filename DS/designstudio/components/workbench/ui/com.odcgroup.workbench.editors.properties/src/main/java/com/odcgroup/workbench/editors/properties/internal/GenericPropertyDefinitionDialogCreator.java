package com.odcgroup.workbench.editors.properties.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialogCreator;

/**
 *
 * @author pkk
 *
 */
public class GenericPropertyDefinitionDialogCreator implements IPropertyDefinitionDialogCreator {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.controls.IPropertyDefinitionDialogCreator#createDialog(org.eclipse.swt.widgets.Shell, org.eclipse.emf.ecore.EObject, boolean)
	 */
	public IPropertyDefinitionDialog createDialog(Shell shell, EObject element, EObject parent, boolean update) {
		return new GenericPropertyDefinitionDialog(shell, element, parent, update);
	}

}
