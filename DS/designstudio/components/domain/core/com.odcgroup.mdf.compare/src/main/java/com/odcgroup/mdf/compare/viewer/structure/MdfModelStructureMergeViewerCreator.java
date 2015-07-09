package com.odcgroup.mdf.compare.viewer.structure;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author pkk
 *
 */
public class MdfModelStructureMergeViewerCreator implements IViewerCreator {
	/**
	 * {@inheritDoc}
	 */
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new MdfStructureMergeViewer(parent, config);
	}
}
