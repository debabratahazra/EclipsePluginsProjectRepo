package com.odcgroup.workbench.ui.wizards;

import java.io.InputStream;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.ui.importer.AbstractImporter;
import com.odcgroup.workbench.ui.internal.wizards.ImportFileSelectionPage;

abstract public class AbstractOcsImportWizard extends Wizard implements IImportWizard {
	
	ImportFileSelectionPage wizardPage;
	
	public AbstractOcsImportWizard() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		IFolder folder = wizardPage.createNewFolder();
        if (folder == null || !folder.exists())
            return false;

        InputStream workflowContent = wizardPage.getInitialContents();

        AbstractImporter importer = createImporter();
        boolean success = importer.runImport(workflowContent, folder, wizardPage.getSelectedFileName());
	    try {
			if(!success && folder.members().length==0) {
				// if an error occured and the output folder is empty, delete it again
				wizardPage.deleteNewFolder();
			}
		} catch (CoreException e1) {
			// ignore exception
		}
        try {
			folder.refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			// ignore exception
		}
	    
	    return success;
	}
	 
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Triple'A Plus " + StringHelper.toFirstUpper(getModelName()) + " Import Wizard"); //NON-NLS-1
		setNeedsProgressMonitor(true);

		wizardPage = new ImportFileSelectionPage("Select file to import", getModelName()); //NON-NLS-1
		if(selection.getFirstElement() instanceof IOfsModelFolder) {
			IFolder folder = (IFolder) ((IOfsModelFolder) selection.getFirstElement()).getResource();
			wizardPage.setOutputFolder(folder);
		}
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages();
       	addPage(wizardPage);
    }

    /**
     * Subclasses must implement this method. It simply returns the name of the model that
     * the imported files are based on.
     * 
     * @return name of the model
     */
    abstract protected String getModelName();
    
    /**
     * Subclasses must implement this method. It creates and returns an Importer class that
     * will be called when the wizard finishes.
     * 
     * @return the Importer to perform the action
     */
    abstract protected AbstractImporter createImporter();
    
}
