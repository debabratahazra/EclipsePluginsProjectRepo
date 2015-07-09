package com.odcgroup.pageflow.editor.diagram.custom.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class PageflowViewFilter extends ViewerFilter {
	
	private IFile current;
	
	public PageflowViewFilter(IFile current){
		this.current = current;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {		
		if (parentElement instanceof IWorkspace){
			return true;
		} else if (element instanceof IFolder){
			IFolder folder = (IFolder) element;
			if (folder.getName().equals("pageflow")
					|| (folder.getParent() != null && folder.getParent().getName().equals("pageflow"))) {
				return true;
			}
			return false;
		}else if (element instanceof IFile) {
			IFile file = (IFile) element;
			if (file.equals(current)){
				return false;
			}
			return "pageflow".equals(file.getFileExtension());
		} else {
			return false;
		}

	}

}
