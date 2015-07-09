/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

/**
 * @generated
 */
public abstract class PageflowAbstractNavigatorItem extends PlatformObject {

	/**
	 * @generated
	 */
	static {
		final Class[] supportedTypes = new Class[] { ITabbedPropertySheetPageContributor.class };
		final ITabbedPropertySheetPageContributor propertySheetPageContributor = new ITabbedPropertySheetPageContributor() {
			public String getContributorId() {
				return "com.odcgroup.pageflow.editor.diagram"; //$NON-NLS-1$
			}
		};
		Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {

			public Object getAdapter(Object adaptableObject, Class adapterType) {
				if (adaptableObject instanceof com.odcgroup.pageflow.editor.diagram.navigator.PageflowAbstractNavigatorItem
						&& adapterType == ITabbedPropertySheetPageContributor.class) {
					return propertySheetPageContributor;
				}
				return null;
			}

			public Class[] getAdapterList() {
				return supportedTypes;
			}
		}, com.odcgroup.pageflow.editor.diagram.navigator.PageflowAbstractNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private Object myParent;

	/**
	 * @generated
	 */
	protected PageflowAbstractNavigatorItem(Object parent) {
		myParent = parent;
	}

	/**
	 * @generated
	 */
	public Object getParent() {
		return myParent;
	}

}
