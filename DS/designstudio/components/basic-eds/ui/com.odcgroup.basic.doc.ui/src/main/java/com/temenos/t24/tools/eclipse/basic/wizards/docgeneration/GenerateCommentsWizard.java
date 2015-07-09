package com.temenos.t24.tools.eclipse.basic.wizards.docgeneration;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file.GenerateCommentsManager;

/**
 * Wizard for comments generation
 * 
 * @author sbharathraja
 * 
 */
public class GenerateCommentsWizard extends Wizard {

    /** wizard page */
    private GenerateCommentsWizardPage generateCommentsPage;
    /** array of selected projects */
    private String[] selectedProject;
    /** target folder path */
    private String targetLocation;
    /** input folder location */
    private String inputLocation;

    public GenerateCommentsWizard() {
        setWindowTitle("Generate Doc Wizard");
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        generateCommentsPage = new GenerateCommentsWizardPage();
        addPage(generateCommentsPage);
    }

    /**
     * get the resources needed to strip out comments
     */
    private void getResources() {
        selectedProject = generateCommentsPage.getProjects();
        targetLocation = generateCommentsPage.getTargetPath();
        inputLocation = generateCommentsPage.getInputPath();
    }

    @Override
    public boolean performFinish() {
        getResources();
        final GenerateCommentsManager GCManager = new GenerateCommentsManager(selectedProject, inputLocation, targetLocation);
        try {
            // progress bar
            getContainer().run(true, true, new IRunnableWithProgress() {

                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    monitor.beginTask("Generate Docs", IProgressMonitor.UNKNOWN);
                    GCManager.initCommentsStripOut();
                    monitor.done();
                    openFinishDialog();
                }
            });
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean performCancel() {
        boolean answer = MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Generate Docs",
                "Sure you want to cancel the operation ?");
        if (answer)
            return true;
        else
            return false;
    }

    /**
     * opening the finishing information dialog
     */
    private void openFinishDialog() {
        PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

            public void run() {
                IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                if (window != null) {
                    Shell activeShell = window.getShell();
                    if (activeShell != null) {
                        T24MessageDialog dialog = new T24MessageDialog(activeShell, "Doc Extraction",
                                "Extracting Docs has completed Sucessfully", MessageDialog.INFORMATION);
                        dialog.open();
                    }
                }
            }
        });
    }
}
