package com.odcgroup.workbench.generation.ui.internal.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMarkerResolution;

import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;
import com.odcgroup.workbench.generation.ui.init.UICodeGenInitializer;

/**
 * This marker resolution reinitializes the code generation nature of the project and therefore makes sure that
 * all required projects exist and that they are correctly set up.
 * 
 * @author Kai Kreuzer
 *
 */
public class ReconfigureProjectsResolution implements IMarkerResolution {

	public String getLabel() {
		return "Create and update code generation java projects";
	}

	public void run(IMarker marker) {
		IProject project = (IProject) marker.getResource();
		ProjectInitializer initializer = new CodeGenInitializer();
		ProjectInitializer uiInitializer = new UICodeGenInitializer();
		try {
			if (isQuickFixForGenModel((CodeGenInitializer) initializer, project)
					&& !((CodeGenInitializer) initializer).isGenPomExistInRepoFolder(project)) {
				MessageDialog
						.openError(
								Display.getCurrent().getActiveShell(),
								"Unable to create the gen project",
								"You cannot execute this quick fix unless you haven loaded OCS binaries in Design Studio.\n" +
								"To load OCS binaries,open the preference page (Window > Preferences) and navigate to Design Studio > Servers > Embedded in the left pane." +
								"Then fill the relevant information and click the Reload... button.");
				return;
			}
			initializer.updateConfiguration(project, null);
			uiInitializer.updateConfiguration(project, null);
		} catch (CoreException e) {
			GenerationCore.getDefault().logError("Could not create or update code generation java projects", e);
		}
	}

	protected boolean isQuickFixForGenModel(CodeGenInitializer code, IProject project) {
		IStatus status = code.checkConfiguration(project);
		if (status instanceof MultiStatus) {
			IStatus[] childStatuses = ((MultiStatus) status).getChildren();
			String[] genProjectName = GenerationCore.getJavaProjectNames(project);
			String errorMessage = null;
			if (genProjectName.length != 0) {
				errorMessage = "Project '" + genProjectName[0]
						+ "' does not exist, but is required for code generation.";
			}
			for (IStatus childStaus : childStatuses) {
				if (!childStaus.isOK() && errorMessage != null) {
					if (childStaus.getMessage().equals(errorMessage)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
