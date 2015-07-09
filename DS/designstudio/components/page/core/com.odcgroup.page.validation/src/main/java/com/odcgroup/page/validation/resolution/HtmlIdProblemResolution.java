package com.odcgroup.page.validation.resolution;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.validation.Activator;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;

/**
 * This class can add missing id properties to widgets of a model. It is called by the "quick fix" of the according validation rule.
 * 
 * @author Kai Kreuzer
 *
 */
public class HtmlIdProblemResolution {

	private static final Logger logger = LoggerFactory.getLogger(HtmlIdProblemResolution.class);
	
	/**
	 * This method generates ids for all widgets that are of a type listed in {@link UniqueIdGenerator.generateIdForWidgets}.
	 * It either operates on a single model or iterates over all page/module/fragment models of a given project.
	 * 
	 * @param resource an IFile or an IProject
	 * @param monitor the monitor to report progress on
	 * @throws CoreException if some error occurs
	 */
	static public void generateMissingIds(IResource resource, IProgressMonitor monitor) throws CoreException {
		if(resource instanceof IProject) {
			IOfsProject ofsProject = OfsCore.getOfsProject((IProject) resource);
			final ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, new String[] {"page", "module", "fragment"});
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					Set<IOfsModelResource> modelResources = lookup.getAllOfsModelResources();
					monitor.beginTask("Generating missing ids...", modelResources.size());
					for(IOfsModelResource modelResource : modelResources) {
						monitor.subTask("Processing model '" + modelResource.getName() + "'");
						generateMissingIds(modelResource);
						monitor.worked(1);
						if(monitor.isCanceled()) break;
					}
				}
			}, resource, IWorkspace.AVOID_UPDATE, monitor);
		} else {
			final Object adapter = resource.getAdapter(IOfsElement.class);
			if(adapter instanceof IOfsModelResource) {
				ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor) throws CoreException {
						IOfsModelResource modelResource = (IOfsModelResource) adapter;
						monitor.beginTask("Generating missing ids for model '" + modelResource.getName() + "'", 1);
						generateMissingIds(modelResource);
						monitor.done();
					}
				}, resource, IWorkspace.AVOID_UPDATE, monitor);
				generateMissingIds((IOfsModelResource) adapter);
			}
		}
	}

	static private void generateMissingIds(IOfsModelResource modelResource) throws CoreException {
		try {
			boolean changed = false;
			TreeIterator<EObject> it = modelResource.getEMFModel().get(0).eAllContents();
			while(it.hasNext()) {
				EObject eObj = it.next();
				if(eObj instanceof Widget) {
					Widget widget = (Widget) eObj;
					if(UniqueIdGenerator.generateIdForWidgets.contains(widget.getTypeName()) && (
							widget.getPropertyValue(PropertyTypeConstants.ID)==null || widget.getID().isEmpty())) {
						widget.setID(UniqueIdGenerator.generateId(widget));
						changed = true;
					}
				}
			}
			if(changed) modelResource.getEMFModel().get(0).eResource().save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			logger.error("Error saving model '{}'.", modelResource.getName(), e);
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error saving model '" + modelResource.getName() + "'", e);
			throw new CoreException(status);
		} catch (InvalidMetamodelVersionException e) {
			// simply ignore models that have an invalid meta-model version
		}
	}

}	
