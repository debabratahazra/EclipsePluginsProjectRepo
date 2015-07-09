/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;

/**
 * @generated
 */
public class ProcessNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 5003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof ProcessNavigatorItem) {
			ProcessNavigatorItem item = (ProcessNavigatorItem) element;
			return ProcessVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
