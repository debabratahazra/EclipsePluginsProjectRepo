package com.odcgroup.workbench.core.migration;

import org.eclipse.core.resources.IProject;

public interface IMigrationProcessor {
	
	public void migrateProjects(IProject... projects) throws InterruptedException;

}
