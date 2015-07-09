package com.odcgroup.mdf.editor.ui.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.mdf.editor.ui.MdfViewerFilter;
import com.odcgroup.mdf.metamodel.MdfDataset;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class DatasetFilter extends MdfViewerFilter {

    /**
     * Constructor for EnumFilter
     */
    public DatasetFilter() {
        super();
    }

    /**
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public boolean select(Viewer viewer, Object parent, Object item) {
        return !(item instanceof MdfDataset);
    }
	
	public boolean isApplicable(IProject project) {
		return true;
	}

}
