package com.zealcore.se.iw;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.IWorkbenchPart;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.iw.wizard.ConfigureImportWizard;
import com.zealcore.se.ui.Messages;
import com.zealcore.se.ui.UseAsLogFile;
import com.zealcore.se.ui.actions.AbstractObjectDelegate;
import com.zealcore.se.ui.core.CaseFileManager;

public class ConfigureImportWizardAction extends AbstractObjectDelegate {

    private IStructuredSelection selection;

    private IWorkbenchPart targetPart;

    public ConfigureImportWizardAction() {}

    @Override
    public void setActivePart(final IAction action,
            final IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }

    @Override
    public void runSafe(final IAction action) {
        
        if (Logset.isDisabled()) {
            ErrorDialog.openError(new Shell(), Messages.LICENSE_ERROR,
                    Messages.IMPORT_ERROR,
                    new Status(IStatus.ERROR, "com.zealcore.se.iw",
                            IStatus.ERROR, Messages.LICENSE_EXCEPTION + ".",
                            Logset.getException()));

            return;
        }
        
        if (this.selection == null) {
            return;
        }

        final IFile ifile = getSelectedFile();
        File file = toFile(ifile);

        final GenericImportRegistry registry = SeCorePlugin.getDefault()
                .getService(GenericImportRegistry.class);
        final GenericTextImportData importData = registry.getImportData(file);
        ConfigureImportWizard wizard;
        if (importData == null) {
            wizard = new ConfigureImportWizard(file, ifile);
        } else {
            wizard = new ConfigureImportWizard(file, ifile, importData);
        }
        final WizardDialog dialog = new WizardDialog(this.targetPart.getSite()
                .getShell(), wizard);
        dialog.setHelpAvailable(true);

		dialog.create();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
				ConfigureImportWizard.HELP_ID);

        if (dialog.open() != Window.OK) {
            return;
        }

        final GenericTextImportData newImportData = wizard.getImportData();
        registry.addImportData(newImportData);
        UseAsLogFile useAsLogFile = new UseAsLogFile();
        useAsLogFile.handleImport(ifile);
    }

    private File toFile(final IFile file) {
        return file.getRawLocation().toFile();
    }

    private IFile getSelectedFile() {
        final IFile ifile = (IFile) this.selection.getFirstElement();
        if (ifile == null || !ifile.exists()) {
            return null;
        }
        return ifile;

    }

    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        this.selection = (IStructuredSelection) selection;

        IFile ifile = getSelectedFile();
        if (ifile != null) {
            Logset logset = CaseFileManager.getLogset(ifile);
            if (logset == null) {
            	action.setEnabled(false);
            	return;
            }
            
            if (logset.isLocked()) {
                action.setEnabled(false);
                return;
            } else {
                action.setEnabled(true);
            }
        }

        if (ifile == null || IFWFacade.isImported(ifile)) {
            action.setEnabled(false);
        } else {
            action.setEnabled(true);
        }
    }
}
