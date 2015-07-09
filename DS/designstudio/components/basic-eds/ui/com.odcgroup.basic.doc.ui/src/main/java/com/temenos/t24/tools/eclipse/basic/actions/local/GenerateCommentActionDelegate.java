package com.temenos.t24.tools.eclipse.basic.actions.local;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.GenerateCommentsWizard;

/**
 * entry point when the user hit the action "generate comments"
 * 
 * @author sbharathraja
 * 
 */
public class GenerateCommentActionDelegate implements IWorkbenchWindowActionDelegate {

    IWorkbenchWindow activeWindow = null;

    public void run(IAction proxyAction) {
        // Shell shell = activeWindow.getShell();
        GenerateCommentsWizard wizard = new GenerateCommentsWizard();
        WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
        dialog.create();
        if (dialog.open() == Window.OK) {
            System.out.println("really finished");
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
        activeWindow = window;
    }
}
