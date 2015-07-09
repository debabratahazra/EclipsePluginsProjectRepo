package com.odcgroup.workbench.core.internal.adapter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

public class ResourceAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	private static Class[] PROPERTIES= new Class[] {
		IOfsProject.class,
		IOfsElement.class
	};

	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		return PROPERTIES.clone();
	}
	
	@SuppressWarnings("unchecked")
	public Object getAdapter(Object element, Class key) {

		if (element instanceof IProject && 
				(IOfsProject.class.equals(key))) {
			IProject project = (IProject) element;
			if(OfsCore.isOfsProject(project)) {
				return OfsCore.getOfsProjectManager().getOfsProject(project);
			}
		}
		
		if(element instanceof IResource && !(element instanceof IProject) &&
			(IOfsElement.class.equals(key))) {
			IResource resource = (IResource) element;
			IProject project = resource.getProject();
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
			if(ofsProject!=null) {
				if(resource.getParent().equals(project)) {
					// it's a model folder
					return ofsProject.getModelFolder(resource.getName());
				} else {
					// DS-3683: check that we are inside some model folder otherwise, return null
					if(resource.getFullPath().segmentCount()>2) {
						IOfsModelFolder modelFolder = ofsProject.getModelFolder(resource.getFullPath().segment(1));
						if(modelFolder==null) return null;
						// DS-3108: do not accept "foreign" models in a model folder
						if(resource instanceof IFile && !modelFolder.acceptFileExtension(resource.getFileExtension())) return null;
					}

					URI modelURI = ModelURIConverter.createModelURI(resource);
					if(modelURI.fileExtension()==null) {
						// it's a model package
						String modelFolderName = resource.getProjectRelativePath().segment(0);
						IOfsModelFolder modelFolder = ofsProject.getModelFolder(modelFolderName);
						if(modelFolder!=null) {
							IOfsElement ofsElement = modelFolder.getOfsElement(modelURI);
							return ofsElement;
						}
					} else {
						// it's a model resource
						try {
							return ofsProject.getOfsModelResource(modelURI);
						} catch (ModelNotFoundException e) {}
					}
				}
			}
		}
		return null;
	}
}
