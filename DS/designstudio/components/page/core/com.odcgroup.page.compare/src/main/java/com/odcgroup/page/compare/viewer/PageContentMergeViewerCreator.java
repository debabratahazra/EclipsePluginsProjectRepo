package com.odcgroup.page.compare.viewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * This is the IViewerCreator for the content of the Page Designer.
 * 
 * @author Gary Hayes
 */
public class PageContentMergeViewerCreator implements IViewerCreator {

	/**
	 * Creates a new PageContentMergeViewerCreator.
	 */
	public PageContentMergeViewerCreator() {
	}

	/**
	 * Creates the viewer.
	 * 
	 * @param parent
	 *            The parent Widget
	 * @param config
	 *            The configuration
	 * @return Viewer The viewer
	 */
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new PageContentMergeViewer(parent, config);
	}

}
