package com.odcgroup.mdf.editor.ui.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.mdf.editor.ui.MdfViewerFilter;
import com.odcgroup.mdf.metamodel.MdfClass;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class MainClassFilter extends MdfViewerFilter {

    /**
     * Constructor for ClassFilter
     */
    public MainClassFilter() {
        super();
    }

    @Override
    public boolean select(Viewer viewer, Object parent, Object item) {
    	if (item instanceof MdfClass) {
    		MdfClass clazz = (MdfClass) item;
    		return clazz.getPrimaryKeys().size() != 0;
    	}
    	return true;
    }

	@Override
	public boolean isApplicable(IProject project) {
		return true;
	}

}
