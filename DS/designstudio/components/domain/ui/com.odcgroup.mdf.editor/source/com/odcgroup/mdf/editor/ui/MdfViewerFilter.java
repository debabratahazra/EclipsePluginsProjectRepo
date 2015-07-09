package com.odcgroup.mdf.editor.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * ViewerFilter extension, that check against a type of project
 * 
 * @author pkk
 */
public abstract class MdfViewerFilter extends ViewerFilter {
	
	/**
	 * check whether this filter is applicable for a given project
	 * 
	 * @param project
	 * @return
	 */
	public abstract boolean isApplicable(IProject project);

}
