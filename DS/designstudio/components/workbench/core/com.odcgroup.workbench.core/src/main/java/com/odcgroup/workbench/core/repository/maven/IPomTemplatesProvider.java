package com.odcgroup.workbench.core.repository.maven;

import org.eclipse.core.runtime.CoreException;

public interface IPomTemplatesProvider {
	
	/** Returns the pom template for a new models project */
	public String getModelsPomTemplate() throws CoreException;
	
	/** Returns the pom template for a new models-gen project */
	public String getModelGenPomTemplate() throws CoreException;

}
