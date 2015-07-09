package com.odcgroup.visualrules.integration.init;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.AbstractProjectInitializer;

/**
 *
 * @author pkk
 *
 */
public class RulesIntegrationInitializer extends AbstractProjectInitializer {
	
	private static String RULES_INTERNAL_PCKG = "/internal";

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.AbstractProjectInitializer#updateConfiguration(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void updateConfiguration(IProject project, IProgressMonitor monitor) throws CoreException {
		if (OfsCore.isOfsProject(project)) {
			String internalpath = RulesIntegrationPlugin.getVRBasePath(project)+RULES_INTERNAL_PCKG;
			if (!RulesIntegrationPlugin.packageExists(project, internalpath)) {
				project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.AbstractProjectInitializer#checkConfiguration(org.eclipse.core.resources.IProject)
	 */
	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		MultiStatus status = (MultiStatus) super.checkConfiguration(project, delta);
		String vrbase = RulesIntegrationPlugin.getVRBasePath(project);
		if (OfsCore.isOfsProject(project)) {
			String internalpath = vrbase+RULES_INTERNAL_PCKG;
			if (RulesIntegrationPlugin.isRulesEnabled(project) 
					&& !RulesIntegrationPlugin.packageExists(project, internalpath)) {
				status.add(new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID, 
								"Data types are not yet synchronized to rule model."));				
			} 
		}
		return status;		
	}

}
