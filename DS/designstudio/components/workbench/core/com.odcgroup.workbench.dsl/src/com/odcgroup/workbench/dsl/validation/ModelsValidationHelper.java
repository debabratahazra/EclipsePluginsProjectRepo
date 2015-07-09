package com.odcgroup.workbench.dsl.validation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.Lists;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.OfsCore;

public final class ModelsValidationHelper {

	/**
	 * @param objects
	 * @return the model resources from a list of IResources/IOfsElement.
	 * @throws CoreException
	 */
	public static List<IResource> collectModelResources(List<Object> objects) throws CoreException {
		
		// the model names represents the file extension of the resource (IFile) 
		final Set<String> extensions = new HashSet<String>();
		Collections.addAll(extensions, OfsCore.getRegisteredModelNames());
		
		// contains the model resources
		final List<IResource> resources = Lists.newArrayList();
		
		if (objects != null) {
		
			for (Object obj : objects) {
				if (obj instanceof IOfsElement) { 
					// The action has been executed from the DS navigator
					obj = ((IOfsElement)obj).getResource();
				}
				if (obj instanceof IProject) {
					((IProject)obj).accept(new IResourceVisitor() {
						public boolean visit(IResource resource) throws CoreException {
							if (resource instanceof IFile) {
								if (extensions.contains(resource.getFileExtension())) {
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
								if (extensions.contains(resource.getFileExtension())) {
									resources.add(resource);
								}
							}
							return true;
						}
					});
				} else if (obj instanceof IFile) {
					IFile file = (IFile)obj;
					if (extensions.contains(file.getFileExtension())) {
						resources.add(file);
					}
				}
			}
		
			Collections.reverse(resources);
		}
		
		return resources;

	}
}
