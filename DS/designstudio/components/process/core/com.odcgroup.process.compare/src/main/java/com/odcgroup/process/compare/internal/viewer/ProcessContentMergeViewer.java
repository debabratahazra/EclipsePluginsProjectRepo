package com.odcgroup.process.compare.internal.viewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.compare.viewer.content.BaseModelContentMergeViewer;

public class ProcessContentMergeViewer extends BaseModelContentMergeViewer {

	/**
	 * @param parent
	 * @param config
	 */
	public ProcessContentMergeViewer(Composite parent, CompareConfiguration config) {
		super(parent, config);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.content.BaseModelContentMergeViewer#createCompareViewerParts(org.eclipse.swt.widgets.Composite)
	 */
	protected void createCompareViewerParts(Composite composite) {
		leftPart = new ProcessContentMergeViewerPart(this, composite,
				EMFCompareConstants.LEFT);
		rightPart = new ProcessContentMergeViewerPart(this, composite,
				EMFCompareConstants.RIGHT);
		ancestorPart = new ProcessContentMergeViewerPart(this, composite,
				EMFCompareConstants.ANCESTOR);		
	}

}
