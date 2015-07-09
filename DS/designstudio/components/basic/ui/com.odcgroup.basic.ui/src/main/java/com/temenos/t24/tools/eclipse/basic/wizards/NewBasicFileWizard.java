package com.temenos.t24.tools.eclipse.basic.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * Simple wizard to allow creation of BASIC files.
 * 
 * @author lfernandez
 *
 */
public class NewBasicFileWizard extends Wizard implements INewWizard {
    private final String CREATE_FILE_WIZARD_TITLE = "New BASIC file wizard.";
    
    private NewFileWizardPage  newFileWizardPage;
    private IStructuredSelection initialSelection;

    @Override
    public void addPages() {
        setWindowTitle(CREATE_FILE_WIZARD_TITLE);
        newFileWizardPage = new NewFileWizardPage("", initialSelection);
        addPage(newFileWizardPage);
    }
    

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        initialSelection = selection;
    }

    @Override
    public boolean performFinish() {
        /*
         * Remove the file extension, just in case the user has added it.
         * Then add the standard BASIC extension for this plug-in. 
         */
        String basicFilenameNoPrefix = StringUtil.removeBasicExtension(newFileWizardPage.getFileName());
        newFileWizardPage.setFileName(basicFilenameNoPrefix + PluginConstants.LOCAL_BASIC_DOT_PREFIX);
        newFileWizardPage.createNewFile();
        return true;
    }
    
}
