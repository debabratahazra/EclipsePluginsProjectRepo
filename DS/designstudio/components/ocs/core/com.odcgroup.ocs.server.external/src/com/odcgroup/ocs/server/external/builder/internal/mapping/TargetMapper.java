package com.odcgroup.ocs.server.external.builder.internal.mapping;

import java.io.File;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Interface of mappers
 * @author yan
 */
public interface TargetMapper {

	
	
	/**
	 * Configure the mapper accordingly to the builder configuration
	 * @param builderConfig
	 */
	public void configure(Map<String, String> builderConfig);

	/**
	 * Return the target file for a specific source (file/folder)
	 */
	public File getTarget(String source) throws CoreException;

	/**
	 * Return true if a prepare deployment is required, false otherwise.
	 * @return true if a prepare deployment is required, false otherwise.
	 */
	public boolean needsPrepareDeploymentDestinations() throws CoreException;

	/**
	 * Called to prepare destination(s) of this mapper which means:
	 * <ul><li>renaming the jar to some-project.jar.original</li>
	 * <li>create an empty some-project.jar</li></ul>
	 * @return
	 */
	public IStatus prepareDeploymentDestinations() throws CoreException;

	public IStatus undeployDestinations() throws CoreException;

}
