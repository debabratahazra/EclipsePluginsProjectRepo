package com.odcgroup.workbench.generation.cartridge;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * This interface must be implemented by all OFS code generators. These generators are declared
 * by using the extension point com.odcgroup.workbench.generation.m2cCartridge
 * 
 * @author Kai Kreuzer
 */
public interface CodeGenerator {

	/** This method is called when a code generation is launched in T24. If there is an error in any file 
	 * the next file is processed and finally a list of error report is shown to user.
	 * 
	 * @param project The "master" Odyssey project, for which code should be generated
	 * @param resources The collection of IResources that the generator should consider for the code generation. Can include files and containers 
	 * @param outputFolder The container where the code should be generated to. This is usually a project root directory for the given category
	 * @param nonOkStatuses The list of error files displayed to the user at the end of code generation. 
	 */
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses);
}
 