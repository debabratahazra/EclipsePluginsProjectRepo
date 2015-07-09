package com.odcgroup.process.diagram.custom.dialogs;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowModelLookup;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator;

/**
 *
 * @author pkk
 *
 */
public class PageflowSelectionDialogCreator implements IPropertySelectionDialogCreator {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator#createDialog(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public IPropertySelectionDialog createDialog(Shell shell, EObject element) {
		IOfsProject repository = OfsResourceHelper.getOfsProject(element.eResource());
        PageflowModelLookup lookup = new PageflowModelLookup(repository);
        List<Pageflow> pageflows = lookup.getAllPageflows();

        PageflowModelSelectionDialog dialog = PageflowModelSelectionDialog.createDialog(shell, pageflows);
		dialog.setMultipleSelection(false);
		return dialog;
	}

}
