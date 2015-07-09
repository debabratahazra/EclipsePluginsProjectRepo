package com.odcgroup.workbench.ui.internal.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.ui.OfsUICore;

public class OfsMarkerResolution implements IMarkerResolution {

	public String getDescription() {
		return "Run this quick fix to update the configuration of your Design Studio project, so that it is compatible" +
		" with the version of the workbench that you are running";
	}

	public String getLabel() {
		return "Fix Design Studio project configuration";
	}

	public void run(IMarker marker) {
		boolean success = true;
		IProject project = (IProject) marker.getResource().getProject();
		if (project.getName().endsWith("models-gen")) {
			removeXtextNatureFromGenProjects(project);
		} else {
			for(ProjectInitializer initializer : OfsCore.getProjectInitializers(project, true)) {
				if(OfsCore.MARKER_ID.equals(initializer.getMarkerId())) {
					try {
						initializer.updateConfiguration(project, null);
					} catch (CoreException e) {
						Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						MessageDialog.openError(shell, "Error while fixing configuration problems", 
								e.getLocalizedMessage() +
								"\n\nPlease see the log file for more information.");
						OfsCore.getDefault().logError("Could not update configuration for project " + project.getName(), e);
						success = false;
						break;
					}
				} 
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
	
	private void removeXtextNatureFromGenProjects(IProject project) {
		try {
			// remove the Xtext nature & builder
			if (project.hasNature(XtextProjectHelper.NATURE_ID)) {
				OfsCore.removeNature(project, XtextProjectHelper.NATURE_ID);
				OfsCore.removeProjectBuilder(project, XtextProjectHelper.BUILDER_ID);
			}
		} catch (CoreException e) {
			OfsUICore.getDefault().logError("Could not remove xtext nature and builder for project " + project.getName(), e);
		}
	}
}
