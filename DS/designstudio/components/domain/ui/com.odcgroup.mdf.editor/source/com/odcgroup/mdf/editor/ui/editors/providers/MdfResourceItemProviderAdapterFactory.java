package com.odcgroup.mdf.editor.ui.editors.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceSetItemProvider;

public class MdfResourceItemProviderAdapterFactory extends
		ResourceItemProviderAdapterFactory {

	public MdfResourceItemProviderAdapterFactory() {
	}

	@Override
	public Adapter createResourceSetAdapter() {
		return new ResourceSetItemProvider(this) {
			@Override
			public Collection getChildren(Object object) {
				ResourceSet set = (ResourceSet) object;
				List children = new ArrayList();

				if (set.getResources().size() > 1) {
				//	children.add(new ImportedDomainsListItempProvider(set));
				}
				children.addAll(set.getResources().get(0).getContents());

				return children;
			}
		};
	}

}
