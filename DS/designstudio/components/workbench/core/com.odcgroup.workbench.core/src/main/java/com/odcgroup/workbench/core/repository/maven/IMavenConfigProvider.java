package com.odcgroup.workbench.core.repository.maven;

import java.io.File;

/**
 * @author yan
 */
public interface IMavenConfigProvider {

	public File getLocalRepositoryLocation();
	
	public File getTargetPlatformPath();
	
}
