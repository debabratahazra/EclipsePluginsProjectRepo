package com.odcgroup.t24.common.importer.ui.wizard;

/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.common.importer.IContainerSelector;

/**
 * Provides content for a tree viewer that shows only containers.
 */
public class ContainerContentProvider implements ITreeContentProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContainerContentProvider.class);
	private IContainerSelector containerSelector;
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
                		if (project.getNature(JavaCore.NATURE_ID)!=null) {
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
				if ((containerSelector.level() == -1) || (level < containerSelector.level())) {
				try {
					List<IResource> children = new ArrayList<IResource>();
					IResource[] members = container.members();
					for (int i = 0; i < members.length; i++) {
						if (members[i].getType() != IResource.FILE) {
							if (container.getType() == IResource.PROJECT) {
								Set<String> outputFolders = getOutputFolders(container.getProject());
								if (!outputFolders.contains(members[i].getName())) {
									children.add(members[i]);
								}
							} else {
								children.add(members[i]);
							}
						}
					}
					return children.toArray();
				} catch (CoreException e) {
					// this should never happen because we call #isAccessible
					// before invoking #members
				}
			}
		}
		}
        return new Object[0];
    }

    private Set<String> getOutputFolders(IProject project) {
    	final Set<String> classpathEntries = new LinkedHashSet<String>();
    	final IJavaProject javaProject = JavaCore.create(project);
    	try {
    		for (IClasspathEntry entry : javaProject.getResolvedClasspath(true)) {
    			if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
    				final IPath output = entry.getOutputLocation();
    				if (output != null) {
    					classpathEntries.add(output.removeFirstSegments(1).toString());
    				}
    			}
    		}
    		final IPath output = javaProject.getOutputLocation();
    		classpathEntries.add(output.removeFirstSegments(1).toString());
    	} catch (JavaModelException e) {
    		 LOGGER.error("Failed to find output folders from java project due to ",e);
    	}
    	return classpathEntries;
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

    public ContainerContentProvider(IContainerSelector containerSelector) {
    	this.containerSelector = containerSelector;
    }
}

