package com.odcgroup.mdf.editor.ui.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.mdf.editor.ui.MdfViewerFilter;
import com.odcgroup.mdf.metamodel.MdfClass;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class ClassFilter extends MdfViewerFilter {

    /**
     * Constructor for ClassFilter
     */
    public ClassFilter() {
        super();
    }

    /**
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parent, Object item) {
        return !(item instanceof MdfClass);
    }
	
	public boolean isApplicable(IProject project) {
		return true;
	}

}
