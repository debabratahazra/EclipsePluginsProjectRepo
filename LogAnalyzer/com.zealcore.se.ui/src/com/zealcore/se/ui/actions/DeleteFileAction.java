package com.zealcore.se.ui.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import com.zealcore.se.ui.actions.AbstractObjectDelegate;

public class DeleteFileAction extends AbstractObjectDelegate {

    private static final String DELETE_COMMAND_ID = "org.eclipse.ui.edit.delete";

    public DeleteFileAction() {}

    @Override
    public void runSafe(IAction action) {

        if (this.guardFail()) {
            return;
        }

        IHandlerService handlerService = (IHandlerService) PlatformUI
                .getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .getActivePart().getSite().getService(IHandlerService.class);
        try {
            handlerService.executeCommand(DELETE_COMMAND_ID, null);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NotDefinedException e) {
            e.printStackTrace();
        } catch (NotEnabledException e) {
            e.printStackTrace();
        } catch (NotHandledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
    }

}
