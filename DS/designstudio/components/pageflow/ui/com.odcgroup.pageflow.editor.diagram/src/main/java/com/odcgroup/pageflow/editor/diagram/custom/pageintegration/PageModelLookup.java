package com.odcgroup.pageflow.editor.diagram.custom.pageintegration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.EClassifierMatcher;
import com.odcgroup.workbench.core.repository.ModelMatcher;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;

/**
 * @author pkk
 *
 */
public class PageModelLookup extends ModelResourceLookup {
	/*
	 *  OCS-27615 (Accept page only in viewstate and deny module)
	 */
	public static final String[] EXTENSIONS = {PageConstants.PAGE_FILE_EXTENSION};
	//  PageConstants.MODULE_FILE_EXTENSION
	//	PageConstants.PAGE_FRAGMENT_FILE_EXTENSION
	
	private static final ModelMatcher PAGE_MATCHER = new EClassifierMatcher(ModelPackage.eINSTANCE.getModel());

	/**
	 * @param repository
	 */
	public PageModelLookup(IOfsProject repository) {
		super(repository, EXTENSIONS);
	}
	
	/**
	 * @return
	 */
	public List<Model> getAllPages() {
    	Set<IOfsModelResource> mResources = getAllOfsModelResources();
    	List<Model> pagemodels = new ArrayList<Model>();
    	try {
	    	for (IOfsModelResource mResource : mResources) {    		
	    		List<EObject> contents = mResource.getEMFModel();
	    		if (!contents.isEmpty() && PAGE_MATCHER.match(contents.get(0))){
					pagemodels.add((Model) contents.get(0)); 
				}
			}
    	} catch (Exception e) {
			PageflowDiagramEditorPlugin.getInstance().logError(e.getLocalizedMessage(), e);
		}
        return pagemodels;
    }

}
