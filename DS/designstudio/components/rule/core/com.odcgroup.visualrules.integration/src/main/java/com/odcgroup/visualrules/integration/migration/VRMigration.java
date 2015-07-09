package com.odcgroup.visualrules.integration.migration;

import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.IntegrationException;

public class VRMigration extends AbstractModelMigration {

	public VRMigration() {
	}

	public void migrate(IOfsProject ofsProject,
			IOfsModelResource modelResource, IProgressMonitor monitor)
			throws MigrationException {
		IProject project = ofsProject.getProject();
		String basePath = RulesIntegrationPlugin.getVRBasePath(project);
		try {
			IntegrationCore.getCoreRuleIntegration(project).migrate(basePath);
		} catch (IntegrationException e) {
			throw new MigrationException(e);
		}
	}

	public InputStream migrate(IOfsProject ofsProject, InputStream is,
			URI modelURI, IProgressMonitor monitor)
			throws MigrationException {
		throw new MigrationException("Method not supported for rule migration!");
	}

}
