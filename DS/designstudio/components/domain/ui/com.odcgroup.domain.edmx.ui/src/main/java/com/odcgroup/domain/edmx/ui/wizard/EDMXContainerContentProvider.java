package com.odcgroup.domain.edmx.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.domain.edmx.IEDMXContainerSelector;

/**
 * Provides content for a tree viewer that shows only containers.
 */
public class EDMXContainerContentProvider implements ITreeContentProvider {
	
	// TODO should be a parameter (filter) of this class
	protected static final String OFS_NATURE = "com.odcgroup.workbench.core.OfsNature"; //$NON-NLS-1$
	
	private IEDMXContainerSelector containerSelector;
	
    /**
     * The visual part that is using this content provider is about
     * to be disposed. Deallocate all allocated SWT resources.
     */
    public void dispose() {
    }

    /*
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object element) {
    	
        if (element instanceof IWorkspace) {
            // check if closed projects should be shown
            IProject[] allProjects = ((IWorkspace) element).getRoot().getProjects();

            List<IProject> accessibleProjects = new ArrayList<IProject>();
            for (int i = 0; i < allProjects.length; i++) {
            	IProject project = allProjects[i]; 
                if (project.isOpen()) {
                	try {
                		if (project.getNature(OFS_NATURE)!=null) {
                			accessibleProjects.add(allProjects[i]);
                		}
                	} catch (CoreException ignore) {
                	}
                }
            }
            return accessibleProjects.toArray();
        } else if (element instanceof IContainer) {
            IContainer container = (IContainer) element;
            if (container.isAccessible()) {
            	int level = container.getFullPath().segmentCount()-1;
            	if ((containerSelector.level() == -1) || (level <= containerSelector.level())) {
	                try {
	                    List<IResource> children = new ArrayList<IResource>();
	                    IResource[] members = container.members();
	                    for (int i = 0; i < members.length; i++) {
	                        if (members[i].getType() != IResource.FILE) {
	                        	if (container.getType() == IResource.PROJECT) {
	                        		if (members[i].getName().equals(containerSelector.getModelType())) {
	                                    children.add(members[i]);
	                        		}
	                        	} else {
	                                children.add(members[i]);
	                        	}
	                        }
	                    }
	                    return children.toArray();
	                } catch (CoreException e) {
	                    // this should never happen because we call #isAccessible before invoking #members
	                }
	            }
            }
        }
        return new Object[0];
    }

    /*
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object element) {
        return getChildren(element);
    }

    /*
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        if (element instanceof IResource) {
			return ((IResource) element).getParent();
		}
        return null;
    }

    /*
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    /*
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * Creates a new ContainerContentProvider.
     */
    public EDMXContainerContentProvider(IEDMXContainerSelector containerSelector) {
    	this.containerSelector = containerSelector;
    }
}

