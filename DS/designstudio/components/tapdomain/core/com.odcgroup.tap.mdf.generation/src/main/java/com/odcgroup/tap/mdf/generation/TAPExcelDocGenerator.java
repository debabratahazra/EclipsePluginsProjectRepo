package com.odcgroup.tap.mdf.generation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.common.mdf.generation.MDFGenerator;
import com.odcgroup.documentation.generation.DocGenerator;
import com.odcgroup.tap.mdf.generation.xls.TAPExcelWriter;
import com.odcgroup.workbench.core.IOfsModelResource;


public class TAPExcelDocGenerator extends MDFGenerator implements DocGenerator {

    public TAPExcelDocGenerator() {
        super(new TAPExcelWriter());
    }
    
    //DS-1614
	public boolean isValidForProject(IProject project) {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.documentation.generation.DocGenerator#run(org.eclipse.core.resources.IProject, java.util.Collection, org.eclipse.core.resources.IFolder, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProject project,
			Collection<IOfsModelResource> modelResources, IFolder outputFolder,
			IProgressMonitor monitor) throws CoreException,
			InterruptedException {
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		run(monitor, project, modelResources, outputFolder, nonOkStatuses);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		if (monitor != null && monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}

}
