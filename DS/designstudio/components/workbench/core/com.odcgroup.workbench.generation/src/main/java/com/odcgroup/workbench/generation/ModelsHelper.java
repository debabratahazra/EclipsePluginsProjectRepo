package com.odcgroup.workbench.generation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

import com.google.common.collect.Lists;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

public final class ModelsHelper {
	private ModelsHelper() {
	}

	/**
	 * Method to check whether file is acceptable for code generation.
	 * 
	 * @param file
	 * @param extensions
	 * @return true if the file is contained in a valid models folder
	 */
	private static boolean acceptResource(IFile file, Set<String> extensions) {
		String extension = file.getFileExtension();
		return extensions.contains(extension);
	}

	/**
	 * Method to collect list of IResources/IOfsElement
	 * 
	 * @param objects
	 * @return the model resources from a list of IResources/IOfsElement.
	 * @throws CoreException
	 */
	public static List<IResource> collectModelResources(Object object) throws CoreException {
		// the model names represents the file extension of the resource (IFile)
		final Set<String> extensions = new HashSet<String>();
		Collections.addAll(extensions, OfsCore.getRegisteredModelNames());

		// contains the model resources
		final List<IResource> resources = Lists.newArrayList();

		if (object != null) {
			if (object instanceof IOfsElement) {
				// The action has been executed from the DS navigator
				object = ((IOfsElement) object).getResource();
			}
			if (object instanceof IProject) {
				((IProject) object).accept(new IResourceVisitor() {
					public boolean visit(IResource resource) throws CoreException {
						if (resource instanceof IFile) {
							if (acceptResource((IFile) resource, extensions)) {
								resources.add(resource);
							}
						}
						return true;
					}
				});
			} else if (object instanceof IFolder) {
				((IFolder) object).accept(new IResourceVisitor() {
					public boolean visit(IResource resource) throws CoreException {
						if (resource instanceof IFile) {
							if (acceptResource((IFile) resource, extensions)) {
								resources.add(resource);
							}
						}
						return true;
					}
				});
			} else if (object instanceof IFile) {
				IFile file = (IFile) object;
				if (acceptResource(file, extensions)) {
					resources.add(file);
				}
			}

			Collections.reverse(resources);
		}
		return resources;
	}

	public static Collection<IOfsModelResource> transform(IProject project, Collection<IResource> mResources) {
		Collection<IOfsModelResource> resources = new CopyOnWriteArraySet<IOfsModelResource>();
		if (OfsCore.isOfsProject(project)) {
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
			ModelResourceSet resourceSet = ofsProject.getModelResourceSet();
			for (IResource res : mResources) {
				URI uri = ModelURIConverter.createModelURI(res);
				if (ofsProject.ofsModelResourceExists(uri)) {
					resources.add(resourceSet.getOfsModelResource(uri));
				} else {
					resources.add(resourceSet.createOfsModelResource((IFile) res, IOfsProject.SCOPE_ALL));
				}
			}
		}
		return resources;
	}

}
