package com.odcgroup.workbench.dsl.ui.quickfix;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.AbstractProjectInitializer;

/**
 * Initializer that checks if the xtextbuilder is added to the project
 * 
 * @author phanikumark
 *
 */
public class CommonXtextBuilderInitializer extends AbstractProjectInitializer {

	@Override
	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		if (!xtextBuilderFound(project)) {
			MultiStatus status = new MultiStatus(OfsCore.PLUGIN_ID,
					IStatus.OK, "Errors in configuration", null);
			status.add(new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
					"Indexing is not enabled"));
			return status;
		}
		return super.checkConfiguration(project, delta);
	}

	/**
	 * check if the xtextbuilder is in the project build spec
	 * 
	 * @param project
	 * @return
	 */
	private boolean xtextBuilderFound(IProject project) {
		IProjectDescription desc;
		boolean found = false;
		try {
			desc = project.getDescription();
			ICommand[] commands = desc.getBuildSpec();
			for (int i = 0; i < commands.length; ++i) {
				if (commands[i].getBuilderName().equals(
						XtextProjectHelper.BUILDER_ID)) {
					found = true;
					break;
				}
			}
		} catch (CoreException e) {
		}
		return found;
	}

	/**
	 * add the xtextbuilder to the project along with the xtext nature
	 */
	@Override
	public void updateConfiguration(IProject project, IProgressMonitor monitor)
			throws CoreException {
		if (!xtextBuilderFound(project)) {
			if (!project.hasNature(XtextProjectHelper.NATURE_ID)) {
				OfsCore.addNature(project, XtextProjectHelper.NATURE_ID);
			}
			OfsCore.addProjectBuilder(project, XtextProjectHelper.BUILDER_ID);
		}
	}

}
