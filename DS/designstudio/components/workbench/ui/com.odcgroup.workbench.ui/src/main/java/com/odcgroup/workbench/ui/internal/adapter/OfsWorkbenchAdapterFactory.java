package com.odcgroup.workbench.ui.internal.adapter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.IWorkbenchAdapter2;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

public class OfsWorkbenchAdapterFactory implements IAdapterFactory {

	private static Class[] PROPERTIES = new Class[] {
		IWorkbenchAdapter.class,
		IWorkbenchAdapter2.class
	};

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if ((IWorkbenchAdapter.class.equals(adapterType) || IWorkbenchAdapter2.class.equals(adapterType))
			&& (adaptableObject instanceof IProject || adaptableObject instanceof IOfsElement)) {
			return new OfsWorkbenchAdapter();
		}
		return null;
	}

	public Class[] getAdapterList() {
		return PROPERTIES.clone();
	}
}
