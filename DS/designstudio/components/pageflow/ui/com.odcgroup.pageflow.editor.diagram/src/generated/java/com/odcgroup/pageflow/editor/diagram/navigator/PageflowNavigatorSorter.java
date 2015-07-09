/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry;

/**
 * @generated
 */
public class PageflowNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 3003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof PageflowNavigatorItem) {
			PageflowNavigatorItem item = (PageflowNavigatorItem) element;
			return PageflowVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
