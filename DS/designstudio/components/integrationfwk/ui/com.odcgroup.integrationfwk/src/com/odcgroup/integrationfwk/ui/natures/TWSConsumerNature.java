package com.odcgroup.integrationfwk.ui.natures;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class TWSConsumerNature implements IProjectNature {
	/**
	 * ID of this project nature
	 */
	public static final String NATURE_ID = "com.temenos.tws.consumer.plugin.natures.TWSConsumerNature";
	private IProject project;
	private URI location;

	public void configure() throws CoreException {
		//TODO: investigate if this is required
		// what sis it all about??
		// Add nature-specific information for the project, such as adding a
		// builder to a project's build spec.
		// IProject project = getProject();
		// IProjectDescription projectDescription = project.getDescription();
		// getProject().setDescription(projectDescription, null);
	}

	public void deconfigure() throws CoreException {
		// Remove the nature-specific information here.
	}

	public URI getLocation() {
		return location;
	}

	public IProject getProject() {
		return project;
	}

	public void setLocation(URI loc) {
		location = loc;
	}

	public void setProject(IProject value) {
		project = value;
	}
}
