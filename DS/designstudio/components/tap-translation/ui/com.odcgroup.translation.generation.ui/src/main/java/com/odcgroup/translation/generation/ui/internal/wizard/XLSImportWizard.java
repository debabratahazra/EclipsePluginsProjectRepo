package com.odcgroup.translation.generation.ui.internal.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.workbench.core.OfsCore;

/**
 * A Wizard to import translations from an excel document.
 * 
 * @author atr
 *
 */
public class XLSImportWizard extends Wizard implements INewWizard {

	/** The translations importer wizard page*/
	private XLSImportWizardPage page;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		page = new XLSImportWizardPage("Import Excel translations");
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		try {
			new XLSImporter().importTranslations(page.getFilename());
		} catch (Exception ex) {
			TranslationUICore.getDefault().logError(ex.getMessage(), ex);
			IStatus status = new Status(Status.ERROR, OfsCore.PLUGIN_ID, Status.OK, "See Error Log for more details.", ex);
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "The Import Translations from Excel failed.", 
					status);
		}
		return true;
	}

}
