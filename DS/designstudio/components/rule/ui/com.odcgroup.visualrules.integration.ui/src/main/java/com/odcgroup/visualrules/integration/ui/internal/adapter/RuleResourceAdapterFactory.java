package com.odcgroup.visualrules.integration.ui.internal.adapter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.workbench.core.IOfsElement;

import de.visualrules.model.navigator.adapter.IResourceProvider;

public class RuleResourceAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if(adaptableObject instanceof IResourceProvider && adapterType==IOfsElement.class) {
			IResourceProvider resourceProvider = (IResourceProvider) adaptableObject;
			IProject project = resourceProvider.getFile().getProject();
			IFile ruleTranslationFile = RulesIntegrationUtils.getRulesTranslationFile(project);
			if(ruleTranslationFile!=null) {
				return ruleTranslationFile.getAdapter(IOfsElement.class);
			}
		} 
		else if(adaptableObject instanceof IResourceProvider && adapterType==IResource.class) {
			IResourceProvider resourceProvider = (IResourceProvider) adaptableObject;
			IProject project = resourceProvider.getFile().getProject();
			IFile ruleTranslationFile = RulesIntegrationUtils.getRulesTranslationFile(project);
			if(ruleTranslationFile!=null) {
				return ruleTranslationFile.getAdapter(IResource.class);
		}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Class[] getAdapterList() {
		return new Class[] { IOfsElement.class, IResource.class};
	}

}
