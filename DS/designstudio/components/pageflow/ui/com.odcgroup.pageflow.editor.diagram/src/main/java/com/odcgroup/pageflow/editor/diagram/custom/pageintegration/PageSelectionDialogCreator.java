package com.odcgroup.pageflow.editor.diagram.custom.pageintegration;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.model.Model;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator;

/**
 *
 * @author pkk
 *
 */
public class PageSelectionDialogCreator implements IPropertySelectionDialogCreator {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator#createDialog
	 * (org.eclipse.swt.widgets.Shell, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IPropertySelectionDialog createDialog(Shell shell, EObject element) {
		IOfsProject repository = OfsResourceHelper.getOfsProject(element.eResource());
		PageModelLookup lookup = new PageModelLookup(repository);
        List<Model> pages = lookup.getAllPages();
        PageSelectionDialog dialog = PageSelectionDialog.createDialog(shell, pages.toArray());
        dialog.setMultipleSelection(false);
        dialog.setTitle(Messages.ViewStateViewDefinitionModelSelectionDialogTitle);
		return dialog;
	}

}
