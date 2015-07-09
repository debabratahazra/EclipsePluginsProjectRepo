package com.odcgroup.mdf.editor.model.utils;

import java.io.IOException;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.model.MdfProject;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.model.ModelFactory;


public class RepositorySynchronizer implements IResourceDeltaVisitor,
    IResourceChangeListener {
    private static final Logger LOGGER =
        Logger.getLogger(RepositorySynchronizer.class);

    public RepositorySynchronizer() {
    }

    /**
     * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
     */
    public void resourceChanged(final IResourceChangeEvent event) {
        final IResourceDelta delta = event.getDelta();

        if (delta != null) {
            try {
                delta.accept(this);
            } catch (CoreException e) {
                LOGGER.error("Could not synchronize repository", e);
            }
        }
    }

    /**
     * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
     */
    public boolean visit(IResourceDelta delta) throws CoreException {
        IResource res = delta.getResource();

        if (res instanceof IProject) {
            visitProject(delta, (IProject) res);
        } else if (res instanceof IFile) {
            visitFile(delta, (IFile) res);
        }

        return true;
    }

    private void visitFile(IResourceDelta delta, IFile file)
        throws CoreException {
        if (ModelFactory.INSTANCE.isModelFile(file)) {
            MdfProject project =
                MdfProjectRepository.getInstance().getProject(
                    file.getProject().getName());
            Resource resource = project.getResource(file, false);

            try {
                switch (delta.getKind()) {
                    case IResourceDelta.REMOVED:
                        if (resource != null) {
                            resource.unload();
                            project.getResources().remove(resource);
                            MdfProjectRepository.fireModelChangedEvent(
                                null, resource, ModelChangedEvent.ELEMENT_REMOVED,
                                new NullProgressMonitor());
                        }
                        break;

                    case IResourceDelta.CHANGED:
                    case IResourceDelta.ADDED:
                        if (resource == null) {
                            resource = project.createResource(file);
                            resource.load(Collections.EMPTY_MAP);
                            MdfProjectRepository.fireModelChangedEvent(
                                    null, resource, ModelChangedEvent.ELEMENT_ADDED,
                                    new NullProgressMonitor());
                        } else {
//                            resource.load(Collections.EMPTY_MAP);
//                            MdfProjectRepository.fireModelChangedEvent(
//                                    null, resource, ModelChangedEvent.ELEMENT_UPDATED,
//                                    new NullProgressMonitor());
                        }
                        break;
                }
            } catch (IOException e) {
                MdfCore.throwCoreException(e);
            }
        }
    }

    private void visitProject(IResourceDelta delta, IProject proj)
        throws CoreException {
        MdfProject project = null;

        switch (delta.getKind()) {
        case IResourceDelta.ADDED:
        case IResourceDelta.CHANGED:
            project = MdfProjectRepository.getInstance().getProject(proj.getName());

            if (project == null) {
                // Need to add a new or opened MDF project
                if (proj.isOpen() && proj.hasNature(MdfCore.NATURE_ID)) {
                    project = new MdfProject(proj);
                    MdfProjectRepository.getInstance().addProject(project);
                    MdfProjectRepository.fireModelChangedEvent(
                        null, project, ModelChangedEvent.ELEMENT_ADDED,
                        new NullProgressMonitor());
                    project.load(new NullProgressMonitor());
                }
            } else if (!proj.isOpen()) {
                // Project being closed
                MdfProjectRepository.getInstance().removeProject(proj.getName());
                MdfProjectRepository.fireModelChangedEvent(
                        null, project, ModelChangedEvent.ELEMENT_REMOVED,
                        new NullProgressMonitor());
            }

            break;

        case IResourceDelta.REMOVED:
            project =
                MdfProjectRepository.getInstance().removeProject(
                    proj.getName());
            if (project != null) {
                MdfProjectRepository.fireModelChangedEvent(
                    null, project, ModelChangedEvent.ELEMENT_REMOVED,
                    new NullProgressMonitor());
            }
            break;
        }
    }
}
