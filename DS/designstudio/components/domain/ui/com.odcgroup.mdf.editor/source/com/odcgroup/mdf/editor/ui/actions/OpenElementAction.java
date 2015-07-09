package com.odcgroup.mdf.editor.ui.actions;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PartInitException;

import com.odcgroup.mdf.metamodel.MdfModelElement;


/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class OpenElementAction extends MdfActionDelegate {
    private static final Logger LOGGER =
        Logger.getLogger(OpenElementAction.class);
    private MdfModelElement currentElement = null;

    /**
     * Constructor for OpenElementAction
     */
    public OpenElementAction() {
        super();
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        try {
            getEditionSupport().openModelEditor(currentElement);
        } catch (PartInitException e) {
            LOGGER.error(e, e);
        }
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        Object currentSelection = null;

        if ((selection != null) && !selection.isEmpty()) {
            if (selection instanceof IStructuredSelection) {
                currentSelection =
                    ((IStructuredSelection) selection).getFirstElement();
            }
        }

        if (currentSelection instanceof MdfModelElement) {
            currentElement = (MdfModelElement) currentSelection;
        } else {
            currentElement = null;
        }

        action.setEnabled(currentElement != null);
    }
}
