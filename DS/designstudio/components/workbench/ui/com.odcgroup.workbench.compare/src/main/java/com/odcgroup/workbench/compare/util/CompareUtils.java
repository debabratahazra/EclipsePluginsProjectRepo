package com.odcgroup.workbench.compare.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.compare.CompareUI;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.util.EMFCompareMap;
import org.eclipse.emf.compare.util.ModelUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ui.IWorkbenchPage;

public class CompareUtils {
	

	/**
	 * @param contents
	 * @param name
	 * @param resourceSet
	 * @return
	 * @throws IOException
	 */
	static public EObject fetchModelRoot(InputStream contents, String name,
			ResourceSet resourceSet) throws IOException {
		final Resource modelResource = ModelUtils.createResource(URI.createURI(name), resourceSet);
		final Map<String, String> options = new EMFCompareMap<String, String>();
		modelResource.load(contents, options);
		return fetchModelRoot(modelResource);
	}

	
	/**
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	static public EObject fetchModelRoot(Resource resource) throws IOException {
		if(!resource.isLoaded()) {
			resource.load(null);
		}
		EList<EObject> eRootModels = resource.getContents();
		if(eRootModels.size()==1) return eRootModels.get(0);
		if(eRootModels.size()==2) return eRootModels.get(0);
		
		return null;
	}
	
	/**
	 * @return
	 */
	static public TransactionalEditingDomain createEditingDomain() {
		TransactionalEditingDomain editingDomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain();
		return editingDomain;
	}
	
	/**
	 * @param editorInput
	 * @param page
	 */
	static public void openCompareEditor(OfsModelCompareEditorInput editorInput, IWorkbenchPage page) {
		CompareUI.openCompareEditorOnPage(editorInput, page);
	}

}
