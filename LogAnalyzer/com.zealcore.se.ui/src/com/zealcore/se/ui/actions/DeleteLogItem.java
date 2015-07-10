package com.zealcore.se.ui.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class DeleteLogItem extends AbstractObjectDelegate {

    private static final String DELETE_COMMAND_ID = "org.eclipse.ui.edit.delete";

    public DeleteLogItem() {}

    @Override
    public void runSafe(final IAction action) {
        if (this.guardFail()) {
            return;
        }
        /*
         * if (this.getFirstElement() instanceof ILogSessionWrapper) { final
         * ILogSessionWrapper log = (ILogSessionWrapper) this
         * .getFirstElement(); if (MessageDialog
         * .openQuestion(Display.getDefault().getActiveShell(), "Delete",
         * "Are you sure you want to remove this item from the case file?")) {
         * log.getCaseFile().removeLog(log); } }
         */
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
}
