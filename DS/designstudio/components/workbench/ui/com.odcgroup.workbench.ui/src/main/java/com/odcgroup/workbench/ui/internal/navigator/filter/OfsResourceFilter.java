package com.odcgroup.workbench.ui.internal.navigator.filter;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jdt.internal.ui.packageview.ClassPathContainer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.odcgroup.workbench.core.OfsCore;

public class OfsResourceFilter extends ViewerFilter {

	public OfsResourceFilter() {
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		// only show Java or Odyssey projects
		if(element instanceof IAdaptable) {
			IAdaptable obj = (IAdaptable) element;
			IProject project = (IProject) obj.getAdapter(IProject.class);
			if(project!=null) {
				try {
					if(project.hasNature(JavaCore.NATURE_ID) || 
							project.hasNature(OfsCore.NATURE_ID) ||
							project.hasNature("com.odcgroup.ocs.packager") ||
							project.hasNature("com.odcgroup.t24.server.t24server")) {
						return true;
					} else {
						return false;
					}
				} catch (CoreException e) {}
			}
		}

		// ofsprojects have java nature, hide the default src folder
		if (element instanceof PackageFragmentRoot) {
			PackageFragmentRoot root = (PackageFragmentRoot) element;
			IResource resource = root.getResource();
			if (resource.getName().equals("src")) {
				return false;
			}
		}

		// only filter on OFS projects
		if(element instanceof IAdaptable) {
			IAdaptable obj = (IAdaptable) element;
			IResource resource = (IResource) obj.getAdapter(IResource.class);
			if(resource!=null && !OfsCore.isOfsProject(resource.getProject())) {
				return true;
			}
		}

		// filter all model folders (as IOfsModelFolders will be added instead of the IFolders)
		if(element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			String[] modelNames = OfsCore.getRegisteredModelNames();
			if(ArrayUtils.contains(modelNames, folder.getName())) {
				return false;
			}
		}

		if(element instanceof IResource) {
			IResource folder = (IResource) element;
			if(folder.getName().equals("src")) {
				return false;
			}
		}
		
		// filter bin directory
		if(element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			if(folder.getName().equals("bin")) {
				return false;
			}
		}

		// filter META-INF folder
		if(element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			if(folder.getName().equals("META-INF")) {
				return false;
			}
		}

		// filter build.properties file
		if(element instanceof IFile) {
			IFile file = (IFile) element;
			if(file.getName().equals("build.properties")) {
				return false;
			}
		}

		// filter classpath library entries
		if(element instanceof ClassPathContainer) {
			return false;
		}
		
		// filter Jar entries
		if(element instanceof JarPackageFragmentRoot) {
			return false;
		}
		
		// filter archived folder (which holds remaining data like migrated translations)
		if(element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			if(folder.getName().equals("archived")) {
				return false;
			}
		}
		
		// filter the (old) translation folder (which exists until we decided to put the translations directly into the models)
		if(element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			if(folder.getName().equals("translation")) {
				return false;
			}
		}
		
		return true;
	}

}
