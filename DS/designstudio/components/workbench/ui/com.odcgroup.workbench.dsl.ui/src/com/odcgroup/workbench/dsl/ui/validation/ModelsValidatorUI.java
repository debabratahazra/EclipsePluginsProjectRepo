package com.odcgroup.workbench.dsl.ui.validation;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.editor.validation.IValidationIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.editor.validation.MarkerIssueProcessor;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.validation.Issue;

import com.odcgroup.workbench.dsl.validation.ModelsValidator;

public class ModelsValidatorUI extends ModelsValidator {

	private IResource getResource(URI uri) {
		String platformString = uri.toPlatformString(true);
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
		return resource;
	}
	
	@Override
	protected void handleIssues(URI platformURI, List<Issue> issues) {
		if (issues == null) {
			throw new IllegalArgumentException("Issues list cannot be null");
		}
		IResource resource = getResource(platformURI);
		if (resource != null) {
			// update error markers
			IResourceServiceProvider rsp = getResourceServiceProvider(); 
			MarkerCreator markerCreator = rsp.get(MarkerCreator.class);
			MarkerTypeProvider markerTypeProvider = rsp.get(MarkerTypeProvider.class);
			IValidationIssueProcessor issueProcessor = new MarkerIssueProcessor(resource, markerCreator, markerTypeProvider);
			issueProcessor.processIssues(issues, new NullProgressMonitor());
		}
	}

	/**
	 * Constructor
	 * @param projectName
	 * @param modelType
	 * @param nbModels
	 * @exception IllegalArgumentException
	 * @exception IllegalStateException if the initialization cannot be done
	 */
	/**
	 * @throws IllegalStateException
	 */
	public ModelsValidatorUI(String projectName, String modelType, int nbModels) throws IllegalStateException {
		super(projectName, modelType, nbModels);
	}
}
