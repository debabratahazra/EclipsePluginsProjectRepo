package com.odcgroup.pageflow.migration.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.migration.MetaModelVersionHandler;
import com.odcgroup.workbench.migration.MigrationException;

public class PageflowMetaModelVersionHandler extends MetaModelVersionHandler {

	@Override
	public void setCurrentMetaModelVersion(Resource resource)
			throws MigrationException {
		for(EObject eObj : resource.getContents()) {
			if (eObj instanceof Pageflow) {
				Pageflow model = (Pageflow) eObj;
				model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion(modelType));
			}
		}
	}

	@Override
	public String getMetaModelVersion(IOfsModelResource modelResource) throws CoreException {
		String metamodelVersion = tryXML(modelResource);
		if(metamodelVersion==null) metamodelVersion = "1.30.6"; // this is the version before metamodel version has been introduced
		return metamodelVersion;
	}

}
