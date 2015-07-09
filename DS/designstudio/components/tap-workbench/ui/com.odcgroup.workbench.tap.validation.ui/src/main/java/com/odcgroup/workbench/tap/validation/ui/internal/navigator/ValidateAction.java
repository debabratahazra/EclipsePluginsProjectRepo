package com.odcgroup.workbench.tap.validation.ui.internal.navigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.validation.IValidationIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.editor.validation.MarkerIssueProcessor;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.core.repository.ModelResourceVisitor;
import com.odcgroup.workbench.tap.validation.ValidationUtil;
import com.odcgroup.workbench.tap.validation.ui.ValidationUICore;

/**
 * DS-2251, contextual menu validate action
 * 
 * @author pkk, kkr
 * 
 */
public class ValidateAction extends Action implements IObjectActionDelegate {

	final static private String ID = "VALIDATE_MODELS";
	
	private IStructuredSelection selection = null;
	
	public List<IResource> collectXtextResources(IStructuredSelection selection) throws CoreException {
		
		final List<IResource> resources = new ArrayList<IResource>();
		final Set<String> modelNames = new HashSet<String>();
		Collections.addAll(modelNames, OfsCore.getValidationModelNames());

		@SuppressWarnings("rawtypes")
		Iterator it = selection.iterator();
		
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof IProject) {
				((IProject)obj).accept(new IResourceVisitor() {
					public boolean visit(IResource resource) throws CoreException {
						if (resource instanceof IFile) {
							if (modelNames.contains(resource.getFileExtension())) {
								resources.add(resource);
							}
						}
						return true;
					}
				});
			} else if (obj instanceof IFolder) {
				((IFolder)obj).accept(new IResourceVisitor() {
					public boolean visit(IResource resource) throws CoreException {
						if (resource instanceof IFile) {
							if (modelNames.contains(resource.getFileExtension())) {
								resources.add(resource);
							}
						}
						return true;
					}
				});
			} else if (obj instanceof IFile) {
				IFile file = (IFile)obj;
				if (modelNames.contains(file.getFileExtension())) {
					resources.add(file);
				}
			}
		}
		
		Collections.reverse(resources);
		return resources;
	}

	
	/**
	 * 
	 */
	public ValidateAction() {
		setId(ID);
		setText("Validate Models");
		setImageDescriptor(ValidationUICore.imageDescriptorFromPlugin(ValidationUICore.PLUGIN_ID, "icons/valid.png"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void run() {
		run(this);
	}

//	@Override
//	public void run(IAction action) {
//		
//		try {
//			
//			final List<IResource> resources = collectXtextResources(selection);
//
//			Job job = new WorkspaceJob("Model Validation") {
//				public IStatus runInWorkspace(final IProgressMonitor monitor) {
//					int nbModels = resources.size();
//					monitor.beginTask("Validating models", nbModels);
//					long start = System.currentTimeMillis();
//					try {
//						for (IResource resource : resources) {
//							monitor.subTask("Validating " + resource.getFullPath().lastSegment());
//							
//							validateXTextResource(resource, new NullProgressMonitor());
//							
//							monitor.worked(1);
//							if (monitor.isCanceled()) {
//								return new Status(IStatus.CANCEL, ValidationUICore.PLUGIN_ID, "Validation cancelled by user");
//							}
//						}
//					} finally {
//						long stop = System.currentTimeMillis();
//						long duration = stop - start;
//						ValidationUICore.getDefault().logInfo("Validation of " + nbModels + " models in "+duration+"ms, mean duration:"+(duration/nbModels)+"ms", null);
//						monitor.done();
//					}
//					return Status.OK_STATUS;
//				}
//	
//			};
//		
//			job.setPriority(Job.DECORATE);
//			job.setUser(true);
//			job.schedule();
//
//		
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			// Uncomment and replace with appropriate logger
//			// LOGGER.error(e, e);
//		}
//	}
	@Override
	public void run(IAction action) {
		final Set<IOfsModelResource> modelResources = fetchModelResources(selection);
		Job job = new WorkspaceJob("Model Validation") {
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				int nbModels = modelResources.size();
				monitor.beginTask("Validating models", nbModels);
				long start = System.currentTimeMillis();
				try {
					for (IOfsModelResource modelResource : modelResources) {
						monitor.subTask("Validating " + modelResource.getURI().path().substring(1));
						
						// OLD EMF validation
						ValidationUtil.validate(modelResource, new NullProgressMonitor(), true);
						
						// Xtext Validation
						validateXtextResource(modelResource, new NullProgressMonitor());
						
						monitor.worked(1);
						if (monitor.isCanceled()) {
							return new Status(IStatus.CANCEL, ValidationUICore.PLUGIN_ID, "Validation cancelled by user");
						}
					}
				} finally {
					long stop = System.currentTimeMillis();
					long duration = stop - start;
					ValidationUICore.getDefault().logInfo("Validation of "+nbModels+" models in "+duration+"ms", null);
					monitor.done();
				}
				return Status.OK_STATUS;
			}

		};

		job.setPriority(Job.DECORATE);
		job.setUser(true);
		job.schedule();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

	/**
	 * @param selection
	 */
	public void selectionChanged(final ISelection selection) {
		selectionChanged(this, selection);
	}

	/**
	 * @param selection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Set<IOfsModelResource> fetchModelResources(IStructuredSelection selection) {
		Set<IOfsModelResource> modelResources = new HashSet<IOfsModelResource>();

		String[] registeredModelNames = OfsCore.getValidationModelNames();
		
		Iterator it = selection.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof IAdaptable) {
				Object adapter = ((IAdaptable) obj).getAdapter(IOfsElement.class);

				if (adapter instanceof IOfsElement) {
					IOfsElement ofsElement = (IOfsElement) adapter;
					ModelResourceVisitor visitor = new ModelResourceVisitor(registeredModelNames);
					ofsElement.accept(visitor);
					modelResources.addAll(visitor.getMatchedResources(IOfsProject.SCOPE_ALL - IOfsProject.SCOPE_DEPENDENCY));
				}

				adapter = ((IAdaptable) obj).getAdapter(IOfsProject.class);
				if (adapter instanceof IOfsProject) {
					IOfsProject ofsProject = (IOfsProject) adapter;
					ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, registeredModelNames);
					modelResources.addAll(lookup.getAllOfsModelResources(IOfsProject.SCOPE_ALL - IOfsProject.SCOPE_DEPENDENCY));
				}
			}
		}

		return modelResources;
	}
	
	private void validateXtextResource(IOfsModelResource modelResource, final IProgressMonitor monitor) {
		validateXtextResource(modelResource.getResource(), monitor);
	}
	
	private void validateXtextResource(IResource resource, final IProgressMonitor monitor) {
		
		URI uri = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
		
		IResourceServiceProvider resourceServiceProvider = 
				IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(uri);
		if (resourceServiceProvider != null) {
			
			IResourceValidator resourceValidator = resourceServiceProvider.getResourceValidator();
			if (resourceValidator != null) {
				
				// get the xtext resource
				XtextResourceSet rs = resourceServiceProvider.get(XtextResourceSet.class);
				Resource res = rs.getResource(uri, true);
				
				// perform the validation
				List<Issue> issues = resourceValidator.validate(res, CheckMode.ALL, new CancelIndicator() {
					public boolean isCanceled() {
						return (monitor != null) ? monitor.isCanceled() : false;
					}
				});
				if (monitor.isCanceled())
					return;
				
				// update error markers
				MarkerCreator markerCreator = resourceServiceProvider.get(MarkerCreator.class);
				MarkerTypeProvider markerTypeProvider = resourceServiceProvider.get(MarkerTypeProvider.class);
				IValidationIssueProcessor issueProcessor = new MarkerIssueProcessor(resource, markerCreator, markerTypeProvider);
				issueProcessor.processIssues(issues, monitor);
	
			}
		}
	}

}
