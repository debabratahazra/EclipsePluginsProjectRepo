package com.odcgroup.page.compare.viewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.emf.compare.ui.util.EMFCompareConstants;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.compare.viewer.content.BaseModelContentMergeViewer;


/**
 * The viewer for Fragments, Modules and Pages.
 * 
 * @author Gary Hayes
 */
public class PageContentMergeViewer extends BaseModelContentMergeViewer {

	/**
	 * @param parent
	 * @param config
	 */
	public PageContentMergeViewer(Composite parent, CompareConfiguration config) {
		super(parent, config);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.content.BaseModelContentMergeViewer#createCompareViewerParts(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createCompareViewerParts(Composite composite) {
        leftPart = new PageContentMergeViewerPart(this, composite, EMFCompareConstants.LEFT);
        rightPart = new PageContentMergeViewerPart(this, composite, EMFCompareConstants.RIGHT);
        ancestorPart = new PageContentMergeViewerPart(this, composite, EMFCompareConstants.ANCESTOR);		
	}
	
}