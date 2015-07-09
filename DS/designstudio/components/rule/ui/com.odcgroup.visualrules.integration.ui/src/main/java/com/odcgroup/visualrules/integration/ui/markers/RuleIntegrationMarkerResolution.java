package com.odcgroup.visualrules.integration.ui.markers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.datasync.DataTypeBuilder;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * This marker resolution performs the data type synchronisation to the rule models
 * by performing a full build on the DataTypeBuilder
 * 
 * @author pkk
 */
public class RuleIntegrationMarkerResolution implements IMarkerResolution {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Run data type synchronization";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void run(IMarker marker) {
		final IProject project = (IProject) marker.getResource().getProject();		
		final IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		// ensure the project configuration is correct before performing migration
		IStatus status = null;
		
		if (ofsProject != null) {			
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
				protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
					final DomainRepository repo = DomainRepository.getInstance(ofsProject);
					final Collection<MdfDomain> domainModels = repo.getDomains(IOfsProject.SCOPE_ALL-IOfsProject.SCOPE_DEPENDENCY);
					DataTypeBuilder.syncDomainModels(project, domainModels, true, monitor);	
				}				
			};
			
			try {
				new ProgressMonitorDialog(shell).run(true, true, operation);
			} catch (InvocationTargetException e) {
				if(e.getCause() instanceof CoreException) {
					CoreException ce = (CoreException) e.getCause();
					ErrorDialog.openError(shell, "Error during data type synchronization", "data sync cannot be performed.", ce.getStatus());
				} else {
					status = new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID, e.getCause().getLocalizedMessage(), e.getTargetException());
					ErrorDialog.openError(shell, "Error during data type synchronization", "data sync cannot be performed.", status);
				}
			} catch (InterruptedException e) {}
		}

	}

}
