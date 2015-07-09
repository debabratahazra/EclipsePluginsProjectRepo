package com.temenos.t24.tools.eclipse.basic.wizards.data;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FileUtil;

public class T24DataFileCreationWizard extends Wizard implements INewWizard {

    private final String CREATE_FILE_WIZARD_TITLE = "New data file wizard.";
    private T24DataFileCreationPage page;
    private IStructuredSelection initialSelection;

    @Override
    public void addPages() {
        setWindowTitle(CREATE_FILE_WIZARD_TITLE);
         page = new T24DataFileCreationPage(initialSelection);
         addPage(page);
    }
    

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        initialSelection = selection;
    }

    @Override
    public boolean performFinish() {
          createDataFile();
        return true;
    }


    
    private void createDataFile() {

        String dataFileName = page.getDataRecordKey();
        String location = page.getLocation();
        String filePath = location + "\\" + dataFileName;
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.existFile(filePath)) {
            String errMessage = "File " + dataFileName + " already exists. Are you sure you want to overwrite?";
            T24MessageDialog dialog = new T24MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "File Exists", errMessage, MessageDialog.WARNING);
            if (dialog.open() != InputDialog.OK) {
                return ;
            }
        }
        int status = fileUtil.saveToFile("", filePath, true);
        if (status < 0) {
            T24MessageDialog errorDialog = new T24MessageDialog(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error Dialog",
                    "Failed to Create the File "+dataFileName, MessageDialog.ERROR);
            errorDialog.open();
            return;
        }
       EditorDocumentUtil.getIFile(filePath);
    }


    
}
