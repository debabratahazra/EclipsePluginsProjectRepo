/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.resource.ImageDescriptor;

import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.SystemExplorerNature;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

public class DirectoryCaseFile implements ICaseFile2 {

    private static final int PRIME = 31;

    private static final String CASE_FILE_PROPERTY = "CASE_FILE_PROPERTY";

    private static final QualifiedName QN_CASEFILE_PROPERTY = new QualifiedName(
            SeUiPlugin.PLUGIN_ID, DirectoryCaseFile.CASE_FILE_PROPERTY);

    public static final String CASE_FILE_CONFIG = ".casefile";

    private final IContainer container;

    private IViewSetChain viewSetChain;

    DirectoryCaseFile(final IContainer container) {
        this.container = container;
        try {
            container.setSessionProperty(
                    DirectoryCaseFile.QN_CASEFILE_PROPERTY, this);
        } catch (final CoreException e) {
            throw new RuntimeException(e);
        }

    }

    public ILogSessionWrapper addLog(final String file) {
        if (this.container.findMember(file) == null) {
            try {
                final ILogSessionWrapper create = DirectoryLogSession.create(
                        this.container, file);
                return create;
            } catch (final CoreException e) {
                // TODO Do something more intelligent
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    public ILogSessionWrapper[] getLogs() {

        final Collection<ILogSessionWrapper> wrappers = new ArrayList<ILogSessionWrapper>();
        if (container.exists()) {
            try {
                for (final IResource iResource : this.container.members()) {
                    if (DirectoryLogSession.isLogSession(iResource)) {
                        wrappers.add(DirectoryLogSession.valueOf(iResource));
                    }
                }
            } catch (final CoreException e) {
                throw new RuntimeException(e);
            }

        }
        return wrappers.toArray(new ILogSessionWrapper[wrappers.size()]);
    }

    public IResource getResource() {
        return this.container;
    }

    public void removeLog(final ILogSessionWrapper log) {
        if (log instanceof DirectoryLogSession) {
            final DirectoryLogSession dls = (DirectoryLogSession) log;
            try {
                dls.getFolder().delete(true, null);
            } catch (final CoreException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new AssertionError();
        }

    }

    public void saveState() {}

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(final Class adapter) {
        if (adapter.isInstance(getResource())) {
            return getResource();
        }

        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    /**
     * {@inheritDoc}
     */
    public Object[] getChildren(final Object o) {

        try {
            return this.container.members();
        } catch (final CoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public ImageDescriptor getImageDescriptor(final Object object) {
        return IconManager
                .getImageDescriptor(IconManager.CASEFILE_SMALL_IMG_ID);
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel(final Object o) {
        return getResource().getName();
    }

    /**
     * {@inheritDoc}
     */
    public Object getParent(final Object o) {
        return getResource().getParent();
    }

    /**
     * Checks if resource is a case file.
     * 
     * @param resource
     *                the resource to test
     * 
     * @return true, if resource is case file
     */
    public static boolean isCaseFile(final IResource resource) {

        if (!(resource instanceof IProject)) {
            return false;

        }
        final IProject subject = (IProject) resource;
        if (!subject.isOpen()) {
            return false;
        }
        if (!subject.exists()) {
            return false;
        }
        try {
            final IProjectNature nature = subject
                    .getNature(SystemExplorerNature.NATURE_ID);
            return nature != null;
        } catch (final CoreException e) {
            SeUiPlugin.logError(e);
        }
        return false;
    }

    /**
     * Returns a new DirectoryCaseFile representing the specified IFolder value.
     * the IFolder must be a casefile
     * 
     * 
     * @param folder
     *                the folder
     * 
     * @return the directory case file or null if the folder is not a System
     *         Debugger Project
     * @throws IllegalArgumentException
     *                 if the container is not a Enea Optima Log Analyzer
     *                 Project
     */
    public static DirectoryCaseFile valueOf(final IContainer folder) {
        if (!DirectoryCaseFile.isCaseFile(folder)) {
            return null;
        }
        try {

            if (!folder.getWorkspace().isTreeLocked()) {
                folder.refreshLocal(IResource.DEPTH_ZERO, null);
            }

            final Object sessionProperty = folder
                    .getSessionProperty(DirectoryCaseFile.QN_CASEFILE_PROPERTY);
            if (sessionProperty instanceof DirectoryCaseFile) {
                DirectoryCaseFile subject = (DirectoryCaseFile) sessionProperty;
                if (subject.container.exists()) {
                    return subject;
                }

            }
        } catch (final CoreException e) {
            throw new RuntimeException(e);
        }
        return new DirectoryCaseFile(folder);
    }

    @Override
    public int hashCode() {
        final int prime = DirectoryCaseFile.PRIME;
        int result = 1;
        result = prime * result
                + (this.container == null ? 0 : this.container.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final DirectoryCaseFile other = (DirectoryCaseFile) obj;
        if (this.container == null) {
            if (other.container != null) {
                return false;
            }
        } else if (!this.container.equals(other.container)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getLabel(this);
    }

    public IViewSetChain getViewSetChain() {

        if (this.viewSetChain == null) {
            this.viewSetChain = new ViewSetChain();
        }

        return this.viewSetChain;

    }
}
