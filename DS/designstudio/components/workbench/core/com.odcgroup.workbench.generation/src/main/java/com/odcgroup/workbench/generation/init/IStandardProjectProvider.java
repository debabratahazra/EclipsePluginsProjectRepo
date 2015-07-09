package com.odcgroup.workbench.generation.init;

import java.io.File;
import java.util.Collection;

import com.odcgroup.workbench.core.IContainerIdentifier;

/**
 * This interface must be implemented by classes that want to provide standard projects for the custo feature.
 * These providers can be registered using the extension point com.odcgroup.workbench.custo.core.standardProjectProvider
 *
 * @author Kai Kreuzer
 *
 */
public interface IStandardProjectProvider {

	/**
	 * Returns a collection of container identifiers that can be provided through this provider.
	 * 
	 * @return a collection of container identifiers
	 */
	public Collection<IContainerIdentifier> getContainerIdentifiers();

	/**
	 * Returns the zip file of the packaged standard project.
	 * 
	 * @param containerIdentifier the identifier of the standard project to retrieve
	 * 
	 * @return the file handle to the zip file
	 */
	public File getFile(IContainerIdentifier containerIdentifier);
	
	/**
	 * Returns the pom.xml file of the packaged standard models project.
	 * 
	 * @param containerIdentifier the identifier of the standard project
	 * 
	 * @return the file handle to the pom.xml file
	 */
	public File getModelsPomFile(IContainerIdentifier containerIdentifier);
	
	/**
	 * Returns the pom.xml file of the packaged standard gen project.
	 * 
	 * @param containerIdentifier the identifier of the standard project
	 * 
	 * @return the file handle to the pom.xml file
	 */
	public File getGenPomFile(IContainerIdentifier containerIdentifier);

}
