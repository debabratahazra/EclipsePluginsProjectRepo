package com.odcgroup.menu.model.resource;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;

/**
 * @author pkk
 *
 */
public class MenuResource extends AbstractDSLResource {

	@Override
	protected void preProcessModelBeforeSaving(EObject rootObject) {
		if (rootObject instanceof MenuRoot) {
			MenuRoot root = (MenuRoot) rootObject;
			root.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("menu"));
		}

	}

	@Override
	protected void postProcessModelAfterLoading(EObject rootObject) {
		//
	}
	
	@Override
	protected void postProcessModelAfterSaving(EObject rootObject) {
		//
	}

}
