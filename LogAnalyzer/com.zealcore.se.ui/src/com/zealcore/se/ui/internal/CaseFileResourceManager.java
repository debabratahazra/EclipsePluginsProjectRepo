package com.zealcore.se.ui.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ImportExceptionList;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.core.AbstractSafeUIJob;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.editors.LogsetEditor;

public class CaseFileResourceManager implements IResourceChangeListener,
        IResourceDeltaVisitor {

    private static final String IMPORT_FAILED = "Import failed";

    private final List<IFile> changedFiles = new ArrayList<IFile>();

    public void resourceChanged(final IResourceChangeEvent event) {
        changedFiles.clear();

        final IResourceDelta delta = event.getDelta();
        if (delta != null) {
            try {
                delta.accept(this);
            } catch (final CoreException e) {
                throw new RuntimeException(e);
            }
        }

        importChangedLogsets();

        changedFiles.clear();
    }

    private void importChangedLogsets() {
        final Set<Logset> sessions = new HashSet<Logset>();

        for (final IFile file : changedFiles) {
            final Logset logset = CaseFileManager.getLogset(file);
            if (logset != null && logset.contains(file)) {
                sessions.add(logset);
            }
        }

        for (final Logset logset : sessions) {
            try {
                logset.refresh();
            } catch (final RuntimeException e) {
                if (e instanceof ImportException) {
                    reportException((ImportException) e);
                    logset.refresh();
                } else if (e instanceof ImportExceptionList) {
                    for (ImportException ie : ((ImportExceptionList) e)
                            .getExceptions()) {
                        reportException(ie);
                    }
                    logset.refresh();
                } else {
                    SeUiPlugin.reportUnhandledRuntimeException(this.getClass(),
                            e, true);
                }
            }
        }
    }

    protected static void reportException(final ImportException e) {
        SeUiPlugin.reportError(IMPORT_FAILED, e);
        if (e.equals(e.getCause())) {
            SeUiPlugin.logError(e.getCause());
        } else {
            SeUiPlugin.logError(e);
        }
    }

    public boolean visit(final IResourceDelta delta) {

        if (delta.getKind() == IResourceDelta.REMOVED) {

            if (delta.getResource() instanceof IFile) {
                fileRemoved(delta);
            } else if (delta.getResource() instanceof IFolder) {
                folderRemoved(delta);
            }
        }

        if (delta.getKind() == IResourceDelta.ADDED
                || delta.getKind() == IResourceDelta.REPLACED) {

            if (delta.getResource() instanceof IFile) {
                fileAdded(delta);
            } else if (delta.getResource() instanceof IFolder) {
                folderAdded(delta);
            }
        }

        if (delta.getKind() == IResourceDelta.CHANGED) {
            if (delta.getResource() instanceof IFile) {
                fileChanged((IFile) delta.getResource());

            }
        }
        return true;
    }

    private void fileChanged(final IFile file) {
        changedFiles.add(file);

    }

    private void folderAdded(final IResourceDelta delta) {

    }

    private void fileAdded(final IResourceDelta delta) {
    }

    void fileRemoved(final IResourceDelta delta) {}

    void folderRemoved(final IResourceDelta delta) {
        // TODO Yet to be implemented in a smart way - views should be smarter
        new AbstractSafeUIJob("Closing Affected Windows") {
            @Override
            public IStatus runInUIThreadSafe(final IProgressMonitor monitor) {
                for (final IWorkbenchWindow window : PlatformUI.getWorkbench()
                        .getWorkbenchWindows()) {
                    for (final IWorkbenchPage page : window.getPages()) {
                        for (final IEditorReference reference : page
                                .getEditorReferences()) {
                            if (reference.getId()
                                    .equals(LogsetEditor.EDITOR_ID)) {
                                IEditorInput input;
                                try {
                                    input = reference.getEditorInput();
                                    if (input instanceof ILogViewInput) {
                                        final ILogViewInput oldInput = (ILogViewInput) input;
                                        if (oldInput.getLog().equals(
                                                delta.getResource())) {
                                            page
                                                    .closeEditors(
                                                            new IEditorReference[] { reference },
                                                            false);
                                        }
                                    }
                                } catch (final PartInitException e) {
                                    SeUiPlugin.logError(e);
                                }
                            }
                        }
                    }
                }
                return Status.OK_STATUS;
            }
        }.schedule();
    }
}
