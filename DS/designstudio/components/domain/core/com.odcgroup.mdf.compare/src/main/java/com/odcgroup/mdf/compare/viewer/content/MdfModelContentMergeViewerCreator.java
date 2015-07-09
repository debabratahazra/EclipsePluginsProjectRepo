package com.odcgroup.mdf.compare.viewer.content;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class MdfModelContentMergeViewerCreator  implements IViewerCreator {
	/**
	 * {@inheritDoc}
	 */
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new MdfModelContentMergeViewer(parent, config);
	}
}

