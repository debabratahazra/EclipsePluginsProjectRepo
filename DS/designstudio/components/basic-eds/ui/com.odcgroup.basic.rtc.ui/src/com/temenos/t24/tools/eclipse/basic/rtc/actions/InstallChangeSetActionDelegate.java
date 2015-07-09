/**
 * 
 */
package com.temenos.t24.tools.eclipse.basic.rtc.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.wizards.rtc.install.InstallChangeSetWizard;

//import com.temenos.t24.tools.eclipse.basic.wizards.rtc.install.InstallChangeSetWizard;

/**
 * @author ssethupathi
 * 
 */
public class InstallChangeSetActionDelegate implements IWorkbenchWindowActionDelegate {

    public void dispose() {
    }

    public void run(IAction action) {
        InstallChangeSetWizard wizard = new InstallChangeSetWizard();
        WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
        dialog.create();
        if (dialog.open() == Window.OK) {
            System.out.println("really finished");
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    public void init(IWorkbenchWindow window) {
    }
}
