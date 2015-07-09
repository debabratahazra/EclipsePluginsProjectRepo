package com.odcgroup.workbench.generation.ui.internal.filter;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.generation.GenerationCore;

public class PackagingResourceFilter extends ViewerFilter {


	public PackagingResourceFilter() {
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		// only filter on OFS projects
		if(element instanceof IAdaptable) {
			IAdaptable obj = (IAdaptable) element;
			IResource resource = (IResource) obj.getAdapter(IResource.class);
			if(resource!=null && 
					(!OfsCore.isOfsProject(resource.getProject()) || GenerationCore.isTechnical(resource.getProject()))) {
				return true;
			}
		}
		
		// filter packaging directory
		if(element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			if(folder.getParent() instanceof IProject) {
				if(folder.getName().equals(GenerationCore.PACKAGING_RESOURCE_FOLDER)) {
					return false;
				}
			}
		}		
		return true;
	}

}
