package com.odcgroup.ocs.support.ui.actions;

import java.util.Iterator;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.ocs.support.ui.builder.PatchBuilder;

public class AddPatcherAction implements IObjectActionDelegate {

    private ISelection selection;

    private IWorkbenchPart targetPart;

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        if (selection instanceof IStructuredSelection) {
            for (Iterator<?> it = ((IStructuredSelection) selection).iterator(); it
                    .hasNext();) {
                Object element = it.next();
                IProject project = null;
                if (element instanceof IProject) {
                    project = (IProject) element;
                } else if (element instanceof IAdaptable) {
                    project = (IProject) ((IAdaptable) element)
                            .getAdapter(IProject.class);
                }
                if (project != null) {
                    addBuilder(project);
                }
            }
        }
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

    /**
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }

    /**
     * Toggles sample nature on a project
     * 
     * @param project
     *            to have sample nature added or removed
     */
    private void addBuilder(IProject project) {
        try {
            IProjectDescription desc = project.getDescription();
            ICommand[] commands = desc.getBuildSpec();

            for (int i = 0; i < commands.length; ++i) {
                if (commands[i].getBuilderName().equals(PatchBuilder.BUILDER_ID)) {
                    return;
                }
            }

            // add builder to project
            ICommand command = desc.newCommand();
            command.setBuilderName(PatchBuilder.BUILDER_ID);
            ICommand[] newCommands = new ICommand[commands.length + 1];

            // Add it after other builders.
            System.arraycopy(commands, 0, newCommands, 0, commands.length);
            newCommands[commands.length] = command;
            desc.setBuildSpec(newCommands);
            project.setDescription(desc, null);
        } catch (CoreException e) {
            ErrorDialog.openError(targetPart.getSite().getShell(), null, e
                    .getMessage(), e.getStatus());
        }
    }

}
