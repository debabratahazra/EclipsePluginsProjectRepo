package com.zealcore.se.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.actions.AbstractObjectDelegate;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.internal.DirectoryLogSession;

public class DeleteProjectAction extends AbstractObjectDelegate {

    private static final String DELETE_COMMAND_ID = "org.eclipse.ui.edit.delete";

    @Override
    public void runSafe(IAction action) {

        if (this.guardFail()) {
            return;
        }
        IHandlerService handlerService = (IHandlerService) PlatformUI
                .getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .getActivePart().getSite().getService(IHandlerService.class);
        if (this.getFirstElement() instanceof IProject) {
            final IProject log = (IProject) this.getFirstElement();
            try {
                IResource[] members = log.getProject().members();
                for (int i = 0; i < members.length; i++) {
                    IResource iResourceFolder = members[i];
                    if (iResourceFolder instanceof IFolder) {
                        IFolder folder = (IFolder) iResourceFolder;
                        IResource[] files = folder.members();
                        for (int j = 0; j < files.length; j++) {
                            IResource iResourceFile = files[j];
                            if (iResourceFile instanceof IFile) {
                                IFile ifile = (IFile) iResourceFile;
                                Logset logset = CaseFileManager
                                        .getLogset(ifile);
                                if (logset != null
                                        && !(logset.getLogs().isEmpty())) {
                                    logset.closeIMBinaryStream();
                                    ((DirectoryLogSession) DirectoryLogSession
                                            .valueOf(folder))
                                            .removeAllListeners();
                                }
                            }
                        }
                    }
                }
                handlerService.executeCommand(DELETE_COMMAND_ID, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
