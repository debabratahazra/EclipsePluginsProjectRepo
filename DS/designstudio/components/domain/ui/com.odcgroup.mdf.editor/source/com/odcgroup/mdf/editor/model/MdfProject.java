package com.odcgroup.mdf.editor.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.IActionFilter;

import com.odcgroup.mdf.editor.model.utils.ProjectLoader;
import com.odcgroup.mdf.metamodel.MdfDomain;


/**
 * @version 2.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini </a>
 */
public class MdfProject extends ResourceSetImpl implements IAdaptable {
    private final IProject project;

    /**
     * Constructor for MdfProject
     */
    public MdfProject(IProject project) {
        super();
        this.project = project;
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
        if (adapter.isInstance(project)) {
            return project;
        }

        if (adapter == IActionFilter.class) {
            final IActionFilter filter =
                (IActionFilter) project.getAdapter(IActionFilter.class);

            if (filter == null)
                return null;

            return new IActionFilter() {
                    public boolean testAttribute(
                        Object target, String name, String value) {
                        return filter.testAttribute(project, name, value);
                    }
                };
        }

        return project.getAdapter(adapter);
    }

    public String getName() {
        return project.getName();
    }

    public IProject getProject() {
        return project;
    }

    public Resource getResource(IFile file, boolean loadOnDemand) {
        URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
        return getResource(uri, loadOnDemand);
    }

    public Resource createResource(MdfDomain domain) {
        String name = ModelFactory.INSTANCE.getModelFileName(domain);
        IFile file = MdfUtility.getModelsFolder(project).getFile(name);
        Resource resource = createResource(file);
        resource.getContents().add((EObject) domain);
        return resource;
    }

    public Resource createResource(IFile file) {
        URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
        return createResource(uri);
    }

    public void load(IProgressMonitor monitor) throws CoreException {
        ProjectLoader loader = new ProjectLoader(this);
        monitor.beginTask(
            "Loading models for project " + getName(), IProgressMonitor.UNKNOWN);

        try {
            MdfResourceVisitor visitor = new MdfResourceVisitor(loader);
            IFolder modelsFolder = MdfUtility.getModelsFolder(project);
            modelsFolder.accept(visitor, IResource.DEPTH_ONE, IResource.NONE);
        } finally {
            monitor.done();
        }
    }
}
