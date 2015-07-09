package com.odcgroup.t24.menu.editor.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.resource.ResourceItemProvider;

import com.odcgroup.t24.menu.menu.MenuRoot;

/**
 * @author pkk
 */
public class MenuResourceItemProvider extends ResourceItemProvider {

	public MenuResourceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.resource.ResourceItemProvider#getChildren
	 * (java.lang.Object)
	 */
	public Collection<?> getChildren(Object object) {
		List<EObject> contents = ((Resource) object).getContents();
		Collection<Object> result = new ArrayList<Object>(contents.size());
		for (Object o : contents) {
			if (!AdapterFactoryEditingDomain.isControlled(o)) {
				// concerned only with the root item
				if (o instanceof MenuRoot) {
					result.add(((MenuRoot) o).getRootItem());
				}
			}
		}
		return result;
	}

}
