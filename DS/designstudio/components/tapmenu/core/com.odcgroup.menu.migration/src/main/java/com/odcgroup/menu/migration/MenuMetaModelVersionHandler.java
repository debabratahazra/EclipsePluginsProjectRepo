package com.odcgroup.menu.migration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.migration.MetaModelVersionHandler;
import com.odcgroup.workbench.migration.MigrationException;

public class MenuMetaModelVersionHandler extends MetaModelVersionHandler {

	@Override
	public void setCurrentMetaModelVersion(Resource resource)
			throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			MenuRoot model = (MenuRoot) contents.get(0);
			model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion(modelType));
		} else {
			throw new MigrationException("Resource '" + resource.getURI()
					+ "' does not contain any content!");
		}
	}

	@Override
	public String getMetaModelVersion(IOfsModelResource modelResource) throws CoreException {
		String metamodelVersion = tryDSL(modelResource);
		if(metamodelVersion==null) metamodelVersion = "1.30.6"; // this is the version before metamodel version has been introduced
		return metamodelVersion;
	}
}
