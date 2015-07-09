package com.odcgroup.server.ui.views;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;

public class ServerTreeContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List<?>) {
			return ((List<?>)inputElement).toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IDSServer) {
			IDSServer server = (IDSServer) parentElement;
			List<IDSProject> projects = server.getDsProjects();
			return projects.toArray();
		} else {
			return null;
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IDSProject) {
			return ((IDSProject) element).getServer();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IDSServer 
				&& !((IDSServer) element).getDsProjects().isEmpty()) {
			return true;
		}
		return false;
	}

}
