package com.odcgroup.page.compare.viewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 *
 */
public class PageStructureMergeViewerCreator implements IViewerCreator {

	public Viewer createViewer(Composite parent, CompareConfiguration config) {
        return new PageStructureMergeViewer(parent, config);
	}

}
