package com.odcgroup.pageflow.compare.internal.viewer;

import org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer;
import org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.compare.viewer.content.part.BaseModelContentMergeTabFolder;

public class PageflowContentMergeViewerPart extends BaseModelContentMergeTabFolder {
	
	/**
	 * @param viewer
	 * @param composite
	 * @param side
	 */
	public PageflowContentMergeViewerPart(ModelContentMergeViewer viewer, Composite composite, int side) {
		super(viewer, composite, side);
	}
	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.content.part.BaseModelContentMergeTabFolder#createViewerTab(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public IModelContentMergeViewerTab createViewerTab(Composite composite) {
		PageflowContentMergeModelPart part = new PageflowContentMergeModelPart(composite, partSide);
		part.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionChanged(event);
			}
		});
		return part;
	}
}
