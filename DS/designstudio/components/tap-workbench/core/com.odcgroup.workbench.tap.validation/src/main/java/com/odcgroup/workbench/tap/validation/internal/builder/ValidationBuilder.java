package com.odcgroup.workbench.tap.validation.internal.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.tap.validation.ValidationUtil;

/**
 * The project builder that runs the Odyssey model validation in batch mode.
 * 
 * @author Kai Kreuzer
 * 
 */
public class ValidationBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.odcgroup.workbench.validation.builder";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
	 * java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
		
		final IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(getProject());
		if (ofsProject == null)
			return null;

		IResourceDelta delta = getDelta(getProject());
		final Set<IOfsModelResource> resourcesToValidate = new HashSet<IOfsModelResource>();

		if (delta == null || kind == IncrementalProjectBuilder.FULL_BUILD) {
			// As the validation can take quite a while on big projects, we deactivate the full build feature.
			// The user still has the possibility to launch a manual validation
			//
//			 ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, OfsCore.getValidationModelNames());
//			 resourcesToValidate.addAll(lookup.getAllOfsModelResources());
		} else if(delta.getKind()!=IResourceDelta.REMOVED) {
			final String[] modelNames = OfsCore.getValidationModelNames();
 			IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				
				public boolean visit(IResourceDelta delta) throws CoreException {
					IResource resource = delta.getResource();
					if(resource instanceof IFile && resource.exists()) {
						if(ArrayUtils.contains(modelNames, resource.getFileExtension())) {
							IOfsElement modelElement = (IOfsElement) resource.getAdapter(IOfsElement.class);
							if(modelElement instanceof IOfsModelResource) resourcesToValidate.add((IOfsModelResource) modelElement);
						}
					}
					return true;
				}
			};
			delta.accept(visitor);
		}
		doBuild(resourcesToValidate, monitor);
		return null;
	}
	
	private void doBuild(Collection<IOfsModelResource> modelResources, IProgressMonitor monitor) {
		monitor.beginTask("Validating model: ", modelResources.size());
		for(IOfsModelResource modelResource : modelResources) {
			//monitor.subTask("Validating model: " + modelResource.getURI().path().substring(1));
			ValidationUtil.validate(modelResource, monitor, true);
			monitor.worked(1);
			if(monitor.isCanceled()) break;
		}
    	monitor.done();
	}

}
