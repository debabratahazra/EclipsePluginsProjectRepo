package com.odcgroup.workbench.migration.ui.internal.markers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.migration.MigrationCore;
import com.odcgroup.workbench.migration.MigrationException;
import com.odcgroup.workbench.migration.ui.MigrationUICore;

public class MigrationMarkerResolution implements IMarkerResolution {

	private final static Logger logger = LoggerFactory.getLogger(MigrationMarkerResolution.class);
	
	private void deleteMarkers(IProject project, String markerId) {
		try {
			project.deleteMarkers(markerId, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
			OfsCore.getDefault().logWarning("Could not delete markers of project " + project.getName(), ce);
		}
	}
	
	public String getLabel() {
		return "Migrate project contents to the latest version";
	}

	@Override
	public void run(IMarker marker) {
		final IProject project = (IProject) marker.getResource().getProject();
		final IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		// ensure the project configuration is correct before performing migration
		IStatus status = null;
		for(ProjectInitializer initializer : OfsCore.getProjectInitializers(project, true)) {
			// Only consider OfsProblem marker id (and not subtype of it)
			if (initializer.getMarkerId().equals("com.odcgroup.workbench.core.OfsProblem")) {
				status = initializer.checkConfiguration(project);
				if (status != null && status.getSeverity() == IStatus.ERROR) {
					String message = "Problems in project configuration found.\n " +
							"Fix the project configuration before performing project migration";
					MessageDialog.openError(shell, "Project Configuration", message);
					return;
				}
					
			}
		}
		
		if (ofsProject != null) {
			
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
						InterruptedException {
					try {
						MigrationCore.migrate(ofsProject, monitor);
						project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
						
						deleteMarkers(ofsProject.getProject(), MigrationCore.MARKER_ID);
						
					} catch (MigrationException e) {
						if(e.getCause()!=null) {
							throw new InvocationTargetException(e, e.getCause().getMessage());
						} else {
							throw new InvocationTargetException(e);
						}
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					}					
				}				
			};

			
			try {
				new ProgressMonitorDialog(shell).run(true, true, operation);
			} catch (InvocationTargetException e) {
				logger.error("Error during migration", e.getCause());
				if(e.getCause() instanceof CoreException) {
					CoreException ce = (CoreException) e.getCause();
					ErrorDialog.openError(shell, "Error during model migration", "Model migration cannot be performed.", ce.getStatus());
				} else {
					status = new Status(IStatus.ERROR, MigrationUICore.PLUGIN_ID, e.getCause().getLocalizedMessage(), e.getCause().getCause());
					ErrorDialog.openError(shell, "Error during model migration", "Model migration cannot be performed.", status);
				}
			} catch (InterruptedException e) {}
		}
	}

}
