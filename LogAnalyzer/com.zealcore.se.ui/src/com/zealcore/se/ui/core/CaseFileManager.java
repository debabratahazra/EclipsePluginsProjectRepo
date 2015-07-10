/*
 * 
 */
package com.zealcore.se.ui.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IProgressMonitor;

import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ImportExceptionList;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.internal.DirectoryCaseFile;
import com.zealcore.se.ui.internal.DirectoryLogSession;
import com.zealcore.se.ui.internal.ILogFileSuspect;
import com.zealcore.se.ui.internal.LogViewInput;

public final class CaseFileManager implements IAdapterFactory {

    private static final String IMPORT_FAILED = "Import failed";

    private static final String FAILED_TO_GET_LOGSET_FOR_FILE = "Failed to get logset for file: ";

    private static final CaseFileManager INSTANCE = new CaseFileManager();

    private static final Class<?>[] SUPPORTED_TYPES = { ICaseFile.class,
            ILogSessionWrapper.class, ILogFileSuspect.class,
            ITimeCluster.class, };

    // Singleton - no inherit instances
    private CaseFileManager() {}

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(final Object adaptableObject,
            final Class adapterType) {

        try {
            if (adapterType.equals(ITimeCluster.class)) {
                final Object subject = getAdapter(adaptableObject,
                        ILogSessionWrapper.class);
                if (subject instanceof ILogSessionWrapper) {
                    return ((ILogSessionWrapper) subject).getDefaultViewSet();
                }
            }

            if (adapterType.equals(ICaseFile.class)) {
                if (adaptableObject instanceof IContainer) {
                    final IContainer suspect = (IContainer) adaptableObject;
                    if (CaseFileManager.isCaseFile(suspect)) {
                        return DirectoryCaseFile.valueOf(suspect);
                    }
                }
            }

            if (adapterType.equals(ILogSessionWrapper.class)) {

                if (adaptableObject instanceof IFolder) {
                    final IFolder folder = (IFolder) adaptableObject;
                    if (!folder.exists()) {
                        return null;
                    }
                    try {
                        folder.refreshLocal(IResource.DEPTH_ZERO, null);
                    } catch (final CoreException e) {
                        return null;
                    }
                    if (DirectoryLogSession.isLogSession(folder)) {
                        return DirectoryLogSession.valueOf(folder);
                    }

                }
            }

            /*
             * Checks for logfilesuspect, which is a IFile instance with a
             * LogSessionWrapper (DirectoryLogsession) as parent, which is
             * currently not configured with an importer.
             */
            if (adapterType.equals(ILogFileSuspect.class)) {
                if (adaptableObject instanceof IFile) {
                    final IFile file = (IFile) adaptableObject;
                    if (file.getName().equals(DirectoryLogSession.CONFIG_FILE)
                            || (file.getName()
                                    .equalsIgnoreCase(DirectoryLogSession.BIN_FILE))
                            || file.getName().equals(
                                    DirectoryCaseFile.CASE_FILE_CONFIG)) {
                        return null;
                    }
                    final IContainer parent = file.getParent();
                    if (getAdapter(parent, ILogSessionWrapper.class) != null) {
                        return new ILogFileSuspect() {
                            public IFile getSuspect() {
                                return file;
                            }

                        };
                    }
                }
            }
        } catch (final RuntimeException e) {
            if (e instanceof ImportException) {
                reportException((ImportException) e);
            } else if (e instanceof ImportExceptionList) {
                for (ImportException ie : ((ImportExceptionList) e)
                        .getExceptions()) {
                    reportException(ie);
                }
            } else {
                SeUiPlugin.reportUnhandledRuntimeException(this.getClass(), e,
                        true);
            }
        }
        return null;
    }

    private void reportException(final ImportException e) {
        SeUiPlugin.reportError(IMPORT_FAILED, e);
        if (e.equals(e.getCause())) {
            SeUiPlugin.logError(e.getCause());
        } else {
            SeUiPlugin.logError(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Class<?>[] getAdapterList() {

        return CaseFileManager.SUPPORTED_TYPES.clone();
    }

    /**
     * Gets the singleton instance.
     * 
     * @return the singleton instance
     */
    public static CaseFileManager getInstance() {
        return CaseFileManager.INSTANCE;
    }

    /**
     * Checks if element is case file.
     * 
     * @param element
     *            the element to test
     * 
     * @return true, if element is case file
     */
    public static boolean isCaseFile(final Object element) {
        if (element instanceof IResource) {
            final IResource folder = (IResource) element;
            return DirectoryCaseFile.isCaseFile(folder);
        }
        return false;
    }

    /**
     * Creates a new case file representing the specified {@link IContainer}
     * value.
     * 
     * @param container
     *            the container
     * @param monitor
     * 
     * @return the I case file
     */
    public static ICaseFile createCaseFile(final IContainer container,
            final IProgressMonitor monitor) {
        return DirectoryCaseFile.valueOf(container);
    }

    /**
     * Gets input by UID. May return null if none is registered.
     * 
     * @param uid
     *            the uid
     * 
     * @return the input by UID
     */
    public static ILogViewInput getInputByUID(final String uid) {
        if (uid == null) {
            return null;
        }

        final UUID uuid = UUID.fromString(uid);
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        for (final IProject p : root.getProjects()) {
            if (DirectoryCaseFile.isCaseFile(p)) {
                final DirectoryCaseFile cf = DirectoryCaseFile.valueOf(p);
                for (final ILogSessionWrapper wrapper : cf.getLogs()) {
                    if (wrapper.getId().equals(uuid)) {
                        return LogViewInput.valueOf(wrapper);
                    }
                }
            }
        }

        return null;
    }

    public static Logset getLogset(final IFile child) {
        if (child == null || !child.exists()) {
            throw new IllegalArgumentException(FAILED_TO_GET_LOGSET_FOR_FILE
                    + child + ", file is null or does not exists!");
        }
        final IContainer parent = child.getParent();
        if (!DirectoryLogSession.isLogSession(parent)) {
            return null;
        }
        ILogSessionWrapper logSessionWrapper = DirectoryLogSession
                .valueOf(parent);
        if (logSessionWrapper == null) {
            throw new IllegalArgumentException(FAILED_TO_GET_LOGSET_FOR_FILE
                    + child.getName());
        }
        return Logset.valueOf(logSessionWrapper.getId());
    }

    public static List<ITimeCluster> getSynchedTimeClusters() {
        List<ITimeCluster> cluster = new ArrayList<ITimeCluster>();

        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        for (final IProject p : root.getProjects()) {
            if (DirectoryCaseFile.isCaseFile(p)) {
                final DirectoryCaseFile cf = DirectoryCaseFile.valueOf(p);
                Object[] children = cf.getChildren(p);
                for (int i = 0; i < children.length; i++) {
                    if (children[i] instanceof IResource) {
                        IResource res = (IResource) children[i];
                        if (DirectoryLogSession.isLogSession(res)) {
                            ILogSessionWrapper logsession = DirectoryLogSession
                                    .valueOf(res);
                            ITimeCluster viewSet = logsession
                                    .getDefaultViewSet();
                            if (viewSet.isChained()) {
                                cluster.add(viewSet);
                            }
                        }
                    }
                }
            }
        }
        return cluster;
    }
}
