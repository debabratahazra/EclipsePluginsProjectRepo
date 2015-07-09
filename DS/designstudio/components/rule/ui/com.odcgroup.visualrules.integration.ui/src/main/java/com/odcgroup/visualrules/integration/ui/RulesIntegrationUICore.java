package com.odcgroup.visualrules.integration.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.workbench.ui.AbstractUIActivator;

import de.visualrules.integration.IRuleIntegration;
import de.visualrules.integration.IntegrationException;
import de.visualrules.ui.integration.VisualRulesIntegration;

/**
 * The activator class controls the plug-in life cycle
 */
public class RulesIntegrationUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.visualrules.integration.ui";


	/**
	 * @return
	 */
	public static RulesIntegrationUICore getDefault() {
		return (RulesIntegrationUICore) getDefault(RulesIntegrationUICore.class);
	}

	static public void openRule(IProject project, String qualifiedName) throws CoreException {
		try {
			IRuleIntegration vr = VisualRulesIntegration.getRuleIntegration(project);
			vr.openRule(RulesIntegrationPlugin.getVRBasePath(project) + "/" + qualifiedName);
		} catch (IntegrationException e) {
			// if the system has shut down already, we see a NPE here
			if(e.getCause() instanceof NullPointerException) return;

			IStatus status = new Status(IStatus.ERROR, RulesIntegrationPlugin.PLUGIN_ID,  
					"Could not open rule '" + qualifiedName + "'", e);
			throw new CoreException(status);
		}				
	}

}
