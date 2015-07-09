package com.odcgroup.pageflow.compare.internal.viewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.compare.viewer.content.BaseModelContentMergeViewer;

public class PageflowContentMergeViewer extends BaseModelContentMergeViewer {	
	

	/**
	 * @param parent
	 * @param config
	 */
	public PageflowContentMergeViewer(Composite parent, CompareConfiguration config) {
		super(parent, config);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.content.BaseModelContentMergeViewer#createCompareViewerParts(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createCompareViewerParts(Composite composite) {
		leftPart = new PageflowContentMergeViewerPart(this, composite,
				EMFCompareConstants.LEFT);
		rightPart = new PageflowContentMergeViewerPart(this, composite,
				EMFCompareConstants.RIGHT);
		ancestorPart = new PageflowContentMergeViewerPart(this, composite,
				EMFCompareConstants.ANCESTOR);
		
	}

}
