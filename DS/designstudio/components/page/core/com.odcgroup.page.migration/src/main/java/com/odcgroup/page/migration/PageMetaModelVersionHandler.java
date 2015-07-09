package com.odcgroup.page.migration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.model.Model;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.migration.MetaModelVersionHandler;
import com.odcgroup.workbench.migration.MigrationException;

public class PageMetaModelVersionHandler extends MetaModelVersionHandler {

	@Override
	public void setCurrentMetaModelVersion(Resource resource)
			throws MigrationException {

		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Model model = (Model) contents.get(0);
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
