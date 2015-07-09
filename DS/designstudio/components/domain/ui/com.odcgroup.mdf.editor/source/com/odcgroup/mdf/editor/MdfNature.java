package com.odcgroup.mdf.editor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class MdfNature implements IProjectNature {
	
	private IProject project = null;

    /**
     * Constructor for MdfNature
     */
    public MdfNature() {
        super();
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
    	// TODO: Add specific configuration
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
		// TODO: Remove specific configuration
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject project) {
    	this.project = project;
    }

}
