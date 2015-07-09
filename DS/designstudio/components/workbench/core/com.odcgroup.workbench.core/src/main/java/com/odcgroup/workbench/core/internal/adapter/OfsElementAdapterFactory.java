package com.odcgroup.workbench.core.internal.adapter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

public class OfsElementAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	private static Class[] PROPERTIES = new Class[] {
		IResource.class,
		IFile.class,
		IProject.class
	};

	@SuppressWarnings("unchecked")
	public Object getAdapter(Object adaptableObject, Class adapterType) {

		IOfsElement element = (IOfsElement) adaptableObject;

		if (IFile.class.equals(adapterType) && element instanceof IOfsModelResource) {
			IFile res = null;
			if(element.getResource()!=null) {
				return element.getResource();
			}
		}
		
		if (IResource.class.equals(adapterType)) {
			IResource res = null;
			if(element.getResource()!=null) {
				res = (IResource) element.getResource();
			} else {
				if(element instanceof IOfsModelPackage) {
					IOfsModelPackage pkg = (IOfsModelPackage) element;
					IProject project = pkg.getOfsProject().getProject();
					IPath path = new Path(pkg.getModelFolder().getName() + pkg.getURI().path());
					res = project.getFolder(path);
				}
			}
			return res;
		}
		if (IProject.class.equals(adapterType) && (element instanceof IOfsProject)) {
			return element.getResource();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		return PROPERTIES.clone();
	}
}
