package com.zealcore.se.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import com.zealcore.se.ui.SeUiPlugin;

public class AbstractResourceChangeListener implements IResourceChangeListener,
        IResourceDeltaVisitor {

    public void resourceChanged(final IResourceChangeEvent event) {
        try {
            if (event.getDelta() == null) {
                return;
            }
            IResourceDelta[] deltas = event.getDelta().getAffectedChildren();
            while ((deltas.length != 0)
                    && (deltas.length != 2 )) {
                deltas = deltas[0].getAffectedChildren(); 
            }
            boolean rename = false;
            // Rename check
            if ((deltas.length == 2)
                     && ((deltas[0].getMovedFromPath() != null)
                     || (deltas[0].getMovedToPath() != null))
                     && ((deltas[1].getMovedFromPath() != null)
                     || (deltas[1].getMovedToPath() != null))) {
                String path1 = deltas[0].getMovedFromPath() != null ? deltas[0].getMovedFromPath().toPortableString() : deltas[0].getMovedToPath().toPortableString();
                String path2 = deltas[1].getMovedToPath() != null ? deltas[1].getMovedToPath().toPortableString() : deltas[1].getMovedFromPath().toPortableString();
                String path11 = deltas[0].getResource().getFullPath().toPortableString();
                String path22 = deltas[1].getResource().getFullPath().toPortableString();
                if (path1.equals(path22) && path2.equals(path11)) {
                    rename = true;
                }
            }
            // Not rename check for delete logset
            boolean notDeleted = true;
            if (!rename) {
                deltas = event.getDelta().getAffectedChildren();
                IResourceDelta[] prevDeltas = null;
                while (deltas.length != 0) {
                    prevDeltas = deltas;
                    deltas = deltas[0].getAffectedChildren(); 
                }
                if (prevDeltas != null) {
                    for (int i = 0; i < prevDeltas.length; i++) {
                        if (prevDeltas[i].getKind() == IResourceDelta.REMOVED) {
                            notDeleted = false;
                        } else {
                            notDeleted = true;
                            break;
                        }
                    }
                }
            }
            if (!notDeleted) {
                rename = true;
            }
            start(rename);
            event.getDelta().accept(this);
            finish();
        } catch (final CoreException e) {
            SeUiPlugin.logError(e);
        }
    }

    protected void start(boolean rename) {

    }

    protected void finish() {

    }

    public boolean visit(final IResourceDelta delta) {

        if (delta.getKind() == IResourceDelta.REMOVED) {

            if (delta.getResource() instanceof IFile) {
                fileRemoved(delta);
            } else if (delta.getResource() instanceof IFolder) {
                folderRemoved(delta);
            }
        }

        if (delta.getKind() == IResourceDelta.ADDED) {

            if (delta.getResource() instanceof IFile) {
                fileAdded(delta);
            } else if (delta.getResource() instanceof IFolder) {
                folderAdded(delta);
            }
        }

        if (delta.getKind() == IResourceDelta.CHANGED) {
            if (delta.getResource() instanceof IFile) {
                fileReplaced(delta);
            } else if (delta.getResource() instanceof IFolder) {
                folderReplaced(delta);
            }
        }
        return true;
    }

    protected void folderReplaced(final IResourceDelta delta) {

    }

    private void fileReplaced(final IResourceDelta delta) {

    }

    protected void fileAdded(final IResourceDelta delta) {}

    protected void folderRemoved(final IResourceDelta delta) {}

    protected void folderAdded(final IResourceDelta delta) {}

    protected void fileRemoved(final IResourceDelta delta) {}
}
