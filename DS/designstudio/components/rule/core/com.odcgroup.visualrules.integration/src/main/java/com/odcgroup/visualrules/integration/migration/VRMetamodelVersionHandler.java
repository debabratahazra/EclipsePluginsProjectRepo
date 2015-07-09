package com.odcgroup.visualrules.integration.migration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.migration.MetaModelVersionHandler;
import com.odcgroup.workbench.migration.MigrationException;

import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.IntegrationException;

public class VRMetamodelVersionHandler extends MetaModelVersionHandler {

	@Override
	public boolean needsMigration(IOfsModelResource modelResource)
			throws CoreException {
		if(modelResource.getResource()==null) return false; // not in local workspace
		IProject project = modelResource.getResource().getProject();
		String basePath = RulesIntegrationPlugin.getVRBasePath(project);
		try {
			return IntegrationCore.getCoreRuleIntegration(project).isMigrationNecessary(basePath);
		} catch (IntegrationException e) {
			// we do not seem to have a valid rule model at all
			return false;
		}
	}
	
	@Override
	public String getMetaModelVersion(IOfsModelResource modelResource)
			throws CoreException {
		// VR metamodel version cannot be accessed through the API, but we only need to know whether
		// it is up-to-date or not as there will be only one migration logic registered.
		boolean outdated = needsMigration(modelResource);
		return (outdated ? "1.0.0" : OfsCore.getVersionNumber());
	}

	@Override
	public void setCurrentMetaModelVersion(Resource resource)
			throws MigrationException {
		// do nothing
	}
}
