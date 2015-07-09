package com.odcgroup.menu.editor.provider;

import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * @author pkk
 *
 */
public class MenuAdapterFactoryContentProvider extends
		AdapterFactoryContentProvider {

	private static final Class<?> IStructuredItemContentProviderClass = IStructuredItemContentProvider.class;
	private static final Class<?> ITreeItemContentProviderClass = ITreeItemContentProvider.class;

	/**
	 * @param adapterFactory
	 */
	public MenuAdapterFactoryContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This implements
	 * {@link org.eclipse.jface.viewers.IStructuredContentProvider}.getElements
	 * to forward the call to an object that implements
	 * {@link org.eclipse.emf.edit.provider.IStructuredItemContentProvider#getElements
	 * IStructuredItemContentProvider.getElements}.
	 */
	public Object[] getElements(Object object) {
		// Get the adapter from the factory.
		//
		IStructuredItemContentProvider structuredItemContentProvider = (IStructuredItemContentProvider) adapterFactory
				.adapt(object, IStructuredItemContentProviderClass);

		// Either delegate the call or return nothing.
		//
		return (structuredItemContentProvider != null ? structuredItemContentProvider
				.getElements(object) : Collections.EMPTY_LIST).toArray();
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ITreeContentProvider}
	 * .getChildren to forward the call to an object that implements
	 * {@link org.eclipse.emf.edit.provider.ITreeItemContentProvider#getChildren
	 * ITreeItemContentProvider.getChildren}.
	 */
	public Object[] getChildren(Object object) {
		// Get the adapter from the factory.
		//
		ITreeItemContentProvider treeItemContentProvider = (ITreeItemContentProvider) adapterFactory
				.adapt(object, ITreeItemContentProviderClass);

		// Either delegate the call or return nothing.
		//
		return (treeItemContentProvider != null ? treeItemContentProvider
				.getChildren(object) : Collections.EMPTY_LIST).toArray();
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ITreeContentProvider}
	 * .hasChildren to forward the call to an object that implements
	 * {@link org.eclipse.emf.edit.provider.ITreeItemContentProvider#hasChildren
	 * ITreeItemContentProvider.hasChildren}.
	 */
	public boolean hasChildren(Object object) {
		// Get the adapter from the factory.
		//
		ITreeItemContentProvider treeItemContentProvider = (ITreeItemContentProvider) adapterFactory
				.adapt(object, ITreeItemContentProviderClass);

		// Either delegate the call or return nothing.
		//
		return treeItemContentProvider != null
				&& treeItemContentProvider.hasChildren(object);
	}

}
