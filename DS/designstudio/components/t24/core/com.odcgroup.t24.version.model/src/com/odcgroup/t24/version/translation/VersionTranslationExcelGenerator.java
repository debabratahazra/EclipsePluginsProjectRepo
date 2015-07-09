package com.odcgroup.t24.version.translation;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.documentation.generation.DocGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;


public class VersionTranslationExcelGenerator extends AbstractCodeGenerator implements DocGenerator {
	
	List<IStatus> errorStatuses = null;
	
	private static String PLUGIN_ID = "com.odcgroup.t24.version.model";
	
    @Override
	public void doRun(IProject project, IContainer outputContainer, Collection<IOfsModelResource> modelResources, List<IStatus> nonOkStatuses) {
		
    	// keep only version model resources
		Collection<IOfsModelResource> versionResources = OfsResourceHelper.filter(modelResources, new String[]{"version"});
	    if (!versionResources.isEmpty()) { 
	    	// export to Excel
	    	try {
				new VersionTranslationExcelWriter(project, outputContainer).write(versionResources);
			} catch (CoreException e) {
				String errorMsg = "Error while executing " + getClass().getName();
				final IStatus errosStatus = new Status(IStatus.ERROR, PLUGIN_ID, 0, errorMsg + ": " + e.getMessage(), e);
				nonOkStatuses.add(errosStatus);
				errorStatuses = nonOkStatuses;
			}
	    }
	    
	}
    
	public boolean isValidForProject(IProject project) {
		return true;
	}

	@Override
	public void run(IProject project, Collection<IOfsModelResource> modelResources, IFolder outputFolder, IProgressMonitor monitor) 
			throws CoreException, InterruptedException {
		run(monitor, project, modelResources, outputFolder, errorStatuses);
	}

	public VersionTranslationExcelGenerator() {
        super();
    }

}
