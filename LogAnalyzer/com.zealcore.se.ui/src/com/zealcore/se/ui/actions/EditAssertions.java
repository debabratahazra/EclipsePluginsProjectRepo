package com.zealcore.se.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.views.SystemNavigator;
import com.zealcore.se.ui.wizards.NewAssertionWizard;

public class EditAssertions extends AbstractObjectDelegate {

    private Shell shell;

    /**
     * @see IActionDelegate#run(IAction)
     */
    // public void run(final IAction action) {s
    @Override
    public void runSafe(final IAction action) {
        ISelection selection = getPart().getSite().getWorkbenchWindow()
                .getSelectionService().getSelection();
        IPath path = null;
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.getFirstElement() instanceof IProject) {
                path = ((IProject) sel.getFirstElement()).getLocation().append(
                    SystemNavigator.ASSERTION_FILE_NAME);
            } else {
                return;
            }
        } else {
            return;
        }
        NewAssertionWizard newAssertionWizard = new NewAssertionWizard(path);

        final Shell dialog = new Shell(shell);
        dialog.setImage(IconManager.getImageDescriptor(
                IconManager.ZEALCORE_LOGGO_SMALL_IMG_ID).createImage());
        dialog.setText("Assertions");
        newAssertionWizard.createPageControls(dialog);

        dialog.open();
        dialog.pack();
        while (!dialog.isDisposed()) {
            if (!dialog.getDisplay().readAndDispatch()) {
                dialog.getDisplay().sleep();
            }
        }
    }
}
