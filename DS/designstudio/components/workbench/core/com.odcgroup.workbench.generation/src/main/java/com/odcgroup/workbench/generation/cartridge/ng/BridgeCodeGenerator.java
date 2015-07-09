package com.odcgroup.workbench.generation.cartridge.ng;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

/**
 *
 * @author phanikumark
 *
 */
public interface BridgeCodeGenerator {
	
	public void run(IProject project, Collection<IResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses, IProgressMonitor monitor);

}
