package com.odcgroup.page.compare.viewer;

import org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer;
import org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.compare.viewer.content.part.BaseModelContentMergeTabFolder;

/**
 * This is the ContentMergeViewerPart for Fragments, Modules and Pages.
 * 
 * @author Gary Hayes
 */
public class PageContentMergeViewerPart extends BaseModelContentMergeTabFolder {

	/**
	 * @param viewer
	 * @param composite
	 * @param side
	 */
	public PageContentMergeViewerPart(ModelContentMergeViewer viewer, Composite composite, int side) {
		super(viewer, composite, side);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.content.part.BaseModelContentMergeTabFolder#createViewerTab(org.eclipse.swt.widgets.Composite)
	 */
	public IModelContentMergeViewerTab createViewerTab(Composite composite) {
		PageContentMergeModelPart part = new PageContentMergeModelPart(composite, partSide);
		part.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionChanged(event);
			}
		});
		return part;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.viewer.content.part.BaseModelContentMergeTabFolder#createPropertiesPart(org.eclipse.swt.widgets.Composite)
	 */
	protected IModelContentMergeViewerTab createPropertiesPart(Composite composite) {
		PageContentMergePropertyPart propertiesPart = new PageContentMergePropertyPart(composite,
				SWT.NONE, partSide);

		propertiesPart.setContentProvider(new PageCompareStructuredContentProvider(partSide));
		propertiesPart.getTable().setHeaderVisible(true);

		propertiesPart.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionChanged(event);
			}
		});

		return propertiesPart;
	}
	
}
