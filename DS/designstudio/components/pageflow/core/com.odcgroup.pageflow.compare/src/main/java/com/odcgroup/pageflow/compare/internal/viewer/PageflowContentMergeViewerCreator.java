package com.odcgroup.pageflow.compare.internal.viewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

public class PageflowContentMergeViewerCreator implements IViewerCreator {

	public Viewer createViewer(Composite parent, CompareConfiguration config)
    {
        return new PageflowContentMergeViewer(parent, config);
    }

}
