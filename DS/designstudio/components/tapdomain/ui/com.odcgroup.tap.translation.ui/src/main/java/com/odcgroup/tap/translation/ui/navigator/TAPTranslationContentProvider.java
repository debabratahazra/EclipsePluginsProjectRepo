package com.odcgroup.tap.translation.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.translation.ui.navigator.TranslationNode;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * This class provides the translations file as a root node in an Odyssey model
 * project in between the model folders.
 * 
 * @author atr
 * 
 */
public class TAPTranslationContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] children = {};
		if (parentElement instanceof IAdaptable) {
			Object adapter = ((IAdaptable) parentElement).getAdapter(IProject.class);
			if (adapter instanceof IProject) {
				IOfsProject ofsProject = OfsCore.getOfsProject((IProject) adapter);
				children = new Object[] {new TranslationNode(ofsProject)};
			}
		}
		return children;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return new Object[0];
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
