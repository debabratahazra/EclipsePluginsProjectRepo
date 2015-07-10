package samplecode.source.view;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SampleContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		ArrayList<IResource> children = new ArrayList<IResource>();

		if (inputElement instanceof IWorkspaceRoot) {
			children.addAll(Arrays.asList(((IWorkspaceRoot) inputElement)
					.getProjects()));
		}

		return children.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		ArrayList<IResource> children = new ArrayList<IResource>();

		if (parentElement instanceof IContainer) {
			try {
				if (((IContainer) parentElement).members().length > 0) {
					children.addAll(Arrays.asList(((IContainer) parentElement)
							.members()));
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IContainer) {
			try {
				if (((IContainer) element).members().length > 0) {
					return true;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
