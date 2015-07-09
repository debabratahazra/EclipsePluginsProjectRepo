package com.odcgroup.pageflow.compare.internal.viewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.compare.viewer.content.BaseModelStructureMergeViewer;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class PageflowStructureMergeViewer extends BaseModelStructureMergeViewer {

	/**
	 * @param parent
	 * @param compareConfiguration
	 */
	public PageflowStructureMergeViewer(Composite parent, CompareConfiguration compareConfiguration) {
		super(parent, compareConfiguration);
		setLabelProvider(new PageflowStructureViewerLabelProvider());
	}

}
