package com.odcgroup.documentation.generation;

import java.util.Collection;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * This interface must be implemented by all Odyssey documentation generators. These generators are declared
 * by using the extension point com.odcgroup.documentation.generation.cartridge
 * 
 * @author Kai Kreuzer
 * @since 1.30.5
 *
 */
public interface DocGenerator {

	/** This method is called when a documentation generation is launched.
	 * 
	 * @param project The "master" Odyssey project, for which documentation should be generated
	 * @param modelResources The collection of IResources that the generator should consider for the code generation. Can include files and containers 
	 * @param outputFolder The folder where the documentation should be generated to.
	 * @throws CoreException
	 */
    public void run(IProject project, Collection<IOfsModelResource> modelResources, IFolder outputFolder, IProgressMonitor monitor)
    	throws CoreException, InterruptedException;
    
    /**
     * This method is called to check whether this generator is valid
     * for the given project
     * Ds-1614
     * 
     * @param project
     * @return
     */
    public boolean isValidForProject(IProject project);
}
