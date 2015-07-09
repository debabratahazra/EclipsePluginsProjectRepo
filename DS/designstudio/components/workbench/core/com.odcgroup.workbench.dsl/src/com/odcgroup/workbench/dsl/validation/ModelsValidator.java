package com.odcgroup.workbench.dsl.validation;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.odcgroup.workbench.core.helper.StringHelper;

/**
 * An helper class to validate a set of resources of the same type
 */
public class ModelsValidator implements IModelsValidator {
	
	private String modelType;
	private int	nbModels;
	
	private IResourceValidator resourceValidator;
	private XtextResourceSet resourceSet;
	private IResourceServiceProvider rsp;
	
	protected final IResourceServiceProvider getResourceServiceProvider() {
		return rsp;
	}
	
	protected final IResourceValidator getResourceValidator() {
		return resourceValidator;
	}
	
	protected final XtextResourceSet getResourceSet() {
		return resourceSet;
	}
	
	protected final String getModelType() {
		return this.modelType;
	}
	
	/**
	 * @param uri a platform URI
	 * @param issues
	 */
	protected void handleIssues(URI uri, List<Issue> issues) {
	}
	
	public void validate(String modelURI, int modelIndex, IProgressMonitor monitor) {

		URI uri = URI.createURI(modelURI, true);
		
		StringBuilder taskName = new StringBuilder();
		taskName.append("Validating ");
		taskName.append("(");
		taskName.append(modelIndex);
		taskName.append(" of ");
		taskName.append(nbModels);
		taskName.append(") ");
		taskName.append((nbModels > 1)
				? StringHelper.toPlural(modelType)
				: modelType.toLowerCase());
		taskName.append(" : ");
		taskName.append(uri.toString());
		monitor.subTask(taskName.toString());
		
		Resource resource = getResourceSet().getResource(uri, true);
		List<Issue> issues = resourceValidator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		if (monitor.isCanceled() || issues == null)
			return;
		handleIssues(uri, issues);
	}

	public void validate(Set<String> models, IProgressMonitor monitor) {
		
		SubMonitor progress = SubMonitor.convert(monitor, 100);
		int nbModels = models.size();
		SubMonitor subMonitor = progress.newChild(100).setWorkRemaining(nbModels);
		
		int modelIndex = 0;
		for (String modelURI : models) {
			if (monitor.isCanceled()) break;
			modelIndex++;
			validate(modelURI, modelIndex, subMonitor.newChild(1));
		}
	}
	
	
	/**
	 * Constructor
	 * @param modelType
	 * @exception IllegalArgumentException
	 * @exception IllegalStateException if the initialization cannot be done
	 */
	public ModelsValidator(String projectName, String modelType, int nbModels) throws IllegalStateException {
		
		this.modelType = modelType;
		if (StringUtils.isBlank(modelType)) {
			throw new IllegalArgumentException("The modelType cannot be null or empty");
		}
		
		this.nbModels = nbModels;
		
		rsp = IResourceServiceProvider
				.Registry.INSTANCE
				.getResourceServiceProvider(URI.createURI("dummy."+getModelType()));
		if (rsp == null) {
			throw new IllegalStateException("Cannot find the Xtext ResourceServiceProvider for ["+modelType+"]");
		}
		
		resourceValidator = rsp.getResourceValidator();
		if (resourceValidator == null) {
			throw new IllegalStateException("Cannot find the Xtext ResourceValidator for ["+modelType+"]");
		}
		
		//IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		//resourceSet = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, project);
		resourceSet = rsp.get(XtextResourceSet.class);
		if (resourceSet == null) {
			throw new IllegalStateException("Cannot find the Xtext ResourceSet for ["+modelType+"]");
		}

	}
	
}
