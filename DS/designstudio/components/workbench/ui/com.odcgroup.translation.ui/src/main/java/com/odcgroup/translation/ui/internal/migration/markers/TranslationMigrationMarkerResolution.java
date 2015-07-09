package com.odcgroup.translation.ui.internal.migration.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.ProjectInitializer;

/**
 * Migrate the centralized translation (before DS 3.0.0) into the models.
 * This is not a standard migration as the translation models is now
 * removed from the models managed by DS. Therefore the standard migration
 * mechanism couldn't be used. 
 *
 * @author yan
 */
public class TranslationMigrationMarkerResolution implements IMarkerResolution {

	private static final String TRANSLATION_MIGRATION_PROBLEM_MARKER = "com.odcgroup.translation.core.TranslationMigrationProblem";

	public String getLabel() {
		return "Migrate the centralized translation into the models";
	}

	@Override
	public void run(IMarker marker) {
		final IProject project = (IProject) marker.getResource().getProject();
		// ensure the project configuration is correct before performing migration
		IStatus status = null;
		boolean ofsProblemFound = false;
		boolean migrationProblemFound = false;
		for(ProjectInitializer initializer : OfsCore.getProjectInitializers(project, true)) {
			// Only consider OfsProblem marker id (and not subtype of it)
			if (initializer.getMarkerId().equals("com.odcgroup.workbench.core.OfsProblem")) {
				status = initializer.checkConfiguration(project);
				if (status != null && status.getSeverity() == IStatus.ERROR) {
					ofsProblemFound = true;
				}
			} else if (initializer.getMarkerId().equals("com.odcgroup.workbench.migration.ProblemMarker")) {
				status = initializer.checkConfiguration(project);
				if (status != null && status.getSeverity() == IStatus.ERROR) {
					migrationProblemFound = true;
				}
			}
		}

		String message = null;
		if (ofsProblemFound && migrationProblemFound) {
			message = "Problems in project configuration were found and a migration is required.\n" +
					"Fix the project configuration and migrate the project before performing translation migration.";
		} else if (ofsProblemFound) {
			message = "Problems in project configuration found.\n " +
					"Fix the project configuration before performing translation migration";
		} else if (migrationProblemFound) {
			message = "The project required a migration.\n" +
					"Please note this differs from the translation migration attempted here.\n" +
					"Migrate the project before performing translation migration";
		}
		if (message != null) {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openError(shell, "Project Configuration", message);
			return;
		}

		boolean success = true;
		for(ProjectInitializer initializer : OfsCore.getProjectInitializers(project, true)) {
			try {
				if(TRANSLATION_MIGRATION_PROBLEM_MARKER.equals(initializer.getMarkerId())) {
					initializer.updateConfiguration(project, null);
				}
			} catch (CoreException e) {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openError(shell, "Error while migrating the configuration problems", 
						e.getLocalizedMessage() +
						"\n\nPlease see the log file for more information.");
				OfsCore.getDefault().logError("Could not update configuration for project " + project.getName(), e);
				success = false;
				break;
			}
		}
		if(success) {
			try {
				marker.delete();
			} catch (CoreException e) {
				OfsCore.getDefault().logWarning("Cannot delete marker", e);
			}
		}
	}

}
