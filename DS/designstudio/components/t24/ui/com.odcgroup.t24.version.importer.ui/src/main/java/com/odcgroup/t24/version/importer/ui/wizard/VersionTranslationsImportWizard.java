package com.odcgroup.t24.version.importer.ui.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.t24.version.importer.ui.VersionImporterUICore;
import com.odcgroup.t24.version.translation.VersionTranslationsExcelImporter;
import com.odcgroup.workbench.core.OfsCore;

/**
 * A Wizard for importing translations from an excel document back to versions/fields.
 */
public class VersionTranslationsImportWizard extends Wizard implements INewWizard {

	/** The translations importer wizard page*/
	private VersionTranslationImportWizardPage page;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		page = new VersionTranslationImportWizardPage("Import Excel translations");
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		try {
			new VersionTranslationsExcelImporter().importTranslations(page.getFilename());
		} catch (Exception ex) {
			VersionImporterUICore.getDefault().logError(ex.getMessage(), ex);
			IStatus status = new Status(Status.ERROR, OfsCore.PLUGIN_ID, Status.OK, "See Error Log for more details.", ex);
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"Error", "The Import Translations from Excel failed.", 
					status);
		}
		return true;
	}

}
