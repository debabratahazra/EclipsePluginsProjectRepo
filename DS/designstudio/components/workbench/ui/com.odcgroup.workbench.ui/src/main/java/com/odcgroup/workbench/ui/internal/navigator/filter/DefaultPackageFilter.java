package com.odcgroup.workbench.ui.internal.navigator.filter;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DefaultPackageFilter extends ViewerFilter {

	/*
	 * @see ViewerFilter
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IPackageFragment) {
			IPackageFragment pkg= (IPackageFragment)element;
			try {
				if (pkg.isDefaultPackage())
					return pkg.hasChildren();
			} catch (JavaModelException e) {
				return false;
			}
		}

		return true;
	}
}
