package com.odcgroup.visualrules.integration.ui.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.visualrules.integration.RulesIntegrationUtils;

import de.visualrules.model.navigator.adapter.IResourceProvider;
import de.visualrules.model.navigator.factory.FactoryUtil;

public class RulesContentProvider implements ITreeContentProvider {
	
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IAdaptable) {
			Object adapter = ((IAdaptable) parentElement).getAdapter(IProject.class);
			if(adapter instanceof IProject) {
				IProject project = (IProject) adapter;
				IFile rulesFile = RulesIntegrationUtils.getRulesFile(project);
				if(rulesFile!=null) {
//					return new Object[] {rulesFile.getParent()};
				      AdapterFactory factory =  FactoryUtil.getFactory("com.odcgroup.workbench.ui.navigator");
				      IResourceProvider adapt = (IResourceProvider) factory.adapt(rulesFile, IResourceProvider.class);
				      if (adapt != null)
				      {
				         return new Object[] {adapt};
				      }
				}
			}
		}
		return new Object[]{};
	}

	public Object getParent(Object element) {
		if(element instanceof IProject) {
			IProject project = (IProject) element;
			return project.getParent();
		}
		if(element instanceof IAdaptable) {
			Object adapter = ((IAdaptable) element).getAdapter(IResourceProvider.class);
			if(adapter instanceof IResourceProvider) {
				IResourceProvider provider = (IResourceProvider) adapter;
				IFile file = provider.getFile();
				if("vrmodel".equals(file.getFileExtension())) {
					return file.getProject();
				}
			}
		}
		if(element instanceof IFile) {
			IFile file = (IFile) element;
			return file.getProject();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length>0;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
