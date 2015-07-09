package com.odcgroup.process.compare.internal.viewer;

import org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer;
import org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.compare.viewer.content.part.BaseModelContentMergeTabFolder;

public class ProcessContentMergeViewerPart extends BaseModelContentMergeTabFolder {
	
	/**
	 * @param viewer
	 * @param composite
	 * @param side
	 */
	public ProcessContentMergeViewerPart(ModelContentMergeViewer viewer, Composite composite, int side) {
		super(viewer, composite, side);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.content.part.BaseModelContentMergeTabFolder#createViewerTab(org.eclipse.swt.widgets.Composite)
	 */
	public IModelContentMergeViewerTab createViewerTab(Composite composite) {
		ProcessContentMergeModelPart part = new ProcessContentMergeModelPart(composite, partSide);
		part.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionChanged(event);
			}
		});
		return part;
	
	}
}
